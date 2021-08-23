package com.sud.laundry.presentation.socalnetwork.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.order.model.OrderInfo.CartDetailsItem;
import com.grocery.presentation.socalnetwork.friend.FriendContract;
import com.grocery.presentation.socalnetwork.friend.FriendPresenter;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.friend.model.friendList.UserdataItem;
import com.grocery.presentation.socalnetwork.group.adaper.FriendMultiSelectAdapter;
import com.grocery.presentation.socalnetwork.group.adaper.SelectedFriendAdapter;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.internal.Util;

public class CreateGroupActivity extends BaseActivity implements FriendContract.View {

    String TAG=CreateGroupActivity.class.getSimpleName();

    @BindView(R.id.im_back)
    ImageView im_back;
    @BindView(R.id.toolbar_center_title)
    TextView toolbar_center_title;
    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.et_group_title)
    EditText et_group_title;

    @BindView(R.id.tv_partiDetails)
    TextView tvPartiDetails;

    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.tv_error_txt_title)
    TextView tv_error_txt_title;
    @BindView(R.id.tv_error_txt_cause)
    TextView tv_error_txt_cause;
    FriendPresenter friendPresenter;
    int UserId = -1;

    SelectedFriendAdapter selectedFriendAdapter;

    ArrayList<UserdataItem> userDataItemArrayList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_group;
    }

    @Override
    public void setView() {
        super.setView();

        toolbar_center_title.setText("New group");
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, -1);
        errorLayout.setVisibility(View.GONE);

        friendPresenter = new FriendPresenter(this, this);
        userDataItemArrayList = (ArrayList<UserdataItem>) getIntent().getSerializableExtra(KeyData.KEY_SELECTED_USER);

        if (userDataItemArrayList != null) {
            tvPartiDetails.setText("Participates: " + userDataItemArrayList.size());
        }
        selectedFriendAdapter = new SelectedFriendAdapter(this, userDataItemArrayList);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(selectedFriendAdapter);
        selectedFriendAdapter.notifyDataSetChanged();


    }


    @OnClick({R.id.im_back, R.id.fb_create_group})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_back:
                finish();
                break;
            case R.id.fb_create_group:

                if (et_group_title.getText().toString().length() > 1) {
                    JsonObject jsonObjectMain= new JsonObject();
                    jsonObjectMain.addProperty("user_id", UserId);
                    jsonObjectMain.addProperty("type", 0);
                    jsonObjectMain.addProperty("friend_id", -1);
                    jsonObjectMain.addProperty("room_name" ,et_group_title.getText().toString());
                    if (userDataItemArrayList.size() > 0) {

                        JsonArray jsonArray = new JsonArray();
                        for (int i = 0; i < userDataItemArrayList.size(); i++) {
                            UserdataItem userdataItem = userDataItemArrayList.get(i);
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("member_id", userdataItem.getUserTo());
                            jsonObject.addProperty("admin_id", "0");
                            jsonArray.add(jsonObject);

                        }
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("member_id", UserId);
                        jsonObject.addProperty("admin_id", "1");
                        jsonArray.add(jsonObject);
                        jsonObjectMain.add("members_details",jsonArray);
                        friendPresenter.createGroup(jsonObjectMain);

                       Log.e(TAG, "onClick: " +  jsonObjectMain.toString() );


                } else {

                    Utils.toastErrorMessage(this, "Please select at least one friend");
                }


        }else{
            Utils.toastErrorMessage(this, "Provide a group subject");
        }

        break;

    }

}


    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void dataMyFriend(MyFriendListRes myFriendListRes) {

    }

    @Override
    public void cancelFriend(AddFriendResponse addFriendResponse) {

    }

    @Override
    public void createGroup(CreateGroupRes createGroupRes) {

        Log.e(TAG, "createGroup: " + createGroupRes.getResponse()  +  " room id " + createGroupRes.getRoom_id());

        Utils.toastSuccessMessage(this,createGroupRes.getResponse()+" ");
        if(createGroupRes.getStatus()==200){
            Intent returnIntent = getIntent();
            returnIntent.putExtra("ReturnValue", 1);
            setResult(RESULT_OK, returnIntent);
            Log.e("TAG", "onClick: Message sent");
            finish();
        }


    }

    @Override
    public void displayError(String s) {

    }

    @Override
    public void showSuccessfulMessage(String message) {

    }

    @Override
    public void showFailedMessage(String message) {

    }
}