package kr.sofac.jangsisters.views.fragments.viewElements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.DetailPostActivity;
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;
import kr.sofac.jangsisters.utils.ProgressBar;
import kr.sofac.jangsisters.views.adapters.GridViewPostAdapter;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

public class GridViewPostFragment extends BaseFragment {

    @BindView(R.id.recycler) RecyclerView recyclerView;
    private List<Post> posts;
    private GridViewPostAdapter adapter;
    private ProgressBar progressBar;
    private int userID = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_view_post, container, false);
        ButterKnife.bind(this, view);
        progressBar = new ProgressBar(getActivity());
        progressBar.showView();

        try {
            userID = getArguments().getInt(EnumPreference.USER_ID.toString(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userID == 0) {
            loadPosts();
        } else if (getArguments().getBoolean(EnumPreference.BOOKMARKS.toString(), false)) {
            loadBookmarks();
        } else {
            loadUserPosts();
        }
        return view;
    }

    private void loadBookmarks() {
        new Connection<List<Post>>().getUserBookmarks(userID, (isSuccess, answerServerResponse) -> {
            if(isSuccess){
                postsLoaded(answerServerResponse.getDataTransferObject());
            } else {
                //todo handle error
            }
            progressBar.dismissView();
        });
    }

    private void loadUserPosts() {
        new Connection<List<Post>>().getUserPosts(new SenderContainerDTO(), (isSuccess, answerServerResponse) -> {
            if (isSuccess) {
                postsLoaded(answerServerResponse.getDataTransferObject());
            } else {
                //TODO handle error
            }
            progressBar.dismissView();
        });
    }

    private void postsLoaded(List<Post> loadedPosts) {
        posts = loadedPosts;
        adapter = new GridViewPostAdapter(posts, position -> {
            startActivity(new Intent(getActivity(), DetailPostActivity.class)
                    .putExtra(EnumPreference.POST_ID.toString(), posts.get(position).getId()));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void loadPosts() {
        new Connection<List<Post>>().getListPosts(new SenderContainerDTO()
                .setFilter(new HashMap<>()), (isSuccess, answerServerResponse) -> {
            if (isSuccess) {
                postsLoaded(answerServerResponse.getDataTransferObject());
            }else{
                //todo handle error
            }
            progressBar.dismissView();
        });
    }

}
