package com.sud.laundry.presentation.socalnetwork.friend.model.friendList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserdataItem implements Serializable {

    @SerializedName("my_name")
    private String myName;

    @SerializedName("user_to")
    private int userTo;

    @SerializedName("profile_url")
    private String profileUrl;

    @SerializedName("mutualUsersCount")
    private int mutualUsersCount;

    @SerializedName("name")
    private String name;

    @SerializedName("isOnline")
    private String isOnline;

    @SerializedName("lastOnline")
    private String lastOnline;

    @SerializedName("follow_id")
    private int followId;

    @SerializedName("room_id")
    private int room_id;

    @SerializedName("type")
    private int type;

    @SerializedName("room_admin")
    private int room_admin;

    @SerializedName("room_name")
    private String room_name;

    @SerializedName("username")
    private String username;

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public String getMyName() {
        return myName;
    }

    public int getUserTo() {
        return userTo;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public int getMutualUsersCount() {
        return mutualUsersCount;
    }

    public String getName() {
        return name;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public int getFollowId() {
        return followId;
    }

    public String getUsername() {
        return username;
    }

    public int getRoom_id() {
        return room_id;
    }

    public int getType() {
        return type;
    }

    public int getRoom_admin() {
        return room_admin;
    }

    public String getRoom_name() {
        return room_name;
    }

}