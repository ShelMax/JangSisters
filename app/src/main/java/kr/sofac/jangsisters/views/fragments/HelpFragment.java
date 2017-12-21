package kr.sofac.jangsisters.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import kr.sofac.jangsisters.R;

/**
 * Created by Sasha on 21.12.2017.
 */

public class HelpFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        return view;
    }

    @OnClick(R.id.help)
    void helpClick(){
        //todo
    }
}
