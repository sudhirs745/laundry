package com.sud.laundry.presentation.socalnetwork.friend;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.search.model.SocialSearchRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class FriendPresenter implements FriendContract.Presenter{

    String TAG ="LoginPresenter";

    @NonNull
    FriendContract.View mView;
    Context mContext;



    public FriendPresenter(@NonNull FriendContract.View view, Context context){
        mView = view;

        mContext = context;
    }

    @Override
    public void MyfriendList(JsonObject jsonObject ) {

        MyfriendObservable(jsonObject).subscribeWith(MyfriendObserver());

    }

    @Override
    public void CancelFriendRequest(JsonObject jsonObject) {

        CancelfriendRequestObservable(jsonObject).subscribeWith(CancelfriendRequestObserver());
    }

    @Override
    public void addFriendAndConfirm(JsonObject jsonObject) {

        AddConfirmFriendUserObservable(jsonObject).subscribeWith(AddConfirmFriendUserObserver());
    }

    @Override
    public void createGroup(JsonObject jsonObject) {

        createGroupObservable(jsonObject).subscribeWith(createGroupObserver());
    }

    public Observable<MyFriendListRes> MyfriendObservable(JsonObject jsonObject ) {

         mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .myFriendList("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public DisposableObserver<MyFriendListRes> MyfriendObserver() {
        return new DisposableObserver<MyFriendListRes>() {

            @Override
            public void onNext(@NonNull MyFriendListRes myFriendListRes) {
                Log.e(TAG, "OnNext" + myFriendListRes);
                mView.hideProgressBar();
                mView.dataMyFriend(myFriendListRes);

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



    public Observable<AddFriendResponse> CancelfriendRequestObservable(JsonObject jsonObject ) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .cancelFriendRequest("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public DisposableObserver<AddFriendResponse> CancelfriendRequestObserver() {
        return new DisposableObserver<AddFriendResponse>() {

            @Override
            public void onNext(@NonNull AddFriendResponse addFriendResponse) {
                Log.e(TAG, "OnNext" + addFriendResponse);
                mView.hideProgressBar();
                mView.cancelFriend(addFriendResponse);

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
                mView.cancelFriend(addFriendResponse);

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


 public Observable<CreateGroupRes> createGroupObservable(JsonObject jsonObject ) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .createGroup("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public DisposableObserver<CreateGroupRes> createGroupObserver() {
        return new DisposableObserver<CreateGroupRes>() {

            @Override
            public void onNext(@NonNull CreateGroupRes createGroupRes) {
                Log.e(TAG, "OnNext" + createGroupRes);
                mView.hideProgressBar();
                mView.createGroup(createGroupRes);

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
