package com.sud.laundry.presentation.Home.ui.Search.shopModel;

import com.google.gson.annotations.SerializedName;

public class ShopListItem{

	@SerializedName("email_id")
	private String emailId;

	@SerializedName("Address")
	private String address;

	@SerializedName("State")
	private String state;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("Postal_Code")
	private String postalCode;

	@SerializedName("Country")
	private String country;

	@SerializedName("Company_Name")
	private String companyName;

	@SerializedName("id")
	private int id;

	@SerializedName("City")
	private String city;

	@SerializedName("shop_image")
	private String shopImage;

	public String getEmailId(){
		return emailId;
	}

	public String getAddress(){
		return address;
	}

	public String getState(){
		return state;
	}

	public String getName(){
		return name;
	}

	public String getMobile(){
		return mobile;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public String getCountry(){
		return country;
	}

	public String getCompanyName(){
		return companyName;
	}

	public int getId(){
		return id;
	}

	public String getCity(){
		return city;
	}

	public String getShopImage(){
		return shopImage;
	}
}