package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.network.Connection;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.editTextEmail)
    TextInputEditText editTextEmail;
    @BindView(R.id.editTextPassword)
    TextInputEditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buttonLogin, R.id.buttonKakoTalk, R.id.buttonFacebook, R.id.TextViewRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                requestAuthorization(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                break;
            case R.id.buttonKakoTalk:
                showToast("KakaoTalk");
                break;
            case R.id.buttonFacebook:
                showToast("Facebook");
                break;
            case R.id.TextViewRegister:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }
    }

    public void requestAuthorization(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            new Connection<String>();
        } else {
            showToast("Need fill all fields!");
        }
    }
}