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
            progressBar.showView();
            new Connection<User>().signInCustomer(
                    new SenderContainerDTO(password, email, ""),
                    (isSuccess, answerServerResponse) -> {
                        if (isSuccess) {
                            User user = answerServerResponse.getDataTransferObject();
                            appPreference.setAuthorization(true);
                        } else {
                            showToast("Some problems with sign in");
                        }
                        progressBar.dismissView();
                    });
        } else {
            showToast("Need fill all fields!");
        }
    }

//    {
//        "responseStatus":"SERVER_RESPONSE_SUCCESS",
//            "dataTransferObject":{
//                "id":"1",
//                "email":"maximkizyun@gmail.com",
//                "password":"81dc9bdb52d04dc20036dbd8313ed055",
//                "name":"Maxim",
//                "visible":"1"
//            }
//    }

}
