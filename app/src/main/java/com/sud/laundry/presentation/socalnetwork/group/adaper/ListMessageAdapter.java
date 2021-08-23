package com.sud.laundry.presentation.socalnetwork.group.adaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grocery.R;
import com.grocery.presentation.socalnetwork.group.MessageClickInterface;
import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageDetailsItem;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String TAG = "ListMessageAdapter";
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;

    private Context context;
    private ArrayList<MessageDetailsItem> consersation;

    int UserId;
    MessageClickInterface messageClickInterface;

    public ListMessageAdapter(Context context, ArrayList<MessageDetailsItem> consersation) {
        this.context = context;
        this.consersation = consersation;
        this.messageClickInterface = (MessageClickInterface) context;
        UserId = AppSharedPreference.getInstance(context).getInteger(AppSharedPreference.SP_USER_ID, -1);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FRIEND_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend, parent, false);
            return new ItemMessageFriendHolder(view);
        } else if (viewType == VIEW_TYPE_USER_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_user, parent, false);
            return new ItemMessageUserHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemMessageFriendHolder) {


            ((ItemMessageFriendHolder) holder).txtContent.setText(consersation.get(position).getMessage() + "");
            Utils.setProfileImage(consersation.get(position).getProfileUrl(), ((ItemMessageFriendHolder) holder).avata);
            ((ItemMessageFriendHolder) holder).tv_user_name.setText("" + consersation.get(position).getName());

            ((ItemMessageFriendHolder) holder).tv_time.setText(Utils.getDateTimeAgo(context,consersation.get(position).getMessageTime()));

            if (consersation.get(position).getImgSrc() != null && consersation.get(position).getImgSrc().length() > 8) {
                ((ItemMessageFriendHolder) holder).imagconFriend.setVisibility(View.VISIBLE);
                Utils.setchatImageImage(consersation.get(position).getImgSrc(), ((ItemMessageFriendHolder) holder).imagconFriend);

                ((ItemMessageFriendHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (consersation.get(position).getProduct_id() > 0) {
                            messageClickInterface.messageClick(position, consersation.get(position));

                        }
                    }
                });


            } else {
                ((ItemMessageFriendHolder) holder).imagconFriend.setVisibility(View.GONE);

            }

            ((ItemMessageFriendHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    messageClickInterface.deleteMessageClick(-1,consersation.get(position));
                    return false;
                }
            });


        } else if (holder instanceof ItemMessageUserHolder) {
            ((ItemMessageUserHolder) holder).txtContent.setText(consersation.get(position).getMessage() + "");

            Utils.setProfileImage(consersation.get(position).getProfileUrl(), ((ItemMessageUserHolder) holder).avata);
            ((ItemMessageUserHolder) holder).tv_user_name.setText("" + consersation.get(position).getName());
            ((ItemMessageUserHolder) holder).tv_time.setText(Utils.getDateTimeAgo(context,consersation.get(position).getMessageTime()));

            if (consersation.get(position).getStatus() != null && consersation.get(position).getStatus().equalsIgnoreCase("read")) {
                ((ItemMessageUserHolder) holder).im_show_not.setColorFilter(context.getResources().getColor(R.color.blue_show_message));
            }else {
                ((ItemMessageUserHolder) holder).im_show_not.setColorFilter(context.getResources().getColor(R.color.grey_500));

            }

            if (consersation.get(position).getImgSrc() != null && consersation.get(position).getImgSrc().length() > 8) {
                ((ItemMessageUserHolder) holder).imagconUser.setVisibility(View.VISIBLE);

                if (consersation.get(position).isImageType()) {

                    try {
                        File imgFile = new File(consersation.get(position).getImgSrc());
                        if (imgFile.exists()) {
                            ((ItemMessageUserHolder) holder).imagconUser.setImageURI(Uri.fromFile(imgFile));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onBindViewHolder: " + e.toString());
                    }
                } else {
                    Utils.setchatImageImage(consersation.get(position).getImgSrc(), ((ItemMessageUserHolder) holder).imagconUser);
                }
                ((ItemMessageUserHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (consersation.get(position).getProduct_id() > 0) {
                            messageClickInterface.messageClick(position, consersation.get(position));
                        }

                    }
                });


            } else {
                ((ItemMessageUserHolder) holder).imagconUser.setVisibility(View.GONE);
            }

            ((ItemMessageUserHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    messageClickInterface.deleteMessageClick(position,consersation.get(position));
                    return false;
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return consersation.get(position).getMssgBy() == UserId ? VIEW_TYPE_USER_MESSAGE : VIEW_TYPE_FRIEND_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return consersation.size();
    }

    class ItemMessageUserHolder extends RecyclerView.ViewHolder {
        public TextView txtContent;
        public TextView tv_user_name;
        public CircleImageView avata;
        public ImageView imagconUser;
        public ImageView im_show_not;
        public TextView  tv_time;

        public ItemMessageUserHolder(View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.textContentUser);
            avata = itemView.findViewById(R.id.imageView2);
            imagconUser = itemView.findViewById(R.id.imagUser);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            im_show_not = itemView.findViewById(R.id.im_show_not);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

    class ItemMessageFriendHolder extends RecyclerView.ViewHolder {
        public TextView txtContent;
        public TextView tv_user_name;
        public CircleImageView avata;
        public ImageView imagconFriend;
        public TextView tv_time;

        public ItemMessageFriendHolder(View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.textContentFriend);
            avata = itemView.findViewById(R.id.imageView3);
            imagconFriend = itemView.findViewById(R.id.imagFriend);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}