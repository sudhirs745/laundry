package com.sud.laundry.presentation.socalnetwork.group.model.grouplist;

import com.google.gson.annotations.SerializedName;

public class LastMssg{

	@SerializedName("lastMssgBy")
	private int lastMssgBy;

	@SerializedName("lastMessage")
	private String lastMessage;

	@SerializedName("lastMssgType")
	private String lastMssgType;

	@SerializedName("lastMssgTime")
	private String lastMssgTime;

	public int getLastMssgBy(){
		return lastMssgBy;
	}

	public String getLastMessage(){
		return lastMessage;
	}

	public String getLastMssgType(){
		return lastMssgType;
	}

	public String getLastMssgTime(){
		return lastMssgTime;
	}
}