package com.sud.laundry.presentation.socalnetwork.friend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.socalnetwork.friend.adaper.MyFriendListAdapter;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.friend.model.friendList.UserdataItem;
import com.grocery.presentation.socalnetwork.group.ChatActivity;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.search.SocialSearchActivity;
import com.grocery.presentation.socalnetwork.search.SocialSearchPresenter;
import com.grocery.presentation.socalnetwork.search.adaper.SocialSearchAdapter;
import com.grocery.presentation.socalnetwork.search.model.UserDataItem;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;
import com.grocery.utils.loading.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFriend extends BaseActivity implements FriendContract.View, FriendClickInteface {


    String TAG = "MyFriend";

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
    FriendPresenter friendPresenter;
    int UserId = -1;

    List<UserdataItem> userDataItemArrayList;
    MyFriendListAdapter myFriendListAdapter;

    String selectdName = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_friend;
    }

    @Override
    public void setView() {
        super.setView();
        toolbar_center_title.setText("Friends");
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, -1);
        errorLayout.setVisibility(View.GONE);
        friendPresenter = new FriendPresenter(this, this);

        userDataItemArrayList = new ArrayList<>();
        myFriendListAdapter = new MyFriendListAdapter(this, userDataItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myFriendListAdapter);
        myFriendListAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();

        getFriendList();

    }

    private void getFriendList() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        friendPresenter.MyfriendList(jsonObject);

    }

    @OnClick({R.id.im_back})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_back:
                finish();
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
    public void dataMyFriend(MyFriendListRes myFriendListRes) {

        Log.e(TAG, "dataMyFriend: " + myFriendListRes.getStatus());

        userDataItemArrayList.clear();

        if (myFriendListRes.getStatus() == 200 && myFriendListRes.getUserdata().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);

            for (UserdataItem userdataItem : myFriendListRes.getUserdata()) {
                userDataItemArrayList.add(userdataItem);
            }

        } else {
            recyclerView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
        myFriendListAdapter.notifyDataSetChanged();

    }


    @Override
    public void cancelFriend(AddFriendResponse addFriendResponse) {

    }

    @Override
    public void createGroup(CreateGroupRes createGroupRes) {
        Log.e(TAG, "createGroup: "+ createGroupRes.getStatus() + " "+ createGroupRes.getRoom_id() );


        if(createGroupRes.getStatus()==200) {

            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(KeyData.KEY_ROOM, createGroupRes.getRoom_id());
            intent.putExtra(KeyData.KEY_ROOM_name, selectdName);
            startActivity(intent);
        }

    }

    @Override
    public void displayError(String s) {
        Log.e(TAG, "displayError:  " + s);

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
    public void UserFriendClick(int position, int status, UserdataItem userDataItem) {

        Intent intent;
        // status  1 for all  click , 0 for   menu  click

        Log.e(TAG, "UserFriendClick: " + position + " " + status + " " + userDataItem);

        selectdName=userDataItem.getName();

        if (userDataItem.getRoom_id() > 0) {

            intent = new Intent(this, ChatActivity.class);
            intent.putExtra(KeyData.KEY_ROOM, userDataItem.getRoom_id());
            intent.putExtra(KeyData.KEY_ROOM_name, selectdName);
            startActivity(intent);

        } else {

            JsonObject jsonObjectMain = new JsonObject();
            jsonObjectMain.addProperty("user_id", UserId);
            jsonObjectMain.addProperty("type", 1);
            jsonObjectMain.addProperty("friend_id", userDataItem.getUserTo());
            jsonObjectMain.addProperty("room_name", userDataItem.getName() + "-" + userDataItem.getMyName());
            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("member_id", userDataItem.getUserTo());
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