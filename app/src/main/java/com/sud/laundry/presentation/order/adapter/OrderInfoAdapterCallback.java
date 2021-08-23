package com.sud.laundry.presentation.order.adapter;

import android.widget.TextView;

import com.grocery.presentation.cart.cartModel.CartDetailsItem;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsItem;
import com.grocery.utils.loading.AVLoadingIndicatorView;

public interface OrderInfoAdapterCallback {

    void  orderDetails(OrderDetailsItem  orderDetailsItem );
    void retryPageLoad();

}
