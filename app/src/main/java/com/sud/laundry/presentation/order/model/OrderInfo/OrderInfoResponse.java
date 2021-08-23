package com.sud.laundry.presentation.order.model.OrderInfo;

import com.google.gson.annotations.SerializedName;

public class OrderInfoResponse{

	@SerializedName("response")
	private String response;

	@SerializedName("orderInfoList")
	private OrderInfoList orderInfoList;

	@SerializedName("status")
	private int status;

	public String getResponse(){
		return response;
	}

	public OrderInfoList getOrderInfoList(){
		return orderInfoList;
	}

	public int getStatus(){
		return status;
	}
}