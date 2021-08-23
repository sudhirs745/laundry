package com.sud.laundry.presentation.product;

import android.widget.TextView;

import com.grocery.presentation.product.ProductModel.productList.ProductPriceItem;
import com.grocery.utils.loading.AVLoadingIndicatorView;

public interface CartPriceMore {

    void  produtAddInCart(int status, ProductPriceItem productPriceItem, TextView tvQuantiy, TextView tvMinus, TextView tvPlus, AVLoadingIndicatorView avLoadingIndicatorView);

}
