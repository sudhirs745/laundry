package com.sud.laundry.presentation.socalnetwork.socialnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.socalnetwork.friend.FriendContract;
import com.grocery.presentation.socalnetwork.friend.FriendPresenter;
import com.grocery.presentation.socalnetwork.friend.adaper.MyFriendListAdapter;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.friend.model.friendList.UserdataItem;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.presentation.socalnetwork.socialnotification.adaper.NotificationAdapter;
import com.grocery.presentation.socalnetwork.socialnotification.model.SocialNotiyRes;
import com.grocery.presentation.socalnetwork.socialnotification.model.UserDataItem;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity  implements  NotificationContract.View , FriendContract.View, NotificationClickInterface{

    String TAG= "NotificationActivity" ;

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
    int UserId =-1;

    NotificationPresenter notificationPresenter;
    FriendPresenter friendPresenter;
    List<UserDataItem> notifyuserDataItemArrayList;

    NotificationAdapter notificationAdapter ;

    int position=0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification;
    }

    @Override
    public void setView() {
        super.setView();
        toolbar_center_title.setText("Notification");
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, -1);
        errorLayout.setVisibility(View.GONE);

        friendPresenter=  new FriendPresenter(this , this);

        notificationPresenter=  new NotificationPresenter(this , this);

        notifyuserDataItemArrayList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this, notifyuserDataItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotificatonList();

    }

    @Override
    protected void onDestroy() {
        // PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(listener);
        super.onDestroy();
    }

    @OnClick({ R.id.im_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;
        }
    }

    private void getNotificatonList() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",UserId);

        Log.e(TAG, "getNotificatonList " + jsonObject.toString() );
        notificationPresenter.getNotification(jsonObject);

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
    public void dataMyFriend(MyFriendListRes myFriendListRes) {

    }

    @Override
    public void cancelFriend(AddFriendResponse addFriendResponse) {
        Log.e(TAG, "cancelFriend: " + addFriendResponse.getResponse() );

        if(addFriendResponse.getStatus()==200) {




            getNotificatonList();


//            if (notifyuserDataItemArrayList.size() > position) {
//
//                if (notifyuserDataItemArrayList.get(position).isFollowing()) {
//                    notifyuserDataItemArrayList.get(position).setFollowing(false);
//                } else {
//                    notifyuserDataItemArrayList.get(position).setFollowing(true);
//
//                }
//
//
//            }
//            notificationAdapter.notifyDataSetChanged();
//        }

        }
    }

    @Override
    public void createGroup(CreateGroupRes createGroupRes) {

    }

    @Override
    public void dataNotification(SocialNotiyRes socialNotiyRes) {

        Log.e(TAG, "dataMyFriend: " + socialNotiyRes.getStatus() );

        notifyuserDataItemArrayList.clear();

        if(socialNotiyRes.getStatus()==200 && socialNotiyRes.getUserData().size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);

            for( UserDataItem userdataItem : socialNotiyRes.getUserData()){
                notifyuserDataItemArrayList.add(userdataItem);
            }

        }else {
            recyclerView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
        notificationAdapter.notifyDataSetChanged();


    }

    @Override
    public void displayError(String s) {
        Log.e(TAG, "displayError: " + s );
    }

    @Override
    public void showSuccessfulMessage(String message) {
        Log.e(TAG, "showSuccessfulMessage: " + message );

    }

    @Override
    public void showFailedMessage(String message) {
        Log.e(TAG, "showFailedMessage: " + message );

    }



    @Override
    public void clickNotification(int position, int status, UserDataItem userDataItem) {
        Log.e(TAG, "clickNotification: " + status  + userDataItem.toString());

        this.position =position;

        if(status==4){

            JsonObject jsonObject= new JsonObject();
            jsonObject.addProperty("user_id", UserId +"");
            jsonObject.addProperty("user_id_to", userDataItem.getNotifyBy()+"");

            Log.e(TAG, "clickNotification: " + jsonObject.toString() );

            friendPresenter.CancelFriendRequest(jsonObject);

        }else  if(status==1){

            JsonObject jsonObjectf = new JsonObject();
            jsonObjectf.addProperty("user_id", UserId+"");
            jsonObjectf.addProperty("user_id_to", userDataItem.getNotifyBy()+"");
            jsonObjectf.addProperty("status", 2);

            Log.e(TAG, "clickNotification: " + jsonObjectf.toString());
            friendPresenter.addFriendAndConfirm(jsonObjectf);


            JsonObject jsonObjectMain = new JsonObject();
            jsonObjectMain.addProperty("user_id", UserId);
            jsonObjectMain.addProperty("type", 1);
            jsonObjectMain.addProperty("friend_id", userDataItem.getNotifyBy()+"");
            jsonObjectMain.addProperty("room_name", userDataItem.getName() + "-" + userDataItem.getNotifyByUsername());

            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("member_id", userDataItem.getNotifyBy());
            jsonObject.addProperty("admin_id", "0");
            jsonArray.add(jsonObject);
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("member_id", UserId);
            jsonObject2.addProperty("admin_id", "1");
            jsonArray.add(jsonObject2);
            jsonObjectMain.add("members_details", jsonArray);
            friendPresenter.createGroup(jsonObjectMain);
            Log.e(TAG, "onClick: " + jsonObjectMain.toString());



        }

    }
}