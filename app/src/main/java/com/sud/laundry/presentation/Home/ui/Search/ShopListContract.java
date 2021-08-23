package com.sud.laundry.presentation.Home.ui.Search;

import com.google.gson.JsonObject;
import com.grocery.presentation.Home.ui.Search.shopModel.ShopListResponse;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ShopProductResponse;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;


@SuppressWarnings("WeakerAccess")
public interface ShopListContract {

    interface View {


        void showProgressBar();

        void hideProgressBar();

        void displayError(String s);

        void ShopListResponse(ShopListResponse shopListResponse);
        void ShopProdcutResponse(ShopProductResponse shopProductResponse);


        void showSuccessfulMessage(String message);

        void showFailedMessage(String message);
    }

    interface Presenter {
        void shopList(JsonObject jsonObject);
        void shopProductList(JsonObject jsonObject);

    }


}
