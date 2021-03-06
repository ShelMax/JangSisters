package kr.sofac.jangsisters.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.models.GlideApp;
import kr.sofac.jangsisters.network.dto.AddPostDTO;
import kr.sofac.jangsisters.views.fragments.viewElements.GridViewCategoryFragment;

public class AddPostMainActivity extends BaseActivity {

    private static final int SELECT_PICTURE = 1;
    private static final int REQUEST_PERMISSION = 2;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.background) ImageView background;
    @BindView(R.id.title_text) EditText title;
    @BindView(R.id.description_text) EditText description;
    @BindView(R.id.add) ImageView add;
    @BindView(R.id.scrollView) ScrollView scrollView;

    private Uri imageUri;
    private GridViewCategoryFragment categoryFragment;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            imageLoaded(data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addImage();
            } else {
                showToast("You should give permission");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_main);
        ButterKnife.bind(this);
        title.getBackground().setColorFilter(getResources().getColor(R.color.underlineEditText), PorterDuff.Mode.SRC_IN);
        description.getBackground().setColorFilter(getResources().getColor(R.color.underlineEditText), PorterDuff.Mode.SRC_IN);
        initToolbar();
        categoryFragment = new GridViewCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.categories, categoryFragment).commit();
    }

    private void imageLoaded(Intent data) {
        GlideApp.with(this).load(data.getData())
                .override(400, 400)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10)))
                .into(background);
        imageUri = data.getData();
        add.setImageResource(R.drawable.file_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_add_post_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_toolbar_add_post_next) {
            if (title.getText().toString().isEmpty() || description.getText().toString().isEmpty()) {
                showToast("Please fill all fields");
            } else if (imageUri == null) {
                showToast("Please load image");
            }
            else if(categoryFragment.getSelectedCategory().size() != appPreference.getCategories().size()){
                hideKeyboard();
                showToast("Please select " + appPreference.getCategories().size() +" categories");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
            else {
                hideKeyboard();
                AddPostDTO postDTO = new AddPostDTO();
                postDTO.setCustomerID(appPreference.getUser().getId())
                        .setTitle(title.getText().toString())
                        .setDescription(description.getText().toString())
                        .setCategories(categoryFragment.getSelectedCategory());
                startActivity(new Intent(AddPostMainActivity.this, AddPostIngredientActivity.class)
                        .putExtra(KeyTransferObj.POST.toString(), postDTO)
                        .putExtra(KeyTransferObj.URI.toString(), imageUri));
                overridePendingTransition(R.anim.forward_start, R.anim.forward_finish);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        toolbar.setTitle("Main info");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left_white);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @OnClick(R.id.circle_add)
    public void addImage() {
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

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }
}
