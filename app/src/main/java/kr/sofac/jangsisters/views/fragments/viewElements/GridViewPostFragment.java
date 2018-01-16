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
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.activities.DetailPostActivity;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_view_post, container, false);
        ButterKnife.bind(this, view);
        progressBar = new ProgressBar(getActivity());
        loadPosts();
        return view;
    }

    private void loadPosts() {
        progressBar.showView();
        new Connection<List<Post>>().getListPosts(new SenderContainerDTO()
                .setFilter(new HashMap<>()), (isSuccess, answerServerResponse) -> {
            if(isSuccess){
                posts = answerServerResponse.getDataTransferObject();
                adapter = new GridViewPostAdapter(posts, position -> {
                    startActivity(new Intent(getActivity(), DetailPostActivity.class)
                        .putExtra(EnumPreference.POST_ID.toString(), posts.get(position).getId()));
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }else{
                //todo handle error
            }
            progressBar.dismissView();
        });
    }

}
