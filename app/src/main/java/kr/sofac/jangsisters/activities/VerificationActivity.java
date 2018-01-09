package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;

import static kr.sofac.jangsisters.config.EnumPreference.USER_ID;

public class VerificationActivity extends BaseActivity {

    @BindView(R.id.editTextCodeVerification)
    TextInputEditText editTextCodeVerification;
    private Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        userID = getIntent().getIntExtra(USER_ID.toString(), 0);
    }

    @OnClick({R.id.buttonSendCode, R.id.buttonTextResendCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonSendCode:
                requestVerification(editTextCodeVerification.getText().toString());
                break;
            case R.id.buttonTextResendCode:
                requestResendVerification();
                break;
        }
    }

    public void requestVerification(String codeVerification) {
        if (!codeVerification.isEmpty()) {
            progressBar.showView();
            new Connection<String>().signUpCustomerVerification(new SenderContainerDTO(userID, codeVerification), (isSuccess, answerServerResponse) -> {
                if (isSuccess) {
                    appPreference.setAuthorization(true);
                    User user = appPreference.getUser();
                    user.setVisible(1);
                    appPreference.setUser(user);
                    startLaunchActivity();
                } else {
                    showToast("Some problems with verification process");
                }
                progressBar.dismissView();
            });
        } else {
            showToast("Need fill all fields!");
        }
    }

    public void requestResendVerification() {
        progressBar.showView();
        new Connection<User>().signUpCustomerResendVerification(userID, (isSuccess, answerServerResponse) -> {
            if (isSuccess) {
                showToast("Verification was sent, check your email!");
            } else {
                showToast("Some problems with resending verification");
            }
            progressBar.dismissView();
        });
    }

    public void startLaunchActivity() {
        Intent intent = new Intent(this, LaunchActivity.class);
        startActivity(intent);
        finishAffinity();
    }

}
