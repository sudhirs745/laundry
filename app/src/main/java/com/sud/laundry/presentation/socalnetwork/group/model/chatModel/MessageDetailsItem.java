package com.sud.laundry.presentation.socalnetwork.group.model.chatModel;

import com.google.gson.annotations.SerializedName;

public class MessageDetailsItem {

    @SerializedName("room_id")
    private int roomId;

    @SerializedName("profile_url")
    private String profileUrl;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("message_id")
    private int messageId;

    @SerializedName("isOnline")
    private String isOnline;

    @SerializedName("message")
    private String message;

    @SerializedName("type")
    private String type;

    @SerializedName("img_src")
    private String imgSrc;

    @SerializedName("message_time")
    private String messageTime;

    @SerializedName("mssg_by")
    private int mssgBy;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("product_id")
    private int product_id;

    @SerializedName("mess_deleted")
    private int mess_deleted;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    private boolean imageType = false;      //  false  // server  true for file


    public MessageDetailsItem() {

    }

    public int getRoomId() {
        return roomId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public int getMssgBy() {
        return mssgBy;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }


    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setMssgBy(int mssgBy) {
        this.mssgBy = mssgBy;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public boolean isImageType() {
        return imageType;
    }

    public void setImageType(boolean imageType) {
        this.imageType = imageType;
    }

    public int getMess_deleted() {
        return mess_deleted;
    }


    @Override
    public String toString() {
        return "MessageDetailsItem{" +
                "roomId=" + roomId +
                ", profileUrl='" + profileUrl + '\'' +
                ", mobile='" + mobile + '\'' +
                ", messageId=" + messageId +
                ", isOnline='" + isOnline + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", messageTime='" + messageTime + '\'' +
                ", mssgBy=" + mssgBy +
                ", userId=" + userId +
                ", product_id=" + product_id +
                ", mess_deleted=" + mess_deleted +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", imageType=" + imageType +
                '}';
    }
}