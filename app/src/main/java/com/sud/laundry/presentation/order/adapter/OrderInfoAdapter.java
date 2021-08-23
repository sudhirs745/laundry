package com.sud.laundry.presentation.order.adapter;

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
import com.grocery.presentation.order.model.OrderInfo.CartDetailsItem;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "PaginationAdapterProductList";


    private List<CartDetailsItem> cartDetailsItemList;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private OrderInfoAdapterCallback mCallback;

    private String errorMsg;

    public OrderInfoAdapter(Context context, List<CartDetailsItem> cartDetailsItemList) {
        this.context = context;
        this.mCallback = (OrderInfoAdapterCallback) context;
        this.cartDetailsItemList = cartDetailsItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.item_order_info_list, parent, false);
        viewHolder = new CartVH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CartDetailsItem result = cartDetailsItemList.get(position); // Movie

        final CartVH cartVH = (CartVH) holder;

        cartVH.tv_product.setText(result.getProductName() + "");
        cartVH.tv_original_price.setText("Rs." + result.getOriginalPrice() + "");
        Utils.underline(cartVH.tv_original_price);

        cartVH.tv_price.setText("RS." + result.getPrice());
        cartVH.tv_quantity.setText("Qty " + result.getCartQuantity() + "");
        cartVH.tv_price_off.setText(Utils.calculatePercentage(result.getPrice(), result.getOriginalPrice()) + "");
        ProductImageView(result.getImageUrl(), cartVH.im_product, cartVH.avLoadingIndicatorView);

        if(!result.getDeliverStatus()){
            cartVH.tv_out_off_stock.setVisibility(View.VISIBLE);
            cartVH.tv_out_off_stock.setText("Seller doesn't deliver to your  Pincode");

        }else {
            cartVH.tv_out_off_stock.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return cartDetailsItemList.size();
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

        @BindView(R.id.tv_quantity)
        TextView tv_quantity;

        @BindView(R.id.tv_out_off_stock)
        TextView tv_out_off_stock;

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
