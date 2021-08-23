package com.sud.laundry.presentation.socalnetwork.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import com.grocery.presentation.fullscreendialog.FullScreenDialogFragment;
import com.grocery.presentation.fullscreendialog.ViewImageFragment;
import com.grocery.presentation.product.ProductDetails.ProductDetailsActivity;
import com.grocery.presentation.socalnetwork.friend.model.friendList.UserdataItem;
import com.grocery.presentation.socalnetwork.group.adaper.GroupListAdapter;
import com.grocery.presentation.socalnetwork.group.adaper.ListMessageAdapter;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageDetailsItem;
import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageListResponse;
import com.grocery.presentation.socalnetwork.group.model.groupInfo.GroupInfoResponse;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupDetailsItem;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupListRes;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;
import com.sud.camerawhatsapp.CameraWhatsapp;
import com.sud.camerawhatsapp.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements GroupContract.View, MessageClickInterface, FullScreenDialogFragment.OnConfirmListener, FullScreenDialogFragment.OnDiscardListener, FullScreenDialogFragment.OnDiscardFromExtraActionListener {

    private TextView fullName;
    private FullScreenDialogFragment dialogFragment;
    String TAG = "ChatActivity";
    @BindView(R.id.toolbar_center_title)
    TextView toolbar_center_title;
    @BindView(R.id.recyclerChat)
    RecyclerView recyclerChat;

//    @BindView(R.id.error_layout)
//    LinearLayout errorLayout;
//    @BindView(R.id.tv_error_txt_title)
//    TextView tv_error_txt_title;

//    @BindView(R.id.tv_error_txt_cause)
//    TextView tv_error_txt_cause;

    @BindView(R.id.editWriteMessage)
    EditText editWriteMessage;

    GroupPresenter groupPresenter;
    int UserId = -1;

    int roomId = -1;
    String Username = "";
    String ProfileUrl = "";

    String groupName = "";

    ArrayList<MessageDetailsItem> consersation = new ArrayList<>();

    ListMessageAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    Options options;
    ArrayList<String> returnValue = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void setView() {
        super.setView();

        roomId = getIntent().getIntExtra(KeyData.KEY_ROOM, -1);
        groupName = getIntent().getStringExtra(KeyData.KEY_ROOM_name);
        toolbar_center_title.setText(groupName + " ");
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, -1);
        Username = AppSharedPreference.getInstance(this).getString(AppSharedPreference.SP_USER_NAME, "");
        ProfileUrl = AppSharedPreference.getInstance(this).getString(AppSharedPreference.SP_PROFILE_URL, "");

        // errorLayout.setVisibility(View.GONE);
        groupPresenter = new GroupPresenter(this, this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerChat.setLayoutManager(linearLayoutManager);
        adapter = new ListMessageAdapter(this, consersation);
        recyclerChat.setAdapter(adapter);
        linearLayoutManager.scrollToPosition(consersation.size() - 1);

        final String dialogTag = "dialog";
        dialogFragment = (FullScreenDialogFragment) getSupportFragmentManager().findFragmentByTag(dialogTag);
        if (dialogFragment != null) {
            dialogFragment.setOnConfirmListener(this);
            dialogFragment.setOnDiscardListener(this);
            dialogFragment.setOnDiscardFromExtraActionListener(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessageList();
        AppSharedPreference.getInstance(getApplicationContext()).setBoolean(AppSharedPreference.KEY_NOTIFY_STATUS, false);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(KeyData.KEY_MESSAGE_REFRESH));
    }

    private void getMessageList() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId + "");
        jsonObject.addProperty("room_id", roomId + "");
        groupPresenter.getGroupMessage(jsonObject);

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.e("receiver", "Got message: " + message);
            getMessageList();
        }
    };

    //Must unregister onPause()
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        AppSharedPreference.getInstance(getApplicationContext()).setBoolean(AppSharedPreference.KEY_NOTIFY_STATUS, true);

    }


    @OnClick({R.id.im_back, R.id.v_tool, R.id.btnSend, R.id.im_camera})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_back:
                finish();
                break;
            case R.id.btnSend:

                String content = editWriteMessage.getText().toString().trim();
                if (content.length() > 0) {
                    editWriteMessage.setText("");
                    MessageDetailsItem newMessage = new MessageDetailsItem();
                    newMessage.setMessage(content);
                    newMessage.setMssgBy(UserId);
                    newMessage.setRoomId(roomId);
                    newMessage.setName(Username);
                    newMessage.setProfileUrl(ProfileUrl);
                    newMessage.setMessageTime(System.currentTimeMillis() + "");
                    consersation.add(newMessage);
                    adapter.notifyDataSetChanged();

                    linearLayoutManager.scrollToPosition(consersation.size() - 1);

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("user_id", UserId);
                    jsonObject.addProperty("name", Username + "");
                    jsonObject.addProperty("room_id", roomId);
                    jsonObject.addProperty("product_id", "0");
                    jsonObject.addProperty("messgae_text", content);

                    groupPresenter.sendMessage(jsonObject);

                }

                break;

            case R.id.v_tool:

                Intent intent = new Intent(this, GroupInfoActivity.class);
                intent.putExtra(KeyData.KEY_ROOM, roomId);
                intent.putExtra(KeyData.KEY_ROOM_name, groupName);
                startActivity(intent);

                break;

            case R.id.im_camera:
                captureFromCamera();

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
    public void dataGroupList(GroupListRes groupListRes) {

    }

    @Override
    public void createGroup(CreateGroupRes createGroupRes) {

    }

    @Override
    public void sendMessageRes(CreateGroupRes createGroupRes) {

        Log.e(TAG, "sendMessageRes: " + createGroupRes.getStatus());

    }

    @Override
    public void groupInfoResponse(GroupInfoResponse groupInfoResponse) {

    }


    int MessageId = 0;

    @Override
    public void getMessageList(MessageListResponse messageListResponse) {

        if (messageListResponse.getStatus() == 200) {
            consersation.clear();
            for (int i = 0; i < messageListResponse.getMessageDetails().size(); i++) {

                if (messageListResponse.getMessageDetails().get(i).getMess_deleted() <= 0) {
                    MessageId = messageListResponse.getMessageDetails().get(i).getMessageId();
                    Log.e(TAG, "getMessageList:-- " + messageListResponse.getMessageDetails().get(i));
                    consersation.add(messageListResponse.getMessageDetails().get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
        linearLayoutManager.scrollToPosition(consersation.size() - 1);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", UserId + "");
        jsonObject.addProperty("room_id", roomId + "");
        jsonObject.addProperty("message_id", MessageId + "");
        groupPresenter.showMessageUpdate(jsonObject);

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

    @Override
    public void deleteMessage(CreateGroupRes createGroupRes) {

        if (createGroupRes.getStatus() == 200) {

            getMessageList();
        }

    }


    @Override
    public void messageClick(int position, MessageDetailsItem messageDetailsItem) {

        Log.e(TAG, "messageClick: " + messageDetailsItem.toString());
        if (messageDetailsItem != null) {
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra(KeyData.PRODUCT_ID, messageDetailsItem.getProduct_id());
            startActivity(intent);
        }

    }

    @Override
    public void deleteMessageClick(int position, MessageDetailsItem messageDetailsItem) {
        Log.e(TAG, "DeleteMessageClick: " + messageDetailsItem.toString());

        dialogBox(position, messageDetailsItem);


    }

    private void captureFromCamera() {

        returnValue.clear();
        options = Options.init()
                .setRequestCode(100)
                .setCount(1)
                .setFrontfacing(false)
                .setPreSelectedUrls(returnValue)
                .setExcludeVideos(false)
                .setVideoDurationLimitinSeconds(60)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/DigiToMart");
        options.setPreSelectedUrls(returnValue);
        CameraWhatsapp.start(ChatActivity.this, options);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    returnValue = data.getStringArrayListExtra(CameraWhatsapp.IMAGE_RESULTS);
                    String StringUrl;
                    if (returnValue.size() > 0) {

                        StringUrl = returnValue.get(0);
                        Log.e(TAG, "onActivityResult: " + StringUrl);

                        if (StringUrl.contains(".png") || StringUrl.contains(".PNG") || StringUrl.contains(".jpg") || StringUrl.contains(".jpeg") || StringUrl.contains(".JPG") || StringUrl.contains(".JPEG")) {
                            Log.e(TAG, "onActivityResult: " + StringUrl);

                            oprnDialog(StringUrl);


                        } else {
                            Utils.showMessage(this, "This  file format is not supported");
                        }
                    }
                }
            }
            break;
        }
    }

    private void oprnDialog(String stringUrl) {

        final Bundle args = new Bundle();
        args.putString(KeyData.KEY_IMAGE_URL, stringUrl);

        dialogFragment = new FullScreenDialogFragment.Builder(ChatActivity.this)
                .setTitle("insert_surname")
                .setConfirmButton("dialog_positive_button")
                .setOnConfirmListener(ChatActivity.this)
                .setOnDiscardListener(ChatActivity.this)
                .setContent(ViewImageFragment.class, args)
                .setOnDiscardFromActionListener(ChatActivity.this)
                .build();

        dialogFragment.show(getSupportFragmentManager(), "extra_items");


    }


    @Override
    public void onConfirm(@Nullable Bundle result) {

        String imageUrl = result.getString(KeyData.KEY_IMAGE_URL);

        Log.e(TAG, "onConfirm:  " + result.getString(KeyData.KEY_IMAGE_URL));


        MessageDetailsItem newMessage = new MessageDetailsItem();
        newMessage.setMessage("");
        newMessage.setImgSrc(imageUrl);
        newMessage.setMssgBy(UserId);
        newMessage.setRoomId(roomId);
        newMessage.setName(Username);
        newMessage.setImageType(true);
        newMessage.setProfileUrl(ProfileUrl);
        newMessage.setMessageTime(System.currentTimeMillis() + "");
        consersation.add(newMessage);
        adapter.notifyDataSetChanged();
        linearLayoutManager.scrollToPosition(consersation.size() - 1);
        groupPresenter.sendImageMessage(UserId + "", roomId + "", Username, imageUrl);


    }

    @Override
    public void onDiscard() {

    }

    @Override
    public void onDiscardFromExtraAction(int actionId, @Nullable Bundle result) {

        //Log.e(TAG, "onDiscardFromExtraAction:  " +  result.getString(KeyData.KEY_IMAGE_URL);

    }


    public void dialogBox(int position, MessageDetailsItem messageDetailsItem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Delete message?");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                        if (position == -1) {

                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("user_id", UserId + "");
                            jsonObject.addProperty("room_id", roomId + "");
                            jsonObject.addProperty("message_id", messageDetailsItem.getMessageId() + "");
                            groupPresenter.deleteOtherUserMessage(jsonObject);

                        } else {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("user_id", UserId + "");
                            jsonObject.addProperty("room_id", roomId + "");
                            jsonObject.addProperty("message_id", messageDetailsItem.getMessageId() + "");
                            groupPresenter.deleteMessage(jsonObject);
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}