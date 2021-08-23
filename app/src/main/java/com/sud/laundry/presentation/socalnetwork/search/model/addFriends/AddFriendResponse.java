package com.sud.laundry.presentation.socalnetwork.search.model.addFriends;

import com.google.gson.annotations.SerializedName;

public class AddFriendResponse{

	@SerializedName("response")
	private String response;

	@SerializedName("success")
	private boolean success;

	@SerializedName("responseData")
	private ResponseData responseData;

	@SerializedName("status")
	private int status;

	public String getResponse(){
		return response;
	}

	public boolean isSuccess(){
		return success;
	}

	public ResponseData getResponseData(){
		return responseData;
	}

	public int getStatus(){
		return status;
	}
}