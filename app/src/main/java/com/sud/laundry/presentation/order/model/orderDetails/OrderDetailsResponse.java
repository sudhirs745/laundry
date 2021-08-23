package com.sud.laundry.presentation.order.model.orderDetails;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsResponse {

    @SerializedName("OrderDetails")
    private List<OrderDetailsItem> orderDetails;

    @SerializedName("response")
    private String response;

    @SerializedName("status")
    private int status;

    @SerializedName("TotalOrder")
    private int TotalOrder;

    public List<OrderDetailsItem> getOrderDetails() {
        return orderDetails;
    }

    public String getResponse() {
        return response;
    }

    public int getStatus() {
        return status;
    }

	public int getTotalOrder() {
		return TotalOrder;
	}
}