package kr.sofac.jangsisters.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.ServersConfig;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.user_image) ImageView image;
    @BindView(R.id.username_change) EditText usernameNew;
    @BindView(R.id.email_change) EditText emailNew;
    @BindView(R.id.blog_name_change)
    EditText blogNameNew;
    @BindView(R.id.blog_description_change)
    EditText blogDescriptionNew;
    @BindView(R.id.background_avatar)
    ImageView backgroundAvatar;

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
                .load(ServersConfig.BASE_URL + ServersConfig.PART_AVATAR + appPreference.getUser().getAvatar())
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder).error(R.drawable.avatar_holder))
                .apply(RequestOptions.circleCropTransform())
                .into(image);
        Glide.with(this)
                .load(ServersConfig.BASE_URL + ServersConfig.PART_AVATAR + appPreference.getUser().getAvatar())
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder).error(R.drawable.avatar_holder))
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(80)).centerCrop())
                .into(backgroundAvatar);
        emailNew.setText(appPreference.getUser().getEmail());
        usernameNew.setText(appPreference.getUser().getName());
        blogNameNew.setText(appPreference.getUser().getBlogName());
        blogDescriptionNew.setText(appPreference.getUser().getBlogDescription());
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
