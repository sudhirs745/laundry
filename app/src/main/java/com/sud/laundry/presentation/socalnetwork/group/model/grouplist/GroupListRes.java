package com.sud.laundry.presentation.socalnetwork.group.model.grouplist;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GroupListRes {

    @SerializedName("response")
    private String response;

    @SerializedName("groupDetails")
    private List<GroupDetailsItem> groupDetails;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;


    public String getResponse() {
        return response;
    }

    public List<GroupDetailsItem> getGroupDetails() {
        return groupDetails;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

}