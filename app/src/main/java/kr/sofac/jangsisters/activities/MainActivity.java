package kr.sofac.jangsisters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.models.TabManager;
import kr.sofac.jangsisters.views.adapters.FilterAdapter;
import kr.sofac.jangsisters.views.fragments.containers.HelpFragment;
import kr.sofac.jangsisters.views.fragments.containers.HomeFragment;
import kr.sofac.jangsisters.views.fragments.containers.ProfileFragment;
import kr.sofac.jangsisters.views.fragments.containers.SearchFragment;
import kr.sofac.jangsisters.views.fragments.containers.ShopFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.NotSignedFragment;

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
    @BindView(R.id.search)
    EditText search;

    ImageButton backButton;

    FilterAdapter filterAdapter;
    FilterAdapter filterSubAdapter;
    RecyclerView recyclerViewFilterCategory;

    private ViewPagerAdapter adapter;
    private ShopFragment shopFragment;
    private SearchFragment searchFragment;
    private HomeFragment homeFragment;
    private TabManager tabManager;

    private Boolean isSubCategoryView = false;

    private boolean isLogged;

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            shopFragment.backClick();
        } else if (viewPager.getCurrentItem() == 1) {
            searchFragment.hideSlideUp();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            drawer.openDrawer(Gravity.END);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isLogged = appPreference.getUser() != null;
        tabManager = TabManager.getTabManager();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        setupViewPager();
        initTabLayout();
        initDrawerEndPosition();
        if (getIntent().getBooleanExtra(EnumPreference.UPDATED_PROFILE.toString(), false))
            tabLayout.getTabAt(4).select();
        else
            tabLayout.getTabAt(2).select();
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        shopFragment = new ShopFragment();
        searchFragment = new SearchFragment();
        homeFragment = new HomeFragment();

        adapter.addFragment(shopFragment);
        adapter.addFragment(searchFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(new HelpFragment());
        if (isLogged) {
            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(EnumPreference.MY_PROFILE.toString(), true);
            profileFragment.setArguments(bundle);
            adapter.addFragment(profileFragment);
        } else {
            adapter.addFragment(new NotSignedFragment());
        }

        viewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        shopFragment.setOpened(true);
                        toolbar.setNavigationIcon(R.drawable.home_white);
                        toolbar.setNavigationOnClickListener(v -> shopFragment.homeClick());
                        toolbar.setTitle(shopFragment.getTitle());
                        break;
                    case 1:
                        toolbar.setNavigationIcon(null);
                        search.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tabHome.setSelected(true);
                        toolbar.setTitle(R.string.home);
                        if (isLogged) {
                            toolbar.setNavigationIcon(R.drawable.add);
                            toolbar.setNavigationOnClickListener(v -> {
                                startActivity(new Intent(MainActivity.this, AddPostMainActivity.class));
                            });
                        }
                        break;
                    case 3:
                        toolbar.setNavigationIcon(null);
                        toolbar.setTitle(R.string.help_title);
                        break;
                    case 4:
                        if (isLogged) {
                            toolbar.setTitle(R.string.your_profile);
                            toolbar.setNavigationIcon(R.drawable.add);
                            toolbar.setNavigationOnClickListener(v -> {
                                startActivity(new Intent(MainActivity.this, AddPostMainActivity.class));
                            });
                        } else
                            toolbar.setTitle(R.string.login_required);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    tabHome.setSelected(false);
                } else if (tab.getPosition() == 0) {
                    toolbar.setNavigationIcon(null);
                    toolbar.setTitle(null);
                    shopFragment.setOpened(false);
                } else if (tab.getPosition() == 1) {
                    search.setVisibility(View.GONE);
                } else if (tab.getPosition() == 3) {
                    toolbar.setTitle(null);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < adapter.getCount(); i++) {
            tabLayout.getTabAt(i).setIcon(getResources().getDrawable(tabManager.getDrawableByPosition(i)));
        }
        searchFragment.onSetTextChanged(search);
    }

    private void initDrawerEndPosition() {

        recyclerViewFilterCategory = navigationView.getHeaderView(0).findViewById(R.id.recyclerViewFilters);
        backButton = navigationView.getHeaderView(0).findViewById(R.id.backButton);

        filterAdapter = new FilterAdapter(appPreference.getCategories(), isSubCategoryView, (view, position) -> {
            filterSubAdapter = new FilterAdapter(appPreference.getCategories().get(position).getSubmenu(), true, (view1, position1) -> {
                drawer.closeDrawer(Gravity.END);
                homeFragment.reloadPostWithFilter(appPreference.getCategories().get(position).getSubmenu().get(position1).getId());
                showMainFilters();
            });
            showSubFilters();
        });

        recyclerViewFilterCategory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFilterCategory.setAdapter(filterAdapter);

        backButton.setOnClickListener(view -> {
            if (isSubCategoryView) {
                showMainFilters();
            } else {
                drawer.closeDrawer(Gravity.END);
            }
        });

        drawer.addDrawerListener(new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                showMainFilters();
            }
        });

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void showMainFilters(){
        recyclerViewFilterCategory.setAdapter(filterAdapter);
        isSubCategoryView = false;
        backButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white));
    }

    public void showSubFilters(){
        recyclerViewFilterCategory.setAdapter(filterSubAdapter);
        isSubCategoryView = true;
        backButton.setImageDrawable(getResources().getDrawable(R.drawable.close));
    }

    @OnClick(R.id.tab_home)
    public void onViewClicked() {
        tabLayout.getTabAt(2).select();
        tabHome.setSelected(true);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();

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

        void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

}