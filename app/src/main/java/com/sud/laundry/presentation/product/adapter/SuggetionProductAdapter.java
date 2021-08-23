package com.sud.laundry.presentation.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.product.CartPriceMore;
import com.grocery.presentation.product.ProductModel.productDetails.RelatedProductsRes;
import com.grocery.presentation.product.ProductModel.productList.ProductPriceItem;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SuggetionProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "MorePriceAdapter";

    private List<RelatedProductsRes> relatedProductsResList;
    private Context context;

    SuggestProductDetailsInterface suggestProductDetailsInterface;

    public SuggetionProductAdapter(Context context,  List<RelatedProductsRes> relatedProductsResList) {
        this.context = context;
        this.relatedProductsResList = relatedProductsResList;
        this.suggestProductDetailsInterface= (SuggestProductDetailsInterface) context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_suggetion_product_list, parent, false);
        viewHolder = new ProductPriceVH(viewItem);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RelatedProductsRes result = relatedProductsResList.get(position); // Movie

        ProductPriceVH productPriceVH = (ProductPriceVH) holder;
        productPriceVH.tv_price.setText("Rs " + result.getPrice());
        productPriceVH.tv_original_price.setText("Rs " + result.getOriginalPrice());
        productPriceVH.tv_price_off.setText(Utils.calculatePercentage(result.getPrice(), result.getOriginalPrice()) + "");

        Utils.ProductImageView(result.getImageUrl(), productPriceVH.im_product, productPriceVH.avLoadingIndicatorView);

        // productPriceVH.tv_price_off.setText(Utils.Pe);

        productPriceVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestProductDetailsInterface.ProductDetails(result);
            }
        });


    }

    @Override
    public int getItemCount() {
        return relatedProductsResList.size();
    }


    protected class ProductPriceVH extends RecyclerView.ViewHolder {

        @BindView(R.id.av_progress)
        AVLoadingIndicatorView avLoadingIndicatorView;

        @BindView(R.id.im_product)
        ImageView im_product;

        @BindView(R.id.tv_product)
        TextView tv_product;

        @BindView(R.id.tv_price)
        TextView tv_price;

        @BindView(R.id.tv_original_price)
        TextView tv_original_price;

        @BindView(R.id.tv_price_off)
        TextView tv_price_off;

        public ProductPriceVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
