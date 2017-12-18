package kr.sofac.jangsisters.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.views.fragments.HomeFragment;
import kr.sofac.jangsisters.views.widget.MainNavigateTabBar;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mainTabBar) MainNavigateTabBar mNavigateTabBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer) DrawerLayout drawer;
    @BindView(R.id.filter) ImageView filter;
    @BindView(R.id.toolbar_title) TextView toolbarTitle;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();


        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.cart, R.drawable.cart_selected, "SHOP"));
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.magnify, R.drawable.magnify_selected, "SEARCH"));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, "HOME"));
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.information, R.drawable.information_selected, "INFO"));
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.account, R.drawable.account_selected, "PROFILE"));
}

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbarTitle.setText("MAIN");
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationIcon(R.drawable.add);
        toolbar.setNavigationOnClickListener(view -> {
            // add click
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.open, R.string.close) {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    }
                    else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                }
                return false;
            }
        };
        drawer.addDrawerListener(toggle);
        filter.setOnClickListener(view -> drawer.openDrawer(Gravity.RIGHT));
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_fruits:
                    break;
            }
            drawer.closeDrawer(Gravity.RIGHT);
            return false;
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }


    public void onClickPublish(View v) {
        Toast.makeText(this, "Home", Toast.LENGTH_LONG).show();
    }

}