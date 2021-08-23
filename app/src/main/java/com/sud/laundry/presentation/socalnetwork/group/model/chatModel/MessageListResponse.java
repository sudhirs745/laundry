package com.sud.laundry.presentation.socalnetwork.group.model.chatModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MessageListResponse{

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("MessageDetails")
	private List<MessageDetailsItem> messageDetails;

	@SerializedName("status")
	private int status;

	public String getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public List<MessageDetailsItem> getMessageDetails(){
		return messageDetails;
	}

	public int getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "MessageListResponse{" +
				"response='" + response + '\'' +
				", message='" + message + '\'' +
				", messageDetails=" + messageDetails +
				", status=" + status +
				'}';
	}
}