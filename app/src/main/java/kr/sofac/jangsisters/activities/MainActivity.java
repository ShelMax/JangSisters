package kr.sofac.jangsisters.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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
import kr.sofac.jangsisters.config.EnumPreference;
import kr.sofac.jangsisters.models.OnLoggedOut;
import kr.sofac.jangsisters.models.TabManager;
import kr.sofac.jangsisters.views.fragments.containers.HelpFragment;
import kr.sofac.jangsisters.views.fragments.containers.HomeFragment;
import kr.sofac.jangsisters.views.fragments.containers.ProfileFragment;
import kr.sofac.jangsisters.views.fragments.containers.SearchFragment;
import kr.sofac.jangsisters.views.fragments.containers.ShopFragment;
import kr.sofac.jangsisters.views.fragments.viewElements.NotSignedFragment;

public class MainActivity extends BaseActivity implements OnLoggedOut {

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

    private ViewPagerAdapter adapter;
    private ShopFragment shopFragment;
    private SearchFragment searchFragment;
    private TabManager tabManager;

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
        tabManager = TabManager.getTabManager();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        setupViewPager();
        initTabLayout();
        initDrawerEndPosition();

        tabLayout.getTabAt(2).select();
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), appPreference.getUser() != null);
        shopFragment = new ShopFragment();
        searchFragment = new SearchFragment();

        adapter.addFragment(shopFragment, tabManager.getNameByPosition(0));
        adapter.addFragment(searchFragment, tabManager.getNameByPosition(1));
        adapter.addFragment(new HomeFragment(), tabManager.getNameByPosition(2));
        adapter.addFragment(new HelpFragment(), tabManager.getNameByPosition(3));
        if (appPreference.getUser() != null) {
            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(EnumPreference.USER_ID.toString(), appPreference.getUser().getId());
            bundle.putBoolean(EnumPreference.MY_PROFILE.toString(), true);
            profileFragment.setArguments(bundle);
            adapter.addFragment(profileFragment, tabManager.getNameByPosition(4));
        } else {
            adapter.addFragment(new NotSignedFragment(), tabManager.getNameByPosition(4));
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
                        toolbar.setNavigationIcon(R.drawable.add);
                        toolbar.setNavigationOnClickListener(v -> {
                            //todo add post
                        });
                        break;
                    case 3:
                        toolbar.setNavigationIcon(null);
                        toolbar.setTitle("Help");
                        break;
                    case 4:
                        toolbar.setNavigationIcon(R.drawable.add);
                        toolbar.setNavigationOnClickListener(v -> {
                            //todo
                        });
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    tabHome.setSelected(false);
                } else if (tab.getPosition() == 0) {
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

        for (int i = 0; i < 5; i++) {
            tabLayout.getTabAt(i).setIcon(getResources().getDrawable(tabManager.getDrawableByPosition(i)));
        }
        searchFragment.onSetTextChanged(search);
    }

    //TODO Category
    private void initDrawerEndPosition() {
        drawer.addDrawerListener(new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.open, R.string.close));
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
    public void loggedOut() {
        adapter.loggedOut();
        adapter.notifyDataSetChanged();
        tabLayout.getTabAt(2).select();
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();
        private boolean isLogged;
        //private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager, boolean isLogged) {
            super(manager);
            this.isLogged = isLogged;
        }

        void loggedOut() {
            isLogged = false;
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
            if (position == 4 && !isLogged) {
                super.destroyItem(container, position, object);
                mFragmentList.remove(4);
                mFragmentList.add(new NotSignedFragment());
                for (int i = 0; i < 5; i++) {
                    tabLayout.getTabAt(i).setIcon(getResources().getDrawable(tabManager.getDrawableByPosition(i)));
                }
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

}