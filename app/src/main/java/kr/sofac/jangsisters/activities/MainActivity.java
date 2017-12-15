package kr.sofac.jangsisters.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.views.fragments.HomeFragment;
import kr.sofac.jangsisters.views.widget.MainNavigateTabBar;

public class MainActivity extends BaseActivity {

    private MainNavigateTabBar mNavigateTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.cart, R.drawable.cart_selected, "SHOP"));
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.magnify, R.drawable.magnify_selected, "SEARCH"));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, "HOME"));
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.information, R.drawable.information_selected, "INFO"));
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.account, R.drawable.account_selected, "PROFILE"));
}


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }


    public void onClickPublish(View v) {
        Toast.makeText(this, "发布", Toast.LENGTH_LONG).show();
    }
}