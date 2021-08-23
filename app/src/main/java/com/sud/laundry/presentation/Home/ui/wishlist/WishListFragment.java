package com.sud.laundry.presentation.Home.ui.wishlist;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.presentation.Home.HomeActivity;
import com.grocery.presentation.Home.ui.Search.SearchFragment;
import com.grocery.presentation.Home.ui.home.adapter.HomeRecyclerViewDataAdapter;
import com.grocery.presentation.Home.ui.wishlist.adapter.WishAdapterCallback;
import com.grocery.presentation.Home.ui.wishlist.adapter.WishListAdapter;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.order.OrderInfoActivity;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.NavigationUtils;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WishListFragment extends Fragment implements WishListContract.View, WishAdapterCallback {

    String TAG = "WishListFragment";

    @BindView(R.id.rv_wish)
    RecyclerView rv_wish;

    @BindView(R.id.lv_empty)
    LinearLayout lv_empty;

    @BindView(R.id.btn_start_wish)
    Button btn_start_wish;

    WishListPresenter wishListPresenter;
    int UserId;
    List<ProductListItem> productListItemList = new ArrayList<>();
    WishListAdapter wishListAdapter;

    public static Fragment newInstance()
    {
        WishListFragment myFragment = new WishListFragment();
        return myFragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_wish_list, container, false);
        ButterKnife.bind(this, root);
        wishListPresenter = new WishListPresenter(this, getActivity());
        UserId = AppSharedPreference.getInstance(getActivity()).getInteger(AppSharedPreference.SP_USER_ID, 0);
        lv_empty.setVisibility(View.GONE);

        wishListAdapter = new WishListAdapter(getActivity(), productListItemList, this);
        LinearLayoutManager homelaoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_wish.setLayoutManager(homelaoutManager);
        rv_wish.setAdapter(wishListAdapter);


        appiCall();

        return root;
    }

    private void appiCall() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        jsonObject.addProperty("page_no", "1");
        wishListPresenter.getwishList(jsonObject);

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
    public void ProDetailsResponse(ProductListResponse productListResponse) {

        if (productListResponse.getStatus() == 200) {

            rv_wish.setVisibility(View.VISIBLE);
            lv_empty.setVisibility(View.GONE);

            productListItemList.clear();
            for (int i = 0; i < productListResponse.getProductList().size(); i++) {

                productListItemList.add(productListResponse.getProductList().get(i));
            }
            wishListAdapter.notifyDataSetChanged();
        } else {
            rv_wish.setVisibility(View.GONE);
            lv_empty.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public void addRemoveSussess(DataResponse dataResponse) {
        Log.e(TAG, "addRemoveSussess: " + dataResponse.toString());

        if (dataResponse != null && dataResponse.getStatus() == 200) {
            appiCall();
        }

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
    public void retryPageLoad() {

    }

    @Override
    public void itemClickRow(int position, ProductListItem productListItem) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        jsonObject.addProperty("product_id", productListItem.getProductId());

        wishListPresenter.addAndRemoveWishList(jsonObject);


    }

    @Override
    public void produtAddInCart(int status, ProductListItem productListItem, TextView tvQuantiy, TextView tvMinus, TextView tvPlus, AVLoadingIndicatorView avLoadingIndicatorView) {

    }

    @OnClick({R.id.btn_start_wish})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_start_wish:

                NavigationUtils.startActivity(getActivity(), HomeActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();

                // startActivity(new Intent(this, CartActivity.class));
                break;


        }
    }
}
