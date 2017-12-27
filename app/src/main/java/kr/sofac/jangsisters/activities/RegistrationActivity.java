package kr.sofac.jangsisters.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.network.Connection;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.editTextEmail)
    TextInputEditText editTextEmail;
    @BindView(R.id.editTextPassword)
    TextInputEditText editTextPassword;
    @BindView(R.id.editTextUserName)
    TextInputEditText editTextUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonRegistration)
    public void onViewClicked() {
        requestRegistration(
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString(),
                editTextUserName.getText().toString()
        );
    }

    public void requestRegistration(String email, String password, String name) {
        if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
            new Connection<String>();
        } else {
            showToast("");
        }

    }


}
