package com.sud.laundry.bases;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sud.laundry.utils.Utils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setView();
    }

    protected abstract int getLayoutId();
    public void setView(){}

    public void showMessageToast(String message){
       // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Utils.toastErrorMessage(getApplicationContext(),message);
    }

}
