package com.sud.laundry.presentation.socalnetwork.group;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.socalnetwork.friend.FriendClickInteface;
import com.grocery.presentation.socalnetwork.friend.FriendContract;
import com.grocery.presentation.socalnetwork.friend.FriendPresenter;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.friend.model.friendList.UserdataItem;
import com.grocery.presentation.socalnetwork.group.adaper.FriendMultiSelectAdapter;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectGroupUserActivity extends BaseActivity implements  FriendContract.View , FriendClickInteface {


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

    int REQUEST_CODE =201;
    ArrayList<UserdataItem> userDataItemArrayList;
    FriendMultiSelectAdapter friendMultiSelectAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_users_group;
    }

    @Override
    public void setView() {
        super.setView();
        toolbar_center_title.setText("Select Friend");
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, -1);
        errorLayout.setVisibility(View.GONE);
        friendPresenter = new FriendPresenter(this, this);

        userDataItemArrayList = new ArrayList<>();
        friendMultiSelectAdapter = new FriendMultiSelectAdapter(this, userDataItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(friendMultiSelectAdapter);
        friendMultiSelectAdapter.notifyDataSetChanged();

        getFriendList();

    }

    @Override
    protected void onResume() {
        super.onResume();

       // getFriendList();

    }


    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        //super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: Entered");
        if(requestCode == REQUEST_CODE) {
            Log.d("TAG", "onActivityResult: requestCode is OK");
            if (resultCode == RESULT_OK) {
                Log.d("TAG", "onActivityResult: result is returned OK");
                int ReturnValue = data.getIntExtra("ReturnValue",0);
                Log.d("TAG", "onActivityResult: done!");
                if(ReturnValue==1){
                    finish();
                }
            }
        }
        
    }

    private void getFriendList() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId);
        friendPresenter.MyfriendList(jsonObject);

    }

    @OnClick({R.id.im_back , R.id.fb_next})
    public void onClick(View v) {

        Intent intent ;
        switch (v.getId()) {

            case R.id.im_back:
                finish();
                break;

                case R.id.fb_next:
                    if (friendMultiSelectAdapter.getSelected().size() > 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < friendMultiSelectAdapter.getSelected().size(); i++) {
                            stringBuilder.append(friendMultiSelectAdapter.getSelected().get(i).getName());
                            stringBuilder.append("\n");
                        }
                        Utils.toastSuccessMessage(this,stringBuilder.toString().trim());

                        intent = new Intent(this , CreateGroupActivity.class);
                        intent.putExtra(KeyData.KEY_SELECTED_USER,friendMultiSelectAdapter.getSelected());
                        startActivityForResult(intent, REQUEST_CODE);
                        Log.d("TAG", "onClickListener: done!");

                    } else {
                        Utils.toastErrorMessage(this,"Please select at least one friend");
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
        friendMultiSelectAdapter.notifyDataSetChanged();

    }


    @Override
    public void cancelFriend(AddFriendResponse addFriendResponse) {

    }

    @Override
    public void createGroup(CreateGroupRes createGroupRes) {

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

        Log.e(TAG, "UserFriendClick: " + position + " " + status + " " + userDataItem);
    }


}