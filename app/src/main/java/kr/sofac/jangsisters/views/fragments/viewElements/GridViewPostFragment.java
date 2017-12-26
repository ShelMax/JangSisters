package kr.sofac.jangsisters.views.fragments.viewElements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.PostDetailedActivity;
import kr.sofac.jangsisters.models.Post;
import kr.sofac.jangsisters.utils.PostWrapper;
import kr.sofac.jangsisters.views.adapters.GridViewPostAdapter;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

/**
 * Created by Sasha on 21.12.2017.
 */

public class GridViewPostFragment extends BaseFragment {

    @BindView(R.id.search_recycler) RecyclerView recyclerView;
    @BindView(R.id.panel) SlidingUpPanelLayout panel;
    private EditText search;
    private List<Post> posts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        panel.setTouchEnabled(false);
        posts = PostWrapper.getAllPosts();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new GridViewPostAdapter(PostWrapper.getAllPosts(), position ->
                startActivity(new Intent(getActivity(), PostDetailedActivity.class)
        .putExtra(getString(R.string.intent_postID), posts.get(position).getId()))));
        return view;
    }

    public void onSetTextChanged(EditText search){
        this.search = search;
        search.setOnFocusChangeListener((view, focused) -> {
            if(focused) {
                panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void hideSlideUp(){
        panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        recyclerView.requestFocus();
    }
}
