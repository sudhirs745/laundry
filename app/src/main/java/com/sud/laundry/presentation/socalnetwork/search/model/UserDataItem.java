package com.sud.laundry.presentation.socalnetwork.search.model;

import com.google.gson.annotations.SerializedName;

public class UserDataItem{

	@SerializedName("friend_status")
	private int friendStatus;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("profile_url")
	private String profileUrl;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("name")
	private String name;

	@SerializedName("isOnline")
	private String isOnline;

	@SerializedName("lastOnline")
	private String lastOnline;

	@SerializedName("email")
	private String email;

	@SerializedName("isPendingFriendStatus")
	private boolean isPendingFriendStatus;

	@SerializedName("isFriendRequestStatus")
	private boolean isFriendRequestStatus;

	@SerializedName("isFriend")
	private boolean isFriend;

	public void setFriendStatus(int friendStatus) {
		this.friendStatus = friendStatus;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public void setLastOnline(String lastOnline) {
		this.lastOnline = lastOnline;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPendingFriendStatus(boolean pendingFriendStatus) {
		isPendingFriendStatus = pendingFriendStatus;
	}

	public void setFriendRequestStatus(boolean friendRequestStatus) {
		isFriendRequestStatus = friendRequestStatus;
	}

	public void setFriend(boolean friend) {
		isFriend = friend;
	}

	public boolean isPendingFriendStatus() {
		return isPendingFriendStatus;
	}

	public boolean isFriendRequestStatus() {
		return isFriendRequestStatus;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public int getFriendStatus(){
		return friendStatus;
	}

	public int getUserId(){
		return userId;
	}

	public String getProfileUrl(){
		return profileUrl;
	}

	public String getMobile(){
		return mobile;
	}

	public String getName(){
		return name;
	}

	public String getIsOnline(){
		return isOnline;
	}

	public String getLastOnline(){
		return lastOnline;
	}

	public String getEmail(){
		return email;
	}


	@Override
	public String toString() {
		return "UserDataItem{" +
				"friendStatus=" + friendStatus +
				", userId=" + userId +
				", profileUrl='" + profileUrl + '\'' +
				", mobile='" + mobile + '\'' +
				", name='" + name + '\'' +
				", isOnline='" + isOnline + '\'' +
				", lastOnline='" + lastOnline + '\'' +
				", email='" + email + '\'' +
				", isPendingFriendStatus=" + isPendingFriendStatus +
				", isFriendRequestStatus=" + isFriendRequestStatus +
				", isFriend=" + isFriend +
				'}';
	}
}