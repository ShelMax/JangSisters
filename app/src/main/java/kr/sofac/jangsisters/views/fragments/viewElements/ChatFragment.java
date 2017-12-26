package kr.sofac.jangsisters.views.fragments.viewElements;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

public class ChatFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @OnClick(R.id.help)
    void helpClick(){
        //TODO open chat help
    }

}
