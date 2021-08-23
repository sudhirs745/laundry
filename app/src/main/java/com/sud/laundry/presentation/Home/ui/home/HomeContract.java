package com.sud.laundry.presentation.Home.ui.home;

import android.widget.EditText;

import com.google.gson.JsonObject;
import com.grocery.bases.BasePresenter;
import com.grocery.bases.BaseView;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;


@SuppressWarnings("WeakerAccess")
public interface HomeContract {

    interface View {


        void showProgressBar();
        void hideProgressBar();
        void dataResponse(CategoryRes categoryRes);
        void catProListResponse(HomeListResponse homeListResponse);
        void displayError(String s);
        void showSuccessfulMessage(String message);
        void showFailedMessage(String message);
    }

    interface Presenter  {


        void getCategory();
        void HomeListDetall(JsonObject jsonObject);



    }



}
