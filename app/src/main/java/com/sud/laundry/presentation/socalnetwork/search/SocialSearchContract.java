package com.sud.laundry.presentation.socalnetwork.search;

import com.google.gson.JsonObject;
import com.grocery.presentation.search.model.SearchListResponse;
import com.grocery.presentation.search.model.suggestion.SuggetionResponse;
import com.grocery.presentation.socalnetwork.search.model.SocialSearchRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;


@SuppressWarnings("WeakerAccess")
public interface SocialSearchContract {

    interface View {

        void showProgressBar();
        void hideProgressBar();
        void dataSearch(SocialSearchRes socialSearchRes);
        void dataResAddFriend(AddFriendResponse addFriendResponse);
        void displayError(String s);
        void showSuccessfulMessage(String message);
        void showFailedMessage(String message);
    }

    interface Presenter  {

        void usersearchList(JsonObject jsonObject);

        void  addFriendAndConfirm(JsonObject jsonObject);



    }



}
