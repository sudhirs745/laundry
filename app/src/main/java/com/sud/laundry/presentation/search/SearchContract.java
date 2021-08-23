package com.sud.laundry.presentation.search;

import com.google.gson.JsonObject;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;
import com.grocery.presentation.search.model.SearchListResponse;
import com.grocery.presentation.search.model.suggestion.SuggetionResponse;


@SuppressWarnings("WeakerAccess")
public interface SearchContract {

    interface View {


        void showProgressBar();
        void hideProgressBar();
        void dataResponse(SearchListResponse searchListResponse , int currentPage);
        void dataSuggetion(SuggetionResponse suggetionResponse);
        void displayError(String s);
        void showSuccessfulMessage(String message);
        void showFailedMessage(String message);
    }

    interface Presenter  {

        void searchList(JsonObject jsonObject , int currentPage);
        void searchSuggetioList(JsonObject jsonObject);



    }



}
