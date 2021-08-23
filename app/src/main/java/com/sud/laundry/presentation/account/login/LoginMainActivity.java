package com.sud.laundry.presentation.account.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
;

import com.sud.laundry.R;
import com.sud.laundry.bases.BaseActivity;
import com.sud.laundry.presentation.Home.HomeActivity;
import com.sud.laundry.presentation.account.model.LoginResponse;
import com.sud.laundry.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

import static androidx.core.util.Preconditions.checkNotNull;

public class LoginMainActivity extends BaseActivity implements LoginContract.View {


    @BindView(R.id.edit_username)
    EditText edit_username;

    @BindView(R.id.edit_password)
    EditText edit_password;

    LoginContract.Presenter mPresenter;
    LoginPresenter loginPresenter;
    private static final String TAG = LoginMainActivity.class.getSimpleName();


    protected int getLayoutId() {
        return R.layout.activity_login_main;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);


    }

    @Override
    public void setView() {
        super.setView();

    }


    @OnClick({R.id.next_button )
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button:

                loginPresenter.validateLoginFields(new EditText[]{edit_username, edit_password});


        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Utils.toastErrorMessage(this, "Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public void showToast(String s) {
        showMessageToast(s);

    }

    @Override
    public void showProgressBar() {
        Log.e(TAG, "showProgressBar: ");
        Utils.showProfressBarActivity(this);

    }

    @Override
    public void hideProgressBar() {
        Log.e(TAG, "hideProgressBar: ");
        Utils.dissmissActiviity();

    }

    @Override
    public void showSuccessfulMessage(String message) {
        showMessageToast(message);
    }

    @Override
    public void loginSuccessful(LoginResponse loginResponse) {

        String statuscode = loginResponse.getStatus();

        if (statuscode.equalsIgnoreCase ("200")) {

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else {
            Utils.toastWarringMessage(this, loginResponse.getResponse());

        }

    }

    @Override
    public void showFailedMessage(String message) {
        showMessageToast(message);
    }

    @Override
    public void showMessageToast(String message) {
        super.showMessageToast(message);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter = new LoginPresenter(this, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
