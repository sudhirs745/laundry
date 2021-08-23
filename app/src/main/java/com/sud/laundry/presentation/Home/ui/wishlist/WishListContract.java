package com.sud.laundry.presentation.Home.ui.wishlist;

import com.google.gson.JsonObject;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.product.ProductModel.productDetails.ProductDetailsResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;


@SuppressWarnings("WeakerAccess")
public interface WishListContract {

    interface View {


        void showProgressBar();

        void hideProgressBar();

        void displayError(String s);

        void ProDetailsResponse(ProductListResponse productListResponse);

        void addRemoveSussess(DataResponse dataResponse);

        void showSuccessfulMessage(String message);

        void showFailedMessage(String message);
    }

    interface Presenter {

        void getwishList(JsonObject jsonObject);

        void addAndRemoveWishList(JsonObject jsonObject);


    }


}
