package com.sud.laundry.presentation.product.ProductModel.productList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductListResponse{

	@SerializedName("ProductList")
	private List<ProductListItem> productList;

	@SerializedName("response")
	private String response;

	@SerializedName("TotalProducts")
	private int totalProducts;

	@SerializedName("status")
	private int status;

	public List<ProductListItem> getProductList(){
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