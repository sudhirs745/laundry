package com.sud.laundry.presentation.Home.ui.Search.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.network.NetworkClient;
import com.grocery.presentation.Home.ui.Search.shopModel.ShopListItem;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "ShopListAdapter";

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HEADER = 2;


    private List<ShopListItem> shopListItemList;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private ShopAdapterCallback mCallback;

    private String errorMsg;

    public ShopListAdapter(Context context, List<ShopListItem> shopListItemList , ShopAdapterCallback shopAdapterCallback) {
        this.context = context;
        this.mCallback =  shopAdapterCallback;
        this.shopListItemList = shopListItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.shop_wish_list, parent, false);
        viewHolder = new ShopListVH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShopListItem result = shopListItemList.get(position); // Movie

        final ShopListVH shopListVH = (ShopListVH) holder;


        shopListVH.tv_shop_name.setText(Utils.isStringNull(result.getCompanyName()) + "");
        shopListVH.tv_address.setText(Utils.isStringNull(result.getAddress()) + " " +
                Utils.isStringNull(result.getCity()) + " " +
                Utils.isStringNull(result.getState()) + " " +
                Utils.isStringNull(result.getPostalCode()) + " ");

        ProductImageView(result.getShopImage(), shopListVH.im_shop_image, shopListVH.avLoadingIndicatorView);
        shopListVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.itemClickRow(position, result);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopListItemList.size();
    }


    protected class ShopListVH extends RecyclerView.ViewHolder {

        @BindView(R.id.av_progress)
        AVLoadingIndicatorView avLoadingIndicatorView;
        @BindView(R.id.im_shop_image)
        ImageView im_shop_image;

        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.tv_rat)
        TextView tv_rat;
        @BindView(R.id.tv_address)
        TextView tv_address;

        public ShopListVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void ProductImageView(String url, ImageView imageView, AVLoadingIndicatorView avLoadingIndicatorView) {
        Picasso.get().load( url)
                .error(R.drawable.image_placeholder)
                .memoryPolicy(MemoryPolicy.NO_CACHE, new MemoryPolicy[]{MemoryPolicy.NO_STORE})
                .into(imageView, (Callback) (new Callback() {
                    public void onSuccess() {
                        Log.e(TAG, "onSuccess: ");
                        avLoadingIndicatorView.hide();
                        avLoadingIndicatorView.setVisibility(View.GONE);
                        //  Toast.makeText(StoryViewVideo.this, "Image loaded from the internet", Toast.LENGTH_LONG).show();
                    }

                    public void onError(@Nullable Exception e) {
                        Log.e(TAG, "onError: " + e.toString());
                        avLoadingIndicatorView.hide();
                        avLoadingIndicatorView.setVisibility(View.GONE);

                    }
                }));
    }

}
