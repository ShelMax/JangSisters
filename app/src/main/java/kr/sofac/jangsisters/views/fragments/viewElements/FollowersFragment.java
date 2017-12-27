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
import kr.sofac.jangsisters.activities.UserActivity;
import kr.sofac.jangsisters.models.SimpleListCallback;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.utils.UserWrapper;
import kr.sofac.jangsisters.views.adapters.FollowersAdapter;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

/**
 * Created by Oleksandr on 27.12.2017.
 */

public class FollowersFragment extends BaseFragment{

    @BindView(R.id.recycler) RecyclerView recycler;
    private List<User> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, null);
        ButterKnife.bind(this, view);
        users = UserWrapper.getAllUsers();
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(new FollowersAdapter(users, position -> {
            startActivity(new Intent(getActivity(), UserActivity.class)
            .putExtra(getString(R.string.userID), users.get(position).getId())
            .putExtra(getString(R.string.myProfile), false));
        }));
        return view;
    }
}
