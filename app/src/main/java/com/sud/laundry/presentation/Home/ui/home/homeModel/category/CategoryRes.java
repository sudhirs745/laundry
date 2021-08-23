package com.sud.laundry.presentation.Home.ui.home.homeModel.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryRes{

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("status")
	private int status;

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public int getStatus(){
		return status;
	}
}