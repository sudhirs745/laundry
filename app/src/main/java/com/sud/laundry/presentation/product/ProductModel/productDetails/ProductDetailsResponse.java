package com.sud.laundry.presentation.product.ProductModel.productDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsResponse{



	@SerializedName("response")
	private String response;

	@SerializedName("ProductDetails")
	private List<ProductDetailsItem> productDetails;

	@SerializedName("status")
	private int status;

	public String getResponse(){
		return response;
	}

	public List<ProductDetailsItem> getProductDetails(){
		return productDetails;
	}

	public int getStatus(){
		return status;
	}


	@Override
	public String toString() {
		return "ProductDetailsResponse{" +
				"response='" + response + '\'' +
				", productDetails=" + productDetails +
				", status=" + status +
				'}';
	}
}