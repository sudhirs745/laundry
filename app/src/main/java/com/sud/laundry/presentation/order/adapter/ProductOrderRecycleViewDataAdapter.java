package com.sud.laundry.presentation.order.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.Home.ui.home.adapter.HomeRowClickInterface;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.ProListItem;
import com.grocery.presentation.order.model.orderDetails.ProDetailsListItem;
import com.grocery.utils.PaginationAdapterCallback;
import com.grocery.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductOrderRecycleViewDataAdapter extends RecyclerView.Adapter<ProductOrderRecycleViewDataAdapter.HomeCildItemRowHolder> {

    private List<ProDetailsListItem> itemsList;
    private Context mContext;
    OrderInfoAdapterCallback mCallback;

    String TAG = "ProductOrderRecycleViewDataAdapter";

    public ProductOrderRecycleViewDataAdapter(Context context, List<ProDetailsListItem> productDetailsItem) {
        this.itemsList = productDetailsItem;
        this.mContext = context;
    }

    @Override
    public HomeCildItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_product_order_item, null);
        HomeCildItemRowHolder mh = new HomeCildItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(HomeCildItemRowHolder holder, int i) {


        ProDetailsListItem proListItem = itemsList.get(i);

        Log.e(TAG, "onBindViewHolder: " + proListItem.getImgUrl());

        Utils.ProductImageView(proListItem.getImgUrl(), holder.imProImage);

        holder.tvProNName.setText(proListItem.getProductName());
        holder.tv_price.setText("Rs " + proListItem.getPrice());
        holder.tv_discount.setText(proListItem.getDiscount() + "% off"); //+ proListItem.getProductUnitPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   mCallback.homeRowclick(1,null,proListItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class HomeCildItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_pro_name)
        protected TextView tvProNName;
        @BindView(R.id.tv_price)
        protected TextView tv_price;
        @BindView(R.id.tv_discount)
        protected TextView tv_discount;
        @BindView(R.id.im_pro_image)
        protected ImageView imProImage;


        public HomeCildItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

}