package com.sud.laundry.presentation.socalnetwork.search.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SocialSearchRes{

	@SerializedName("userData")
	private List<UserDataItem> userData;

	@SerializedName("response")
	private String response;

	@SerializedName("status")
	private int status;

	public List<UserDataItem> getUserData(){
		return userData;
	}

	public String getResponse(){
		return response;
	}

	public int getStatus(){
		return status;
	}


	@Override
	public String toString() {
		return "SocialSearchRes{" +
				"userData=" + userData +
				", response='" + response + '\'' +
				", status=" + status +
				'}';
	}
}