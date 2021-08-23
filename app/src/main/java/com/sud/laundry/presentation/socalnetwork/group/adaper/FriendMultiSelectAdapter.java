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
import com.grocery.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendMultiSelectAdapter extends RecyclerView.Adapter<FriendMultiSelectAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<UserdataItem> userdataItemArrayList;

    public FriendMultiSelectAdapter(Context context, ArrayList<UserdataItem> userdataItemArrayList) {
        this.context = context;
        this.userdataItemArrayList = userdataItemArrayList;
    }

    public void setEmployees(ArrayList<UserdataItem> userdataItemArrayList) {
        this.userdataItemArrayList = new ArrayList<>();
        this.userdataItemArrayList = userdataItemArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend_select_multi, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(userdataItemArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return userdataItemArrayList.size();
    }


    class MultiViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.im_user)
        CircleImageView im_user;

        @BindView(R.id.tv_user_name)
        TextView tv_user_name;

        @BindView(R.id.tv_time)
        TextView tv_time;


        @BindView(R.id.lv_time)
        LinearLayout lv_time;
        @BindView(R.id.ic_check_or_not)
        ImageView ic_check_or_not;



        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bind(final UserdataItem employee) {
            ic_check_or_not.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
            tv_user_name.setText(employee.getName());
            Utils.setProfileImage(employee.getProfileUrl(), im_user);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    employee.setChecked(!employee.isChecked());
                    ic_check_or_not.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<UserdataItem> getAll() {
        return userdataItemArrayList;
    }

    public ArrayList<UserdataItem> getSelected() {
        ArrayList<UserdataItem> selected = new ArrayList<>();
        for (int i = 0; i < userdataItemArrayList.size(); i++) {
            if (userdataItemArrayList.get(i).isChecked()) {
                selected.add(userdataItemArrayList.get(i));
            }
        }
        return selected;
    }
}