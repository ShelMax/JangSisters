package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;

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
            progressBar.showView();
            new Connection<User>().signUpCustomer(new SenderContainerDTO()
                    .setAuthID(email)
                    .setPassword(password)
                    .setName(name)
                    .setType("email")
                    .setGoogleCloudKey(""), (isSuccess, answerServerResponse) -> {
                if (isSuccess) {
                    startVerificationUserActivity(answerServerResponse.getDataTransferObject());
                } else {
                    showToast(getString(R.string.some_problems_with_sign_up));
                }
                progressBar.dismissView();
            });
        } else {
            showToast(getString(R.string.need_fill_all_fields));
        }
    }

    public void startVerificationUserActivity(User user) {
        Intent intent = new Intent(this, VerificationActivity.class);
        intent.putExtra(KeyTransferObj.MY_USER.toString(), user);
        startActivity(intent);
    }

    @OnClick(R.id.buttonBack)
    public void onBackClicked() {
        finish();
    }

}
