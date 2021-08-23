package com.sud.laundry.presentation.order.model.orderAdd;

import com.google.gson.annotations.SerializedName;

public class OrderAddResponse{

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("Totalcart")
	private int Totalcart;

	@SerializedName("status")
	private int status;

	public int getTotalcart() {
		return Totalcart;
	}

	public String getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public int getOrderId(){
		return orderId;
	}

	public int getStatus(){
		return status;
	}
}