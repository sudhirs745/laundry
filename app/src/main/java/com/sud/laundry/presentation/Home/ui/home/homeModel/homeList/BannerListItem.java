package com.sud.laundry.presentation.Home.ui.home.homeModel.homeList;

import com.google.gson.annotations.SerializedName;

public class BannerListItem{

	@SerializedName("cat_pro_id")
	private int catProId;

	@SerializedName("banner_id")
	private int bannerId;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("banner_path")
	private String bannerPath;

	public int getCatProId(){
		return catProId;
	}

	public int getBannerId(){
		return bannerId;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getBannerPath(){
		return bannerPath;
	}
}