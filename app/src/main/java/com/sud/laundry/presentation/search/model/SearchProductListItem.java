package com.sud.laundry.presentation.search.model;

import com.google.gson.annotations.SerializedName;

public class SearchProductListItem {


    @SerializedName("reType")
    private int reType;

    @SerializedName("type")
    private String type;

    @SerializedName("Suppliers_id")
    private int suppliersId;

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("u_name")
    private String uName;

    @SerializedName("original_price")
    private int originalPrice;

    @SerializedName("Product_id")
    private int productId;

    @SerializedName("image_Url")
    private String imageUrl;

    @SerializedName("product_unit_instock")
    private int productUnitInstock;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("Category_Name")
    private String categoryName;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("Category_id")
    private int categoryId;

    @SerializedName("u_id")
    private int uId;

    @SerializedName("price")
    private int price;

    @SerializedName("name")
    private String name;

    @SerializedName("avilable_status")
    private String avilableStatus;

    @SerializedName("Company_Name")
    private String companyName;

    @SerializedName("f_unit_name")
    private String fUnitName;

    @SerializedName("weight")
    private String weight;

    @SerializedName("discount_shop")
    private String discount_shop;


	public String getDiscount_shop() {
		return discount_shop;
	}

	public int getReType() {
        return reType;
    }

    public String getType() {
        return type;
    }

    public String getuName() {
        return uName;
    }

    public int getuId() {
        return uId;
    }

    public String getfUnitName() {
        return fUnitName;
    }

    public String getWeight() {
        return weight;
    }

    public int getSuppliersId() {
        return suppliersId;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getUName() {
        return uName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getProductId() {
        return productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getProductUnitInstock() {
        return productUnitInstock;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getUId() {
        return uId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getAvilableStatus() {
        return avilableStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getFUnitName() {
        return fUnitName;
    }
}