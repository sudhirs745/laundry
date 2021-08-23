package com.sud.laundry.presentation.Home.ui.home.homeModel.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubcatItem implements Serializable {

	@SerializedName("cat_image")
	private String catImage;

	@SerializedName("Description")
	private String description;

	@SerializedName("m_category_id")
	private int mCategoryId;

	@SerializedName("Category_Name")
	private String categoryName;

	@SerializedName("Category_id")
	private int categoryId;

	public String getCatImage(){
		return catImage;
	}

	public String getDescription(){
		return description;
	}

	public int getMCategoryId(){
		return mCategoryId;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public int getCategoryId(){
		return categoryId;
	}
}