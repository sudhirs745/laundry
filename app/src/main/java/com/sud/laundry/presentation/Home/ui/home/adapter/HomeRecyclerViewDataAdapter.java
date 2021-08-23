package com.sud.laundry.presentation.Home.ui.home.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.ProductDetailsItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerViewDataAdapter extends RecyclerView.Adapter<HomeRecyclerViewDataAdapter.HomeParentItemRowHolder> {

    private List<ProductDetailsItem> productDetailsItemArrayList;
    private Context mContext;
    HomeRowClickInterface homeRowClickInterface;

    public HomeRecyclerViewDataAdapter(Context context, HomeRowClickInterface homeRowClickInterface) {
        this.mContext = context;
        this.homeRowClickInterface = homeRowClickInterface;
        productDetailsItemArrayList = new ArrayList<>();
    }

    @Override
    public HomeParentItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_parent_list, null);
        HomeParentItemRowHolder mh = new HomeParentItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(HomeParentItemRowHolder itemRowHolder, int i) {


        ProductDetailsItem productDetailsItem = productDetailsItemArrayList.get(i);

        itemRowHolder.tv_cat_name.setText(productDetailsItem.getCategoryName());
        itemRowHolder.tv_compeny_name.setText( "More");
        List proListItemList = productDetailsItem.getProList();

        HomeChildRecycleViewDataAdapter itemListDataAdapter = new HomeChildRecycleViewDataAdapter(mContext, proListItemList, homeRowClickInterface);
        itemRowHolder.rv_child.setHasFixedSize(true);
        itemRowHolder.rv_child.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.rv_child.setAdapter(itemListDataAdapter);
        itemRowHolder.rv_child.setNestedScrollingEnabled(false);

        itemRowHolder.tv_compeny_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeRowClickInterface.homeRowclick(0,  productDetailsItem,null);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != productDetailsItemArrayList ? productDetailsItemArrayList.size() : 0);
    }

    public class HomeParentItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;

        @BindView(R.id.rv_child)
        protected RecyclerView rv_child;

        @BindView(R.id.tv_cat_name)
        protected TextView tv_cat_name;

        @BindView(R.id.tv_compeny_name)
        protected TextView tv_compeny_name;

        public HomeParentItemRowHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }


    }


    public void add(ProductDetailsItem r) {
        productDetailsItemArrayList.add(r);
        notifyItemInserted(productDetailsItemArrayList.size() - 1);
    }


    public void addAll(List<ProductDetailsItem> productDetailsItemList) {
        for (ProductDetailsItem result : productDetailsItemList) {
            if (result.getProList().size() > 0)
                add(result);
        }
    }

    public ProductDetailsItem getProcuctListItem(int position) {
        return productDetailsItemArrayList.get(position);


    }

    public List<ProductDetailsItem> getProcuctAllproduct() {
        return productDetailsItemArrayList;


    }


}