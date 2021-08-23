package com.sud.laundry.presentation.Home.ui.home.homeModel.homeList;

import com.google.gson.annotations.SerializedName;

public class ProListItem {

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
    private double productUnitPrice;

    @SerializedName("product_original_price")
    private double OriginPrice;

    @SerializedName("used_for")
    private Object usedFor;

    @SerializedName("Company_Name")
    private Object companyName;

    @SerializedName("create_date")
    private String createDate;

    @SerializedName("unit_id")
    private int unitId;

    @SerializedName("brand")
    private String brand;

    @SerializedName("minimum")
    private int minimum;

    @SerializedName("status")
    private int status;

    public int getSuppliersId() {
        return suppliersId;
    }

    public int getProductUnitInStock() {
        return productUnitInStock;
    }

    public int getProductId() {
        return productId;
    }

    public int getApproveStatus() {
        return approveStatus;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getProductAvailabilityStatus() {
        return productAvailabilityStatus;
    }

    public String getModelName() {
        return modelName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getViewed() {
        return viewed;
    }

    public String getName() {
        return name;
    }

    public double getProductUnitPrice() {
        return productUnitPrice;
    }

    public Object getUsedFor() {
        return usedFor;
    }

    public Object getCompanyName() {
        return companyName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getUnitId() {
        return unitId;
    }

    public String getBrand() {
        return brand;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getStatus() {
        return status;
    }

	public double getOriginPrice() {
		return OriginPrice;
	}
}