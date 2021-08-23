package com.sud.laundry.presentation.product;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.presentation.comonCart.CartCommanContract;
import com.grocery.presentation.comonCart.CartCommonPresenter;
import com.grocery.presentation.product.ProductModel.cartadd.CartResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.presentation.product.ProductModel.productList.ProductPriceItem;
import com.grocery.presentation.product.adapter.MorePriceAdapter;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import java.util.List;


public class PriceBottomSheetDialogFragment extends BottomSheetDialogFragment implements CartPriceMore, CartCommanContract.View {

    String TAG ="PriceBottomSheetDialogFragment";
    private BottomSheetBehavior mBehavior;
    List<ProductPriceItem> productPriceItems;

    ProductListItem productListItem;

    CartCommonPresenter cartCommonPresenter;

    TextView tvQuantiy;
    TextView tvMinus;
    TextView tvPlus;
    AVLoadingIndicatorView avLoadingIndicatorView;
    int UserId;

    public PriceBottomSheetDialogFragment() {

    }

    public PriceBottomSheetDialogFragment(ProductListItem productListItem) {

        this.productListItem = productListItem;
        this.productPriceItems = productListItem.getProductPrice();
        cartCommonPresenter = new CartCommonPresenter(this, getContext());
        UserId = AppSharedPreference.getInstance(getContext()).getInteger(AppSharedPreference.SP_USER_ID, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.bottom_sheet, null);

        view.findViewById(R.id.fakeShadow).setVisibility(View.GONE);

        TextView tv_product_name = view.findViewById(R.id.tv_product_name);
        ImageView im_crose = view.findViewById(R.id.im_crose);
        im_crose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_product_name.setText(productListItem.getProductName() + "");

        RecyclerView recyclerView = view.findViewById(R.id.rvPrice);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MorePriceAdapter morePriceAdapter = new MorePriceAdapter(getContext(), this, productListItem.getImageUrl(), productPriceItems);
        recyclerView.setAdapter(morePriceAdapter);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    public void produtAddInCart(int status, ProductPriceItem productPriceItem, TextView tvQuantiy, TextView tvMinus, TextView tvPlus, AVLoadingIndicatorView avLoadingIndicatorView) {

        Log.e(TAG, "produtAddInCart: " + status );
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
            jsonObject.addProperty("shop_id", productListItem.getSuppliersId());
            jsonObject.addProperty("product_quantity_id", productPriceItem.getId());
            cartCommonPresenter.cartAddAndUpdate(jsonObject);

        } else {
            this.tvQuantiy.setText(0 + "");
            //Utils.toastErrorMessage(this, getString(R.id.));
        }


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
    public void AddCartResponse(CartResponse cartResponse) {

        if (tvPlus != null && tvMinus != null && tvQuantiy != null) {
            tvMinus.setClickable(true);
            tvPlus.setClickable(true);
            tvQuantiy.setText(cartResponse.getCartQunatiry() + "");
            AppSharedPreference.getInstance(getContext()).setInteger(AppSharedPreference.SP_CART_VALUE, cartResponse.getTotalcart());
        }
    }

    @Override
    public void displayError(String s) {

    }

    @Override
    public void showSuccessfulMessage(String message) {

    }

    @Override
    public void showFailedMessage(String message) {

    }
}