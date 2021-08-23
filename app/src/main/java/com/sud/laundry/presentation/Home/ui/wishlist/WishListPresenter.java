package com.sud.laundry.presentation.Home.ui.wishlist;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class WishListPresenter implements WishListContract.Presenter{

    String TAG ="LoginPresenter";

    @NonNull
    WishListContract.View mView;
    Context mContext;

    public WishListPresenter(@NonNull WishListContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getwishList(JsonObject jsonObject) {

        getWishListObservable(jsonObject).subscribeWith(getWishListObserver());
    }

    @Override
    public void addAndRemoveWishList(JsonObject jsonObject) {

        AddUpdateObservable(jsonObject).subscribeWith(AddUpdateObserver());

    }


    public Observable<ProductListResponse> getWishListObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getWishList("" , jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<ProductListResponse> getWishListObserver() {
        return new DisposableObserver<ProductListResponse>() {

            @Override
            public void onNext(@NonNull ProductListResponse productListResponse) {
                Log.e(TAG, "OnNext" + productListResponse);
                mView.hideProgressBar();
                mView.ProDetailsResponse(productListResponse);

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



    public Observable<DataResponse> AddUpdateObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .addRenoveUpdate("" ,jsonObject )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<DataResponse> AddUpdateObserver() {
        return new DisposableObserver<DataResponse>() {

            @Override
            public void onNext(@NonNull DataResponse dataResponse) {
                Log.e(TAG, "OnNext" + dataResponse);
                mView.hideProgressBar();
                mView.addRemoveSussess(dataResponse);

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
