package com.sud.laundry.presentation.socalnetwork.group.adaper;

import android.content.Context;
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
import com.grocery.presentation.socalnetwork.group.model.groupInfo.GroupInfoDetailsItem;
import com.grocery.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class GroupInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GroupInfoDetailsItem> groupInfoDetailsItemList;
    private Context context;


   // private FriendClickInteface mCallback;

    private String errorMsg;

    public GroupInfoListAdapter(Context context, List<GroupInfoDetailsItem> groupInfoDetailsItemList) {
        this.context = context;
        //this.mCallback = (FriendClickInteface) context;
        this.groupInfoDetailsItemList = groupInfoDetailsItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.item_group_info_list, parent, false);
        viewHolder = new VH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupInfoDetailsItem result = groupInfoDetailsItemList.get(position);
        final VH vh = (VH) holder;

        vh.tv_user_name.setText(result.getName());
        Utils.setProfileImage(result.getProfileUrl(), vh.im_user);


    }

    @Override
    public int getItemCount() {
        return groupInfoDetailsItemList.size();
    }


    protected class VH extends RecyclerView.ViewHolder {


        @BindView(R.id.im_user)
        CircleImageView im_user;

        @BindView(R.id.tv_user_name)
        TextView tv_user_name;

        @BindView(R.id.tv_time)
        TextView tv_time;


        @BindView(R.id.lv_time)
        LinearLayout lv_time;
        @BindView(R.id.ic_menu_click)
        ImageView ic_menu_click;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
