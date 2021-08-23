package com.sud.laundry.presentation.Home.ui.home.homeModel.homeList;

import com.google.gson.annotations.SerializedName;

public class HeadlinesDetailsItem{

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	@SerializedName("create_date")
	private String createDate;

	@SerializedName("status")
	private int status;

	@SerializedName("short_by")
	private int shortBy;

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getBody(){
		return body;
	}

	public String getCreateDate(){
		return createDate;
	}

	public int getStatus(){
		return status;
	}

	public int getShortBy(){
		return shortBy;
	}
}