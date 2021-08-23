package com.sud.laundry.presentation.account.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("imagename")
	private String imagename;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private int status;

	@SerializedName("token")
	private String token;

	public String getImagename(){
		return imagename;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public int getStatus(){
		return status;
	}

	public String getToken(){
		return token;
	}
}