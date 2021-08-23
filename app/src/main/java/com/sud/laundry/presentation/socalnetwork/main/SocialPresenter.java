package com.sud.laundry.presentation.socalnetwork.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.main.model.SocialDetailsRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class SocialPresenter implements SocialContract.Presenter {

    String TAG = "LoginPresenter";

    @NonNull
    SocialContract.View mView;
    Context mContext;


    public SocialPresenter(@NonNull SocialContract.View view, Context context) {
        mView = view;

        mContext = context;
    }

    @Override
    public void userSocialDetails(JsonObject jsonObject) {

        userSocialDetailsObservable(jsonObject).subscribeWith(userSocialDetailsObserver());

    }


    public Observable<SocialDetailsRes> userSocialDetailsObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .userSocialDetails("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<SocialDetailsRes> userSocialDetailsObserver() {
        return new DisposableObserver<SocialDetailsRes>() {

            @Override
            public void onNext(@NonNull SocialDetailsRes socialDetailsRes) {
                Log.e(TAG, "OnNext" + socialDetailsRes);
                mView.hideProgressBar();
                mView.showUserDetails(socialDetailsRes);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "Error" + e);
                e.printStackTrace();
                mView.displayError(Utils.fetchErrorMessage(mContext, e));
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
