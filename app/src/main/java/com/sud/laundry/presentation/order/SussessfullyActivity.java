package com.sud.laundry.presentation.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.Home.HomeActivity;
import com.grocery.utils.KeyData;
import com.grocery.utils.NavigationUtils;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;

public class SussessfullyActivity extends BaseActivity {

    @BindView(R.id.im_succCancle)
    ImageView im_succCancle;
    @BindView(R.id.tv_sussMeaage)
    TextView tv_sussMeaage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sussessfully;
    }

    @Override
    public void setView() {
        super.setView();

        String OrderId = getIntent().getStringExtra(KeyData.KEY_ORDER);
        int statusCode = getIntent().getIntExtra(KeyData.KEY_STATUS, 0);

        if (statusCode == 1){

              im_succCancle.setImageResource(R.drawable.success_tick);
            tv_sussMeaage.setText("Your order Placed successfully");
        }else {
            im_succCancle.setImageResource(R.drawable.order_calcel);
            tv_sussMeaage.setText("Opps! Your order failed due to payment issue");

        }

    }

    @OnClick({R.id.btn_start_wish})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_start_wish:

                NavigationUtils.startActivity(this, HomeActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                // startActivity(new Intent(this, CartActivity.class));
                break;


        }
    }
}

