package com.sud.laundry.presentation.Home.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoriesItem;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class HomePresenter implements HomeContract.Presenter{

    String TAG ="LoginPresenter";

    @NonNull
    HomeContract.View mView;
    Context mContext;



    public HomePresenter(@NonNull HomeContract.View view, Context context){
        mView = view;

        mContext = context;
    }

    @Override
    public void getCategory() {

        GetGategoryObservable().subscribeWith(categoryObserver());

    }

    @Override
    public void HomeListDetall(JsonObject jsonObject) {
        GetHomeDetailsObservable(jsonObject).subscribeWith(GetHomeDetailsObserver());
    }


    public Observable<CategoryRes> GetGategoryObservable( ) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getCategoryy()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<CategoryRes> categoryObserver() {
        return new DisposableObserver<CategoryRes>() {

            @Override
            public void onNext(@NonNull CategoryRes categoryRes) {
                Log.e(TAG, "OnNext" + categoryRes);
                mView.hideProgressBar();
                mView.dataResponse(categoryRes);

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



    public Observable<HomeListResponse> GetHomeDetailsObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .HomeDetails("" ,jsonObject )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<HomeListResponse> GetHomeDetailsObserver() {
        return new DisposableObserver<HomeListResponse>() {

            @Override
            public void onNext(@NonNull HomeListResponse homeListResponse) {
                Log.e(TAG, "OnNext" + homeListResponse);
                mView.hideProgressBar();
                mView.catProListResponse(homeListResponse);

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
