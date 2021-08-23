package com.sud.laundry.presentation.socalnetwork.main;

import com.google.gson.JsonObject;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.main.model.SocialDetailsRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;


@SuppressWarnings("WeakerAccess")
public interface SocialContract {

    interface View {

        void showProgressBar();
        void hideProgressBar();
        void displayError(String s);
        void showSuccessfulMessage(String message);
        void showFailedMessage(String message);
        void showUserDetails(SocialDetailsRes socialDetailsRes);

    }

    interface Presenter  {

        void  userSocialDetails(JsonObject jsonObject);

    }



}
