package com.sud.laundry.presentation.Home.ui.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.presentation.Home.ui.Search.adapter.ShopAdapterCallback;
import com.grocery.presentation.Home.ui.Search.adapter.ShopListAdapter;
import com.grocery.presentation.Home.ui.Search.shopModel.ShopListItem;
import com.grocery.presentation.Home.ui.Search.shopModel.ShopListResponse;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ShopProductResponse;
import com.grocery.presentation.Home.ui.home.HomeFragment;
import com.grocery.presentation.Home.ui.shopPro.ShopProductActivity;
import com.grocery.presentation.Home.ui.wishlist.WishListPresenter;
import com.grocery.presentation.Home.ui.wishlist.adapter.WishListAdapter;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment implements  ShopListContract.View  , ShopAdapterCallback {


    @BindView(R.id.rv_shop_list)
    RecyclerView rv_shop_list;

    @BindView(R.id.lv_empty)
    LinearLayout lv_empty;

    ShopListPresenter shopListPresenter;
    int UserId;
    List<ShopListItem> shopListItemArrayList = new ArrayList<>();
    ShopListAdapter shopListAdapter;
    public static Fragment newInstance()
    {
        SearchFragment myFragment = new SearchFragment();
        return myFragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, root);

        shopListPresenter = new ShopListPresenter(this, getActivity());
        UserId = AppSharedPreference.getInstance(getActivity()).getInteger(AppSharedPreference.SP_USER_ID, 0);
        lv_empty.setVisibility(View.GONE);

        shopListAdapter = new ShopListAdapter(getActivity(), shopListItemArrayList, this);
        LinearLayoutManager homelaoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_shop_list.setLayoutManager(homelaoutManager);
        rv_shop_list.setAdapter(shopListAdapter);

        apiCall();

        return root;
    }

    private void apiCall() {

        JsonObject jsonObject = new JsonObject();

        shopListPresenter.shopList(jsonObject);
    }


    @OnClick({R.id.v_search_bar})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.v_search_bar:

                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void displayError(String s) {

    }

    @Override
    public void ShopListResponse(ShopListResponse shopListResponse) {


        if (shopListResponse.getStatus() == 200) {

            rv_shop_list.setVisibility(View.VISIBLE);
            lv_empty.setVisibility(View.GONE);

            shopListItemArrayList.clear();
            for (int i = 0; i < shopListResponse.getShopList().size(); i++) {

                shopListItemArrayList.add(shopListResponse.getShopList().get(i));
            }
            shopListAdapter.notifyDataSetChanged();
        } else {
            rv_shop_list.setVisibility(View.GONE);
            lv_empty.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public void ShopProdcutResponse(ShopProductResponse shopProductResponse) {

    }

    @Override
    public void showSuccessfulMessage(String message) {

    }

    @Override
    public void showFailedMessage(String message) {

    }

    @Override
    public void retryPageLoad() {

    }

    @Override
    public void itemClickRow(int position, ShopListItem productListItem) {

        Intent intent = new Intent(getActivity(), ShopProductActivity.class);
        intent.putExtra(KeyData.SHOP_ID, productListItem.getId());
        intent.putExtra(KeyData.SHOP_NAME, productListItem.getCompanyName());
        startActivity(intent);

    }
}
