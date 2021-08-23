package com.sud.laundry.presentation.Home.ui.Search.shopModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ShopListResponse{

	@SerializedName("shopList")
	private List<ShopListItem> shopList;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public List<ShopListItem> getShopList(){
		return shopList;
	}

	public String getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}