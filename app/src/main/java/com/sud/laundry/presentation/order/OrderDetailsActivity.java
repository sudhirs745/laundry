package com.sud.laundry.presentation.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.order.adapter.PaginationAdapterMyOrderDetails;
import com.grocery.presentation.order.adapter.ProductOrderRecycleViewDataAdapter;
import com.grocery.presentation.order.model.OrderInfo.OrderInfoResponse;
import com.grocery.presentation.order.model.orderAdd.OrderAddResponse;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsItem;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsResponse;
import com.grocery.presentation.order.model.orderDetails.ProDetailsListItem;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.PaginationScrollListener;
import com.grocery.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailsActivity extends BaseActivity implements OrderInfoContract.View {


    @BindView(R.id.tv_order_no)
    TextView tv_order_no;

    @BindView(R.id.lv_deliver_cancel)
    LinearLayout lv_deliver_cancel;

    @BindView(R.id.lv_traking)
    LinearLayout lv_traking;


    @BindView(R.id.im_qr_code)
    ImageView im_qr_code;

    @BindView(R.id.tv_payment_type)
    TextView tv_payment_type;

    @BindView(R.id.tv_total_payment)
    TextView tv_total_payment;

    @BindView(R.id.tv_order_date)
    TextView tv_order_date;

    @BindView(R.id.rv_product_details)
    RecyclerView rv_product_details;

    String OrderID;

    int UserId;
    OrderPresenter orderPresenter;
    String TAG = "OrderDetailsActivity";

    ProductOrderRecycleViewDataAdapter productOrderRecycleViewDataAdapter;
    List<ProDetailsListItem> proDetailsListItemArrayList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    public void setView() {
        super.setView();
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);
        orderPresenter = new OrderPresenter(this, this);
        OrderID = getIntent().getStringExtra(KeyData.KEY_ORDER_ID);
        Log.e(TAG, "setView: OrderID-> " + OrderID);

        productOrderRecycleViewDataAdapter = new ProductOrderRecycleViewDataAdapter(this, proDetailsListItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        rv_product_details.addItemDecoration(itemDecorator);
        rv_product_details.setLayoutManager(linearLayoutManager);
        rv_product_details.setItemAnimator(new DefaultItemAnimator());
        rv_product_details.setAdapter(productOrderRecycleViewDataAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        orderDetailsApi();
    }

    @OnClick({R.id.im_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;

        }
    }

    private void orderDetailsApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        jsonObject.addProperty("order_id", OrderID);
        orderPresenter.orderDetails(jsonObject);

    }

    @Override
    public void showProgressBar() {
        Utils.showProfressBarActivity(this);
    }

    @Override
    public void hideProgressBar() {
        Utils.dissmissActiviity();

    }

    @Override
    public void OrderInfoResponse(OrderInfoResponse orderInfoResponse) {

    }

    @Override
    public void OrderAddResponse(OrderAddResponse orderAddResponse) {

    }

    @Override
    public void PaymentSuccess(OrderAddResponse orderAddResponse, int PaymentStatus) {

    }

    @Override
    public void MyOrderResponse(OrderDetailsResponse orderDetailsResponse, int currentPage) {

    }

    @Override
    public void OrderDetailsResponse(OrderDetailsResponse orderDetailsResponse) {

        if (orderDetailsResponse.getStatus() == 200) {

            OrderDetailsItem result = orderDetailsResponse.getOrderDetails().get(0);
            tv_order_no.setText(result.getInvoicePrefix() + "" + result.getOrderId());
//            tv_name.setText("" + result.getFirstname());
//            tv_tracking.setText("" + result.getTrackingName());
//            tv_order_status.setText("" + result.getOrderStatus());
            tv_payment_type.setText("" + result.getPaymentStatusName());
            tv_total_payment.setText("RS " + result.getTotal());

            Utils.QRImageView(result.getQr_path(), im_qr_code);
            tv_order_date.setText(null != Utils.getdatetTimeFormat(result.getCreatedDate()) ? Utils.getdatetTimeFormat(result.getCreatedDate()) : "");

            if (result.getPaymentStatus() == 2 || result.getPaymentStatus() == 3) {
                lv_traking.setVisibility(View.VISIBLE);

            } else {
                lv_traking.setVisibility(View.GONE);
            }

            if (orderDetailsResponse.getOrderDetails() != null) {

                for (int i = 0; i < orderDetailsResponse.getOrderDetails().get(0).getProDetailsList().size(); i++) {

                    proDetailsListItemArrayList.add(orderDetailsResponse.getOrderDetails().get(0).getProDetailsList().get(i));

                }
                productOrderRecycleViewDataAdapter.notifyDataSetChanged();
            }

        }


    }

    @Override
    public void displayError(String s) {
        Log.e(TAG, "displayError: " + s);


    }

    @Override
    public void showSuccessfulMessage(String message) {
        Log.e(TAG, "showSuccessfulMessage: " + message);

    }

    @Override
    public void showFailedMessage(String message) {
        Log.e(TAG, "showFailedMessage: " + message);

    }
}