package com.sud.laundry.presentation.socalnetwork.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.presentation.search.SearchListDetailsActivity;
import com.grocery.presentation.search.SearchPresenter;
import com.grocery.presentation.search.adapter.SearchSuggestAdapter;
import com.grocery.presentation.search.model.suggestion.SuggetionListItem;
import com.grocery.presentation.socalnetwork.search.adaper.SocialSearchAdapter;
import com.grocery.presentation.socalnetwork.search.model.SocialSearchRes;
import com.grocery.presentation.socalnetwork.search.model.UserDataItem;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.utils.AppSharedPreference;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class SocialSearchActivity extends BaseActivity implements SocialSearchContract.View, SocialSearchInteface {

    String TAG = "SocialSearchActivity";

    @BindView(R.id.im_back)
    ImageView im_back;

    @BindView(R.id.im_search_clear)
    ImageView im_search_clear;

    @BindView(R.id.editQuery)
    EditText editQuery;
    @BindView(R.id.rv_suggest)
    RecyclerView rv_suggest;

    SocialSearchPresenter socialSearchPresenter;
    SocialSearchAdapter socialSearchAdapter;
    List<UserDataItem> userDataItemArrayList;

    String word;
    int UserId;

    int position;
    int friendstatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_social_search;
    }

    @Override
    public void setView() {
        super.setView();
        UserId = AppSharedPreference.getInstance(this).getInteger(AppSharedPreference.SP_USER_ID, 0);
        userDataItemArrayList = new ArrayList<>();
        socialSearchPresenter = new SocialSearchPresenter(this, this);
        socialSearchAdapter = new SocialSearchAdapter(this, userDataItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        rv_suggest.addItemDecoration(itemDecorator);
        rv_suggest.setLayoutManager(linearLayoutManager);
        rv_suggest.setItemAnimator(new DefaultItemAnimator());
        rv_suggest.setAdapter(socialSearchAdapter);

        socialSearchAdapter.notifyDataSetChanged();

        Random r = new Random();
        char s = (char) (r.nextInt(26) + 'a');
        Log.e(TAG, "setView: " + s);
        apiCall(" ");

        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                apiCall(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                word = editQuery.getText().toString();
                if (editQuery.getText().toString().trim().length() == 1) {
                    editQuery.clearFocus();
                    editQuery.requestFocus();
                    im_search_clear.setImageResource(R.drawable.ic_close_black_24dp);
                    // editQuery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_back_black_24dp, 0, R.drawable.ic_close_black_24dp, 0);

                }
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        editQuery.requestFocus();
        editQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    Intent searchIntent = new Intent(SearchActivity.this, SearchListDetailsActivity.class);
//                    searchIntent.putExtra(KeyData.SEARCH_QUERY, word);
//                    startActivity(searchIntent);
//                    return true;
//                }
//

                return false;
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.hideKeyboard(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.fade_out);
    }


    @Override
    protected void onDestroy() {
        // PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(listener);
        super.onDestroy();
    }

    @OnClick({R.id.im_search_clear, R.id.im_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_search_clear:
                editQuery.getText().clear();
                break;
            case R.id.im_back:
                finish();
                break;
        }
    }


    public void apiCall(String stSeartch) {

        if (stSeartch != null && stSeartch.length() > 0) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("search_query", stSeartch);
            jsonObject.addProperty("user_id", UserId);
            Log.e(TAG, "apiCall: " + jsonObject.toString());
            socialSearchPresenter.usersearchList(jsonObject);
        }

    }


    @Override
    public void showProgressBar() {

        Utils.showProfressBarActivity(this);

    }

    @Override
    public void hideProgressBar() {

        Utils.dissmissActiviity();
    }

    @Override
    public void dataSearch(SocialSearchRes socialSearchRes) {


        if (socialSearchRes.getStatus() == 1 || socialSearchRes.getStatus() == 200) {

            userDataItemArrayList.clear();

            if (socialSearchRes.getUserData().size() > 0) {
                for (int i = 0; i < socialSearchRes.getUserData().size(); i++) {
                    userDataItemArrayList.add(socialSearchRes.getUserData().get(i));
                }
                socialSearchAdapter.notifyDataSetChanged();
            } else {
                socialSearchAdapter.notifyDataSetChanged();

            }
        } else {
            userDataItemArrayList.clear();
            socialSearchAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void dataResAddFriend(AddFriendResponse addFriendResponse) {

        Log.e(TAG, "dataResAddFriend: " + addFriendResponse.getResponse());


        if (userDataItemArrayList.size() > position) {
            if (friendstatus == 1) {
                userDataItemArrayList.get(position).setPendingFriendStatus(true);
            } else if (friendstatus == 2) {
                userDataItemArrayList.get(position).setFriendRequestStatus(true);
                userDataItemArrayList.get(position).setFriend(true);
            } else {

            }
        }
        socialSearchAdapter.notifyDataSetChanged();

    }

    @Override
    public void displayError(String s) {

    }

    @Override
    public void showSuccessfulMessage(String message) {

    }

    @Override
    public void showFailedMessage(String message) {

    }


    @Override
    public void socialClick(int position, int status, UserDataItem userDataItem) {

        // status 1= add friend  , 2 for accept , 3 for  remove cancel , 4  request cancel

        Log.e(TAG, "socialClick: " + status + " " + position + " " + userDataItem);

        this.position = position;
        this.friendstatus = status;


        if (status == 1) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", UserId);
            jsonObject.addProperty("user_id_to", userDataItem.getUserId());
            jsonObject.addProperty("status", 1);
            socialSearchPresenter.addFriendAndConfirm(jsonObject);

        } else if (status == 2) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", UserId);
            jsonObject.addProperty("user_id_to", userDataItem.getUserId());
            jsonObject.addProperty("status", 2);
            socialSearchPresenter.addFriendAndConfirm(jsonObject);
        }


    }
}