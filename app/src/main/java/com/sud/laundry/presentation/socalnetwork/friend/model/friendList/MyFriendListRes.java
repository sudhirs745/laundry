package com.sud.laundry.presentation.socalnetwork.friend.model.friendList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyFriendListRes{

	@SerializedName("userdata")
	private List<UserdataItem> userdata;

	@SerializedName("status")
	private int status;

	public List<UserdataItem> getUserdata(){
		return userdata;
	}

	public int getStatus(){
		return status;
	}
}