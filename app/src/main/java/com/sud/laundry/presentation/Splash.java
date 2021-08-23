package com.sud.laundry.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.Home.HomeActivity;
import com.grocery.presentation.account.login.LoginMainActivity;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import butterknife.BindView;

public class Splash extends BaseActivity {


    @BindView(R.id.progressAnimationView)
    AVLoadingIndicatorView avLoadingIndicatorView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void setView() {
        super.setView();

        avLoadingIndicatorView.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(AppSharedPreference.getInstance(Splash.this).getBoolean(AppSharedPreference.SP_LOGIN_OR_NOT ,false)) {
                    startActivity(new Intent(Splash.this, HomeActivity.class));
                }else {
                    startActivity(new Intent(Splash.this, LoginMainActivity.class));
                }
                finish();
            }
        }, 1500);
    }
}
