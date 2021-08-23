package com.sud.laundry.presentation.socalnetwork.group;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageListResponse;
import com.grocery.presentation.socalnetwork.group.model.groupInfo.GroupInfoResponse;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupListRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.utils.Utils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@SuppressWarnings("WeakerAccess")
public class GroupPresenter implements GroupContract.Presenter {

    String TAG = "LoginPresenter";

    @NonNull
    GroupContract.View mView;
    Context mContext;


    public GroupPresenter(@NonNull GroupContract.View view, Context context) {
        mView = view;
        mContext = context;
    }


    @Override
    public void getGroupList(JsonObject jsonObject) {
        GroupListObservable(jsonObject).subscribeWith(GroupListObserver());
    }

    @Override
    public void createGroup(JsonObject jsonObject) {

        createGroupObservable(jsonObject).subscribeWith(createGroupObserver());
    }

    @Override
    public void getGroupMessage(JsonObject jsonObject) {
        getGroupMessageObservable(jsonObject).subscribeWith(getGroupMessageObserver());


    }

    @Override
    public void sendMessage(JsonObject jsonObject) {
        SendMessageObservable(jsonObject).subscribeWith(SendMessageObserver());

        // showMessageUpdateObservable(jsonObject).subscribeWith(showMessageUpdateObserver());
    }


    @Override
    public void getgroupInfo(JsonObject jsonObject) {
        groupInfoObservable(jsonObject).subscribeWith(groupInfoObserver());
    }


    @Override
    public void sendImageMessage(String userId, String roomId, String Name, String ImagePath) {

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody room_Id = RequestBody.create(MediaType.parse("text/plain"), roomId);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), Name);

        MultipartBody.Part fileToUpload = null;
        try {
            File file = new File(ImagePath);
            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                fileToUpload = MultipartBody.Part.createFormData("myImage", file.getName(), requestBody);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

        }

        SendImageMessageObservable(user_id, room_Id, name, fileToUpload).subscribeWith(SendMessageObserver());
    }


    @Override
    public void showMessageUpdate(JsonObject jsonObject) {

        showMessageUpdateObservable(jsonObject).subscribeWith(showMessageUpdateObserver());

    }


    @Override
    public void deleteMessage(JsonObject jsonObject) {

        deleteMessageObservable(jsonObject).subscribeWith(deleteMessageObserver());

    }

    @Override
    public void deleteOtherUserMessage(JsonObject jsonObject) {

        deleteOtherUserMessageObservable(jsonObject).subscribeWith(deleteMessageObserver());

    }


    public Observable<GroupListRes> GroupListObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getGroupList("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<GroupListRes> GroupListObserver() {
        return new DisposableObserver<GroupListRes>() {

            @Override
            public void onNext(@NonNull GroupListRes groupListRes) {
                Log.e(TAG, "OnNext" + groupListRes);
                mView.hideProgressBar();
                mView.dataGroupList(groupListRes);

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


    public Observable<CreateGroupRes> createGroupObservable(JsonObject jsonObject) {

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


    public Observable<MessageListResponse> getGroupMessageObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getMessageList("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<MessageListResponse> getGroupMessageObserver() {
        return new DisposableObserver<MessageListResponse>() {

            @Override
            public void onNext(@NonNull MessageListResponse messageListResponse) {
                Log.e(TAG, "OnNext" + messageListResponse);
                mView.hideProgressBar();
                mView.getMessageList(messageListResponse);
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


    public Observable<CreateGroupRes> SendMessageObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .sendMessage("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    public Observable<CreateGroupRes> SendImageMessageObservable(RequestBody user_id,
                                                                 RequestBody room_Id,
                                                                 RequestBody name,
                                                                 MultipartBody.Part ImagePart) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .sendImageMessage(user_id, room_Id, name, ImagePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    public DisposableObserver<CreateGroupRes> SendMessageObserver() {
        return new DisposableObserver<CreateGroupRes>() {

            @Override
            public void onNext(@NonNull CreateGroupRes createGroupRes) {
                Log.e(TAG, "OnNext" + createGroupRes);
                mView.hideProgressBar();
                mView.sendMessageRes(createGroupRes);
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


    public Observable<GroupInfoResponse> groupInfoObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .groupInfoDetails("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    public DisposableObserver<GroupInfoResponse> groupInfoObserver() {
        return new DisposableObserver<GroupInfoResponse>() {

            @Override
            public void onNext(@NonNull GroupInfoResponse groupInfoResponse) {
                Log.e(TAG, "OnNext" + groupInfoResponse);
                mView.hideProgressBar();
                mView.groupInfoResponse(groupInfoResponse);
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


    public Observable<CreateGroupRes> showMessageUpdateObservable(JsonObject jsonObject) {

        Log.e(TAG, "showMessageUpdateObservable: --  " + jsonObject.toString());

        //  mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .showUpdateMessage("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<CreateGroupRes> showMessageUpdateObserver() {
        return new DisposableObserver<CreateGroupRes>() {

            @Override
            public void onNext(@NonNull CreateGroupRes createGroupRes) {
                Log.e(TAG, "showMessageUpdateObservable " + createGroupRes);
                mView.hideProgressBar();
                //mView.sendMessageRes(createGroupRes);
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


    public Observable<CreateGroupRes> deleteMessageObservable(JsonObject jsonObject) {

        Log.e(TAG, "showMessageUpdateObservable: --  " + jsonObject.toString());
        //  mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .messageDelete("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<CreateGroupRes> deleteOtherUserMessageObservable(JsonObject jsonObject) {

        Log.e(TAG, "showMessageUpdateObservable: --  " + jsonObject.toString());
        //  mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .groupMessageDelete("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public DisposableObserver<CreateGroupRes> deleteMessageObserver() {
        return new DisposableObserver<CreateGroupRes>() {

            @Override
            public void onNext(@NonNull CreateGroupRes createGroupRes) {
                Log.e(TAG, "OnNext" + createGroupRes);
                mView.hideProgressBar();
                mView.deleteMessage(createGroupRes);
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
