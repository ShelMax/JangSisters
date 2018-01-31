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


public class VerificationActivity extends BaseActivity {

    @BindView(R.id.editTextCodeVerification)
    TextInputEditText editTextCodeVerification;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra(KeyTransferObj.MY_USER.toString());
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
            new Connection<String>().signUpCustomerVerification(new SenderContainerDTO()
                    .setID(user.getId())
                    .setCode(codeVerification), (isSuccess, answerServerResponse) -> {
                if (isSuccess) {
                    appPreference.setAuthorization(true);
                    user.setVisible(1);
                    appPreference.setUser(user);
                    startLaunchActivity();
                } else {
                    showToast(getString(R.string.some_problems_with_verification_process));
                }
                progressBar.dismissView();
            });
        } else {
            showToast(getString(R.string.need_fill_all_fields));
        }
    }

    public void requestResendVerification() {
        progressBar.showView();
        new Connection<User>().signUpCustomerResendVerification(user.getId(), (isSuccess, answerServerResponse) -> {
            if (isSuccess) {
                showToast(getString(R.string.verification_was_sent_check_your_email));
            } else {
                showToast(getString(R.string.some_problems_with_resending_verification));
            }
            progressBar.dismissView();
        });
    }

    public void startLaunchActivity() {
        Intent intent = new Intent(this, LaunchActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @OnClick(R.id.buttonBack)
    public void onBackClicked() {
        finish();
    }

}
