package kr.sofac.jangsisters.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.views.fragments.containers.ProfileFragment;

public class UserActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.userID),
                getIntent().getExtras().getString(getString(R.string.userID)));
        bundle.putBoolean(getString(R.string.myProfile),
                getIntent().getExtras().getBoolean(getString(R.string.myProfile)));
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.userFrame, profileFragment)
                .commit();
    }
}
