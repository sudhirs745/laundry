package com.sud.laundry.presentation.Home.ui.shopPro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.Home.ui.Search.ShopListContract;
import com.grocery.presentation.Home.ui.Search.ShopListPresenter;
import com.grocery.presentation.Home.ui.Search.adapter.ShopListAdapter;
import com.grocery.presentation.Home.ui.Search.adapter.ShopRecyclerViewDataAdapter;
import com.grocery.presentation.Home.ui.Search.adapter.ShopRowClickInterface;
import com.grocery.presentation.Home.ui.Search.shopModel.ShopListResponse;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ProListItem;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ProductDetailsItem;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ShopProductResponse;
import com.grocery.presentation.Home.ui.home.homeModel.category.SubcatItem;
import com.grocery.presentation.cart.CartActivity;
import com.grocery.presentation.product.ProductDetails.ProductDetailsActivity;
import com.grocery.presentation.product.productList.ProductListActivity;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopProductActivity extends BaseActivity implements ShopListContract.View, ShopRowClickInterface {

    @BindView(R.id.tv_cart)
    TextView tv_cart;

 @BindView(R.id.toolbar_center_title)
    TextView toolbar_center_title;

    @BindView(R.id.rv_shop_list)
    RecyclerView rv_shop_list;

    @BindView(R.id.lv_empty)
    LinearLayout lv_empty;

    ShopListPresenter shopListPresenter;
    ShopRecyclerViewDataAdapter shopRecyclerViewDataAdapter;
    int UserId;
    int shopId;

    String ShoppName ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_product;
    }

    @Override
    public void setView() {
        super.setView();
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);
        shopId = getIntent().getIntExtra(KeyData.SHOP_ID, 0);
        ShoppName = getIntent().getStringExtra(KeyData.SHOP_NAME);
        toolbar_center_title.setText(ShoppName+"");
        shopListPresenter = new ShopListPresenter(this, this);

        lv_empty.setVisibility(View.GONE);
        shopRecyclerViewDataAdapter = new ShopRecyclerViewDataAdapter(this, this);
        LinearLayoutManager homelaoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_shop_list.setLayoutManager(homelaoutManager);
        rv_shop_list.setAdapter(shopRecyclerViewDataAdapter);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("shop_id", shopId);
        shopListPresenter.shopProductList(jsonObject);

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

    }

    @Override
    public void ShopProdcutResponse(ShopProductResponse shopProductResponse) {

        List<ProductDetailsItem>  productDetailsItems = new ArrayList<>();
        if(shopProductResponse.getStatus()==200) {
            for (ProductDetailsItem result : shopProductResponse.getProductDetails()) {
                if (result.getProList().size() > 0)
                    productDetailsItems.add(result);
            }
           if(productDetailsItems.size()>0) {
               rv_shop_list.setVisibility(View.VISIBLE);
               lv_empty.setVisibility(View.GONE);
               shopRecyclerViewDataAdapter.addAll(productDetailsItems);
           }else {
               rv_shop_list.setVisibility(View.GONE);
               lv_empty.setVisibility(View.VISIBLE);
           }

       }else {
            rv_shop_list.setVisibility(View.GONE);
            lv_empty.setVisibility(View.VISIBLE);
       }

    }

    @Override
    public void showSuccessfulMessage(String message) {

    }

    @Override
    public void showFailedMessage(String message) {

    }

    @Override
    public void homeRowclick(int Clickstatus, ProductDetailsItem productDetailsItemArrayList, ProListItem proListItemArrayList) {


        Intent intent;
        if (Clickstatus == 0) {
            if (productDetailsItemArrayList != null) {
                intent = new Intent(this, ProductListActivity.class);
                intent.putExtra(KeyData.CATEGORY_ID, productDetailsItemArrayList.getCategoryId());
                startActivity(intent);
            }
        } else if (Clickstatus == 1) {

            if (proListItemArrayList != null) {
                intent = new Intent(this, ProductDetailsActivity.class);
                intent.putExtra(KeyData.PRODUCT_ID, proListItemArrayList.getProductId());
                startActivity(intent);
            }
        }



    }

    @Override
    protected void onResume() {
        super.onResume();

        getCartlistList();
    }

    @Override
    public void homeRowclick(SubcatItem subcatItem) {

    }

    @OnClick({R.id.im_back , R.id.rl_cart , R.id.im_cart_image ,R.id.tv_cart ,R.id.im_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.im_cart_image:
            case R.id.rl_cart:
            case R.id.tv_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.im_search:

                startActivity(new Intent(this, SearchActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

        }
    }

    private void getCartlistList() {
        int cartvalue = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_CART_VALUE, 0);
        tv_cart.setText("" + cartvalue);
    }

}
