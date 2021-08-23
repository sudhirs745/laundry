package com.sud.laundry.presentation.socalnetwork.search;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.search.model.SearchListResponse;
import com.grocery.presentation.search.model.suggestion.SuggetionResponse;
import com.grocery.presentation.socalnetwork.search.model.SocialSearchRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class SocialSearchPresenter implements SocialSearchContract.Presenter{

    String TAG ="LoginPresenter";

    @NonNull
    SocialSearchContract.View mView;
    Context mContext;



    public SocialSearchPresenter(@NonNull SocialSearchContract.View view, Context context){
        mView = view;

        mContext = context;
    }

    @Override
    public void usersearchList(JsonObject jsonObject ) {

        searchUserObservable(jsonObject).subscribeWith(searchUserObserver());

    }

    @Override
    public void addFriendAndConfirm(JsonObject jsonObject) {

        AddConfirmFriendUserObservable(jsonObject).subscribeWith(AddConfirmFriendUserObserver());
    }


    public Observable<SocialSearchRes> searchUserObservable(JsonObject jsonObject ) {

       // mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .userSearch("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public DisposableObserver<SocialSearchRes> searchUserObserver() {
        return new DisposableObserver<SocialSearchRes>() {

            @Override
            public void onNext(@NonNull SocialSearchRes socialSearchRes) {
                Log.e(TAG, "OnNext" + socialSearchRes);
                mView.hideProgressBar();
                mView.dataSearch(socialSearchRes);

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



 public Observable<AddFriendResponse> AddConfirmFriendUserObservable(JsonObject jsonObject ) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .addFriendConfirm("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public DisposableObserver<AddFriendResponse> AddConfirmFriendUserObserver() {
        return new DisposableObserver<AddFriendResponse>() {

            @Override
            public void onNext(@NonNull AddFriendResponse addFriendResponse) {
                Log.e(TAG, "OnNext" + addFriendResponse);
                mView.hideProgressBar();
                mView.dataResAddFriend(addFriendResponse);

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
