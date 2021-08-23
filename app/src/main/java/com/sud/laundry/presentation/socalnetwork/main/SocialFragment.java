package com.sud.laundry.presentation.socalnetwork.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.presentation.Home.HomeActivity;
import com.grocery.presentation.Home.ui.wishlist.WishListContract;
import com.grocery.presentation.Home.ui.wishlist.WishListPresenter;
import com.grocery.presentation.Home.ui.wishlist.adapter.WishAdapterCallback;
import com.grocery.presentation.Home.ui.wishlist.adapter.WishListAdapter;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;
import com.grocery.presentation.socalnetwork.friend.MyFriend;
import com.grocery.presentation.socalnetwork.group.GroupDetailsActivity;
import com.grocery.presentation.socalnetwork.main.model.SocialDetailsRes;
import com.grocery.presentation.socalnetwork.search.SocialSearchActivity;
import com.grocery.presentation.socalnetwork.socialnotification.NotificationActivity;
import com.grocery.userDatabase.DbRepository;
import com.grocery.userDatabase.model.LoginModel;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.NavigationUtils;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SocialFragment extends Fragment implements SocialContract.View {


    String TAG = "SocialNetworkActivity";


    @BindView(R.id.profile_image)
    CircleImageView profile_image;

    @BindView(R.id.tv_total_friend)
    TextView tv_total_friend;

    @BindView(R.id.tv_total_noti)
    TextView tv_total_noti;

    @BindView(R.id.tv_name)
    TextView tv_name;


    DbRepository dbRepository;
    SocialPresenter socialPresenter;

    int UserId;
    List<ProductListItem> productListItemList = new ArrayList<>();
    WishListAdapter wishListAdapter;

    public static Fragment newInstance() {
        SocialFragment myFragment = new SocialFragment();
        return myFragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_social_list, container, false);
        ButterKnife.bind(this, root);
        UserId = AppSharedPreference.getInstance(getActivity()).getInteger(AppSharedPreference.SP_USER_ID, -1);
        socialPresenter = new SocialPresenter(this, getActivity());

        dbRepository = new DbRepository(getActivity());
        tv_total_friend.setText("0");
        tv_total_noti.setText("0");
        getUserList();

        getSocialDetails();

        return root;
    }

    private void getSocialDetails() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        socialPresenter.userSocialDetails(jsonObject);

    }


    @OnClick({R.id.tv_view_friends, R.id.tv_notification, R.id.tv_group, R.id.v_search_bar,
            R.id.im_notify, R.id.tv_total_noti, R.id.tv_notify, R.id.tv_total_friend, R.id.im_friend, R.id.tv_friends,
    })
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_total_friend:
            case R.id.im_friend:
            case R.id.tv_friends:
            case R.id.tv_view_friends:

                startActivity(new Intent(getActivity(), MyFriend.class));

                break;
            case R.id.im_notify:
            case R.id.tv_total_noti:
            case R.id.tv_notify:
            case R.id.tv_notification:

                startActivity(new Intent(getActivity(), NotificationActivity.class));

                break;
            case R.id.tv_group:

                startActivity(new Intent(getActivity(), GroupDetailsActivity.class));

                break;

            case R.id.v_search_bar:

                startActivity(new Intent(getActivity(), SocialSearchActivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;


        }
    }

    private void getUserList() {

        dbRepository.getTasks().observe(getActivity(), new Observer<List<LoginModel>>() {
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


    @Override
    public void showProgressBar() {

        Utils.showProfressBarActivity(getActivity());

    }

    @Override
    public void hideProgressBar() {

        Utils.dissmissActiviity();

    }

    @Override
    public void displayError(String s) {

        Log.e(TAG, "displayError: " + s);

    }

    @Override
    public void showSuccessfulMessage(String message) {

        Log.e(TAG, "showSuccessfulMessage: " + message);

    }

    @Override
    public void showFailedMessage(String message) {
        Log.e(TAG, "showFailedMessage: " + message);

    }

    @Override
    public void showUserDetails(SocialDetailsRes socialDetailsRes) {

        if (socialDetailsRes != null) {
            Utils.setProfileImage(socialDetailsRes.getUserData().getProfileUrl(), profile_image);
            tv_name.setText(socialDetailsRes.getUserData().getName() + "");
        }
        tv_total_friend.setText(socialDetailsRes.getTotalFriend() + "");
        tv_total_noti.setText(socialDetailsRes.getTotalNotification() + "");


    }
}
