package com.sud.laundry.presentation.order;

import com.google.gson.JsonObject;
import com.grocery.presentation.order.model.OrderInfo.OrderInfoResponse;
import com.grocery.presentation.order.model.orderAdd.OrderAddResponse;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsItem;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsResponse;

import org.json.JSONObject;


@SuppressWarnings("WeakerAccess")
public interface OrderInfoContract {

    interface View {

        void showProgressBar();

        void hideProgressBar();

        void OrderInfoResponse(OrderInfoResponse orderInfoResponse);

        void OrderAddResponse(OrderAddResponse orderAddResponse);

        void PaymentSuccess(OrderAddResponse orderAddResponse, int PaymentStatus);

        void MyOrderResponse(OrderDetailsResponse orderDetailsResponse, int currentPage);

        void OrderDetailsResponse(OrderDetailsResponse orderDetailsResponse);

        void displayError(String s);

        void showSuccessfulMessage(String message);

        void showFailedMessage(String message);
    }

    interface Presenter {

        void orderinfo(JsonObject jsonObject);

        void AddOrder(JsonObject jsonObject);

        void myorderDetals(JsonObject jsonObject, int currentPage);

        void orderPayment(JsonObject jsonObject, int PaymentStatus);

        void orderDetails(JsonObject jsonObject);


    }


}
