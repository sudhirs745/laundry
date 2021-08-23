package com.sud.laundry.presentation.Home.ui.Search;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.Home.ui.Search.shopModel.ShopListResponse;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ShopProductResponse;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class ShopListPresenter implements ShopListContract.Presenter{

    String TAG ="LoginPresenter";

    @NonNull
    ShopListContract.View mView;
    Context mContext;

    public ShopListPresenter(@NonNull ShopListContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }



    @Override
    public void shopList(JsonObject jsonObject) {

        getShopListObservable(jsonObject).subscribeWith(getShopListObserver());
    }

    @Override
    public void shopProductList(JsonObject jsonObject) {
        getShopProductObservable(jsonObject).subscribeWith(getShopProductbserver());
    }


    public Observable<ShopListResponse> getShopListObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .shopList("" , jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<ShopListResponse> getShopListObserver() {
        return new DisposableObserver<ShopListResponse>() {

            @Override
            public void onNext(@NonNull ShopListResponse shopListResponse) {
                Log.e(TAG, "OnNext" + shopListResponse);
                mView.hideProgressBar();
                mView.ShopListResponse(shopListResponse);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "Error" + e);
                e.printStackTrace();
                mView.displayError(Utils.fetchErrorMessage(mContext,e));
                mView.hideProgressBar();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "Completed");
                mView.hideProgressBar();
            }
        };
    }


 public Observable<ShopProductResponse> getShopProductObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .shopProduct("" , jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<ShopProductResponse> getShopProductbserver() {
        return new DisposableObserver<ShopProductResponse>() {

            @Override
            public void onNext(@NonNull ShopProductResponse shopListResponse) {
                Log.e(TAG, "OnNext" + shopListResponse);
                mView.hideProgressBar();
                mView.ShopProdcutResponse(shopListResponse);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "Error" + e);
                e.printStackTrace();
                mView.displayError(Utils.fetchErrorMessage(mContext,e));
                mView.hideProgressBar();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "Completed");
                mView.hideProgressBar();
            }
        };
    }



}
