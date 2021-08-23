package com.sud.laundry.presentation.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.network.NetworkClient;
import com.grocery.presentation.account.profile.AddressDetailsActivity;
import com.grocery.presentation.order.model.OrderInfo.CartDetailsItem;
import com.grocery.presentation.order.model.OrderInfo.OrderInfoResponse;
import com.grocery.presentation.order.model.orderAdd.OrderAddResponse;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsResponse;
import com.grocery.presentation.paymetGatway.JSONParser;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.NavigationUtils;
import com.grocery.utils.Utils;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CodOnlinePaymentActivity extends BaseActivity implements OrderInfoContract.View, PaytmPaymentTransactionCallback {

    String TAG = "CodOnlinePaymentActivity";


    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;

    @BindView(R.id.tv_place_order)
    TextView tv_place_order;

    @BindView(R.id.lv_place_order)
    LinearLayout lv_place_order;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    ArrayList<CartDetailsItem> cartDetails;

    String addressNname;
    String address;
    String StTotalPrice;
    String StTotalAllPrice;
    String addressId;

    int UserId;

    boolean PlaceOrderStatus;

    double totalPrice = 0;
    double PassTotalAmount = 0;
    double totalOrignalPrice = 0;

    OrderPresenter orderPresenter;
    String orderId = "";

    int OnlineOrCod = 0;    //  0 for cod  1 for online

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cod_online_payment;
    }

    @Override
    public void setView() {
        super.setView();

        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);
        cartDetails = (ArrayList<CartDetailsItem>) getIntent().getSerializableExtra(KeyData.TOTAL_CART);
        addressNname = getIntent().getStringExtra(KeyData.DELIVER_ADDRESS_NAME);
        address = getIntent().getStringExtra(KeyData.DELIVER_ADDRESS);
        StTotalPrice = getIntent().getStringExtra(KeyData.TOTAL_PRICE);
        StTotalAllPrice = getIntent().getStringExtra(KeyData.TOTAL_ALL_PRICE);
        addressId = getIntent().getStringExtra(KeyData.ADDRESS_ID);
        PlaceOrderStatus = getIntent().getBooleanExtra(KeyData.PLACE_ORDER_STATUS, false);

        tv_name.setText(addressNname + "");
        tv_address.setText(address + "");
