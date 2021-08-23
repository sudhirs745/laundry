package com.sud.laundry.presentation.order.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsItem;
import com.grocery.presentation.order.model.orderDetails.ProDetailsListItem;

import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;


public class PaginationAdapterMyOrderDetails extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "PaginationAdapterProductList";

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HEADER = 2;

    private List<OrderDetailsItem> orderDetailsItemList;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private OrderInfoAdapterCallback mCallback;

    private String errorMsg;

    public PaginationAdapterMyOrderDetails(Context context) {
        this.context = context;
        this.mCallback = (OrderInfoAdapterCallback) context;
        orderDetailsItemList = new ArrayList<>();
    }

    public List<OrderDetailsItem> getOrderDetals() {
        return orderDetailsItemList;
    }

    public void setOrderDetails(List<OrderDetailsItem> orderDetailsItems) {
        this.orderDetailsItemList = orderDetailsItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_order_details_list, parent, false);
                viewHolder = new ProductVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderDetailsItem result = orderDetailsItemList.get(position); // Movie

        switch (getItemViewType(position)) {


            case ITEM:
                final ProductVH productVH = (ProductVH) holder;

                productVH.tv_order_no.setText(result.getInvoicePrefix() + "" + result.getOrderId());

                // productVH.tv_name.setText("" + result.getFirstname());
//                productVH.tv_tracking.setText("" + result.getTrackingName());
//                productVH.tv_order_status.setText("" + result.getOrderStatus());

                productVH.tv_payment_type.setText("" + result.getPaymentStatusName());
                productVH.tv_total_payment.setText("RS " + result.getTotal());

                Utils.QRImageView(result.getQr_path(), productVH.im_qr_code);

                productVH.tv_order_date.setText(null != Utils.getdatetTimeFormat(result.getCreatedDate()) ? Utils.getdatetTimeFormat(result.getCreatedDate()) : "");

                if (result.getPaymentStatus() == 2 || result.getPaymentStatus() == 3) {
                    productVH.lv_traking.setVisibility(View.VISIBLE);

                } else {
                    productVH.lv_traking.setVisibility(View.GONE);
                }


                String AddressDetails = result.getShippingAddress1() + "," + result.getShippingAddress2() + " " + result.getShippingCity() + " " + result.getShipping_state() + " " + result.getShippingPostcode() + " \nMobile No." + result.getMobileNo();
                // productVH.tv_address.setText(AddressDetails);
//                if (result != null && result.getProDetailsList() != null && result.getProDetailsList().size() > 0) {
//
//                    List<ProDetailsListItem> productDetailsItem = result.getProDetailsList();
//                    Log.e(TAG, "onBindViewHolder: " + productDetailsItem.size());
//
//
//                    ProductOrderRecycleViewDataAdapter itemListDataAdapter = new ProductOrderRecycleViewDataAdapter(context, productDetailsItem, mCallback);
//                    productVH.rv_product_details.setHasFixedSize(true);
//                    productVH.rv_product_details.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//                    productVH.rv_product_details.setAdapter(itemListDataAdapter);
//                    productVH.rv_product_details.setNestedScrollingEnabled(false);
//
//                }


                productVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mCallback.orderDetails(result);
                    }
                });


                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;
                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderDetailsItemList == null ? 0 : orderDetailsItemList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return (position == orderDetailsItemList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;

    }


    public void add(OrderDetailsItem r) {
        orderDetailsItemList.add(r);
        notifyItemInserted(orderDetailsItemList.size() - 1);
    }

    public void addAll(List<OrderDetailsItem> orderDetailsItemList) {
        for (OrderDetailsItem orderDetailsItem : orderDetailsItemList) {
            add(orderDetailsItem);
        }
    }

    public void remove(OrderDetailsItem r) {
        int position = orderDetailsItemList.indexOf(r);
        if (position > -1) {
            orderDetailsItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new OrderDetailsItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = orderDetailsItemList.size() - 1;
        OrderDetailsItem productListItem = getItem(position);

        if (productListItem != null) {
            orderDetailsItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public OrderDetailsItem getItem(int position) {
        return orderDetailsItemList.get(position);
    }


    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(orderDetailsItemList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    /**
     * Main list's content ViewHolder
     */
    protected class ProductVH extends RecyclerView.ViewHolder {


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


        public ProductVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.loadmore_progress)
        public AVLoadingIndicatorView mProgressBar;
        @BindView(R.id.loadmore_retry)
        public ImageButton mRetryBtn;
        @BindView(R.id.loadmore_errortxt)
        public TextView mErrorTxt;
        @BindView(R.id.loadmore_errorlayout)
        public LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

}
