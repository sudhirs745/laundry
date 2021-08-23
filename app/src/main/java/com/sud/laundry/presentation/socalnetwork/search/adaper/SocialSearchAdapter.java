package com.sud.laundry.presentation.socalnetwork.search.adaper;

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
import com.grocery.presentation.search.adapter.SearchClickInterface;
import com.grocery.presentation.search.model.suggestion.SuggetionListItem;
import com.grocery.presentation.socalnetwork.search.SocialSearchInteface;
import com.grocery.presentation.socalnetwork.search.model.UserDataItem;
import com.grocery.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class SocialSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserDataItem> userDataItemList;
    private Context context;


    private SocialSearchInteface mCallback;

    private String errorMsg;

    public SocialSearchAdapter(Context context, List<UserDataItem> userDataItemList) {
        this.context = context;
        this.mCallback = (SocialSearchInteface) context;
        this.userDataItemList = userDataItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.item_search_user_list, parent, false);
        viewHolder = new VH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserDataItem result = userDataItemList.get(position);
        final VH vh = (VH) holder;
        vh.tv_user_name.setText(result.getName());

        Utils.setProfileImage(result.getProfileUrl(), vh.im_user);
        if(!result.isFriend()) {

            if(result.isFriendRequestStatus()) {
                vh.lv_time.setVisibility(View.GONE);
                vh.lv_add_friend.setVisibility(View.VISIBLE);
                vh.tv_remove_cancel_friend.setVisibility(View.VISIBLE);
                vh.tv_description.setVisibility(View.GONE);
                vh.tv_add_accept_friend.setText("Confirm");
                vh.tv_remove_cancel_friend.setText("Cancel");
                vh.tv_add_accept_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          mCallback.socialClick(position , 2, result);
                    }
                });
                vh.tv_remove_cancel_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.socialClick(position , 3, result);
                    }
                });

            } else if(result.isPendingFriendStatus()){

                vh.lv_time.setVisibility(View.GONE);
                vh.lv_add_friend.setVisibility(View.VISIBLE);
                vh.tv_remove_cancel_friend.setVisibility(View.VISIBLE);
                vh.tv_description.setVisibility(View.GONE);
                vh.tv_add_accept_friend.setText("Request sent");
                vh.tv_remove_cancel_friend.setText("Cancel");
                vh.tv_add_accept_friend.setVisibility(View.VISIBLE);

                vh.tv_add_accept_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // mCallback.socialClick(position , 1, result);
                    }
                });
                vh.tv_remove_cancel_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.socialClick(position , 4, result);
                    }
                });


            } else {

                vh.lv_time.setVisibility(View.GONE);
                vh.lv_add_friend.setVisibility(View.VISIBLE);
                vh.tv_remove_cancel_friend.setVisibility(View.GONE);
                vh.tv_description.setVisibility(View.GONE);
                vh.tv_add_accept_friend.setText("Add Friend");
                vh.tv_add_accept_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.socialClick(position , 1, result);
                    }
                });
                vh.tv_remove_cancel_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.socialClick(position , 1, result);
                    }
                });
            }


        }else {
            vh.tv_description.setVisibility(View.VISIBLE);
            vh.tv_description.setText("Friend");
            vh.tv_time.setText(result.getLastOnline()+"");
            vh.lv_time.setVisibility(View.GONE);
            vh.lv_add_friend.setVisibility(View.GONE);
        }

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
        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.tv_time)
        TextView tv_time;

        @BindView(R.id.tv_add_accept_friend)
        TextView tv_add_accept_friend;
        @BindView(R.id.tv_remove_cancel_friend)
        TextView tv_remove_cancel_friend;

        @BindView(R.id.lv_add_friend)
        LinearLayout lv_add_friend;

        @BindView(R.id.lv_time)
        LinearLayout lv_time;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
