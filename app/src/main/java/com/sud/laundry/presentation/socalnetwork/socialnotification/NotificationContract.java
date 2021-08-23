package com.sud.laundry.presentation.socalnetwork.socialnotification;

import com.google.gson.JsonObject;
import com.grocery.presentation.socalnetwork.search.model.SocialSearchRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.presentation.socalnetwork.socialnotification.model.SocialNotiyRes;


@SuppressWarnings("WeakerAccess")
public interface NotificationContract {

    interface View {

        void showProgressBar();
        void hideProgressBar();
        void dataNotification(SocialNotiyRes socialNotiyRes);
        void displayError(String s);
        void showSuccessfulMessage(String message);
        void showFailedMessage(String message);
    }

    interface Presenter  {

        void getNotification(JsonObject jsonObject);



    }



}
