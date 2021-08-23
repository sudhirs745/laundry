package com.sud.laundry.presentation.search.model.suggestion;

import com.google.gson.annotations.SerializedName;

public class SuggetionListItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;
@SerializedName("reType")
	private int reType;

	@SerializedName("type")
	private String type;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public int getReType() {
		return reType;
	}
}