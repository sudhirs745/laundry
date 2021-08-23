package com.sud.laundry.presentation.search.adapter;

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
import com.grocery.presentation.cart.adapter.CartAdapterCallback;
import com.grocery.presentation.cart.cartModel.CartDetailsItem;
import com.grocery.presentation.search.model.suggestion.SuggetionListItem;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchSuggestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SuggetionListItem> suggetionListItemList;
    private Context context;


    private SearchClickInterface mCallback;

    private String errorMsg;

    public SearchSuggestAdapter(Context context, List<SuggetionListItem> suggetionListItemList) {
        this.context = context;
        this.mCallback = (SearchClickInterface) context;
        this.suggetionListItemList = suggetionListItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.item_search_suggest_list, parent, false);
        viewHolder = new VH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SuggetionListItem result = suggetionListItemList.get(position); // Movie
        final VH vh = (VH) holder;
        vh.txtWord.setText(result.getName());
        vh.txtType.setText(result.getType());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mCallback.onItemClick(result,position);
             }
         });

    }

    @Override
    public int getItemCount() {
        return suggetionListItemList.size();
    }


    protected class VH extends RecyclerView.ViewHolder {


        @BindView(R.id.im_search_resentOrNot)
        ImageView im_search_resentOrNot;

        @BindView(R.id.txtWord)
        TextView txtWord;
        @BindView(R.id.txtType)
        TextView txtType;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
