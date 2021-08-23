package com.sud.laundry.presentation.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.Home.ui.ContactUsFragment;
import com.grocery.presentation.Home.ui.Search.SearchFragment;
import com.grocery.presentation.Home.ui.home.HomeFragment;
import com.grocery.presentation.Home.ui.profile.ProfileFragment;
import com.grocery.presentation.Home.ui.wishlist.WishListFragment;
import com.grocery.presentation.cart.CartActivity;
import com.grocery.presentation.socalnetwork.main.SocialFragment;
import com.grocery.presentation.socalnetwork.main.SocialNetworkActivity;
import com.grocery.userDatabase.DbRepository;
import com.grocery.userDatabase.model.CartModel;
import com.grocery.userDatabase.model.LoginModel;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.NavigationUtils;
import com.grocery.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    String TAG = "HomeActivity";
    @BindView(R.id.bottom_navigation)
    BottomNavigationView navView;
    @BindView(R.id.toolbar_center_title)
    TextView toolbar_center_title;
    @BindView(R.id.nav_drawer_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.ic_menu)
    ImageView ic_menu;

    @BindView(R.id.tv_cart)
    TextView tv_cart;

    private ActionBarDrawerToggle mToggle;
    DbRepository dbRepository;
    int UserId;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar_center_title.setText(getString(R.string.app_name));
                    // if (!(currentFragment instanceof HomeFragment)) {
                    // fragment = HomeFragment.newInstance();
                    HomeFragment homeFragment = new HomeFragment();
                    switchToFragment(homeFragment, "HomeFragment");
                    // }

                    break;
                case R.id.navigation_search:
                    toolbar_center_title.setText(getString(R.string.app_name));
                    // if (!(currentFragment instanceof SearchFragment)) {
                    SearchFragment searchFragment = new SearchFragment();
                    switchToFragment(searchFragment, "SearchFragment");

                    // }

                    break;
                case R.id.navigation_network:

                    SocialFragment socialFragment = new SocialFragment();
                    switchToFragment(socialFragment, " SocialFragment");
                    //NavigationUtils.startActivity(HomeActivity.this, SocialNetworkActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //toolbar_center_title.setText(getString(R.string.app_name));
                    //switchToFragment(new WishListFragment());
                    break;
                case R.id.navigation_wish:
                    toolbar_center_title.setText(getString(R.string.app_name));
                    // if (!(currentFragment instanceof WishListFragment)) {
                    // fragment = WishListFragment.newInstance();
                    WishListFragment wishListFragment = new WishListFragment();
                    switchToFragment(wishListFragment, " WishListFragment");
                    // }
                    break;
                case R.id.navigation_profile:
                    toolbar_center_title.setText("Profile");
                    // if (!(currentFragment instanceof ProfileFragment)) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    switchToFragment(profileFragment, " HomeFragment");
                    // }

                    break;

            }

            return true;
        }

    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setView() {
        super.setView();


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupDrawerLayout();
        switchToFragment(new HomeFragment(), "HomeFragment");
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);


    }

    private void setupDrawerLayout() {

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        toolbar_center_title.setText(getString(R.string.app_name));
                        switchToFragment(new HomeFragment(), " HomeFragment");
                        break;
                    case R.id.nav_profile:
                        toolbar_center_title.setText("Profile");
                        switchToFragment(new ProfileFragment(), "ProfileFragment");
                        break;

                    case R.id.nav_contact_us:
                        switchToFragment(new ContactUsFragment(), "ProfileFragment");
                        break;


                }

                return true;
            }
        });
    }

    public void switchToFragment(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        // transaction.addToBackStack(null);
        transaction.commit();

    }

//
//    public void switchToFragment(Fragment fragment) {
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Utils.MessageSuccess(this, "Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbRepository = new DbRepository(this);
        getCartlistList();
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.ic_menu, R.id.rl_cart, R.id.im_cart_image, R.id.tv_cart})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_menu:
                if (!drawerLayout.isDrawerOpen(Gravity.START))
                    drawerLayout.openDrawer(Gravity.START);
                else
                    drawerLayout.closeDrawer(Gravity.END);
                break;

            case R.id.im_cart_image:
            case R.id.rl_cart:
            case R.id.tv_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;


        }
    }

    private void getCartlistList() {
        int cartvalue = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_CART_VALUE, 0);
        tv_cart.setText("" + cartvalue);

    }

}
