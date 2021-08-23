package com.sud.laundry.presentation.order.model.OrderInfo;

import com.google.gson.annotations.SerializedName;

public class UserAddressItem{

	@SerializedName("post_group")
	private int postGroup;

	@SerializedName("city")
	private String city;

	@SerializedName("address_1")
	private String address1;

	@SerializedName("address_2")
	private String address2;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("mobile_other")
	private String mobileOther;

	@SerializedName("default_address")
	private int defaultAddress;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("post_code")
	private String postCode;

	@SerializedName("name")
	private String name;

	@SerializedName("company")
	private String company;

	@SerializedName("id")
	private int id;

	@SerializedName("state")
	private String state;

	@SerializedName("landmark")
	private String landmark;

	@SerializedName("longitude")
	private String longitude;

	public int getPostGroup(){
		return postGroup;
	}

	public String getCity(){
		return city;
	}

	public String getAddress1(){
		return address1;
	}

	public String getAddress2(){
		return address2;
	}

	public String getLatitude(){
		return latitude;
	}

	public String getMobile(){
		return mobile;
	}

	public String getMobileOther(){
		return mobileOther;
	}

	public int getDefaultAddress(){
		return defaultAddress;
	}

	public int getUserId(){
		return userId;
	}

	public String getPostCode(){
		return postCode;
	}

	public String getName(){
		return name;
	}

	public String getCompany(){
		return company;
	}

	public int getId(){
		return id;
	}

	public String getState(){
		return state;
	}

	public String getLandmark(){
		return landmark;
	}

	public String getLongitude(){
		return longitude;
	}
}