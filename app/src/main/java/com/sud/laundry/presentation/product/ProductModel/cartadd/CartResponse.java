package com.sud.laundry.presentation.product.ProductModel.cartadd;

import com.google.gson.annotations.SerializedName;

public class CartResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("updateStatus")
    private int updateStatus;

    @SerializedName("cartQunatiry")
    private int cartQunatiry;

    @SerializedName("Totalcart")
    private int Totalcart;

    @SerializedName("status")
    private int status;

    public String getResponse() {
        return response;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public int getStatus() {
        return status;
    }

    public int getCartQunatiry() {
        return cartQunatiry;
    }

	public int getTotalcart() {
		return Totalcart;
	}
}