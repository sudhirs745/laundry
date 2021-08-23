package com.sud.laundry.presentation.Home.ui.Search.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ProListItem;
import com.grocery.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopChildRecycleViewDataAdapter extends RecyclerView.Adapter<ShopChildRecycleViewDataAdapter.HomeCildItemRowHolder> {

    private List<ProListItem> itemsList;
    private Context mContext;
    ShopRowClickInterface shopRowClickInterface;
    public ShopChildRecycleViewDataAdapter(Context context, List<ProListItem> itemsList,   ShopRowClickInterface shopRowClickInterface) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.shopRowClickInterface = shopRowClickInterface;
    }

    @Override
    public HomeCildItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_home_child_item, null);
        HomeCildItemRowHolder mh = new HomeCildItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(HomeCildItemRowHolder holder, int i) {


        ProListItem proListItem = itemsList.get(i);
        Utils.ProductImageView(proListItem.getImgUrl(), holder.imProImage);
        holder.tvProNName.setText(proListItem.getProductName());
        holder.tv_price.setText("Rs "+ proListItem.getProductUnitPrice());
        holder.tv_discount.setText(Utils.calculatePercentage(proListItem.getProductUnitPrice(), proListItem.getProductOriginalPrice()) +""); //+ proListItem.getProductUnitPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shopRowClickInterface.homeRowclick(1,null,proListItem);
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
        protected TextView tv_price ;
        @BindView(R.id.tv_discount)
        protected TextView tv_discount ;
        @BindView(R.id.im_pro_image)
        protected ImageView imProImage;


        public HomeCildItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

}