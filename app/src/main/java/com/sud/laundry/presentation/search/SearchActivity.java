package com.sud.laundry.presentation.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.Home.HomeActivity;
import com.grocery.presentation.Home.ui.shopPro.ShopProductActivity;
import com.grocery.presentation.cart.adapter.CartAdapter;
import com.grocery.presentation.order.OrderInfoActivity;
import com.grocery.presentation.search.adapter.SearchClickInterface;
import com.grocery.presentation.search.adapter.SearchSuggestAdapter;
import com.grocery.presentation.search.adapter.WordAdapter;
import com.grocery.presentation.search.model.SearchListResponse;
import com.grocery.presentation.search.model.suggestion.SuggetionListItem;
import com.grocery.presentation.search.model.suggestion.SuggetionResponse;
import com.grocery.utils.KeyData;
import com.grocery.utils.NavigationUtils;
import com.grocery.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SearchContract.View, SearchClickInterface {

    String TAG = "SearchActivity";

    @BindView(R.id.im_back)
    ImageView im_back;

    @BindView(R.id.im_search_clear)
    ImageView im_search_clear;

    @BindView(R.id.editQuery)
    EditText editQuery;
    @BindView(R.id.rv_suggest)
    RecyclerView rv_suggest;
    SearchSuggestAdapter searchSuggestAdapter;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    SearchPresenter searchPresenter;
    List<SuggetionListItem> suggetionListItemList;
    String word ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void setView() {
        super.setView();

        suggetionListItemList = new ArrayList<>();
        searchPresenter = new SearchPresenter(this, this);
        searchSuggestAdapter = new SearchSuggestAdapter(this, suggetionListItemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        rv_suggest.addItemDecoration(itemDecorator);
        rv_suggest.setLayoutManager(linearLayoutManager);
        rv_suggest.setItemAnimator(new DefaultItemAnimator());
        rv_suggest.setAdapter(searchSuggestAdapter);

        searchSuggestAdapter.notifyDataSetChanged();

        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                apiCall(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                word= editQuery.getText().toString();
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
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent searchIntent = new Intent(SearchActivity.this, SearchListDetailsActivity.class);
                    searchIntent.putExtra(KeyData.SEARCH_QUERY, word);
                    startActivity(searchIntent);
                    return true;
                }
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


    @Override
    public void onItemClick(SuggetionListItem result, int position) {

        Log.e(TAG, "onItemClick: " + suggetionListItemList.get(position).getName());

        if(result.getReType()==3){

            Intent intent = new Intent(this, ShopProductActivity.class);
            intent.putExtra(KeyData.SHOP_ID, result.getId());
            intent.putExtra(KeyData.SHOP_NAME, result.getName());
            startActivity(intent);

        }else {
            Intent intent = new Intent(getApplicationContext(), SearchListDetailsActivity.class);
            // Send KEYWORD to ResultActivity
            intent.putExtra(KeyData.SEARCH_QUERY, result.getName());
            startActivity(intent);

        }

    }


    @Override
    public void onItemLongClick(SuggetionListItem result, int position) {
        Log.v("long clicked", "position: " + position);
        // Get value of a specific position

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void dataResponse(SearchListResponse searchListResponse, int pageNo) {


    }

    @Override
    public void dataSuggetion(SuggetionResponse suggetionResponse) {

        if (suggetionResponse.getStatus() == 200) {

            suggetionListItemList.clear();

            if (suggetionResponse.getSuggetionList().size() > 0) {

                for (int i = 0; i < suggetionResponse.getSuggetionList().size(); i++) {

                    suggetionListItemList.add(suggetionResponse.getSuggetionList().get(i));

                }
                searchSuggestAdapter.notifyDataSetChanged();
            } else {
                searchSuggestAdapter.notifyDataSetChanged();

            }
        } else {
            suggetionListItemList.clear();
            searchSuggestAdapter.notifyDataSetChanged();

        }

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

    public void apiCall(String stSeartch) {

        if (stSeartch != null && stSeartch.length() > 0) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("search_query", stSeartch);

            Log.e(TAG, "apiCall: " + jsonObject.toString());
            searchPresenter.searchSuggetioList(jsonObject);
        }

    }

}
