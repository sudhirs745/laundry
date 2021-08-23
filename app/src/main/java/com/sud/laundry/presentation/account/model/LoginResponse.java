package com.sud.laundry.presentation.account.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("response")
	private String response;

	@SerializedName("status")
	private String status;

	public List<DataItem> getData(){
		return data;
	}

	public String getResponse(){
		return response;
	}

	public String getStatus(){
		return status;
	}
}