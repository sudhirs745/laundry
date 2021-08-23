package com.sud.laundry.presentation.account.login;
import com.sud.laundry.bases.BasePresenter;
import com.sud.laundry.bases.BaseView;
import com.sud.laundry.presentation.account.model.LoginResponse;


@SuppressWarnings("WeakerAccess")
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showToast(String s);
        void showProgressBar();
        void hideProgressBar();
        void showSuccessfulMessage(String message);
        void loginSuccessful(LoginResponse loginResponse);
        void showFailedMessage(String message);

    }

    interface Presenter extends BasePresenter {

        void login(String username , String password);


    }


}
