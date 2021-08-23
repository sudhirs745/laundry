package com.sud.laundry.presentation.Home.ui.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoriesItem;
import com.grocery.presentation.Home.ui.home.homeModel.category.SubcatItem;
import com.grocery.utils.Utils;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyView> {

    private List<SubcatItem> categoriesItemList;
    Context mContext;
    HomeRowClickInterface homeRowClickInterface ;
    public CategoryAdapter(List<SubcatItem> categoriesItemList, Context mContext , HomeRowClickInterface homeRowClickInterface  ) {
        this.mContext = mContext;
        this.categoriesItemList = categoriesItemList;

        this.homeRowClickInterface=homeRowClickInterface;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_user_item, parent, false);

        return new MyView(itemView);
    }


    int row_index=-1;
    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        SubcatItem details = categoriesItemList.get(position);

        holder.tv_catName.setText(details.getCategoryName());
        Utils.loadImageCatImage(details.getCatImage() , holder.image_view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeRowClickInterface.homeRowclick(details);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoriesItemList.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_cat_name)
        TextView tv_catName;

        @BindView(R.id.image_view)
        ImageView image_view;

        public MyView(View view) {
            super(view);
            ButterKnife.bind(this, view );

        }
    }
}
