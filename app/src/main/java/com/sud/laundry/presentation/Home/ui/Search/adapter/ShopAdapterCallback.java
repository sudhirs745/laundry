package com.sud.laundry.presentation.Home.ui.Search.adapter;

import android.widget.TextView;

import com.grocery.presentation.Home.ui.Search.shopModel.ShopListItem;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.utils.loading.AVLoadingIndicatorView;

public interface ShopAdapterCallback {

    void retryPageLoad();
    void itemClickRow(int position, ShopListItem productListItem);


}
