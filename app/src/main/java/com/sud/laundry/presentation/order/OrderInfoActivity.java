package com.sud.laundry.presentation.order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;

import com.grocery.network.NetworkClient;
import com.grocery.presentation.Home.HomeActivity;
import com.grocery.presentation.account.profile.AddAddressActivity;
import com.grocery.presentation.account.profile.AddressDetailsActivity;
import com.grocery.presentation.order.adapter.OrderInfoAdapter;
import com.grocery.presentation.order.adapter.OrderInfoAdapterCallback;
import com.grocery.presentation.order.model.OrderInfo.CartDetailsItem;
import com.grocery.presentation.order.model.OrderInfo.OrderInfoResponse;
import com.grocery.presentation.order.model.OrderInfo.UserAddressItem;
import com.grocery.presentation.order.model.orderAdd.OrderAddResponse;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsItem;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsResponse;
import com.grocery.presentation.paymetGatway.JSONParser;
import com.grocery.presentation.paymetGatway.checksum;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.NavigationUtils;
import com.grocery.utils.Utils;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderInfoActivity extends BaseActivity implements OrderInfoContract.View, OrderInfoAdapterCallback {
    String TAG = "OrderInfoActivity";

    @BindView(R.id.rv_cart_details)
    RecyclerView rv_cart_details;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.lv_address_message)
    LinearLayout lv_address_message;
    @BindView(R.id.im_delete)
    ImageView im_delete;

    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;

    @BindView(R.id.tv_place_order)
    TextView tv_place_order;

    @BindView(R.id.lv_place_order)
    LinearLayout lv_place_order;

    boolean PlaceOrderStatus = false;

    double totalPrice = 0;
    double PassTotalAmount = 0;
    double totalOrignalPrice = 0;


    String PinCode = "";
    String orderId = "";

    private List<CartDetailsItem> cartDetailsItemList = new ArrayList<>();
    private List<CartDetailsItem> cartDetailsItemListNotDeliver = new ArrayList<>();
    int UserId;
    OrderInfoAdapter orderInfoAdapter;
    OrderPresenter orderPresenter;

    ArrayList<CartDetailsItem> cartDetails;
    int AddressId = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_info;
    }

    @Override
    public void setView() {
        super.setView();

        im_delete.setVisibility(View.GONE);

        orderPresenter = new OrderPresenter(this, this);
        orderInfoAdapter = new OrderInfoAdapter(this, cartDetailsItemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_new));
        rv_cart_details.addItemDecoration(itemDecorator);
        rv_cart_details.setLayoutManager(linearLayoutManager);
        rv_cart_details.setItemAnimator(new DefaultItemAnimator());
        rv_cart_details.setAdapter(orderInfoAdapter);


    }

    private void loadNextPage() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId + "");
        jsonObject.addProperty("pin_code", PinCode + "");
        Log.e(TAG, "loadNextPage: " + jsonObject.toString());

        orderPresenter.orderinfo(jsonObject);

    }


    @Override
    protected void onResume() {
        super.onResume();
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);
        PinCode = AppSharedPreference.getInstance(this).getString(AppSharedPreference.SP_PIN_CODE, "0");

        loadNextPage();

    }

    @OnClick({R.id.im_back, R.id.im_search, R.id.tv_change_or_add_address, R.id.tv_place_order})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.im_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;

            case R.id.tv_change_or_add_address:

                Intent intent = new Intent(this, AddressDetailsActivity.class);
                intent.putExtra(KeyData.DELIVER_STATUS, 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavigationUtils.startActivity(this, intent);


//                NavigationUtils.startActivity(this, AddAddressActivity.class,
//                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                //  finish();

                break;
            case R.id.tv_place_order:

                if (PlaceOrderStatus) {
                    //showAlertDialog(totalPrice);

                    Intent intent1 = new Intent(this, CodOnlinePaymentActivity.class);
                    intent1.putExtra(KeyData.DELIVER_ADDRESS_NAME, tv_name.getText().toString());
                    intent1.putExtra(KeyData.DELIVER_ADDRESS, tv_address.getText().toString());
                    intent1.putExtra(KeyData.TOTAL_ALL_PRICE, tv_total_price.getText().toString());
                    intent1.putExtra(KeyData.TOTAL_PRICE, totalPrice + "");
                    intent1.putExtra(KeyData.TOTAL_CART, cartDetails);
                    intent1.putExtra(KeyData.ADDRESS_ID, AddressId + "");
                    intent1.putExtra(KeyData.PLACE_ORDER_STATUS, PlaceOrderStatus);
                    NavigationUtils.startActivity(this, intent1);


                } else {

                    Utils.toastErrorMessage(this, "Opps! You are not able to order ");
                }


                break;

        }
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

        boolean addresssetStatus = true;

        cartDetailsItemList.clear();
        cartDetailsItemListNotDeliver.clear();
        if (orderInfoResponse.getStatus() == 200) {

            if (orderInfoResponse.getOrderInfoList().getUserAddress().size() > 0) {

                for (int i = 0; i < orderInfoResponse.getOrderInfoList().getUserAddress().size(); i++) {

                    UserAddressItem addressItem = orderInfoResponse.getOrderInfoList().getUserAddress().get(i);

                    if (addressItem.getDefaultAddress() == 1) {
                        tv_name.setVisibility(View.VISIBLE);
                        tv_address.setVisibility(View.VISIBLE);
                        lv_address_message.setVisibility(View.VISIBLE);
                        addresssetStatus = false;
                        AddressId = orderInfoResponse.getOrderInfoList().getUserAddress().get(i).getId();
                        String Addname = addressItem.getName();
                        String AddressDetails = addressItem.getAddress1() + "," + addressItem.getAddress2() + " " + addressItem.getLandmark() + " " + addressItem.getCity() + " " + addressItem.getState() + " " + addressItem.getPostCode() + " \nMobile No." + addressItem.getMobile();
                        tv_name.setText(Addname + "");
                        tv_address.setText(AddressDetails);
                    }
                }

                if (addresssetStatus) {
                    tv_name.setVisibility(View.VISIBLE);
                    tv_address.setVisibility(View.VISIBLE);
                    lv_address_message.setVisibility(View.VISIBLE);

                    AddressId = orderInfoResponse.getOrderInfoList().getUserAddress().get(0).getId();
                    UserAddressItem addressItem = orderInfoResponse.getOrderInfoList().getUserAddress().get(0);
                    String Addname = addressItem.getName();
                    String AddressDetails = addressItem.getAddress1() + "," + addressItem.getAddress2() + " " + addressItem.getCity() + " " + addressItem.getPostCode() + " \nMobile No." + addressItem.getMobile();
                    tv_name.setText(Addname + " ");
                    tv_address.setText(AddressDetails);
                }

            } else {
                AddressId = 0;
                tv_name.setVisibility(View.GONE);
                tv_address.setVisibility(View.GONE);
                lv_address_message.setVisibility(View.GONE);

            }

            for (int i = 0; i < orderInfoResponse.getOrderInfoList().getCartDetails().size(); i++) {


                if (orderInfoResponse.getOrderInfoList().getCartDetails().get(i).getDeliverStatus()) {
                    cartDetailsItemList.add(orderInfoResponse.getOrderInfoList().getCartDetails().get(i));
                } else {
                    cartDetailsItemListNotDeliver.add(orderInfoResponse.getOrderInfoList().getCartDetails().get(i));
                }
            }

            if (cartDetailsItemListNotDeliver.size() > 0) {
                for (CartDetailsItem cartDetailsItem : cartDetailsItemListNotDeliver) {
                    cartDetailsItemList.add(cartDetailsItem);
                }
            }

            cartDetails = orderInfoResponse.getOrderInfoList().getCartDetails();
            cartTotalPrice(orderInfoResponse.getOrderInfoList().getCartDetails());

        } else {
            tv_name.setVisibility(View.GONE);
            tv_address.setVisibility(View.GONE);
            lv_address_message.setVisibility(View.GONE);

        }
        orderInfoAdapter.notifyDataSetChanged();
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

    @Override
    public void orderDetails(OrderDetailsItem orderDetailsItem) {

    }

    @Override
    public void retryPageLoad() {

    }


    private void cartTotalPrice(List<CartDetailsItem> cartDetails) {


        totalPrice = 0;
        totalOrignalPrice = 0;

        for (int i = 0; i < cartDetails.size(); i++) {
            CartDetailsItem cartDetailsItem = cartDetails.get(i);
            if (cartDetailsItem.getDeliverStatus()) {
                totalPrice = totalPrice + (cartDetails.get(i).getPrice() * cartDetails.get(i).getCartQuantity());
                totalOrignalPrice = totalOrignalPrice + (cartDetails.get(i).getOriginalPrice() * cartDetails.get(i).getCartQuantity());

                PlaceOrderStatus = true;

            }
        }

        if (PlaceOrderStatus) {
            lv_place_order.setBackgroundResource(R.drawable.chat_button);
            tv_place_order.setTextColor(Color.parseColor("#ffffff"));
        } else {
            lv_place_order.setBackgroundResource(R.drawable.chat_button_disable);
            tv_place_order.setTextColor(Color.parseColor("#000000"));

        }
        double savePrice = totalOrignalPrice - totalPrice;
        tv_price.setText("Rs. " + Utils.value2decimal(totalPrice));
        tv_total_price.setText("Saved Rs. " + Utils.value2decimal(savePrice));

    }


    private void callApiForOrder(List<CartDetailsItem> cartDetails) {

        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", UserId);
            jsonObject.addProperty("address_id", AddressId);

            JsonArray jsonArray = new JsonArray();
            totalPrice = 0;
            totalOrignalPrice = 0;

            PlaceOrderStatus = false;
            for (int i = 0; i < cartDetails.size(); i++) {
                CartDetailsItem cartDetailsItem = cartDetails.get(i);
                if (cartDetailsItem.getDeliverStatus()) {
                    JsonObject forProductDetails = new JsonObject();
                    forProductDetails.addProperty("product_id", cartDetailsItem.getProductId());
                    forProductDetails.addProperty("quantity", cartDetailsItem.getCartQuantity());
                    forProductDetails.addProperty("price", cartDetailsItem.getPrice());
                    forProductDetails.addProperty("pro_name", cartDetailsItem.getProductName());
                    forProductDetails.addProperty("pro_model", cartDetailsItem.getProductName());
                    forProductDetails.addProperty("total_w_tex", cartDetailsItem.getOriginalPrice());
                    forProductDetails.addProperty("discount", (cartDetailsItem.getOriginalPrice()) - (cartDetailsItem.getPrice()));
                    jsonArray.add(forProductDetails);

                    PlaceOrderStatus = true;

                }
            }
            jsonObject.add("product_details", jsonArray);
            Log.e(TAG, "Json object: " + jsonObject);

            if (PlaceOrderStatus) {
                orderPresenter.AddOrder(jsonObject);
            } else {

                Utils.toastErrorMessage(this, "Opps! You are not able to order ");
            }

        } catch (Exception e) {
            Log.e(TAG, "error: " + e.toString());
        }
    }


}



