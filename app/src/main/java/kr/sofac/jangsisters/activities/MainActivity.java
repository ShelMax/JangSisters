package kr.sofac.jangsisters.activities;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Constants;
import kr.sofac.jangsisters.views.fragments.GridViewPostFragment;
import kr.sofac.jangsisters.views.fragments.HelpFragment;
import kr.sofac.jangsisters.views.fragments.HomeFragment;
import kr.sofac.jangsisters.views.fragments.ShopFragment;

public class MainActivity extends BaseActivity{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.drawer) DrawerLayout drawer;
    @BindView(R.id.tab_home) Button tabHome;
    @BindView(R.id.search) EditText search;

    private ViewPagerAdapter adapter;
    private ShopFragment shopFragment;

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem()==0){
            shopFragment.backClick();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        initTabLayout();
        initDrawerEndPosition();

        tabLayout.getTabAt(2).select();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        shopFragment = new ShopFragment();

        adapter.addFragment(shopFragment, Constants.tabNames().get(0));
        adapter.addFragment(new GridViewPostFragment(), Constants.tabNames().get(1));
        adapter.addFragment(new HomeFragment(), Constants.tabNames().get(2));
        adapter.addFragment(new HelpFragment(), Constants.tabNames().get(3));
        adapter.addFragment(new HomeFragment(), Constants.tabNames().get(4));
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
                toolbar.setTitle(Constants.tabNames().get(tab.getPosition()));
                search.setVisibility(View.GONE);
                if(tab.getPosition()==2){
                    tabHome.setSelected(true);
                    toolbar.setNavigationIcon(R.drawable.add);
                    toolbar.setNavigationOnClickListener(view -> {}
                    );
                }
                else if(tab.getPosition()==0){
                    toolbar.setNavigationIcon(R.drawable.home);
                    toolbar.setNavigationOnClickListener(view ->
                            shopFragment.homeClick());
                }
                else if(tab.getPosition()==1){
                    search.setVisibility(View.VISIBLE);
                }
                invalidateOptionsMenu();
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
    public boolean onCreateOptionsMenu(Menu newMenu) {
        getMenuInflater().inflate(Constants.toolbarMenus().get(viewPager.getCurrentItem()), newMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                drawer.openDrawer(Gravity.END);
                break;
            case R.id.menu_toolbar_shop_back:
                shopFragment.backClick();
                break;
            case R.id.menu_toolbar_shop_forward:
                shopFragment.forwardClick();
                break;
            case R.id.menu_toolbar_shop_refresh:
                shopFragment.refreshClick();
                break;

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
        public void destroyItem(ViewGroup container, int position, Object object) {
            //Для того, чтобы фрагменты не пересоздавались
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


}