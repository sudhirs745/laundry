package com.sud.laundry.presentation.product;

import com.google.gson.JsonObject;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.product.ProductModel.cartadd.CartResponse;
import com.grocery.presentation.product.ProductModel.productDetails.ProductDetailsResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;


@SuppressWarnings("WeakerAccess")
public interface ProductContract {

    interface View {

        void showProgressBar();
        void hideProgressBar();
        void ProListResponse(ProductListResponse productListResponse ,  int PageNo);
        void ProDetailsResponse(ProductDetailsResponse productDetailsResponse);
        void displayError(String s);
        void showSuccessfulMessage(String message);
        void showFailedMessage(String message);
        void addRemoveSussess(DataResponse dataResponse);

    }

    interface Presenter  {

        void getProductList(JsonObject jsonObject, int PageNo);
        void getProductDetails(JsonObject jsonObject );
        void addAndRemoveWishList(JsonObject jsonObject);


    }



}