//        tv_price.setText(StTotalPrice + "");
//        tv_total_price.setText(StTotalAllPrice + "");


        cartTotalPrice(cartDetails);

        orderPresenter = new OrderPresenter(this, this);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.cod:
                        OnlineOrCod = 0;
                        //Toast.makeText(getApplicationContext(), " COD", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.online:
                        OnlineOrCod = 1;
                        //  Toast.makeText(getApplicationContext(), " Online", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

    }


    private void callApiForOrder(ArrayList<CartDetailsItem> cartDetails) {


        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", UserId);
            jsonObject.addProperty("address_id", addressId);

            JsonArray jsonArray = new JsonArray();
            PassTotalAmount = totalPrice;
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

                Log.e(TAG, "callApiForOrder: " + jsonObject.toString());
                orderPresenter.AddOrder(jsonObject);
            } else {

                Utils.toastErrorMessage(this, "Opps! You are not able to order ");
            }

        } catch (Exception e) {
            Log.e(TAG, "error: " + e.toString());
        }
    }


    @OnClick({R.id.im_back, R.id.tv_place_order})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;

            case R.id.tv_place_order:

                Log.e(TAG, "onClick: tv place order  ");
                callApiForOrder(cartDetails);

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

    }

    @Override
    public void OrderAddResponse(OrderAddResponse orderAddResponse) {
        if (orderAddResponse.getStatus() == 200) {

            orderId = orderAddResponse.getOrderId() + "";
            AppSharedPreference.getInstance(this).setInteger(AppSharedPreference.SP_CART_VALUE, orderAddResponse.getTotalcart());
            // Utils.toastErrorMessage(this, "" + orderAddResponse.getResponse());

            if (OnlineOrCod == 1) {

                Log.e(TAG, "OrderAddResponse: Online  --  ");

                sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
                dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//                Intent intent = new Intent(OrderInfoActivity.this, checksum.class);
//                intent.putExtra("orderid", orderAddResponse.getOrderId()+"");
//                intent.putExtra("custid", UserId+"");
//                intent.putExtra("TotalPrice", ((int)PassTotalAmount)+"");
//                startActivity(intent);

            } else {
                apicallForpayment(orderAddResponse.getOrderId() + "", UserId, 2, "COD_GORCIYA_MART" + orderAddResponse.getOrderId(), "COD" + orderAddResponse.getOrderId(), 1);

            }

        } else if (orderAddResponse.getStatus() == 202) {

            Utils.toastErrorMessage(this, orderAddResponse.getResponse());

        } else {
            Utils.toastErrorMessage(this, getString(R.string.opps_something_wrong));
        }


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


    @Override
    public void PaymentSuccess(OrderAddResponse orderAddResponse, int PaymentStatus) {

        if (orderAddResponse.getStatus() == 200) {
            AppSharedPreference.getInstance(this).setInteger(AppSharedPreference.SP_CART_VALUE, orderAddResponse.getTotalcart());
            Utils.toastErrorMessage(this, "" + orderAddResponse.getResponse());
            OrderSucceffyly(orderId, PaymentStatus);
        } else if (orderAddResponse.getStatus() == 202) {
            OrderSucceffyly(orderId, 3);
            Utils.toastErrorMessage(this, orderAddResponse.getResponse());
        } else {
            Utils.toastErrorMessage(this, getString(R.string.opps_something_wrong));
        }

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


    private void apicallForpayment(String OrderId, int UserId, int stPaymetStatus, String trans_id, String bank_ref_no, int PaymentStatus) {


        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("user_id", UserId);
        jsonObject.addProperty("order_id", OrderId);
        jsonObject.addProperty("trans_id", trans_id);
        jsonObject.addProperty("bank_ref_no", bank_ref_no);
        jsonObject.addProperty("amount", PassTotalAmount);
        jsonObject.addProperty("payment_status", stPaymetStatus);

        Log.e(TAG, "apicallForpayment: " + jsonObject.toString());

        orderPresenter.orderPayment(jsonObject, PaymentStatus);


    }


    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(CodOnlinePaymentActivity.this);

        //private String orderId , mid, custid, amt;
        String url = NetworkClient.PAYTM_URL;    //"http://13.233.246.90/paytm_checksum/generateChecksum.php";

        //  String url ="https://www.blueappsoftware.com/payment/payment_paytm/generateChecksum.php";
        String varifyurl = NetworkClient.PAYTM_URL_varifyurl; //"https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

        String CHECKSUMHASH = "";

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(CodOnlinePaymentActivity.this);
            String param =
                    "MID=" + NetworkClient.PAYTIM_mid +
                            "&ORDER_ID=" + orderId +
                            "&CUST_ID=" + UserId +
                            "&CHANNEL_ID=WAP&TXN_AMOUNT=" + PassTotalAmount + "&WEBSITE=WEBSTAGING" +
                            "&CALLBACK_URL=" + varifyurl + "&INDUSTRY_TYPE_ID=Retail";

            JSONObject jsonObject = jsonParser.makeHttpRequest(url, "POST", param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>", jsonObject.toString());
            if (jsonObject != null) {
                Log.e("CheckSum result >>", jsonObject.toString());
                try {

                    CHECKSUMHASH = jsonObject.has("CHECKSUMHASH") ? jsonObject.getString("CHECKSUMHASH") : "";
                    Log.e("CheckSum result >>", CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ", "  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            PaytmPGService Service = PaytmPGService.getStagingService();
            // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", NetworkClient.PAYTIM_mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", UserId + "");
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", PassTotalAmount + "");
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put("CALLBACK_URL", varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH", CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param " + paramMap.toString());
            Service.initialize(Order, null);
            // start payment service call here
            Service.startPaymentTransaction(CodOnlinePaymentActivity.this, true, true,
                    CodOnlinePaymentActivity.this);


        }

    }


    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Log.e("checksum ", " respon true " + inResponse.toString());


        String STATUS = inResponse.getString("STATUS");
        String BANKNAME = inResponse.getString("BANKNAME");
        String ORDERID = inResponse.getString("ORDERID");
        String TXNAMOUNT = inResponse.getString("TXNAMOUNT");
        String TXNDATE = inResponse.getString("TXNDATE");

        String MID = inResponse.getString("MID");
        String TXNID = inResponse.getString("TXNID");
        String RESPCODE = inResponse.getString("RESPCODE");
        String PAYMENTMODE = inResponse.getString("PAYMENTMODE");
        String BANKTXNID = inResponse.getString("BANKTXNID");
        String CURRENCY = inResponse.getString("CURRENCY");
        String GATEWAYNAME = inResponse.getString("GATEWAYNAME");


        Log.e(TAG, "onTransactionResponse: " + STATUS);

        if (STATUS != null && STATUS.equalsIgnoreCase("TXN_SUCCESS")) {

            apicallForpayment(ORDERID, UserId, 3, TXNID, BANKTXNID, 1);

        } else {
            apicallForpayment(ORDERID, UserId, 4, TXNID + "", BANKTXNID + "", 0);
        }
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();

    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + s.toString(), Toast.LENGTH_LONG).show();
        apicallForpayment(orderId, UserId, 4, "TXNID_cancel" + "", "BANKTXNID_cancel" + "", 0);
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  " + s);
        Toast.makeText(getApplicationContext(), "UI Error " + s, Toast.LENGTH_LONG).show();
        apicallForpayment(orderId, UserId, 4, "TXNID_cancel" + "", "BANKTXNID_cancel" + "", 0);
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true " + s + "  s1 " + s1);
        Toast.makeText(getApplicationContext(), "Unable to load webpage " + s.toString(), Toast.LENGTH_LONG).show();
        apicallForpayment(orderId, UserId, 4, "TXNID_cancel" + "", "BANKTXNID_cancel" + "", 0);
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  ");
        Toast.makeText(getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();
        apicallForpayment(orderId, UserId, 4, "TXNID_cancel" + "", "BANKTXNID_cancel" + "", 0);
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel ");
        apicallForpayment(orderId, UserId, 4, "TXNID_cancel" + "", "BANKTXNID_cancel" + "", 0);
        // finish();
    }


    public void OrderSucceffyly(String OrderId, int statusCode) {

        // 0 for cancel
        Intent intent = new Intent(this, SussessfullyActivity.class);
        intent.putExtra(KeyData.KEY_ORDER, OrderId);
        intent.putExtra(KeyData.KEY_STATUS, statusCode);
        startActivity(intent);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        finish();

    }


}
