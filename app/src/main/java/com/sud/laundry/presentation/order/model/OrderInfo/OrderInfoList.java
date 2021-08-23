package com.sud.laundry.presentation.order.model.OrderInfo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderInfoList{

	@SerializedName("UserAddress")
	private List<UserAddressItem> userAddress;

	@SerializedName("cartDetails")
	private ArrayList<CartDetailsItem> cartDetails;

	@SerializedName("status")
	private int status;

	public List<UserAddressItem> getUserAddress(){
		return userAddress;
	}

	public ArrayList<CartDetailsItem> getCartDetails(){
		return cartDetails;
	}

	public int getStatus(){
		return status;
	}
}