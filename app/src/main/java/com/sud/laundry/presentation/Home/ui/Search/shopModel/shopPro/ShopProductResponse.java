package com.sud.laundry.presentation.Home.ui.Search.shopModel.shopPro;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ShopProductResponse{

	@SerializedName("response")
	private String response;

	@SerializedName("ProductDetails")
	private List<ProductDetailsItem> productDetails;

	@SerializedName("Shopdetails")
	private List<ShopdetailsItem> shopdetails;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public String getResponse(){
		return response;
	}

	public List<ProductDetailsItem> getProductDetails(){
		return productDetails;
	}

	public List<ShopdetailsItem> getShopdetails(){
		return shopdetails;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}