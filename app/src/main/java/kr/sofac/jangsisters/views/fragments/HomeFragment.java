package kr.sofac.jangsisters.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.PostDetailedActivity;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.PostCallback;
import kr.sofac.jangsisters.utils.PostWrapper;
import kr.sofac.jangsisters.views.adapters.PostAdapter;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_recycler) RecyclerView recyclerView;
    private List<Post> posts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        posts = PostWrapper.getAllPosts();
        PostAdapter adapter = new PostAdapter(posts, new PostCallback() {
            @Override
            public void postClick(int position) {
                startActivity(new Intent(getActivity(), PostDetailedActivity.class)
                        .putExtra("postID", posts.get(position).getId()));
            }

            @Override
            public void ingredientsClick(int position) {
                Log.i("TAG", "ALERT");
                //TODO AlertDialog
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }


}
