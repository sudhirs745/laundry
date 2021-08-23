package com.sud.laundry.presentation.order;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.grocery.network.NetworkClient;
import com.grocery.network.NetworkInterface;
import com.grocery.presentation.order.model.OrderInfo.OrderInfoResponse;
import com.grocery.presentation.order.model.orderAdd.OrderAddResponse;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsResponse;
import com.grocery.utils.Utils;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class OrderPresenter implements OrderInfoContract.Presenter {

    String TAG = "ProductPresenter";

    @NonNull
    OrderInfoContract.View mView;
    Context mContext;

    public OrderPresenter(@NonNull OrderInfoContract.View view, Context context) {
        mView = view;
        mContext = context;
    }


    @Override
    public void orderinfo(JsonObject jsonObject) {

        OrderInfoObservable(jsonObject).subscribeWith(OrderInfoListObserver());
    }

    @Override
    public void AddOrder(JsonObject jsonObject) {

        OrderAddObservable(jsonObject).subscribeWith(OrderAddListObserver());
    }

    @Override
    public void myorderDetals(JsonObject jsonObject , int  currentPage) {

        myOrderObservable(jsonObject).subscribeWith(myOrderAddListObserver(currentPage));
    }

    @Override
    public void orderPayment(JsonObject jsonObject, int PaymentStatus) {
        PaymentObservable(jsonObject).subscribeWith(PaymentObserver(PaymentStatus));
    }

    @Override
    public void orderDetails(JsonObject jsonObject) {
        OrderDetailsObservable(jsonObject).subscribeWith(OrderDetailsListObserver());

    }


    public Observable<OrderInfoResponse> OrderInfoObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getOrderInfoList("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<OrderInfoResponse> OrderInfoListObserver() {
        return new DisposableObserver<OrderInfoResponse>() {

            @Override
            public void onNext(@NonNull OrderInfoResponse orderInfoResponse) {
                Log.e(TAG, "OnNext" + orderInfoResponse);
                mView.hideProgressBar();
                mView.OrderInfoResponse(orderInfoResponse);

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


    public Observable<OrderAddResponse> OrderAddObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .Addorder("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<OrderAddResponse> OrderAddListObserver() {
        return new DisposableObserver<OrderAddResponse>() {

            @Override
            public void onNext(@NonNull OrderAddResponse orderInfoResponse) {
                Log.e(TAG, "OnNext" + orderInfoResponse);
                mView.hideProgressBar();
                mView.OrderAddResponse(orderInfoResponse);

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


    public Observable<OrderAddResponse>PaymentObservable(JsonObject jsonObject) {

        Log.e(TAG, "PaymentObservable: " + jsonObject.toString() );
        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .OrderPayment("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




    public DisposableObserver<OrderAddResponse> PaymentObserver(int PaymentStatus) {
        return new DisposableObserver<OrderAddResponse>() {

            @Override
            public void onNext(@NonNull OrderAddResponse orderInfoResponse) {
                Log.e(TAG, "OnNext" + orderInfoResponse);
                mView.hideProgressBar();
                mView.PaymentSuccess(orderInfoResponse , PaymentStatus);

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


 public Observable<OrderDetailsResponse> myOrderObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .myOrderDetails("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<OrderDetailsResponse> myOrderAddListObserver(int  currentPage) {
        return new DisposableObserver<OrderDetailsResponse>() {

            @Override
            public void onNext(@NonNull OrderDetailsResponse orderDetailsResponse) {
                Log.e(TAG, "OnNext" + orderDetailsResponse);
                mView.hideProgressBar();
                mView.MyOrderResponse(orderDetailsResponse , currentPage);

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




    public Observable<OrderDetailsResponse> OrderDetailsObservable(JsonObject jsonObject) {

        mView.showProgressBar();
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .OrderDetailsDetails("", jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public DisposableObserver<OrderDetailsResponse> OrderDetailsListObserver() {
        return new DisposableObserver<OrderDetailsResponse>() {

            @Override
            public void onNext(@NonNull OrderDetailsResponse orderDetailsResponse) {
                Log.e(TAG, "OnNext" + orderDetailsResponse);
                mView.hideProgressBar();
                mView.OrderDetailsResponse(orderDetailsResponse);

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
