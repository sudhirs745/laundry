package com.sud.laundry.presentation.socalnetwork.group.model.grouplist;

import com.google.gson.annotations.SerializedName;

public class GroupDetailsItem {

    @SerializedName("room_id")
    private int roomId;

    @SerializedName("lastMssg")
    private LastMssg lastMssg;

    @SerializedName("room_name")
    private String roomName;

    @SerializedName("unreadMssgs")
    private int unreadMssgs;

    @SerializedName("group_icon")
    private String groupIcon;

    @SerializedName("room_admin")
    private int roomAdmin;

    @SerializedName("create_date")
    private String createDate;

    @SerializedName("lastMssgTime")
    private String lastMssgTime;

    @SerializedName("status")
    private int status;

    @SerializedName("type")
    private int type;

    @SerializedName("friend_id")
    private int friend_id;

    @SerializedName("f_name")
    private String f_name;

    @SerializedName("f_email")
    private String f_email;

    @SerializedName("f_mobile")
    private String f_mobile;

    @SerializedName("a_name")
    private String a_name;

    @SerializedName("a_email")
    private String a_email;

    @SerializedName("a_mobile")
    private String a_mobile;

    @SerializedName("f_profile_url")
    private String f_profile_url;

    @SerializedName("a_profile_url")
    private String a_profile_url;


    @SerializedName("userDetails")
    private UserDetailsRes userDetailsRes;


    public int getRoomId() {
        return roomId;
    }

    public LastMssg getLastMssg() {
        return lastMssg;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getUnreadMssgs() {
        return unreadMssgs;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public int getRoomAdmin() {
        return roomAdmin;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getLastMssgTime() {
        return lastMssgTime;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public UserDetailsRes getUserDetailsRes() {
        return userDetailsRes;
    }


    public String getF_name() {
        return f_name;
    }

    public String getF_email() {
        return f_email;
    }

    public String getF_mobile() {
        return f_mobile;
    }

    public String getA_name() {
        return a_name;
    }

    public String getA_email() {
        return a_email;
    }

    public String getA_mobile() {
        return a_mobile;
    }

    public String getF_profile_url() {
        return f_profile_url;
    }

    public String getA_profile_url() {
        return a_profile_url;
    }
}