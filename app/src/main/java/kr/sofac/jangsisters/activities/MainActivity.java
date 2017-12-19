package kr.sofac.jangsisters.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.views.fragments.HomeFragment;


public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.tab_home)
    Button tabHome;

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        setupViewPager(viewPager);
        initTabLayout();
        initDrawerEndPosition();
    }

    private void initToolbar() {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.add);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "SHOP");
        adapter.addFragment(new HomeFragment(), "SEARCH");
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new HomeFragment(), "INFO");
        adapter.addFragment(new HomeFragment(), "PROFILE");
        viewPager.setAdapter(adapter);
    }

    private void initTabLayout(){

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.selector_shop));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.selector_search));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.selector_home));
        tabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.selector_chat));
        tabLayout.getTabAt(4).setIcon(getResources().getDrawable(R.drawable.selector_profile));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==2){
                    tabHome.setSelected(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition()==2){
                    tabHome.setSelected(false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(2).select();
    }

    private void initDrawerEndPosition() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.open, R.string.close) {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.filter) {
                    drawer.closeDrawer(Gravity.END);
                }
                return false;
            }
        };

        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_fruits:
                    navigationView.setCheckedItem(R.id.nav_fruits);
                    break;
                case R.id.nav_soups:
                    navigationView.setCheckedItem(R.id.nav_soups);
                    break;
                case R.id.nav_sweets:
                    navigationView.setCheckedItem(R.id.nav_sweets);
                    break;
            }
            drawer.closeDrawer(Gravity.END);
            return false;
        });
    }

    @OnClick(R.id.tab_home)
    public void onViewClicked() {
        tabLayout.getTabAt(2).select();
        tabHome.setSelected(true);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                drawer.openDrawer(Gravity.END);
                break;
            case android.R.id.home:

        }
        return super.onOptionsItemSelected(item);
    }



    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        //private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            //mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }








}