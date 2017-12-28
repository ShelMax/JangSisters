package kr.sofac.jangsisters.views.fragments.viewElements;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.activities.LoginActivity;
import kr.sofac.jangsisters.activities.RegistrationActivity;
import kr.sofac.jangsisters.views.fragments.BaseFragment;

public class NotSignedFragment extends BaseFragment {

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_not_signed, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.register)
    public void registerClick() {
        startActivity(new Intent(getActivity(), RegistrationActivity.class));
    }

    @OnClick(R.id.login)
    public void loginClick(){
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
