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
import com.grocery.presentation.product.ProductModel.productList.ProductPriceItem;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MorePriceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "MorePriceAdapter";

    CartPriceMore cartPriceMore ;
    private List<ProductPriceItem> productPriceItems;
    private Context context;
    private String imageUrl;

    public MorePriceAdapter(Context context, CartPriceMore cartPriceMore , String imageUrl, List<ProductPriceItem> productPriceItems) {
        this.context = context;
        this.cartPriceMore=cartPriceMore;
        this.productPriceItems = productPriceItems;
        this.imageUrl = imageUrl;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_product_price_list, parent, false);
        viewHolder = new ProductPriceVH(viewItem);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductPriceItem result = productPriceItems.get(position); // Movie

        ProductPriceVH productPriceVH = (ProductPriceVH) holder;
        productPriceVH.tv_price.setText("Rs " + result.getPrice());
        productPriceVH.tv_original_price.setText("Rs " + result.getOriginalPrice());
        productPriceVH.tv_kg.setText(result.getWeight() + result.getFUnitName());
        productPriceVH.tv_price_off.setText(Utils.calculatePercentage(result.getPrice(), result.getOriginalPrice()) + "");

        Utils.ProductImageView(imageUrl, productPriceVH.im_product, productPriceVH.avLoadingIndicatorView);

        // productPriceVH.tv_price_off.setText(Utils.Pe);

        productPriceVH.tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartPriceMore.produtAddInCart(0, result, productPriceVH.tv_quantity, productPriceVH.tv_minus, productPriceVH.tv_plus, productPriceVH.av_cart_progress);
            }
        });

        productPriceVH.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartPriceMore.produtAddInCart(1, result, productPriceVH.tv_quantity, productPriceVH.tv_minus, productPriceVH.tv_plus, productPriceVH.av_cart_progress);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productPriceItems.size();
    }


    protected class ProductPriceVH extends RecyclerView.ViewHolder {

        @BindView(R.id.av_progress)
        AVLoadingIndicatorView avLoadingIndicatorView;
        @BindView(R.id.av_cart_progress)
        AVLoadingIndicatorView av_cart_progress;
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
        @BindView(R.id.tv_kg)
        TextView tv_kg;


        public ProductPriceVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
