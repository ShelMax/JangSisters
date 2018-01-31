package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.KeyTransferFlag;
import kr.sofac.jangsisters.config.KeyTransferObj;
import kr.sofac.jangsisters.views.fragments.containers.ProfileFragment;

public class UserActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        initToolbar();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt(KeyTransferObj.USER_ID.toString(),
                getIntent().getIntExtra(KeyTransferObj.USER_ID.toString(),0));
        bundle.putBoolean(KeyTransferFlag.IS_MY_PROFILE.toString(),
                getIntent().getBooleanExtra(KeyTransferFlag.IS_MY_PROFILE.toString(), false));
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.userFrame, profileFragment)
                .commit();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getIntent().getBooleanExtra(KeyTransferFlag.IS_MY_PROFILE.toString(), false)) {
            toolbar.setNavigationIcon(R.drawable.add);
            toolbar.setNavigationOnClickListener(v -> {
                startActivity(new Intent(UserActivity.this, AddPostMainActivity.class));
            });
        } else {
            toolbar.setNavigationIcon(R.drawable.arrow_left_white);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }
}
