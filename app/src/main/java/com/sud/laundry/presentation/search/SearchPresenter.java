package com.sud.laundry.presentation.search;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;
import com.grocery.presentation.search.model.SearchListResponse;
import com.grocery.presentation.search.model.suggestion.SuggetionResponse;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class SearchPresenter implements SearchContract.Presenter{

    String TAG ="LoginPresenter";

    @NonNull
    SearchContract.View mView;
    Context mContext;



    public SearchPresenter(@NonNull SearchContract.View view, Context context){
        mView = view;

        mContext = context;
    }

    @Override
    public void searchList(JsonObject jsonObject ,int currentPage) {

        searchListObservable(jsonObject).subscribeWith(searchListObserver(currentPage));

    }

    @Override
    public void searchSuggetioList(JsonObject jsonObject) {

        searchSuggetionObservable(jsonObject).subscribeWith(searchSuggetionObserver());


    }

    public Observable<SearchListResponse> searchListObservable(JsonObject jsonObject ) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .searchList("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<SearchListResponse> searchListObserver(int currentPage) {
        return new DisposableObserver<SearchListResponse>() {

            @Override
            public void onNext(@NonNull SearchListResponse searchListResponse) {
                Log.e(TAG, "OnNext" + searchListResponse);
                mView.hideProgressBar();
                mView.dataResponse(searchListResponse , currentPage);

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


  public Observable<SuggetionResponse> searchSuggetionObservable(JsonObject jsonObject ) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .searchSuggetion("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<SuggetionResponse> searchSuggetionObserver() {
        return new DisposableObserver<SuggetionResponse>() {

            @Override
            public void onNext(@NonNull SuggetionResponse suggetionResponse) {
                Log.e(TAG, "OnNext" + suggetionResponse);
                mView.hideProgressBar();
                mView.dataSuggetion(suggetionResponse);

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
