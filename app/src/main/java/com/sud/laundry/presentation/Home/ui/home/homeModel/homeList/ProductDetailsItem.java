package com.sud.laundry.presentation.Home.ui.home.homeModel.homeList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsItem{

	@SerializedName("cat_image")
	private String catImage;

	@SerializedName("Description")
	private String description;

	@SerializedName("m_category_id")
	private int mCategoryId;

	@SerializedName("proList")
	private List<ProListItem> proList;

	@SerializedName("Category_Name")
	private String categoryName;

	@SerializedName("create_date")
	private String createDate;

	@SerializedName("Category_id")
	private int categoryId;

	@SerializedName("status")
	private int status;

	public String getCatImage(){
		return catImage;
	}

	public String getDescription(){
		return description;
	}

	public int getMCategoryId(){
		return mCategoryId;
	}

	public List<ProListItem> getProList(){
		return proList;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public String getCreateDate(){
		return createDate;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public int getStatus(){
		return status;
	}
}