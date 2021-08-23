package com.sud.laundry.presentation.search.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchListResponse{

	@SerializedName("ProductList")
	private List<SearchProductListItem> productList;

	@SerializedName("response")
	private String response;

	@SerializedName("TotalProducts")
	private int totalProducts;

	@SerializedName("status")
	private int status;

	public List<SearchProductListItem> getProductList(){
		return productList;
	}

	public String getResponse(){
		return response;
	}

	public int getTotalProducts(){
		return totalProducts;
	}

	public int getStatus(){
		return status;
	}
}