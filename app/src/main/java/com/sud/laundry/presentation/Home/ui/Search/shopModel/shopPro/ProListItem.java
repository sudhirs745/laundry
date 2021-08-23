package com.sud.laundry.presentation.Home.ui.Search.shopModel.shopPro;

import com.google.gson.annotations.SerializedName;

public class ProListItem{

	@SerializedName("Suppliers_id")
	private int suppliersId;

	@SerializedName("Product_Unit_InStock")
	private int productUnitInStock;

	@SerializedName("Product_id")
	private int productId;

	@SerializedName("approve_status")
	private int approveStatus;

	@SerializedName("Product_Name")
	private String productName;

	@SerializedName("weight")
	private String weight;

	@SerializedName("description")
	private String description;

	@SerializedName("Category_id")
	private int categoryId;

	@SerializedName("Product_Availability_Status")
	private String productAvailabilityStatus;

	@SerializedName("model_name")
	private String modelName;

	@SerializedName("img_url")
	private String imgUrl;

	@SerializedName("viewed")
	private int viewed;

	@SerializedName("name")
	private String name;

	@SerializedName("Product_Unit_Price")
	private int productUnitPrice;

	@SerializedName("used_for")
	private Object usedFor;

	@SerializedName("Company_Name")
	private String companyName;

	@SerializedName("create_date")
	private String createDate;

	@SerializedName("unit_id")
	private int unitId;

	@SerializedName("brand")
	private String brand;

	@SerializedName("minimum")
	private int minimum;

	@SerializedName("product_original_price")
	private double productOriginalPrice;

	@SerializedName("status")
	private int status;

	public int getSuppliersId(){
		return suppliersId;
	}

	public int getProductUnitInStock(){
		return productUnitInStock;
	}

	public int getProductId(){
		return productId;
	}

	public int getApproveStatus(){
		return approveStatus;
	}

	public String getProductName(){
		return productName;
	}

	public String getWeight(){
		return weight;
	}

	public String getDescription(){
		return description;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public String getProductAvailabilityStatus(){
		return productAvailabilityStatus;
	}

	public String getModelName(){
		return modelName;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public int getViewed(){
		return viewed;
	}

	public String getName(){
		return name;
	}

	public int getProductUnitPrice(){
		return productUnitPrice;
	}

	public Object getUsedFor(){
		return usedFor;
	}

	public String getCompanyName(){
		return companyName;
	}

	public String getCreateDate(){
		return createDate;
	}

	public int getUnitId(){
		return unitId;
	}

	public String getBrand(){
		return brand;
	}

	public int getMinimum(){
		return minimum;
	}

	public double getProductOriginalPrice(){
		return productOriginalPrice;
	}

	public int getStatus(){
		return status;
	}
}