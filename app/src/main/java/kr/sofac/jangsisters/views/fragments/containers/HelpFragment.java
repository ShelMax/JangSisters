package kr.sofac.jangsisters.views.fragments.containers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.ChatActivity;
import kr.sofac.jangsisters.utils.AppPreference;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

public class HelpFragment extends BaseFragment {

    private AppPreference appPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);
        appPreference = new AppPreference(getActivity());
        return view;
    }


    @OnClick(R.id.help)
    void helpClick(){
        if(appPreference.getUser() == null){
            Toast.makeText(getActivity(), "You must login first", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(getActivity(), ChatActivity.class));
        getActivity().overridePendingTransition(R.anim.forward_start, R.anim.forward_finish);
    }

}
