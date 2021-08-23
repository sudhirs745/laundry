package com.sud.laundry.presentation.socalnetwork.group.model;

import com.google.gson.annotations.SerializedName;
import com.grocery.presentation.socalnetwork.search.model.addFriends.ResponseData;

public class CreateGroupRes {



    @SerializedName("response")
    private String response;

    @SerializedName("success")
    private boolean success;

    @SerializedName("responseData")
    private ResponseData responseData;

    @SerializedName("status")
    private int status;

    @SerializedName("room_id")
    private int room_id;

   @SerializedName("message_id")
    private int message_id;

    public String getResponse(){
        return response;
    }

    public boolean isSuccess(){
        return success;
    }

    public ResponseData getResponseData(){
        return responseData;
    }

    public int getStatus(){
        return status;
    }

    public int getRoom_id() {
        return room_id;
    }


    public int getMessage_id() {
        return message_id;
    }
}
