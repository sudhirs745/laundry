package com.sud.laundry.presentation.socalnetwork.socialnotification;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.socalnetwork.search.model.SocialSearchRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.presentation.socalnetwork.socialnotification.model.SocialNotiyRes;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class NotificationPresenter implements NotificationContract.Presenter{

    String TAG = NotificationPresenter.class.getName();
    @NonNull
    NotificationContract.View mView;
    Context mContext;


    public NotificationPresenter(@NonNull NotificationContract.View view, Context context){
        mView = view;

        mContext = context;
    }

    @Override
    public void getNotification(JsonObject jsonObject ) {

        getNotificationObservable(jsonObject).subscribeWith(getNotificationObserver());

    }


    public Observable<SocialNotiyRes> getNotificationObservable(JsonObject jsonObject ) {

       // mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getNotification("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public DisposableObserver<SocialNotiyRes> getNotificationObserver() {
        return new DisposableObserver<SocialNotiyRes>() {

            @Override
            public void onNext(@NonNull SocialNotiyRes socialNotiyRes) {
                Log.e(TAG, "OnNext " + socialNotiyRes);
                mView.hideProgressBar();
                mView.dataNotification(socialNotiyRes);

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
