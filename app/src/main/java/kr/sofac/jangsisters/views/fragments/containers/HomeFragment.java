package kr.sofac.jangsisters.views.fragments.containers;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.DetailPostActivity;
import kr.sofac.jangsisters.activities.UserActivity;
import kr.sofac.jangsisters.config.KeyTransferFlag;
import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.TabManager;
import kr.sofac.jangsisters.models.callback.PostCallback;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;
import kr.sofac.jangsisters.utils.AppPreference;
import kr.sofac.jangsisters.utils.ProgressBar;
import kr.sofac.jangsisters.views.adapters.PostAdapter;
import kr.sofac.jangsisters.views.adapters.PostIngredientsAdapter;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

public class HomeFragment extends BaseFragment {

    // TODO сообщение если нет новостей, подписчиков и т.д. ()

    // TODO цыфры в кружок ()

    @BindView(R.id.home_recycler) RecyclerView recyclerView;
    @BindView(R.id.home_swipe) SwipeRefreshLayout swipeRefresh;

    private ListView listView;
    private List<Post> posts;
    private AlertDialog dialog;
    private PostAdapter adapter;
    private ProgressBar progressBar;
    private AppPreference appPreference;
    private ImageButton imageButtonClose;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        appPreference = new AppPreference(getActivity());
        progressBar = new ProgressBar(getActivity());
        progressBar.showView();
        loadPosts(new HashMap<>());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View ingredientsView = getLayoutInflater().inflate(R.layout.dialog_post_ingredients, null);
        builder.setView(ingredientsView);
        dialog = builder.create();
        imageButtonClose = ingredientsView.findViewById(R.id.imageButtonClosePopup);
        imageButtonClose.setOnClickListener(view1 -> dialog.hide());

        listView = ingredientsView.findViewById(R.id.post_ingredients_list);
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(false);
            loadPosts(new HashMap<>());
        });
        return view;
    }

    private void loadPosts(HashMap<Integer, Integer> hashMapFilter) {
        new Connection<List<Post>>().getListPosts(new SenderContainerDTO()
                .setCategories(hashMapFilter), (isSuccess, answerServerResponse) -> {
            if(isSuccess){
                posts = answerServerResponse.getDataTransferObject();
                adapter = new PostAdapter(posts, getActivity(), new PostCallback() {
                    @Override
                    public void postClick(int position) {
                        int userID = appPreference.getUser() == null ? 0 : appPreference.getUser().getId();
                        startActivity(new Intent(getActivity(), DetailPostActivity.class)
                                .putExtra(KeyTransferObj.POST_ID.toString(), posts.get(position).getId())
                                .putExtra(KeyTransferObj.USER_ID.toString(), userID));
                    }

                    @Override
                    public void ingredientsClick(int position) {
                        listView.setAdapter(new PostIngredientsAdapter(posts.get(position).getIngredients(),
                                posts.get(position).getShopIngredients(), getActivity()));
                        dialog.show();
                    }

                    @Override
                    public void userClick(int position) {
                        boolean isLogged = appPreference.getUser() != null;
                        Intent intent = new Intent(getActivity(), UserActivity.class);
                        intent.putExtra(KeyTransferObj.USER_ID.toString(), posts.get(position).getAuthorID());
                        if (isLogged)
                            intent.putExtra(KeyTransferFlag.IS_MY_PROFILE.toString(),
                                    appPreference.getUser().getId() == posts.get(position).getAuthorID());
                        else
                            intent.putExtra(KeyTransferFlag.IS_MY_PROFILE.toString(),
                                    false);
                        startActivity(intent);
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }else{
                Toast.makeText(getActivity(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
            progressBar.dismissView();
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(TabManager.getTabManager().getMenuByPosition(2), menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public void reloadPostWithFilter(int categoryID){
        progressBar.showView();
        HashMap<Integer, Integer> filterRequest = new HashMap<>();
        filterRequest.put(0, categoryID);
        loadPosts(filterRequest);
    }

}













