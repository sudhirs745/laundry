package com.sud.laundry.presentation.socalnetwork.search.model.addFriends;

import com.google.gson.annotations.SerializedName;

public class ResponseData{

	@SerializedName("follow_by")
	private String followBy;

	@SerializedName("follow_id")
	private int followId;

	@SerializedName("follow_to")
	private String followTo;

	public String getFollowBy(){
		return followBy;
	}

	public int getFollowId(){
		return followId;
	}

	public String getFollowTo(){
		return followTo;
	}
}