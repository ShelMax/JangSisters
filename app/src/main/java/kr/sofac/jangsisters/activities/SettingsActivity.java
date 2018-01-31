package kr.sofac.jangsisters.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.KeyTransferFlag;
import kr.sofac.jangsisters.config.ServerConfig;
import kr.sofac.jangsisters.models.GlideApp;
import kr.sofac.jangsisters.models.User;
import kr.sofac.jangsisters.network.Connection;
import kr.sofac.jangsisters.network.dto.SenderContainerDTO;

public class SettingsActivity extends BaseActivity {

    private static final int REQUEST_PERMISSION = 1;
    private static final int SELECT_PICTURE = 2;

    private Uri imageUri;
    private User user;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.user_image) ImageView image;
    @BindView(R.id.username_change) EditText usernameNew;
    @BindView(R.id.email_change) EditText emailNew;
    @BindView(R.id.blog_name_change) EditText blogNameNew;
    @BindView(R.id.blog_description_change) EditText blogDescriptionNew;
    @BindView(R.id.background_avatar)
    ImageView backgroundAvatar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.backward_start, R.anim.backward_finish);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            imageUri = data.getData();
            imageLoaded(imageUri);
        }
    }

    private void imageLoaded(Uri data) {
        GlideApp.with(this)
                .load(data)
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder).error(R.drawable.avatar_holder))
                .apply(RequestOptions.circleCropTransform())
                .into(image);
        GlideApp.with(this)
                .load(data)
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder).error(R.drawable.avatar_holder))
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(100)))
                .into(backgroundAvatar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                changeAvatar();
            } else {
                showToast("You should give permission");
            }
        }
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
        user = appPreference.getUser();
        GlideApp.with(this)
                .load(ServerConfig.BASE_URL + ServerConfig.PART_AVATAR +
                        user.getAvatar())
                .override(200, 200)
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder).error(R.drawable.avatar_holder))
                .apply(RequestOptions.circleCropTransform())
                .into(image);
        GlideApp.with(this)
                .load(ServerConfig.BASE_URL + ServerConfig.PART_AVATAR +
                        user.getAvatar())
                .override(400, 400)
                .apply(new RequestOptions().placeholder(R.drawable.avatar_holder).error(R.drawable.avatar_holder))
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(30)))
                .into(backgroundAvatar);
        emailNew.setText(appPreference.getUser().getEmail());
        usernameNew.setText(appPreference.getUser().getName());
        blogNameNew.setText(appPreference.getUser().getBlogName());
        blogDescriptionNew.setText(appPreference.getUser().getBlogDescription());
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.settings);
        toolbar.setNavigationIcon(R.drawable.arrow_left_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @OnClick(R.id.save)
    public void save(){
        progressBar.showView();
        SenderContainerDTO senderContainerDTO = new SenderContainerDTO()
                .setID(appPreference.getUser().getId())
                .setName(usernameNew.getText().toString())
                .setEmail(emailNew.getText().toString())
                .setBlogName(blogNameNew.getText().toString())
                .setBlogDescription(blogDescriptionNew.getText().toString());
        if (imageUri != null) {
            new Connection<User>().updateUser(this, senderContainerDTO,
                    imageUri, (isSuccess, answerServerResponse) -> {
                        updated(isSuccess, answerServerResponse.getDataTransferObject());
                    });
        } else {
            new Connection<User>().updateUser(senderContainerDTO,
                    (isSuccess, answerServerResponse) -> {
                        updated(isSuccess, answerServerResponse.getDataTransferObject());
                    });
        }

    }

    private void updated(boolean isSuccess, User newUser) {
        if (isSuccess) {
            if (imageUri != null)
                newUser.setAvatar(user.getAvatar());
            appPreference.setUser(newUser);
            startActivity(new Intent(SettingsActivity.this, MainActivity.class)
                    .putExtra(KeyTransferFlag.IS_UPDATED_PROFILE.toString(), true));
            finishAffinity();
        } else {
            showToast("Couldn't update profile info");
        }
        progressBar.dismissView();
    }

    @OnClick(R.id.camera)
    public void changeAvatar() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            askForPermission();
        }
    }

    private void askForPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSION);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"),
                SELECT_PICTURE);
    }
}
