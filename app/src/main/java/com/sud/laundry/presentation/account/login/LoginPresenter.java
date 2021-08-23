package com.sud.laundry.presentation.account.login;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.gson.JsonObject;
import com.sud.laundry.network.NetworkClient;
import com.sud.laundry.network.NetworkInterface;
import com.sud.laundry.presentation.account.model.LoginResponse;
import com.sud.laundry.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class LoginPresenter implements LoginContract.Presenter {

    String TAG = "LoginPresenter";

    @NonNull
    LoginContract.View mView;
    Context mContext;

    public LoginPresenter(@NonNull LoginContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mContext = context;

    }


    public void login(String username, String password) {
        String fcm_key =  "" ; //AppSharedPreference.getInstance(mContext).getFcmKey();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("fcm_key", fcm_key);
        LoginObservable(jsonObject).subscribeWith(LoginObserver());

    }



    public Observable<LoginResponse> LoginObservable(JsonObject jsonObject) {
        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .login(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<LoginResponse> LoginObserver() {
        return new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse dataResponse) {
                Log.e(TAG, "onNext: " + dataResponse.getStatus());
                mView.hideProgressBar();
                mView.loginSuccessful(dataResponse);
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



    @Override
    public void start() {


    }

}
