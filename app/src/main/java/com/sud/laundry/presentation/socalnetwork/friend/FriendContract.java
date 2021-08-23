package com.sud.laundry.presentation.socalnetwork.friend;

import com.google.gson.JsonObject;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.search.model.SocialSearchRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;


@SuppressWarnings("WeakerAccess")
public interface FriendContract  {

    interface View {

        void showProgressBar();
        void hideProgressBar();
        void dataMyFriend(MyFriendListRes myFriendListRes);
        void cancelFriend(AddFriendResponse addFriendResponse);
        void createGroup(CreateGroupRes createGroupRes);
        void displayError(String s);
        void showSuccessfulMessage(String message);
        void showFailedMessage(String message);

    }

    interface Presenter  {

        void MyfriendList(JsonObject jsonObject);
        void CancelFriendRequest(JsonObject jsonObject);
        void addFriendAndConfirm(JsonObject jsonObject);
        void createGroup(JsonObject jsonObject);


    }



}
