package kr.sofac.jangsisters.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.views.fragments.containers.ProfileFragment;

public class UserActivity extends BaseActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt(EnumPreference.USER_ID.toString(),
                getIntent().getIntExtra(EnumPreference.USER_ID.toString(),0));
        bundle.putBoolean(EnumPreference.MY_PROFILE.toString(),
                getIntent().getBooleanExtra(EnumPreference.MY_PROFILE.toString(), false));
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.userFrame, profileFragment)
                .commit();
    }
}
