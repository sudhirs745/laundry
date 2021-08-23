package com.sud.laundry.presentation.Home.ui.home.homeModel.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoriesItem implements Serializable {

	@SerializedName("m_category_name")
	private String mCategoryName;

	@SerializedName("m_cat_url")
	private String mCatUrl;

	@SerializedName("cat_url")
	private String catUrl;

	@SerializedName("subcat")
	private ArrayList<SubcatItem> subcat;

	@SerializedName("m_id")
	private int mId;

	public String getMCategoryName(){
		return mCategoryName;
	}

	public String getMCatUrl(){
		return mCatUrl;
	}

	public String getCatUrl(){
		return catUrl;
	}

	public ArrayList<SubcatItem> getSubcat(){
		return subcat;
	}

	public int getMId(){
		return mId;
	}
}