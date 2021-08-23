package com.sud.laundry.presentation.order.model.OrderInfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartDetailsItem implements Serializable  {

	@SerializedName("Suppliers_id")
	private int suppliersId;

	@SerializedName("email_id")
	private String emailId;

	@SerializedName("u_name")
	private String uName;

	@SerializedName("original_price")
	private float originalPrice;

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

	@SerializedName("cart_quantity")
	private int cartQuantity;

	@SerializedName("Category_id")
	private int categoryId;

	@SerializedName("cart_id")
	private int cartId;

	@SerializedName("u_id")
	private int uId;

	@SerializedName("price")
	private float price;

	@SerializedName("name")
	private String name;

	@SerializedName("avilable_status")
	private String avilableStatus;

	@SerializedName("Company_Name")
	private String companyName;

	@SerializedName("f_unit_name")
	private String fUnitName;

	@SerializedName("deliver_status")
	private boolean deliverStatus;

	@SerializedName("deliverPrice")
	private String deliverPrice;

	@SerializedName("weight")
	private String weight;

	public String getWeight() {
		return weight;
	}

	public int getSuppliersId(){
		return suppliersId;
	}

	public String getEmailId(){
		return emailId;
	}

	public String getUName(){
		return uName;
	}

	public float getOriginalPrice(){
		return originalPrice;
	}

	public int getProductId(){
		return productId;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public int getProductUnitInstock(){
		return productUnitInstock;
	}

	public String getMobile(){
		return mobile;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public String getProductName(){
		return productName;
	}

	public int getCartQuantity(){
		return cartQuantity;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public int getCartId(){
		return cartId;
	}

	public int getUId(){
		return uId;
	}

	public float getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getAvilableStatus(){
		return avilableStatus;
	}

	public String getCompanyName(){
		return companyName;
	}

	public String getFUnitName(){
		return fUnitName;
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

	public boolean getDeliverStatus() {
		return deliverStatus;
	}

	public String getDeliverPrice() {
		return deliverPrice;
	}
}