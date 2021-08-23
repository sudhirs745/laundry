package com.sud.laundry.presentation.socalnetwork.socialnotification.model;

import com.google.gson.annotations.SerializedName;

public class UserDataItem{

	@SerializedName("notify_time")
	private String notifyTime;

	@SerializedName("profile_url")
	private String profileUrl;

	@SerializedName("message_title")
	private String messageTitle;

	@SerializedName("type")
	private String type;

	@SerializedName("notify_id")
	private int notifyId;

	@SerializedName("notify_by")
	private int notifyBy;

	@SerializedName("post_id")
	private int postId;

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("name")
	private String name;


	@SerializedName("isFollowing")
	private boolean isFollowing;

	@SerializedName("user")
	private int user;

	@SerializedName("notify_by_username")
	private String notifyByUsername;

	@SerializedName("status")
	private String status;

	public String getNotifyTime(){
		return notifyTime;
	}

	public String getProfileUrl(){
		return profileUrl;
	}

	public String getMessageTitle(){
		return messageTitle;
	}



	public String getType(){
		return type;
	}

	public int getNotifyId(){
		return notifyId;
	}

	public int getNotifyBy(){
		return notifyBy;
	}

	public int getPostId(){
		return postId;
	}

	public int getGroupId(){
		return groupId;
	}

	public String getName(){
		return name;
	}



	public boolean isFollowing(){
		return isFollowing;
	}

	public int getUser(){
		return user;
	}

	public String getNotifyByUsername(){
		return notifyByUsername;
	}

	public String getStatus(){
		return status;
	}

	public void setFollowing(boolean following) {
		isFollowing = following;
	}

	@Override
	public String toString() {
		return "UserDataItem{" +
				"notifyTime='" + notifyTime + '\'' +
				", profileUrl='" + profileUrl + '\'' +
				", messageTitle='" + messageTitle + '\'' +
				", type='" + type + '\'' +
				", notifyId=" + notifyId +
				", notifyBy=" + notifyBy +
				", postId=" + postId +
				", groupId=" + groupId +
				", name='" + name + '\'' +
				", isFollowing=" + isFollowing +
				", user=" + user +
				", notifyByUsername='" + notifyByUsername + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}