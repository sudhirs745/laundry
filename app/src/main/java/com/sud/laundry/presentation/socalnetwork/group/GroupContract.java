package com.sud.laundry.presentation.socalnetwork.group;

import com.google.gson.JsonObject;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageListResponse;
import com.grocery.presentation.socalnetwork.group.model.groupInfo.GroupInfoResponse;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupListRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;


@SuppressWarnings("WeakerAccess")
public interface GroupContract {

    interface View {

        void showProgressBar();
        void hideProgressBar();
        void dataGroupList(GroupListRes groupListRes);
        void createGroup(CreateGroupRes createGroupRes);
        void sendMessageRes(CreateGroupRes createGroupRes);
        void groupInfoResponse(GroupInfoResponse groupInfoResponse);
        void getMessageList(MessageListResponse messageListResponse);
        void displayError(String s);
        void showSuccessfulMessage(String message);
        void showFailedMessage(String message);
        void deleteMessage(CreateGroupRes createGroupRes);

    }

    interface Presenter  {

        void getGroupList(JsonObject jsonObject);
        void createGroup(JsonObject jsonObject);
        void getGroupMessage(JsonObject jsonObject);
        void sendMessage(JsonObject jsonObject);
        void getgroupInfo(JsonObject jsonObject);
        void sendImageMessage(String userId , String roomId, String Name, String ImagePath);
        void showMessageUpdate(JsonObject jsonObject );
        void deleteMessage(JsonObject jsonObject );

        void deleteOtherUserMessage(JsonObject jsonObject );


    }



}
