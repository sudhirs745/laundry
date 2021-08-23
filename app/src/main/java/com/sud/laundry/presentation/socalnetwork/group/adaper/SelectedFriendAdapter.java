package com.sud.laundry.presentation.socalnetwork.group.adaper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.socalnetwork.friend.FriendClickInteface;
import com.grocery.presentation.socalnetwork.friend.model.friendList.UserdataItem;
import com.grocery.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class SelectedFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserdataItem> userDataItemList;
    private Context context;

    private String errorMsg;

    public SelectedFriendAdapter(Context context, List<UserdataItem> userDataItemList) {
        this.context = context;
        this.userDataItemList = userDataItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.item_selected_friend_list, parent, false);
        viewHolder = new VH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserdataItem result = userDataItemList.get(position);
        final VH vh = (VH) holder;

        vh.tv_user_name.setText(result.getName());
        Utils.setProfileImage(result.getProfileUrl(), vh.im_user);


    }

    @Override
    public int getItemCount() {
        return userDataItemList.size();
    }


    protected class VH extends RecyclerView.ViewHolder {


        @BindView(R.id.im_user)
        CircleImageView im_user;
        @BindView(R.id.tv_user_name)
        TextView tv_user_name;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
