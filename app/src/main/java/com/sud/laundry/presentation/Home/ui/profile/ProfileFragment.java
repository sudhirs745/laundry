package com.sud.laundry.presentation.Home.ui.profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.presentation.Home.ui.Search.SearchFragment;
import com.grocery.presentation.account.login.LoginMainActivity;
import com.grocery.presentation.account.model.UserDetals.LocationDetailsItem;
import com.grocery.presentation.account.model.UserDetals.SubDivisionListItem;
import com.grocery.presentation.account.model.UserDetals.UsersDataResponse;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.account.model.rewardPoints.RewardsPointRes;
import com.grocery.presentation.account.profile.AddAddressActivity;
import com.grocery.presentation.account.profile.AddressDetailsActivity;
import com.grocery.presentation.account.profile.RewardsPointUserActivity;
import com.grocery.presentation.order.MyOrderActivity;
import com.grocery.presentation.socalnetwork.main.SocialNetworkActivity;
import com.grocery.userDatabase.DbRepository;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.NavigationUtils;
import com.grocery.utils.NetworkUtil;
import com.grocery.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    String TAG = ProfileFragment.class.getSimpleName();

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.tv_v_coin)
    TextView tv_v_coin;

    @BindView(R.id.tv_share_coin)
    TextView tv_share_coin;

    @BindView(R.id.tv_share_code)
    TextView tv_share_code;
    @BindView(R.id.tv_address_more)
    TextView tv_address_more;
    @BindView(R.id.tv_address)
    TextView tv_address;

    int UserId;
    DbRepository dbRepository;
    ProfilePresenter profilePresenter;

    String FCMTOKEN;
    String Token;

    List<SubDivisionListItem> subDivisionListItems;


    public static Fragment newInstance() {
        ProfileFragment myFragment = new ProfileFragment();
        return myFragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");
        ButterKnife.bind(this, root);
        profilePresenter = new ProfilePresenter(this, getActivity());
        dbRepository = new DbRepository(getActivity());
        UserId = AppSharedPreference.getInstance(getActivity()).getInteger(AppSharedPreference.SP_USER_ID, 0);
        FCMTOKEN = AppSharedPreference.getInstance(getActivity()).getFcmKey();
        Token = AppSharedPreference.getInstance(getActivity()).getString(AppSharedPreference.SP_TOKEN, "");


//        loginRepository.deleteAllUsers();
//        AppSharedPreference.getInstance(this).setInteger(AppSharedPreference.SP_USER_ID,loginSuccessRes.getUserData().getId());
//        loginRepository.insertTask( loginSuccessRes.getToken(), loginSuccessRes.getUserData());

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        apicall();
    }

    private void apicall() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId + "");
        jsonObject.addProperty("lat", "");
        jsonObject.addProperty("long", "");
        jsonObject.addProperty("fcm_key", FCMTOKEN);
        profilePresenter.userData("abc " + Token, jsonObject);
    }


    @OnClick({R.id.lv_logout, R.id.im_share, R.id.lv_share, R.id.tv_address_more, R.id.tv_view_order, R.id.v_network,
            R.id.tv_share_coin, R.id.tv_reward_pont, R.id.lv_reward_pont, R.id.im_reward_cpin})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.lv_logout:
                dbRepository.deleteAllUsers();
                AppSharedPreference.getInstance(getActivity()).removeKey(AppSharedPreference.SP_USER_ID);
                AppSharedPreference.getInstance(getActivity()).removeKey(AppSharedPreference.SP_LOGIN_OR_NOT);
                NavigationUtils.startActivity(getActivity(), LoginMainActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();

                break;
            case R.id.v_network:

                NavigationUtils.startActivity(getActivity(), SocialNetworkActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                break;

            case R.id.im_share:
                shareCode(tv_share_code.getText().toString());
                break;
            case R.id.lv_share:

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", tv_share_code.getText().toString());
                clipboard.setPrimaryClip(clip);
                Snackbar.make(getView(), tv_share_code.getText().toString() + "  copied to clipboard", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.tv_address_more:

                if (tv_address_more.getText().toString() != null && tv_address_more.getText().toString().equalsIgnoreCase("ADD AN ADDRESS")) {

                    NavigationUtils.startActivity(getActivity(), AddAddressActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                } else {

                    NavigationUtils.startActivity(getActivity(), AddressDetailsActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                break;
            case R.id.tv_view_order:
                NavigationUtils.startActivity(getActivity(), MyOrderActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.tv_share_coin:
            case R.id.tv_reward_pont:
            case R.id.lv_reward_pont:
            case R.id.im_reward_cpin:

                NavigationUtils.startActivity(getActivity(), RewardsPointUserActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                break;

        }

    }

    @Override
    public void showToast(String s) {
        Log.e(TAG, "showToast: " + s);

    }

    @Override
    public void showProgressBar() {
        Utils.showProfressBar(getActivity());
    }

    @Override
    public void hideProgressBar() {

        Utils.dissmiss();
    }

    @Override
    public void showSuccessfulMessage(String message) {
        Log.e(TAG, "showSuccessfulMessage: " + message);

    }

    @Override
    public void userDataSussessful(UsersDataResponse usersDataResponse) {

        boolean addresssetStatus = true;


        if (usersDataResponse.getStatus() == 199) {
            dbRepository.deleteAllUsers();
            AppSharedPreference.getInstance(getActivity()).removeKey(AppSharedPreference.SP_USER_ID);
            AppSharedPreference.getInstance(getActivity()).removeKey(AppSharedPreference.SP_LOGIN_OR_NOT);
            AppSharedPreference.getInstance(getActivity()).removeKey(AppSharedPreference.SP_PIN_CODE);

        } else if (usersDataResponse.getStatus() == 200) {
            subDivisionListItems = usersDataResponse.getSubDivisionListItems();

            if (subDivisionListItems != null) {

                Gson gson = new Gson();
                String json = gson.toJson(subDivisionListItems);

                AppSharedPreference.getInstance(getActivity()).setString(AppSharedPreference.SP_SBU_DIVISION, json + "");

            }

            if (usersDataResponse.getUsersdetails() != null) {
                tv_name.setText(usersDataResponse.getUsersdetails().getName());
                tv_email.setText(usersDataResponse.getUsersdetails().getEmail());
                tv_share_coin.setText(usersDataResponse.getUsersdetails().getTotalRefCoin());
                tv_v_coin.setText(usersDataResponse.getUsersdetails().getTotalSignup());
                tv_share_code.setText(usersDataResponse.getUsersdetails().getReferralCode());
            }

            if (usersDataResponse.getLocationDetails().size() > 0) {

                for (int i = 0; i < usersDataResponse.getLocationDetails().size(); i++) {

                    LocationDetailsItem addressItem = usersDataResponse.getLocationDetails().get(i);

                    if (addressItem.getDefaultAddress() == 1) {

                        addresssetStatus = false;
                        String Addname = addressItem.getName();
                        String AddressDetails = Addname + " \n" + addressItem.getAddress1() + "," + addressItem.getAddress2() + " " + addressItem.getLandmark() + "," + Utils.isNullOrEmpty(addressItem.getSub_division_name()) + " " + Utils.isNullOrEmpty(addressItem.getBlock_name()) + " " + Utils.isNullOrEmpty(addressItem.getCity_name()) + " " + Utils.isNullOrEmpty(addressItem.getState_name()) + " " + addressItem.getPostCode() + " \nMobile No." + addressItem.getMobile();
                        tv_address.setText(AddressDetails);

                        AppSharedPreference.getInstance(getActivity()).setString(AppSharedPreference.SP_PIN_CODE, addressItem.getPostCode() + "");

                    }
                }

                tv_address_more.setText("VIEW " + usersDataResponse.getLocationDetails().size() + " MORE");

                if (addresssetStatus) {
                    LocationDetailsItem addressItem = usersDataResponse.getLocationDetails().get(0);

                    String Addname = addressItem.getName();
                    String AddressDetails = Addname + " \n" + addressItem.getAddress1() + "," + addressItem.getAddress2() + " " + addressItem.getLandmark() + " " + addressItem.getCity() + " " + addressItem.getState() + " " + addressItem.getPostCode() + " \nMobile No." + addressItem.getMobile();
                    tv_address.setText(AddressDetails);
                    AppSharedPreference.getInstance(getActivity()).setString(AppSharedPreference.SP_PIN_CODE, addressItem.getPostCode() + "");

                }

            } else {
                tv_address.setText(getString(R.string.save_delivery_message));
                tv_address_more.setText("ADD AN ADDRESS");

            }
        }

    }

    @Override
    public void showFailedMessage(String message) {
        Utils.toastErrorMessage(getActivity(), message);

    }

    @Override
    public void SignupSuccessful(DataResponse dataResponse) {

    }

    @Override
    public void RewardsPointSuccessful(RewardsPointRes rewardsPointRes) {

    }

    public void shareCode(String shareCode) {

        NavigationUtils.shareMyApp(getActivity(), shareCode);

    }

}
