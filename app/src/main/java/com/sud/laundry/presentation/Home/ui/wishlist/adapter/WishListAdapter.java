package com.sud.laundry.presentation.Home.ui.wishlist.adapter;

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
import com.grocery.presentation.cart.cartModel.CartDetailsItem;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WishListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "WishListAdapter";

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HEADER = 2;


    private List<ProductListItem> productListItemList;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private WishAdapterCallback mCallback;

    private String errorMsg;

    public WishListAdapter(Context context, List<ProductListItem> productListItemList , WishAdapterCallback wishAdapterCallback) {
        this.context = context;
        this.mCallback =  wishAdapterCallback;
        this.productListItemList = productListItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.item_wish_list, parent, false);
        viewHolder = new CartVH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductListItem result = productListItemList.get(position); // Movie

        final CartVH cartVH = (CartVH) holder;

        cartVH.tv_product.setText(result.getProductName() + "");
        cartVH.tv_original_price.setText("Rs." + result.getOriginal_price() + "");
        Utils.underline(cartVH.tv_original_price);

        cartVH.tv_price.setText("RS." + result.getPrice());
        cartVH.tv_price_off.setText(Utils.calculatePercentage(result.getPrice(), result.getOriginal_price()) + "");

        ProductImageView(result.getImageUrl(), cartVH.im_product, cartVH.avLoadingIndicatorView);
        cartVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.itemClickRow(position, result);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productListItemList.size();
    }


    protected class CartVH extends RecyclerView.ViewHolder {

        @BindView(R.id.av_progress)
        AVLoadingIndicatorView avLoadingIndicatorView;

        @BindView(R.id.tv_original_price)
        TextView tv_original_price;
        @BindView(R.id.tv_product)
        TextView tv_product;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.im_product)
        ImageView im_product;

        @BindView(R.id.tv_price_off)
        TextView tv_price_off;

        public CartVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void ProductImageView(String url, ImageView imageView, AVLoadingIndicatorView avLoadingIndicatorView) {
        Picasso.get().load(NetworkClient.IMAGE_URL + url)
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
