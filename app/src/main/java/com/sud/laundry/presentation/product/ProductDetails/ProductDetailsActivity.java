package com.sud.laundry.presentation.product.ProductDetails;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.network.NetworkClient;
import com.grocery.presentation.Home.ui.home.adapter.CategoryAdapter;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.cart.CartActivity;
import com.grocery.presentation.comonCart.CartCommanContract;
import com.grocery.presentation.comonCart.CartCommonPresenter;
import com.grocery.presentation.product.ProductContract;
import com.grocery.presentation.product.ProductModel.cartadd.CartResponse;
import com.grocery.presentation.product.ProductModel.productDetails.ProImageListItem;
import com.grocery.presentation.product.ProductModel.productDetails.ProductDetailsItem;
import com.grocery.presentation.product.ProductModel.productDetails.ProductDetailsResponse;
import com.grocery.presentation.product.ProductModel.productDetails.RelatedProductsRes;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductPriceItem;
import com.grocery.presentation.product.ProductPresenter;
import com.grocery.presentation.product.adapter.ProductDetailsInterface;
import com.grocery.presentation.product.adapter.ProductSizePriceAdapter;
import com.grocery.presentation.product.adapter.SliderAdapterImage;
import com.grocery.presentation.product.adapter.SuggestProductDetailsInterface;
import com.grocery.presentation.product.adapter.SuggetionProductAdapter;
import com.grocery.presentation.product.productList.ProductListActivity;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.presentation.socalnetwork.group.ChatActivity;
import com.grocery.presentation.socalnetwork.group.GroupForSelectDetailsActivity;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailsActivity extends BaseActivity implements ProductContract.View, CartCommanContract.View, ProductDetailsInterface, SuggestProductDetailsInterface {

    String TAG = ProductDetailsActivity.class.getSimpleName();

    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    ProductPresenter productPresenter;
    int product_id;

    @BindView(R.id.tv_cart)
    TextView tv_cart;

    @BindView(R.id.tv_kg)
    TextView tv_kg;
    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.tv_original_price)
    TextView tv_original_price;

    @BindView(R.id.tv_price_off)
    TextView tv_price_off;

    @BindView(R.id.tv_productname)
    TextView tv_productname;
    @BindView(R.id.tv_description)
    TextView tv_description;

    @BindView(R.id.fav_off)
    ImageView fav_off;

    @BindView(R.id.fav_on)
    ImageView fav_on;

    @BindView(R.id.v_fav)
    View v_fav;

    @BindView(R.id.rv_size)
    RecyclerView rv_size;

    @BindView(R.id.rv_suggestion)
    RecyclerView rv_suggestion;

    @BindView(R.id.suggestion_product)
    LinearLayout suggestion_product;

    boolean favStatus = false;

    int UserId;
    int shopId;
    CartCommonPresenter cartCommonPresenter;
    ProductSizePriceAdapter productSizePriceAdapter;
    boolean ClickBuyNow = false;

    int product_quantity_id = 0;
    int shop_id = 0;

    String productImage;
    String product_name_price;

    List<ProductPriceItem> productPriceItemList = new ArrayList<>();
    SliderAdapterImage adapter;

    SuggetionProductAdapter suggetionProductAdapter;
    List<RelatedProductsRes> relatedProductsResList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public void setView() {
        super.setView();
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);
        product_id = getIntent().getIntExtra(KeyData.PRODUCT_ID, 0);
        Log.e(TAG, "setView:  product_id " + product_id);
        shopId = getIntent().getIntExtra(KeyData.SHOP_ID, 0);
        cartCommonPresenter = new CartCommonPresenter(this, this);

        rv_size.setHasFixedSize(true);
        rv_size.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productSizePriceAdapter = new ProductSizePriceAdapter(this, this, productPriceItemList);
        rv_size.setAdapter(productSizePriceAdapter);

        suggetionProductAdapter = new SuggetionProductAdapter(this, relatedProductsResList);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_suggestion.setLayoutManager(HorizontalLayout);
        rv_suggestion.setAdapter(suggetionProductAdapter);

        productPresenter = new ProductPresenter(this, this);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("product_id", product_id);
        jsonObject.addProperty("user_id", UserId);
        productPresenter.getProductDetails(jsonObject);


        adapter = new SliderAdapterImage(this);

        imageSlider.setSliderAdapter(adapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.RED);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSlider.startAutoCycle();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartlistList();
    }

    @Override
    public void showProgressBar() {

        Log.e(TAG, "showProgressBar: ");
    }

    @Override
    public void hideProgressBar() {
        Log.e(TAG, "hideProgressBar: ");

    }

    @Override
    public void ProListResponse(ProductListResponse productListResponse, int PageNo) {

    }

    @Override
    public void ProDetailsResponse(ProductDetailsResponse productDetailsResponse) {

        Log.e(TAG, "ProDetailsResponse: " + productDetailsResponse.getStatus());

        if (productDetailsResponse.getStatus() == 200) {

            if (productDetailsResponse.getProductDetails().size() > 0) {

                ProductDetailsItem productDetailsItem = productDetailsResponse.getProductDetails().get(0);

                if (productDetailsItem.getWishStatus() != null && productDetailsItem.getWishStatus().equalsIgnoreCase("true")) {
                    favStatus = true;
                    fav_off.setVisibility(View.GONE);
                    fav_on.setVisibility(View.VISIBLE);
                } else {
                    favStatus = false;
                    fav_off.setVisibility(View.VISIBLE);
                    fav_on.setVisibility(View.GONE);
                }

                product_quantity_id = 0;
                shop_id = productDetailsItem.getSuppliersId();

                productImage = NetworkClient.IMAGE_URL + productDetailsItem.getImageUrl();
                product_name_price = productDetailsItem.getProductName() + " \nRS " + productDetailsItem.getPrice();

                tv_productname.setText(productDetailsItem.getProductName() + "");
                tv_description.setText(productDetailsItem.getDescription() + "");

                tv_kg.setText(productDetailsItem.getWeight() + "" + productDetailsItem.getfUnitName());
                tv_price.setText("Rs " + productDetailsItem.getPrice() + "");
                tv_original_price.setText("Rs " + productDetailsItem.getOriginal_price() + "");
                Utils.underline(tv_original_price);
                tv_price_off.setText(Utils.calculatePercentage(productDetailsItem.getPrice(), productDetailsItem.getOriginal_price()) + "");

                List<ProImageListItem> bannerListItemList = new ArrayList<>();
                bannerListItemList.add(new ProImageListItem(productDetailsItem.getProductId(), productDetailsItem.getImageUrl(), 0));
                Log.e(TAG, "ProDetailsResponse: " + productDetailsItem.getImageUrl());
                List<ProImageListItem> proImage = productDetailsResponse.getProductDetails().get(0).getProImageList();
                for (int i = 0; i < proImage.size(); i++) {
                    bannerListItemList.add(proImage.get(i));
                    Log.e(TAG, "ProDetailsResponse: " + proImage.get(i).getImgUrl());
                }

                adapter.renewItems(bannerListItemList);

                productPriceItemList.add(new ProductPriceItem(
                        productDetailsItem.getOriginal_price(),
                        productDetailsItem.getPrice(),
                        productDetailsItem.getProductId() + "",
                        productDetailsItem.getUName(),
                        productDetailsItem.getWeight(),
                        productDetailsItem.getuId(),
                        productDetailsItem.getfUnitName(),
                        0

                ));

                if (productDetailsItem.getProductPrice() != null && productDetailsItem.getProductPrice().size() > 0) {

                    for (int i = 0; i < productDetailsItem.getProductPrice().size(); i++) {
                        productPriceItemList.add(productDetailsItem.getProductPrice().get(i));
                    }
                }
                productSizePriceAdapter.notifyDataSetChanged();

                if (productDetailsItem.getRelatedProducts() != null) {
                    suggestion_product.setVisibility(View.VISIBLE);
                    for (int i = 0; i < productDetailsItem.getRelatedProducts().size(); i++) {

                        relatedProductsResList.add(productDetailsItem.getRelatedProducts().get(i));
                        Log.e(TAG, "ProDetailsResponse: " + i + " " + productDetailsItem.getRelatedProducts().get(i));

                    }

                    suggetionProductAdapter.notifyDataSetChanged();
                } else {
                    suggestion_product.setVisibility(View.GONE);
                }


            }

        } else {

            Log.e(TAG, "ProDetailsResponse: ");
        }


    }

    @Override
    public void showCartProgressBar() {
        Utils.showProfressBarActivity(this);

    }

    @Override
    public void hideCartProgressBar() {
        Utils.dissmissActiviity();
    }

    @Override
    public void AddCartResponse(CartResponse cartResponse) {

        if (ClickBuyNow) {
            AppSharedPreference.getInstance(this).setInteger(AppSharedPreference.SP_CART_VALUE, cartResponse.getTotalcart());
            getCartlistList();
            startActivity(new Intent(this, CartActivity.class));

        } else {

            AppSharedPreference.getInstance(this).setInteger(AppSharedPreference.SP_CART_VALUE, cartResponse.getTotalcart());
            getCartlistList();

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

    @Override
    public void addRemoveSussess(DataResponse dataResponse) {

        if (dataResponse.getStatus() == 200) {
            if (favStatus) {
                fav_off.setVisibility(View.VISIBLE);
                fav_on.setVisibility(View.GONE);
                favStatus = false;
            } else {
                fav_off.setVisibility(View.GONE);
                fav_on.setVisibility(View.VISIBLE);
                favStatus = true;
            }

        }
    }


    @OnClick({R.id.im_back, R.id.rl_cart, R.id.im_cart_image, R.id.tv_cart, R.id.v_fav, R.id.im_search
            , R.id.tv_add_cart, R.id.tv_buy_now, R.id.im_share})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_back:
                finish();
                break;

            case R.id.im_search:

                startActivity(new Intent(this, SearchActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.im_cart_image:
            case R.id.rl_cart:
            case R.id.tv_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.v_fav:
                callfavOrNot();
                break;

            case R.id.tv_buy_now:
                ClickBuyNow = true;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_id", UserId + "");
                jsonObject.addProperty("product_id", product_id);
                jsonObject.addProperty("quantity", "1");
                jsonObject.addProperty("shop_id", shop_id);
                jsonObject.addProperty("product_quantity_id", product_quantity_id);
                cartCommonPresenter.cartAddAndUpdate(jsonObject);

                break;

            case R.id.tv_add_cart:
                ClickBuyNow = false;
                JsonObject jsonObjectCart = new JsonObject();
                jsonObjectCart.addProperty("user_id", UserId + "");
                jsonObjectCart.addProperty("product_id", product_id);
                jsonObjectCart.addProperty("quantity", "1");
                jsonObjectCart.addProperty("shop_id", shop_id);
                jsonObjectCart.addProperty("product_quantity_id", product_quantity_id);
                cartCommonPresenter.cartAddAndUpdate(jsonObjectCart);
                break;

            case R.id.im_share:

                Intent intent = new Intent(this, GroupForSelectDetailsActivity.class);
                intent.putExtra(KeyData.PRODUCT_ID, product_id + "");
                intent.putExtra(KeyData.PRODUCT_IAMGE, productImage);
                intent.putExtra(KeyData.PRODUCT_NAME_PRICE, product_name_price);
                intent.putExtra(KeyData.SHOP_ID, shop_id + "");
                startActivity(intent);
                // dff
                break;


        }
    }

    private void getCartlistList() {
        int cartvalue = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_CART_VALUE, 0);
        tv_cart.setText("" + cartvalue);

    }

    private void callfavOrNot() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        jsonObject.addProperty("product_id", product_id);
        productPresenter.addAndRemoveWishList(jsonObject);

    }


    @Override
    public void sizeClick(ProductPriceItem productPriceItem) {
        Log.e(TAG, "sizeClick: " + productPriceItem.toString());

        product_quantity_id = productPriceItem.getId();

        tv_kg.setText(productPriceItem.getWeight() + "" + productPriceItem.getFUnitName());
        tv_price.setText("Rs " + productPriceItem.getPrice() + "");
        tv_original_price.setText("Rs " + productPriceItem.getOriginalPrice() + "");
        Utils.underline(tv_original_price);
        tv_price_off.setText(Utils.calculatePercentage(productPriceItem.getPrice(), productPriceItem.getOriginalPrice()) + "");


    }

    @Override
    public void ProductDetails(RelatedProductsRes relatedProductsRes) {
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra(KeyData.CATEGORY_ID, relatedProductsRes.getCategoryId());
        startActivity(intent);
        finish();
    }
}
