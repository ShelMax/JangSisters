package kr.sofac.jangsisters.views.fragments.containers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.LoginActivity;
import kr.sofac.jangsisters.activities.SettingsActivity;
import kr.sofac.jangsisters.config.KeyActionLoading;
import kr.sofac.jangsisters.config.KeyTransferFlag;
import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.config.ServerConfig;
import kr.sofac.jangsisters.models.GlideApp;
import kr.sofac.jangsisters.models.TabManager;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;
import kr.sofac.jangsisters.utils.AppPreference;
import kr.sofac.jangsisters.utils.ProgressBar;
import kr.sofac.jangsisters.views.customview.ButtonLight;
import kr.sofac.jangsisters.views.fragments.BaseFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.FollowersFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.GridViewPostFragment;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.user_image) ImageView userImage;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.description) TextView blogDescription;
    @BindView(R.id.blog_name) TextView blogName;
    @BindView(R.id.user_balance) TextView userBalance;
    @BindView(R.id.message) ButtonLight message;
    @BindView(R.id.follow) ButtonLight follow;
    @BindView(R.id.balance) ButtonLight balance;

    private FragmentManager fragmentManager;
    private int userID; // ID загружаемого юзера, не свой
    private boolean myProfile;

    private GridViewPostFragment posts;
    private FollowersFragment followers;
    private FollowersFragment following;
    private GridViewPostFragment bookmarks;
    private AppPreference appPreference;
    private User user;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        progressBar = new ProgressBar(getActivity());
        appPreference = new AppPreference(getActivity());

        myProfile = getArguments().getBoolean(KeyTransferFlag.IS_MY_PROFILE.toString());
        if(myProfile){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.your_profile);
            follow.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
            user = appPreference.getUser();
            userID = user.getId();
            updateUI();
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.profile);
            balance.setVisibility(View.GONE);
            userID = getArguments().getInt(KeyTransferObj.USER_ID.toString());
            getUser();
        }
        return view;
    }

    private void updateUI() {
        GlideApp.with(this)
                .load(ServerConfig.BASE_URL + ServerConfig.PART_AVATAR +
                user.getAvatar())
                .override(200, 200)
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder).error(R.drawable.avatar_holder))
                .apply(RequestOptions.circleCropTransform())
                .into(userImage);
        username.setText(user.getName());
        if (user.getBlogDescription().isEmpty())
            blogDescription.setVisibility(View.GONE);
        else
            blogDescription.setText(user.getBlogDescription());
        if (user.getBlogName().isEmpty())
            blogName.setVisibility(View.GONE);
        else
            blogName.setText(user.getBlogName());
        if(user.isFollower() !=0){
            follow.setText("Unfollow");
            follow.setBackground(getResources().getDrawable(R.drawable.custom_button_light));
        }
        follow.setOnClickListener(v -> onSubscribe(user.isFollower()));
        initFragments();
        initTabLayout();
    }

    private void onSubscribe(int isSubscribed) {
        progressBar.showView();
        new Connection<SenderContainerDTO>().subscribeToUser(new SenderContainerDTO()
                .setUserID(appPreference.getUser().getId()).setSubscribeID(user.getId()), (isSuccess, answerServerResponse) -> {
            if(isSuccess){
                if(isSubscribed > 0) {
                    follow.setText("Follow");
                    follow.setBackground(getResources().getDrawable(R.drawable.custom_button_light));
                    user.setIsFollower(0);
                }
                else {
                    follow.setText("Unfollow");
                    follow.setBackground(getResources().getDrawable(R.drawable.custom_button_light));
                    user.setIsFollower(1);
                }
            }
            else{
                //TODO HANDLE ERROR
            }
            progressBar.dismissView();
        });
    }

    private void getUser() {
        progressBar.showView();
        new Connection<User>().getUserByID(new SenderContainerDTO()
                .setCurrentUser(appPreference.getUser().getId())
                .setUserID(userID), (isSuccess, answerServerResponse) -> {
            if(isSuccess){
                user = answerServerResponse.getDataTransferObject();
                updateUI();
            }
            else{
                //todo handle error
            }
            progressBar.dismissView();
        });
    }

    private void initFragments() {
        Bundle bundleFollowers = new Bundle();
        Bundle bundleFollowing = new Bundle();
        Bundle bundlePosts = new Bundle();
        Bundle bundleBookmarks = new Bundle();

        bundleFollowers.putSerializable(KeyTransferFlag.IS_SHOWING_FOLLOWERS.toString(), true);
        bundleFollowers.putInt(KeyTransferObj.USER_ID.toString(), userID);
        bundleFollowing.putBoolean(KeyTransferFlag.IS_SHOWING_FOLLOWERS.toString(), false);
        bundleBookmarks.putSerializable(KeyTransferObj.GRID_ACTION.toString(), KeyActionLoading.BOOKMARKS);
        bundleBookmarks.putInt(KeyTransferObj.USER_ID.toString(), userID);
        bundleFollowing.putInt(KeyTransferObj.USER_ID.toString(), userID);
        bundlePosts.putInt(KeyTransferObj.USER_ID.toString(), userID);
        bundlePosts.putSerializable(KeyTransferObj.GRID_ACTION.toString(), KeyActionLoading.USER_POSTS);

        followers = new FollowersFragment();
        following = new FollowersFragment();
        posts = new GridViewPostFragment();

        followers.setArguments(bundleFollowers);
        following.setArguments(bundleFollowing);
        posts.setArguments(bundlePosts);

        bookmarks = new GridViewPostFragment();
        bookmarks.setArguments(bundleBookmarks);
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame, posts).commit();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.posts));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.following));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.followers));
        if (myProfile) {
            tabLayout.addTab(tabLayout.newTab().setText(R.string.bookmarks));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0:
                            setCurrentFragment(posts);
                            break;
                        case 1:
                            setCurrentFragment(following);
                            break;
                        case 2:
                            setCurrentFragment(followers);
                            break;
                        case 3:
                            setCurrentFragment(bookmarks);
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } else {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0:
                            setCurrentFragment(posts);
                            break;
                        case 1:
                            setCurrentFragment(followers);
                            break;
                        case 2:
                            setCurrentFragment(following);
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }

    private void setCurrentFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!myProfile) {
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.settings).setVisible(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(TabManager.getTabManager().getMenuByPosition(4), menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                getActivity().overridePendingTransition(R.anim.forward_start, R.anim.forward_finish);
                break;
            case R.id.logout:
                appPreference.clearUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
