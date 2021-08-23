package com.sud.laundry.presentation.socalnetwork.main.model;

import com.google.gson.annotations.SerializedName;

public class SocialDetailsRes{

	@SerializedName("TotalFriend")
	private int totalFriend;

	@SerializedName("TotalNotification")
	private int totalNotification;

	@SerializedName("userData")
	private UserData userData;

	@SerializedName("response")
	private String response;

	@SerializedName("status")
	private int status;

	public int getTotalFriend(){
		return totalFriend;
	}

	public int getTotalNotification(){
		return totalNotification;
	}

	public UserData getUserData(){
		return userData;
	}

	public String getResponse(){
		return response;
	}

	public int getStatus(){
		return status;
	}
}