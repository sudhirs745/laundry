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
import com.grocery.presentation.socalnetwork.friend.model.friendList.UserdataItem;
import com.grocery.presentation.socalnetwork.group.GroupClickInteface;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupDetailsItem;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.internal.Util;

public class GroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<GroupDetailsItem> groupDetailsItemArrayList;
    private Context context;

    private String errorMsg;
    GroupClickInteface groupClickInteface;

    int UserId ;
    public GroupListAdapter(Context context, ArrayList<GroupDetailsItem> groupDetailsItemArrayList) {
        this.context = context;
        this.groupDetailsItemArrayList = groupDetailsItemArrayList;
        this.groupClickInteface =(GroupClickInteface)context;
        UserId = AppSharedPreference.getInstance(context).getInteger(AppSharedPreference.SP_USER_ID, -1);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.item_group_list, parent, false);
        viewHolder = new VH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupDetailsItem result = groupDetailsItemArrayList.get(position);
        final VH vh = (VH) holder;

        if(result.getType()==1 && result.getUserDetailsRes()!=null){
            if(UserId==result.getFriend_id()) {
                vh.tv_user_name.setText(result.getA_name());
                Utils.setProfileImage(result.getA_profile_url(), vh.im_user);
            }else if(UserId==result.getRoomAdmin()) {

                vh.tv_user_name.setText(result.getF_name());
                Utils.setProfileImage(result.getF_email(), vh.im_user);
            }
        }else {
            vh.tv_user_name.setText(result.getRoomName());
            Utils.setGroupImage(result.getGroupIcon(), vh.im_user);
        }

        if(result.getLastMssg()!=null  && result.getLastMssg().getLastMessage()!=null && result.getLastMssg().getLastMessage().length()>0 ) {
            vh.tv_message.setText(result.getLastMssg().getLastMessage());
            vh.tv_time.setText(Utils.getDateTimeAgo(context,result.getLastMssg().getLastMssgTime()));
        }else {
                vh.tv_message.setText("---");
                vh.tv_time.setText("");

        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupClickInteface.GroupClick(position,result);
            }
        });


    }

    @Override
    public int getItemCount() {
        return groupDetailsItemArrayList.size();
    }


    protected class VH extends RecyclerView.ViewHolder {


        @BindView(R.id.im_user)
        CircleImageView im_user;
        @BindView(R.id.tv_user_name)
        TextView tv_user_name;
        @BindView(R.id.tv_message)
        TextView tv_message;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}