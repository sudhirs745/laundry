package com.sud.laundry.presentation.socalnetwork.group.model.groupInfo;

import com.google.gson.annotations.SerializedName;

public class GroupInfo{

	@SerializedName("room_id")
	private int roomId;

	@SerializedName("room_name")
	private String roomName;

	@SerializedName("group_icon")
	private String groupIcon;

	@SerializedName("room_admin")
	private int roomAdmin;

	@SerializedName("create_date")
	private String createDate;

	@SerializedName("status")
	private int status;

	public int getRoomId(){
		return roomId;
	}

	public String getRoomName(){
		return roomName;
	}

	public String getGroupIcon(){
		return groupIcon;
	}

	public int getRoomAdmin(){
		return roomAdmin;
	}

	public String getCreateDate(){
		return createDate;
	}

	public int getStatus(){
		return status;
	}
}