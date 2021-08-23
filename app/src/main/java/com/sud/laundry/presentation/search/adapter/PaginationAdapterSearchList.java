package com.sud.laundry.presentation.search.adapter;

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
import com.grocery.presentation.search.model.SearchProductListItem;
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
import okhttp3.internal.Util;


public class PaginationAdapterSearchList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "PaginationAdapterProductList";

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HEADER = 2;


    private List<SearchProductListItem> productListItemList;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationSearchInterface mCallback;

    private String errorMsg;

    public PaginationAdapterSearchList(Context context) {
        this.context = context;
        this.mCallback = (PaginationSearchInterface) context;
        productListItemList = new ArrayList<>();
    }

    public List<SearchProductListItem> getProductList() {
        return productListItemList;
    }

    public void setProductList(List<SearchProductListItem> productListItemList) {
        this.productListItemList = productListItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_search_list, parent, false);
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
        SearchProductListItem result = productListItemList.get(position); // Movie

        switch (getItemViewType(position)) {


            case ITEM:
                final ProductVH productVH = (ProductVH) holder;

                if(result.getReType()==2) {
                    productVH.tv_product.setText(result.getProductName() + "");
                    // productVH.tv_in_stock.setText(result.getWeight() + "" + result.getFUnitName());
                    productVH.tv_price.setVisibility(View.GONE);
                    productVH.tv_original_price.setVisibility(View.GONE);;
                    productVH.tv_price_off.setText(result.getDiscount_shop()+"");
                    productVH.tv_type.setText(result.getType()+"");

                }else {
                    productVH.tv_product.setText(result.getProductName() + "");
                    // productVH.tv_in_stock.setText(result.getWeight() + "" + result.getFUnitName());
                    productVH.tv_price.setText("RS " + result.getPrice());
                    productVH.tv_original_price.setText("Rs." + result.getOriginalPrice() + "");
                    Utils.underline(productVH.tv_original_price);
                    productVH.tv_price_off.setText(Utils.calculatePercentage(result.getPrice(), result.getOriginalPrice()) + "");
                    productVH.tv_type.setText(result.getType()+"");
                }
                Utils.ProductImageView(result.getImageUrl(), productVH.im_product, productVH.avLoadingIndicatorView);
                productVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mCallback.itemClickRow(position, result);
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


    public void add(SearchProductListItem r) {
        productListItemList.add(r);
        notifyItemInserted(productListItemList.size() - 1);
    }

    public void addAll(List<SearchProductListItem> productListItemList) {
        for (SearchProductListItem productListItem : productListItemList) {
            add(productListItem);
        }
    }

    public void remove(SearchProductListItem r) {
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
        add(new SearchProductListItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productListItemList.size() - 1;
        SearchProductListItem productListItem = getItem(position);

        if (productListItem != null) {
            productListItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public SearchProductListItem getItem(int position) {
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

        @BindView(R.id.tv_product)
        TextView tv_product;

        @BindView(R.id.im_product)
        ImageView im_product;


        @BindView(R.id.tv_price)
        TextView tv_price;

        @BindView(R.id.tv_original_price)
        TextView tv_original_price;

        @BindView(R.id.tv_price_off)
        TextView tv_price_off;

        @BindView(R.id.tv_type)
        TextView tv_type;


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
