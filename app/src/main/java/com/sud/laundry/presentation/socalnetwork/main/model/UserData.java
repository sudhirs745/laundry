package com.sud.laundry.presentation.socalnetwork.main.model;

import com.google.gson.annotations.SerializedName;

public class UserData{

	@SerializedName("profile_url")
	private String profileUrl;

	@SerializedName("referral_code")
	private String referralCode;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public String getProfileUrl(){
		return profileUrl;
	}

	public String getReferralCode(){
		return referralCode;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getUsername(){
		return username;
	}
}