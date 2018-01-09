package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;

import static kr.sofac.jangsisters.config.EnumPreference.USER_ID;

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
            new Connection<User>().signUpCustomer(new SenderContainerDTO(password, email, name, ""), (isSuccess, answerServerResponse) -> {
                if (isSuccess) {
                    appPreference.setUser(answerServerResponse.getDataTransferObject());
                    startVerificationUserActivity(answerServerResponse.getDataTransferObject().getId());
                } else {
                    showToast("Some problems with sign up");
                }
                progressBar.dismissView();
            });
        } else {
            showToast("Need fill all fields!");
        }
    }

    public void startVerificationUserActivity(Integer userID) {
        Intent intent = new Intent(this, VerificationActivity.class);
        intent.putExtra(USER_ID.toString(), userID);
        startActivity(intent);
    }

}
