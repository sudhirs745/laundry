package com.sud.laundry.presentation.socalnetwork.group.model.groupInfo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GroupInfoResponse{

	@SerializedName("GroupInfo")
	private GroupInfo groupInfo;

	@SerializedName("response")
	private String response;

	@SerializedName("groupDetails")
	private List<GroupInfoDetailsItem>  groupInfoDetailsItems;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public GroupInfo getGroupInfo(){
		return groupInfo;
	}

	public String getResponse(){
		return response;
	}

	public List<GroupInfoDetailsItem> getGroupInfoDetails(){
		return groupInfoDetailsItems;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}