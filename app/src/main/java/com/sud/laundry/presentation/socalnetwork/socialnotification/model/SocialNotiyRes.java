package com.sud.laundry.presentation.socalnetwork.socialnotification.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SocialNotiyRes{

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
		return "SocialNotiyRes{" +
				"userData=" + userData +
				", response='" + response + '\'' +
				", status=" + status +
				'}';
	}
}