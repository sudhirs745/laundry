package com.sud.laundry.presentation.search.adapter;

import android.widget.TextView;

import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.presentation.search.model.SearchProductListItem;
import com.grocery.utils.loading.AVLoadingIndicatorView;

public interface PaginationSearchInterface {

    void retryPageLoad();
    void itemClickRow(int  position, SearchProductListItem productListItem);

    void  produtAddInCart(int status , SearchProductListItem productListItem , TextView tvQuantiy, TextView tvMinus, TextView tvPlus , AVLoadingIndicatorView avLoadingIndicatorView );

}
