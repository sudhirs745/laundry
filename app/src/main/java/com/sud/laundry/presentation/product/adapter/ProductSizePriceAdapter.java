package com.sud.laundry.presentation.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.product.ProductModel.productList.ProductPriceItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductSizePriceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "MorePriceAdapter";

    ProductDetailsInterface productDetailsInterface;
    private List<ProductPriceItem> productPriceItems;
    private Context context;

    public ProductSizePriceAdapter(Context context, ProductDetailsInterface productDetailsInterface, List<ProductPriceItem> productPriceItems) {
        this.context = context;
        this.productDetailsInterface = productDetailsInterface;
        this.productPriceItems = productPriceItems;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_size_product, parent, false);
        viewHolder = new ProductPriceVH(viewItem);

        return viewHolder;
    }

    int selectedPosition = 0;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductPriceItem result = productPriceItems.get(position); // Movie
        ProductPriceVH productPriceVH = (ProductPriceVH) holder;

        if (selectedPosition == position) {
            productPriceVH.lvSize.setBackgroundResource(R.drawable.round_size_select);
        } else {
            productPriceVH.lvSize.setBackgroundResource(R.drawable.round_size_select_desable);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();

                productDetailsInterface.sizeClick(result);
            }
        });

        productPriceVH.tvTextSize.setText(result.getWeight() + result.getFUnitName());



    }

    @Override
    public int getItemCount() {
        return productPriceItems.size();
    }


    protected class ProductPriceVH extends RecyclerView.ViewHolder {


        @BindView(R.id.lvSize)
        LinearLayout lvSize;
        @BindView(R.id.tvTextSize)
        TextView tvTextSize;

        public ProductPriceVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
