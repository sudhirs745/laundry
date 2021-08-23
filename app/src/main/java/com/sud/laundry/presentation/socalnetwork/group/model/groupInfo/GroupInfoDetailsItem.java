package com.sud.laundry.presentation.socalnetwork.group.model.groupInfo;

import com.google.gson.annotations.SerializedName;

public class GroupInfoDetailsItem {

	@SerializedName("room_id")
	private int roomId;

	@SerializedName("member_id")
	private int memberId;

	@SerializedName("profile_url")
	private String profileUrl;

	@SerializedName("rm_id")
	private int rmId;

	@SerializedName("main_admin")
	private int mainAdmin;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	public int getRoomId(){
		return roomId;
	}

	public int getMemberId(){
		return memberId;
	}

	public String getProfileUrl(){
		return profileUrl;
	}

	public int getRmId(){
		return rmId;
	}

	public int getMainAdmin(){
		return mainAdmin;
	}

	public String getName(){
		return name;
	}

	public String getEmail(){
		return email;
	}
}