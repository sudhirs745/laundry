package com.sud.laundry.presentation.Home.ui.profile;

import com.google.gson.JsonObject;
import com.grocery.presentation.account.model.UserDetals.UsersDataResponse;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.account.model.rewardPoints.RewardsPointRes;


@SuppressWarnings("WeakerAccess")
public interface ProfileContract {

    interface View {

        void showToast(String s);
        void showProgressBar();
        void hideProgressBar();
        void showSuccessfulMessage(String message);
        void userDataSussessful(UsersDataResponse usersDataResponse);
        void showFailedMessage(String message);
        void SignupSuccessful(DataResponse dataResponse);
        void RewardsPointSuccessful(RewardsPointRes rewardsPointRes);

    }

    interface Presenter  {

        void userData(String  token , JsonObject jsonObject);

        void  deleteAddress(JsonObject jsonObject);
        void  setDefaultAddress(JsonObject jsonObject);
        void  RewardPointsGet(JsonObject jsonObject);
    }


}
