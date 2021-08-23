package com.sud.laundry.presentation.product.ProductModel.productDetails;

import com.google.gson.annotations.SerializedName;

public class ProImageListItem{

	@SerializedName("Product_id")
	private int productId;

	@SerializedName("img_url")
	private String imgUrl;

	@SerializedName("id")
	private int id;

	public ProImageListItem() {

	}

	public ProImageListItem(int productId, String imgUrl, int id) {
		this.productId = productId;
		this.imgUrl = imgUrl;
		this.id = id;
	}

	public int getProductId(){
		return productId;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public int getId(){
		return id;
	}
}