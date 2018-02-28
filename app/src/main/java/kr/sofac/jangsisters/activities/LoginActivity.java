package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;
import kr.sofac.jangsisters.views.customview.ButtonFacebook;
import kr.sofac.jangsisters.views.customview.ButtonKakaoTalk;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.editTextEmail)
    TextInputEditText editTextEmail;
    @BindView(R.id.editTextPassword)
    TextInputEditText editTextPassword;

    @BindView(R.id.buttonFacebook)
    ButtonFacebook facebook;

    @BindView(R.id.buttonKakaoTalk)
    ButtonKakaoTalk kakaoTalk;

    @BindView(R.id.kakaoLogin)
    LoginButton kakaoLogin;

    private CallbackManager callbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Session.getCurrentSession().checkAndImplicitOpen();
            return;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initFacebookLogin();
        initKakaoLogin();
    }

    private void initFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> {
                                    registerFacebookUser(object);
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.i("TAG", "Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.i("TAG", exception.getMessage());
                    }
                });
        facebook.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
        });
    }

    private void initKakaoLogin() {
        kakaoTalk.setOnClickListener(v -> {
            kakaoLogin.performClick();
        });
        SessionCallback callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }

    private void registerFacebookUser(JSONObject object) {
        progressBar.showView();
        try {
            new Connection<User>().signUpCustomer(new SenderContainerDTO()
                    .setType("facebook")
                    .setName(object.getString("name"))
                    .setAuthID(object.getString("email"))
                    .setGoogleCloudKey(""), (isSuccess, answerServerResponse) -> {
                if(isSuccess){
                    startVerificationUserActivity(answerServerResponse.getDataTransferObject());
                }
                else{

                }
                progressBar.dismissView();
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.buttonLogin, R.id.buttonKakaoTalk, R.id.TextViewRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                requestAuthorization(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                break;
            case R.id.buttonKakaoTalk:
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
        Intent intent = new Intent(this, VerificationActivity.class);
        intent.putExtra(KeyTransferObj.MY_USER.toString(), user);
        startActivity(intent);

    }

    @OnClick(R.id.buttonBack)
    public void onBackClicked() {
        finish();
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {}

                @Override
                public void onSessionClosed(ErrorResult errorResult) {}

                @Override
                public void onNotSignedUp() {
                    Log.i("TAG", "error");
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    progressBar.showView();
                    new Connection<User>().signUpCustomer(new SenderContainerDTO()
                            .setType("kakao")
                            .setName(userProfile.getNickname())
                            .setAuthID(userProfile.getEmail())
                            .setKakaoEmail(userProfile.getEmail())
                            .setGoogleCloudKey(""), (isSuccess, answerServerResponse) -> {
                        if(isSuccess){
                            startVerificationUserActivity(answerServerResponse.getDataTransferObject());
                        }
                        else{

                        }
                        progressBar.dismissView();
                    });

                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {

        }
    }
}
