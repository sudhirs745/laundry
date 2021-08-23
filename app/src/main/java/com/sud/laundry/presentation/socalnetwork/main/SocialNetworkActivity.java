package com.sud.laundry.presentation.socalnetwork.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.account.login.LoginMainActivity;
import com.grocery.presentation.account.profile.AddAddressActivity;
import com.grocery.presentation.account.profile.AddressDetailsActivity;
import com.grocery.presentation.order.MyOrderActivity;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.presentation.socalnetwork.friend.MyFriend;
import com.grocery.presentation.socalnetwork.group.GroupDetailsActivity;
import com.grocery.presentation.socalnetwork.search.SocialSearchActivity;
import com.grocery.presentation.socalnetwork.socialnotification.NotificationActivity;
import com.grocery.userDatabase.DbRepository;
import com.grocery.userDatabase.model.LoginModel;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.NavigationUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SocialNetworkActivity extends BaseActivity {


    String TAG = "SocialNetworkActivity";


    @BindView(R.id.tv_total_friend)
    TextView tv_total_friend;

    @BindView(R.id.tv_total_noti)
    TextView tv_total_noti;

    @BindView(R.id.tv_name)
    TextView tv_name;

    DbRepository dbRepository;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_social_network;
    }

    @Override
    public void setView() {
        super.setView();
        dbRepository = new DbRepository(this);
        tv_total_friend.setText("0");
        tv_total_noti.setText("0");
        getUserList();

    }


    @OnClick({R.id.im_back, R.id.tv_view_friends, R.id.tv_notification, R.id.tv_group , R.id.v_search_bar})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_back:
                finish();
                break;
            case R.id.tv_view_friends:

                startActivity(new Intent(this, MyFriend.class));

                break;
            case R.id.tv_notification:

                startActivity(new Intent(this, NotificationActivity.class));

                break;
            case R.id.tv_group:

                startActivity(new Intent(this, GroupDetailsActivity.class));

                break;

            case R.id.v_search_bar:

                startActivity(new Intent(this, SocialSearchActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;


        }
    }

    private void getUserList() {

        dbRepository.getTasks().observe(this, new Observer<List<LoginModel>>() {
            @Override
            public void onChanged(@Nullable List<LoginModel> loginModels) {
                if (loginModels.size() > 0) {

                    for (int i = 0; i < loginModels.size(); i++) {

                        tv_name.setText(loginModels.get(i).getName() + "");

                        Log.e(TAG, "email: " + loginModels.get(i).getEmail());
                        Log.e(TAG, "name: " + loginModels.get(i).getName());
                        Log.e(TAG, "ref: " + loginModels.get(i).getReferralCode());
                        Log.e(TAG, "token: " + loginModels.get(i).getToken());
                    }
                } else {
                    Log.e(TAG, " onChanged: data not found ");
                }
            }
        });
    }


}
