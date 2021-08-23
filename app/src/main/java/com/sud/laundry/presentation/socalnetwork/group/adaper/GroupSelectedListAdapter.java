package com.sud.laundry.presentation.socalnetwork.group.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.socalnetwork.group.GroupClickInteface;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupDetailsItem;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupSelectedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<GroupDetailsItem> groupDetailsItemArrayList;
    private Context context;

    private String errorMsg;
    GroupClickInteface groupClickInteface;

    int UserId;

    public GroupSelectedListAdapter(Context context, ArrayList<GroupDetailsItem> groupDetailsItemArrayList) {
        this.context = context;
        this.groupDetailsItemArrayList = groupDetailsItemArrayList;
        this.groupClickInteface = (GroupClickInteface) context;
        UserId = AppSharedPreference.getInstance(context).getInteger(AppSharedPreference.SP_USER_ID, -1);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.item_group_seleted_list, parent, false);
        viewHolder = new VH(viewItem);

        return viewHolder;
    }

    int index = -1;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupDetailsItem result = groupDetailsItemArrayList.get(position);
        final VH vh = (VH) holder;

        if(result.getType()==1){

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


        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=position;
                groupClickInteface.GroupClick(position, result);
                notifyDataSetChanged();
            }
        });

        if (index == position) {
            vh.ic_check_or_not.setVisibility(View.VISIBLE);
        } else {
            vh.ic_check_or_not.setVisibility(View.GONE);
        }

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

        @BindView(R.id.ic_check_or_not)
        ImageView ic_check_or_not;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}