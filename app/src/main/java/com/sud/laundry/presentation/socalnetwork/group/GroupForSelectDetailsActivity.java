package com.sud.laundry.presentation.socalnetwork.group;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.Util;
import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.socalnetwork.group.adaper.GroupListAdapter;
import com.grocery.presentation.socalnetwork.group.adaper.GroupSelectedListAdapter;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageListResponse;
import com.grocery.presentation.socalnetwork.group.model.groupInfo.GroupInfoResponse;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupDetailsItem;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupListRes;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupForSelectDetailsActivity extends BaseActivity implements GroupContract.View, GroupClickInteface {


    String TAG = "GroupDetailsActivity";

    @BindView(R.id.im_back)
    ImageView im_back;
    @BindView(R.id.toolbar_center_title)
    TextView toolbar_center_title;
    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.tv_error_txt_title)
    TextView tv_error_txt_title;
    @BindView(R.id.tv_error_txt_cause)
    TextView tv_error_txt_cause;

    @BindView(R.id.tv_setGroupName)
    TextView tv_setGroupName;

    GroupPresenter groupPresenter;
    int UserId = -1;
    String  Username = "";
    ArrayList<GroupDetailsItem> groupDetailsItemArrayList;

    GroupSelectedListAdapter groupListAdapter;

    String product_id;
    String productImage;
    String product_name_price;
    String shop_id;

    int roomId;
    String roomName;

    GroupDetailsItem groupDetailsItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_select_details;
    }

    @Override
    public void setView() {
        super.setView();
        toolbar_center_title.setText("Send To...");
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, -1);
        Username = AppSharedPreference.getInstance(this).getString(AppSharedPreference.SP_USER_NAME, "");

        errorLayout.setVisibility(View.GONE);

        product_id=getIntent().getStringExtra(KeyData.PRODUCT_ID);
        productImage=getIntent().getStringExtra(KeyData.PRODUCT_IAMGE);
        product_name_price=getIntent().getStringExtra(KeyData.PRODUCT_NAME_PRICE);
        shop_id=getIntent().getStringExtra(KeyData.PRODUCT_NAME_PRICE);

        groupPresenter = new GroupPresenter(this, this);

        groupDetailsItemArrayList = new ArrayList<>();
        groupListAdapter = new GroupSelectedListAdapter(this, groupDetailsItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(groupListAdapter);
        groupListAdapter.notifyDataSetChanged();


    }


    private void getGroupList() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        Log.e(TAG, "getGroupList: " + jsonObject.toString());
        groupPresenter.getGroupList(jsonObject);

    }

    @OnClick({R.id.im_back, R.id.tv_add_group_new, R.id.lv_add_group , R.id.fb_create_group})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_back:
                finish();
                break;

            case R.id.lv_add_group:
            case R.id.tv_add_group_new:
                startActivity(new Intent(this, SelectGroupUserActivity.class));

                break;
            case R.id.fb_create_group:
             if(groupDetailsItem!=null){

                  roomId=groupDetailsItem.getRoomId();
                 if(groupDetailsItem.getType()==1 ){
                     roomName=groupDetailsItem.getUserDetailsRes().getName();
                 }else {
                     roomName=groupDetailsItem.getRoomName();
                 }


                 JsonObject jsonObject  = new JsonObject();

                 jsonObject.addProperty("user_id" ,UserId);
                 jsonObject.addProperty("name" , Username +"");
                 jsonObject.addProperty("room_id" , groupDetailsItem.getRoomId()+"");
                 jsonObject.addProperty("messgae_text",product_name_price );
                 jsonObject.addProperty("image_url",productImage );
                 jsonObject.addProperty("product_id",product_id+"" );
                 groupPresenter.sendMessage(jsonObject);


             }else {

                 Utils.toastSuccessMessage(this,"Please select  group ");
             }
             break;
        }
    }

    @Override
    public void showProgressBar() {
        Utils.showProfressBarActivity(this);

    }

    @Override
    public void hideProgressBar() {
        Utils.dissmissActiviity();
    }

    @Override
    public void dataGroupList(GroupListRes groupListRes) {
        groupDetailsItemArrayList.clear();
        if (groupListRes.getStatus() == 200) {

            for (int i = 0; i < groupListRes.getGroupDetails().size(); i++) {
                groupDetailsItemArrayList.add(groupListRes.getGroupDetails().get(i));

            }

        }else {
            recyclerView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);

        }

        groupListAdapter.notifyDataSetChanged();
    }

    @Override
    public void createGroup(CreateGroupRes createGroupRes) {


    }

    @Override
    public void sendMessageRes(CreateGroupRes createGroupRes) {

        Log.e(TAG, "sendMessageRes: " + createGroupRes.getStatus() );

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(KeyData.KEY_ROOM, roomId);
        intent.putExtra(KeyData.KEY_ROOM_name, roomName);

        startActivity(intent);


    }

    @Override
    public void groupInfoResponse(GroupInfoResponse groupInfoResponse) {

    }

    @Override
    public void getMessageList(MessageListResponse messageListResponse) {

    }

    @Override
    public void displayError(String s) {
        Log.e(TAG, "displayError: " + s);

    }

    @Override
    public void showSuccessfulMessage(String message) {
        Log.e(TAG, "showSuccessfulMessage: " + message);

    }

    @Override
    public void showFailedMessage(String message) {
        Log.e(TAG, "showFailedMessage: " + message);
    }

    @Override
    public void deleteMessage(CreateGroupRes createGroupRes) {

    }

    @Override
    public void GroupClick(int position, GroupDetailsItem groupDetailsItem) {

        this.groupDetailsItem=groupDetailsItem;

        if(groupDetailsItem.getType()==1 ){
            tv_setGroupName.setText(groupDetailsItem.getUserDetailsRes().getName());
        }else {
            tv_setGroupName.setText(groupDetailsItem.getRoomName() + "");

        }
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtra(KeyData.KEY_ROOM, groupDetailsItem.getRoomId());
//        intent.putExtra(KeyData.KEY_ROOM_name, groupDetailsItem.getRoomName());
//        startActivity(intent);

    }



    @Override
    protected void onResume() {
        super.onResume();
        getGroupList();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(KeyData.KEY_MESSAGE_REFRESH));
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.e("receiver", "Got message: " + message);
            getGroupList();
        }
    };

    //Must unregister onPause()
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

    }

}