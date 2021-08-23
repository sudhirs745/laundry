package com.sud.laundry.presentation.product.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.network.NetworkClient;
import com.grocery.presentation.product.ProductModel.productList.ProductListItem;
import com.grocery.utils.PaginationAdapterCallback;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PaginationAdapterProductList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "PaginationAdapterProductList";

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HEADER = 2;


    private List<ProductListItem> productListItemList;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    public PaginationAdapterProductList(Context context) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        productListItemList = new ArrayList<>();
    }

    public List<ProductListItem> getMovies() {
        return productListItemList;
    }

    public void setMovies(List<ProductListItem> productListItemList) {
        this.productListItemList = productListItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_product_list, parent, false);
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
        ProductListItem result = productListItemList.get(position); // Movie

        switch (getItemViewType(position)) {


            case ITEM:
                final ProductVH productVH = (ProductVH) holder;
                productVH.tv_product.setText(result.getProductName() + "");
                productVH.tv_price.setText("RS " + result.getPrice());
                productVH.tv_original_price.setText("Rs." + result.getOriginal_price() + "");
                Utils.underline(productVH.tv_original_price);
                productVH.tv_price_off.setText(Utils.calculatePercentage(result.getPrice(), result.getOriginal_price()) + "");

                productVH.tv_kg.setText(result.getWeight() + "" + result.getfUnitName());

                if (result.getProductPrice() != null && result.getProductPrice().size() > 0) {
                    productVH.lv_kg.setBackgroundResource(R.drawable.bg_drop_down);
                    productVH.im_drop_down.setVisibility(View.VISIBLE);

                    productVH.lv_kg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCallback.itemClickRow(-1, result);
                        }
                    });


                } else {
                    productVH.im_drop_down.setVisibility(View.INVISIBLE);
                    productVH.lv_kg.setBackgroundResource(R.drawable.bg_drop_down_desable);

                }

                ProductImageView(result.getImageUrl(), productVH.im_product, productVH.avLoadingIndicatorView);
                productVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.itemClickRow(position, result);
                    }
                });
                productVH.tv_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.produtAddInCart(0, result, productVH.tv_quantity, productVH.tv_minus, productVH.tv_plus, productVH.av_cart_progress);
                    }
                });

                productVH.tv_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.produtAddInCart(1, result, productVH.tv_quantity, productVH.tv_minus, productVH.tv_plus, productVH.av_cart_progress);
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
        return productListItemList == null ? 0 : productListItemList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return (position == productListItemList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;

    }


    public void add(ProductListItem r) {
        productListItemList.add(r);
        notifyItemInserted(productListItemList.size() - 1);
    }

    public void addAll(List<ProductListItem> productListItemList) {
        for (ProductListItem productListItem : productListItemList) {
            add(productListItem);
        }
    }

    public void remove(ProductListItem r) {
        int position = productListItemList.indexOf(r);
        if (position > -1) {
            productListItemList.remove(position);
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
        add(new ProductListItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productListItemList.size() - 1;
        ProductListItem productListItem = getItem(position);

        if (productListItem != null) {
            productListItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ProductListItem getItem(int position) {
        return productListItemList.get(position);
    }


    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(productListItemList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    /**
     * Main list's content ViewHolder
     */
    protected class ProductVH extends RecyclerView.ViewHolder {

        @BindView(R.id.av_progress)
        AVLoadingIndicatorView avLoadingIndicatorView;
        @BindView(R.id.av_cart_progress)
        AVLoadingIndicatorView av_cart_progress;

        @BindView(R.id.tv_product)
        TextView tv_product;

        @BindView(R.id.im_product)
        ImageView im_product;

        @BindView(R.id.tv_minus)
        TextView tv_minus;
        @BindView(R.id.tv_plus)
        TextView tv_plus;
        @BindView(R.id.tv_quantity)
        TextView tv_quantity;

        @BindView(R.id.tv_price)
        TextView tv_price;

        @BindView(R.id.tv_original_price)
        TextView tv_original_price;

        @BindView(R.id.tv_price_off)
        TextView tv_price_off;

        @BindView(R.id.lv_kg)
        LinearLayout lv_kg;

        @BindView(R.id.tv_kg)
        TextView tv_kg;

        @BindView(R.id.im_drop_down)
        ImageView im_drop_down;


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
