package com.sud.laundry.presentation.Home.ui.profile;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.account.model.UserDetals.UsersDataResponse;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.account.model.rewardPoints.RewardsPointRes;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class ProfilePresenter implements ProfileContract.Presenter {

    String TAG = "LoginPresenter";

    @NonNull
    ProfileContract.View mView;
    Context mContext;

    public ProfilePresenter(@NonNull ProfileContract.View view, Context context) {
        mView = view;
        mContext = context;


    }

    @Override
    public void userData(String token, JsonObject jsonObject) {

        getUserDataObservable(token, jsonObject).subscribeWith(UserDetalsObserver());
    }

    @Override
    public void deleteAddress(JsonObject jsonObject) {

        DeleteAddressObservable(jsonObject).subscribeWith(DeleteAddressObserver());
    }

    @Override
    public void setDefaultAddress(JsonObject jsonObject) {

        DefaultAddressObservable(jsonObject).subscribeWith(DeleteAddressObserver());

    }

    @Override
    public void RewardPointsGet(JsonObject jsonObject) {

        rewardPointsObservable(jsonObject).subscribeWith(rewardPointsObserver());

    }


    public Observable<UsersDataResponse> getUserDataObservable(String Token, JsonObject jsonObject) {
        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .GetUserData(Token, jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<UsersDataResponse> UserDetalsObserver() {
        return new DisposableObserver<UsersDataResponse>() {
            @Override
            public void onNext(UsersDataResponse usersDataResponse) {
                Log.e(TAG, "onNext: " + usersDataResponse.getStatus());
                mView.hideProgressBar();
                mView.userDataSussessful(usersDataResponse);
            }

            @Override
            public void onError(Throwable e) {

                Utils.fetchErrorMessage(mContext, e);
                Log.e(TAG, "Error" + e);
                e.printStackTrace();
                mView.showFailedMessage(Utils.fetchErrorMessage(mContext, e) + " ");
                mView.hideProgressBar();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "Completed");
                mView.hideProgressBar();
            }
        };
    }


    public Observable<DataResponse> DeleteAddressObservable(JsonObject jsonObject) {
        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .DeleteAddress("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<DataResponse> DefaultAddressObservable(JsonObject jsonObject) {
        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .setDefaultAddress("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<DataResponse> DeleteAddressObserver() {
        return new DisposableObserver<DataResponse>() {
            @Override
            public void onNext(DataResponse dataResponse) {
                Log.e(TAG, "onNext: " + dataResponse.getStatus());
                mView.hideProgressBar();
                mView.SignupSuccessful(dataResponse);
            }

            @Override
            public void onError(Throwable e) {

                Utils.fetchErrorMessage(mContext, e);
                Log.e(TAG, "Error" + e);
                e.printStackTrace();
                mView.showFailedMessage(Utils.fetchErrorMessage(mContext, e) + " ");
                mView.hideProgressBar();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "Completed");
                mView.hideProgressBar();
            }
        };
    }


    public Observable<RewardsPointRes> rewardPointsObservable(JsonObject jsonObject) {
        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .RewardsPointGet("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<RewardsPointRes> rewardPointsObserver() {
        return new DisposableObserver<RewardsPointRes>() {
            @Override
            public void onNext(RewardsPointRes rewardsPointRes) {
                Log.e(TAG, "onNext: " + rewardsPointRes.getStatus());
                mView.hideProgressBar();
                mView.RewardsPointSuccessful(rewardsPointRes);
            }

            @Override
            public void onError(Throwable e) {
                Utils.fetchErrorMessage(mContext, e);
                Log.e(TAG, "Error" + e);
                e.printStackTrace();
                mView.showFailedMessage(Utils.fetchErrorMessage(mContext, e) + " ");
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
