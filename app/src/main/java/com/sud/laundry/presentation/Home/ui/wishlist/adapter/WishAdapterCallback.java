package com.sud.laundry.presentation.Home.ui.wishlist.adapter;

import android.widget.TextView;

import com.grocery.presentation.cart.cartModel.CartDetailsItem;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.utils.loading.AVLoadingIndicatorView;

public interface WishAdapterCallback {

    void retryPageLoad();
    void itemClickRow(int position, ProductListItem productListItem);

    void  produtAddInCart(int status, ProductListItem productListItem, TextView tvQuantiy, TextView tvMinus, TextView tvPlus, AVLoadingIndicatorView avLoadingIndicatorView);
}
