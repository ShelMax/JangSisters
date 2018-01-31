package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;

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
                showToast(getString(R.string.kakaotalk_button));
                break;
            case R.id.buttonFacebook:
                showToast(getString(R.string.facebook_button));
                break;
            case R.id.TextViewRegister:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }
    }


    public void requestAuthorization(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            progressBar.showView();
            new Connection<User>().signInCustomer(new SenderContainerDTO()
                    .setEmail(email)
                    .setPassword(password)
                    .setGoogleCloudKey(""), (isSuccess, answerServerResponse) -> {
                if (isSuccess) {
                    if (0 == answerServerResponse.getDataTransferObject().getVisible()) {
                        startVerificationUserActivity(answerServerResponse.getDataTransferObject());
                    } else {
                        appPreference.setUser(answerServerResponse.getDataTransferObject());
                        appPreference.setAuthorization(true);
                        startLaunchActivity();
                    }
                } else {
                    showToast(getString(R.string.some_problems_with_sign_in));
                }
                progressBar.dismissView();
            });
        } else {
            showToast(getString(R.string.need_fill_all_fields));
        }
    }


    public void startLaunchActivity() {
        startActivity(new Intent(LoginActivity.this, LaunchActivity.class));
        finishAffinity();
    }


    public void startVerificationUserActivity(User user) {
        if (0 != user.getId()) {
            startActivity(new Intent(LoginActivity.this, VerificationActivity.class).putExtra(KeyTransferObj.MY_USER.toString(), user));
        } else {
            showToast(getString(R.string.you_not_registered_user_or_not_correct_email));
        }

    }

    @OnClick(R.id.buttonBack)
    public void onBackClicked() {
        finish();
    }
}
