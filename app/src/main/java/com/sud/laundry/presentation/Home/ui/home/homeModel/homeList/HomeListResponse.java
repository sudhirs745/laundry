package com.sud.laundry.presentation.Home.ui.home.homeModel.homeList;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class HomeListResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("ProductDetails")
    private List<ProductDetailsItem> productDetails;

    @SerializedName("BannerList")
    private List<BannerListItem> bannerList;

    @SerializedName("HeadlinesDetails")
    private List<HeadlinesDetailsItem> headlinesDetailsItemList;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public String getResponse() {
        return response;
    }

    public List<ProductDetailsItem> getProductDetails() {
        return productDetails;
    }

    public List<BannerListItem> getBannerList() {
        return bannerList;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public List<HeadlinesDetailsItem> getHeadlinesDetailsItemList() {
        return headlinesDetailsItemList;
    }
}