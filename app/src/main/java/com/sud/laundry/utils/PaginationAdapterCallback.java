package com.sud.laundry.utils;

import android.widget.TextView;

import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.utils.loading.AVLoadingIndicatorView;

public interface PaginationAdapterCallback {

    void retryPageLoad();
    void itemClickRow(int  position, ProductListItem productListItem);

    void  produtAddInCart(int status , ProductListItem productListItem , TextView tvQuantiy, TextView tvMinus, TextView tvPlus , AVLoadingIndicatorView avLoadingIndicatorView );
}
