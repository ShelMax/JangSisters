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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.PostDetailedActivity;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.SimpleListCallback;
import kr.sofac.jangsisters.utils.PostWrapper;
import kr.sofac.jangsisters.views.adapters.GridViewPostAdapter;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

/**
 * Created by Sasha on 21.12.2017.
 */

public class GridViewPostFragment extends BaseFragment {

    @BindView(R.id.recycler) RecyclerView recyclerView;
    private List<Post> posts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_view_post, container, false);
        ButterKnife.bind(this, view);
        posts = PostWrapper.getAllPosts();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new GridViewPostAdapter(PostWrapper.getAllPosts(), new SimpleListCallback() {
            @Override
            public void itemClick(int position) {
                startActivity(new Intent(getActivity(), PostDetailedActivity.class)
                        .putExtra(getString(R.string.intent_postID), posts.get(position).getId()));
            }
        }));
        return view;
    }

}
