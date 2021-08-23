package com.sud.laundry.presentation.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.cart.CartActivity;
import com.grocery.presentation.order.adapter.OrderInfoAdapterCallback;
import com.grocery.presentation.order.adapter.PaginationAdapterMyOrderDetails;
import com.grocery.presentation.order.adapter.ProductOrderRecycleViewDataAdapter;
import com.grocery.presentation.order.model.OrderInfo.OrderInfoResponse;
import com.grocery.presentation.order.model.orderAdd.OrderAddResponse;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsItem;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsResponse;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.PaginationScrollListener;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity implements OrderInfoContract.View, OrderInfoAdapterCallback {

    String TAG = "MyOrderActivity";

    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.tv_cart)
    TextView tv_cart;

    @BindView(R.id.main_recycler)
    RecyclerView rv;
    @BindView(R.id.main_progress)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.error_btn_retry)
    Button btnRetry;
    @BindView(R.id.error_txt_cause)
    TextView txtError;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    int UserId;

    OrderPresenter orderPresenter;
    PaginationAdapterMyOrderDetails adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void setView() {
        super.setView();
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);
        orderPresenter = new OrderPresenter(this, this);
        adapter = new PaginationAdapterMyOrderDetails(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        rv.addItemDecoration(itemDecorator);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage(currentPage);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadNextPage(currentPage);

        btnRetry.setOnClickListener(view -> loadNextPage(currentPage));

    }

    private void loadNextPage(int currentPage) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        jsonObject.addProperty("page_no", currentPage);
        orderPresenter.myorderDetals(jsonObject, currentPage);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // loadNextPage(currentPage);
        getCartlistList();
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
    public void PaymentSuccess(OrderAddResponse orderAddResponse, int status) {

    }

    @Override
    public void MyOrderResponse(OrderDetailsResponse orderDetailsResponse, int currentPage) {


        Log.e(TAG, "ProListResponse: " + orderDetailsResponse.toString() + " page  " + currentPage);
        hideErrorView();

        if (orderDetailsResponse.getStatus() == 200) {

            if (currentPage == 1) {
                TOTAL_PAGES = (orderDetailsResponse.getTotalOrder() / 10) + 1;
                progressBar.setVisibility(View.GONE);
                adapter.addAll(orderDetailsResponse.getOrderDetails());
                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;

            } else {

                adapter.removeLoadingFooter();
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                adapter.addAll(orderDetailsResponse.getOrderDetails());

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;

            }

        } else {
            progressBar.setVisibility(View.GONE);
            isLastPage = true;
        }


    }

    @Override
    public void OrderDetailsResponse(OrderDetailsResponse orderDetailsResponse) {

    }


    @Override
    public void displayError(String s) {
        Log.e(TAG, "displayError: " + s);

        if (currentPage == 1) {
            showErrorView(s);
        } else {
            adapter.showRetry(true, s);
        }

    }

    @Override
    public void showSuccessfulMessage(String message) {
        Log.e(TAG, "showSuccessfulMessage: " + message);

    }

    @Override
    public void showFailedMessage(String message) {

        Log.e(TAG, "showFailedMessage: " + message);


    }

    private void showErrorView(String message) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            txtError.setText(message + "");
        }
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void orderDetails(OrderDetailsItem orderDetailsItem) {

        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra(KeyData.KEY_ORDER_ID, orderDetailsItem.getOrderId() + "");
        startActivity(intent);

    }

    @Override
    public void retryPageLoad() {

    }

    @OnClick({R.id.im_back, R.id.rl_cart, R.id.im_cart_image, R.id.tv_cart, R.id.im_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;

            case R.id.im_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;

            case R.id.im_cart_image:
            case R.id.rl_cart:
            case R.id.tv_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;

        }
    }

    private void getCartlistList() {
        int cartvalue = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_CART_VALUE, 0);
        tv_cart.setText("" + cartvalue);
    }
}
