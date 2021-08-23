package com.sud.laundry.presentation.product;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.product.ProductModel.productDetails.ProductDetailsResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class ProductPresenter implements ProductContract.Presenter{

    String TAG ="ProductPresenter";

    @NonNull
    ProductContract.View mView;
    Context mContext;

    public ProductPresenter(@NonNull ProductContract.View view, Context context){
        mView = view;

        mContext = context;
    }

    @Override
    public void getProductList(JsonObject jsonObject , int pageNo) {
        GetProductListObservable(jsonObject).subscribeWith(GetProductListObserver(pageNo));
    }

    @Override
    public void getProductDetails(JsonObject jsonObject) {

        GetProductDetailsObservable(jsonObject).subscribeWith(GetProductDetailsObserver());
    }

    @Override
    public void addAndRemoveWishList(JsonObject jsonObject) {

        AddUpdateObservable(jsonObject).subscribeWith(AddUpdateObserver());
    }

    public Observable<ProductListResponse> GetProductListObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .ProductList("" ,jsonObject )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<ProductListResponse> GetProductListObserver(int PageNo) {
        return new DisposableObserver<ProductListResponse>() {

            @Override
            public void onNext(@NonNull ProductListResponse productListResponse) {
                Log.e(TAG, "OnNext" + productListResponse);
                mView.hideProgressBar();
                mView.ProListResponse(productListResponse , PageNo);

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



    public Observable<ProductDetailsResponse> GetProductDetailsObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .ProductDetails("" ,jsonObject )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public DisposableObserver<ProductDetailsResponse> GetProductDetailsObserver() {
        return new DisposableObserver<ProductDetailsResponse>() {

            @Override
            public void onNext(@NonNull ProductDetailsResponse productDetailsResponse) {
                Log.e(TAG, "OnNext" + productDetailsResponse);
                mView.hideProgressBar();
                mView.ProDetailsResponse(productDetailsResponse );

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
