package kr.sofac.jangsisters.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.user_image) ImageView image;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.username_change) EditText usernameNew;
    @BindView(R.id.email_change) EditText emailNew;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.backward_start, R.anim.backward_finish);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();
        Glide.with(this)
                .load("")
                .apply(new RequestOptions().placeholder(R.drawable.boy))
                .apply(RequestOptions.circleCropTransform())
                .into(image);
        username.setText(appPreference.getUser().getName());
        emailNew.setText(appPreference.getUser().getEmail());
        usernameNew.setText(appPreference.getUser().getName());
        image.requestFocus();
    }

    private void initToolbar() {
        toolbar.setTitle("Settings");
        toolbar.setNavigationIcon(R.drawable.arrow_left_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @OnClick(R.id.save)
    public void save(){
        onBackPressed();
    }
}
