package com.sud.laundry.presentation.socalnetwork.group;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.socalnetwork.group.adaper.GroupInfoListAdapter;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageListResponse;
import com.grocery.presentation.socalnetwork.group.model.groupInfo.GroupInfoDetailsItem;
import com.grocery.presentation.socalnetwork.group.model.groupInfo.GroupInfoResponse;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupDetailsItem;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupListRes;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupInfoActivity extends BaseActivity implements GroupContract.View, GroupClickInteface {


    String TAG = "GroupDetailsActivity";

    @BindView(R.id.im_back)
    ImageView im_back;
    @BindView(R.id.toolbar_center_title)
    TextView toolbar_center_title;
    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.n_scrolle)
    NestedScrollView n_scrolle;


    GroupPresenter groupPresenter;
    int UserId = -1;

    int roomId = -1;
    String Username = "";
    String groupName = "";
    GroupInfoListAdapter groupInfoListAdapter;

    List<GroupInfoDetailsItem> groupInfoDetailsItemList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_info;
    }

    @Override
    public void setView() {
        super.setView();
        roomId = getIntent().getIntExtra(KeyData.KEY_ROOM, -1);
        groupName = getIntent().getStringExtra(KeyData.KEY_ROOM_name);
        toolbar_center_title.setText(groupName + " ");
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, -1);
        groupPresenter = new GroupPresenter(this, this);

        groupInfoDetailsItemList = new ArrayList<>();
        groupInfoListAdapter = new GroupInfoListAdapter(this, groupInfoDetailsItemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(groupInfoListAdapter);
        groupInfoListAdapter.notifyDataSetChanged();
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getGroupInfo();
    }

    private void getGroupInfo() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("room_id", roomId);
        groupPresenter.getgroupInfo(jsonObject);

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
    public void GroupClick(int position, GroupDetailsItem groupDetailsItem) {

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

    }

    @Override
    public void createGroup(CreateGroupRes createGroupRes) {

    }

    @Override
    public void sendMessageRes(CreateGroupRes createGroupRes) {

    }

    @Override
    public void groupInfoResponse(GroupInfoResponse groupInfoResponse) {

        if (groupInfoResponse.getStatus() == 200) {
            groupInfoDetailsItemList.clear();
            for (int i = 0; i < groupInfoResponse.getGroupInfoDetails().size(); i++) {
                groupInfoDetailsItemList.add(groupInfoResponse.getGroupInfoDetails().get(i));
            }
        }

        groupInfoListAdapter.notifyDataSetChanged();

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

}