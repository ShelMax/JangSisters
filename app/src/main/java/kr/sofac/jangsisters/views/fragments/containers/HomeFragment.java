package kr.sofac.jangsisters.views.fragments.containers;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.PostDetailedActivity;
import kr.sofac.jangsisters.models.Constants;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.models.PostCallback;
import kr.sofac.jangsisters.utils.PostWrapper;
import kr.sofac.jangsisters.views.adapters.PostAdapter;
import kr.sofac.jangsisters.views.adapters.PostIngredientsAdapter;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_recycler) RecyclerView recyclerView;
    private ListView listView;
    private List<Post> posts;
    private AlertDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        posts = PostWrapper.getAllPosts();
        PostAdapter adapter = new PostAdapter(posts, getActivity(), new PostCallback() {
            @Override
            public void postClick(int position) {
                startActivity(new Intent(getActivity(), PostDetailedActivity.class)
                        .putExtra(getString(R.string.intent_postID), posts.get(position).getId()));
            }

            @Override
            public void ingredientsClick(int position) {
                listView.setAdapter(new PostIngredientsAdapter(posts.get(position).getIngredients(), getActivity()));
                dialog.show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View ingredientsView = getLayoutInflater().inflate(R.layout.dialog_post_ingredients, null);
        builder.setView(ingredientsView);
        dialog = builder.create();
        listView = ingredientsView.findViewById(R.id.post_ingredients_list);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(Constants.toolbarMenus().get(2), menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
