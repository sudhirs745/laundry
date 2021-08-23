package com.sud.laundry.presentation.search.model.suggestion;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SuggetionResponse{

	@SerializedName("response")
	private String response;

	@SerializedName("SuggetionList")
	private List<SuggetionListItem> suggetionList;

	@SerializedName("status")
	private int status;

	public String getResponse(){
		return response;
	}

	public List<SuggetionListItem> getSuggetionList(){
		return suggetionList;
	}

	public int getStatus(){
		return status;
	}
}