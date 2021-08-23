package com.sud.laundry.presentation.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.Home.ui.shopPro.ShopProductActivity;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.cart.CartActivity;
import com.grocery.presentation.comonCart.CartCommanContract;
import com.grocery.presentation.comonCart.CartCommonPresenter;
import com.grocery.presentation.product.ProductContract;
import com.grocery.presentation.product.ProductDetails.ProductDetailsActivity;
import com.grocery.presentation.product.ProductModel.cartadd.CartResponse;
import com.grocery.presentation.product.ProductModel.productDetails.ProductDetailsResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;
import com.grocery.presentation.product.ProductPresenter;
import com.grocery.presentation.product.adapter.PaginationAdapterProductList;
import com.grocery.presentation.product.productList.ProductListActivity;
import com.grocery.presentation.search.adapter.PaginationAdapterSearchList;
import com.grocery.presentation.search.adapter.PaginationSearchInterface;
import com.grocery.presentation.search.model.SearchListResponse;
import com.grocery.presentation.search.model.SearchProductListItem;
import com.grocery.presentation.search.model.suggestion.SuggetionResponse;
import com.grocery.userDatabase.DbRepository;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.PaginationAdapterCallback;
import com.grocery.utils.PaginationScrollListener;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchListDetailsActivity extends BaseActivity implements SearchContract.View, PaginationSearchInterface, CartCommanContract.View {


    private static final String TAG = ProductListActivity.class.getSimpleName();
    PaginationAdapterSearchList adapter;
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.tv_cart)
    TextView tv_cart;

    @BindView(R.id.toolbar_center_title)
    TextView toolbar_center_title;

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
    SearchPresenter searchPresenter;
    CartCommonPresenter cartCommonPresenter;
    String  searchQuery;

    DbRepository dbRepository;
    int UserId;

    TextView tvQuantiy;
    TextView tvMinus;
    TextView tvPlus;
    AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list_details;
    }

    @Override
    public void setView() {
        super.setView();
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);
        searchQuery = getIntent().getStringExtra(KeyData.SEARCH_QUERY);

        Log.e(TAG, "setView:  cat_id " + searchQuery);

        toolbar_center_title.setText(""+searchQuery);

        searchPresenter = new SearchPresenter (this, this);
        cartCommonPresenter = new CartCommonPresenter(this, this);
        adapter = new PaginationAdapterSearchList(this);
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
        jsonObject.addProperty("search_query", searchQuery);
        jsonObject.addProperty("page_no", currentPage);
        searchPresenter.searchList(jsonObject, currentPage);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dbRepository = new DbRepository(this);
        getCartlistList();

        if (tvPlus != null && tvMinus != null && tvQuantiy != null) {
            tvMinus.setClickable(true);
            tvPlus.setClickable(true);
        }

    }

    @Override
    public void retryPageLoad() {
        loadNextPage(currentPage);

    }

    @Override
    public void itemClickRow(int position, SearchProductListItem productListItem) {


        Intent intent ;
        if(productListItem.getReType()==2){

            intent = new Intent(this, ShopProductActivity.class);
            intent.putExtra(KeyData.SHOP_ID, productListItem.getSuppliersId());
            intent.putExtra(KeyData.SHOP_NAME, productListItem.getCompanyName());
            startActivity(intent);

        }else {
            intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra(KeyData.PRODUCT_ID, productListItem.getProductId());
            startActivity(intent);


        }

    }


    @Override
    public void produtAddInCart(int status, SearchProductListItem productListItem, TextView tvQuantiy, TextView tvMinus, TextView tvPlus, AVLoadingIndicatorView avLoadingIndicatorView) {

        //status =0  minus  and 1 for  plus

        this.tvMinus = tvMinus;
        this.tvPlus = tvPlus;
        this.tvQuantiy = tvQuantiy;
        this.avLoadingIndicatorView = avLoadingIndicatorView;

        int currentQuntity = Utils.StringConvertIntoInt(tvQuantiy);
        if (status == 0) {
            currentQuntity = currentQuntity - 1;
        } else {
            currentQuntity = currentQuntity + 1;
        }

        if (tvPlus != null && tvMinus != null && tvQuantiy != null) {
            tvMinus.setClickable(false);
            tvPlus.setClickable(false);
        }


        if (currentQuntity > -1) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", UserId + "");
            jsonObject.addProperty("product_id", productListItem.getProductId());
            jsonObject.addProperty("quantity", currentQuntity + "");
            cartCommonPresenter.cartAddAndUpdate(jsonObject);


        } else {
            this.tvQuantiy.setText(0 + "");
            //Utils.toastErrorMessage(this, getString(R.id.));
        }

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void dataResponse(SearchListResponse searchListResponse , int PageNo) {


        Log.e(TAG, "ProListResponse: " + searchListResponse.toString() + " page  " + PageNo);
        hideErrorView();

        if (searchListResponse.getStatus() == 200) {

            if (PageNo == 1) {
                TOTAL_PAGES = (searchListResponse.getTotalProducts() / 10) + 1;
                progressBar.setVisibility(View.GONE);
                adapter.addAll(searchListResponse.getProductList());
                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;

            } else {

                adapter.removeLoadingFooter();
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                adapter.addAll(searchListResponse.getProductList());

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;

            }

        } else {
            progressBar.setVisibility(View.GONE);
            isLastPage = true;
        }



    }

    @Override
    public void dataSuggetion(SuggetionResponse suggetionResponse) {

    }



    @Override
    public void AddCartResponse(CartResponse cartResponse) {

        if (tvPlus != null && tvMinus != null && tvQuantiy != null) {
            tvMinus.setClickable(true);
            tvPlus.setClickable(true);
            tvQuantiy.setText(cartResponse.getCartQunatiry() + "");

            AppSharedPreference.getInstance(this).setInteger(AppSharedPreference.SP_CART_VALUE, cartResponse.getTotalcart());
        }
        getCartlistList();

    }

    @Override
    public void showCartProgressBar() {

        if (avLoadingIndicatorView != null) {
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
            avLoadingIndicatorView.show();
        }

    }

    @Override
    public void hideCartProgressBar() {
        if (avLoadingIndicatorView != null) {
            avLoadingIndicatorView.setVisibility(View.GONE);
            avLoadingIndicatorView.hide();
        }
        if (tvPlus != null && tvMinus != null && tvQuantiy != null) {
            tvMinus.setClickable(true);
            tvPlus.setClickable(true);
        }

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
        showErrorView(message);
    }



    private void showErrorView(String message) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            txtError.setText(message + "");
        }
    }


    // Helpers -------------------------------------------------------------------------------------

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.im_back, R.id.rl_cart, R.id.im_cart_image, R.id.tv_cart , R.id.im_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_search:
            case R.id.im_back:
                finish();
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
