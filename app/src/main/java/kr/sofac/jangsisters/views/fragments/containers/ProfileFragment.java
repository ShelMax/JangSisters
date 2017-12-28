package kr.sofac.jangsisters.views.fragments.containers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.LoginActivity;
import kr.sofac.jangsisters.models.TabManager;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.utils.AppPreference;
import kr.sofac.jangsisters.utils.UserWrapper;
import kr.sofac.jangsisters.views.customview.ButtonLight;
import kr.sofac.jangsisters.views.fragments.BaseFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.FollowersFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.GridViewPostFragment;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.user_image) ImageView userImage;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.user_balance) TextView userBalance;
    @BindView(R.id.message) ButtonLight message;
    @BindView(R.id.follow) ButtonLight follow;
    @BindView(R.id.balance) ButtonLight balance;

    private FragmentManager fragmentManager;
    private int userID;
    private boolean myProfile;
    private GridViewPostFragment myPosts;
    private FollowersFragment followers;
    private FollowersFragment following;
    private GridViewPostFragment bookmarks;
    private AppPreference appPreference;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        appPreference = new AppPreference(getActivity());
        userID = getArguments().getInt(getString(R.string.userID));
        myProfile = getArguments().getBoolean(getString(R.string.myProfile));
        if(myProfile){
            follow.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
            user = appPreference.getUser();
        }
        else{
            balance.setVisibility(View.GONE);
            user = UserWrapper.getUserByID(userID);
        }
        Glide.with(this).load(user.getUserImage())
                .apply(new RequestOptions().placeholder(R.drawable.boy))
                .apply(RequestOptions.circleCropTransform())
                .into(userImage);
        username.setText(user.getName());
        if(appPreference.getUser().getId() ==userID)
            myPosts = new GridViewPostFragment();
        followers = new FollowersFragment();
        following = new FollowersFragment();
        bookmarks = new GridViewPostFragment();
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame, new GridViewPostFragment()).commit();

        initTabLayout();
        return view;
    }

    private void initTabLayout() {
        if (appPreference.getUser().getId() == userID) {
            tabLayout.addTab(tabLayout.newTab().setText("My posts"), true);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {
                        case 0:
                            setCurrentFragment(myPosts);
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
                            setCurrentFragment(following);
                            break;
                        case 1:
                            setCurrentFragment(following);
                            break;
                        case 2:
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
        }
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("Followers"));
        tabLayout.addTab(tabLayout.newTab().setText("Bookmarks"));
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(TabManager.getTabManager().getMenuByPosition(4), menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                break;
            case R.id.logout:
                appPreference.clearUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
