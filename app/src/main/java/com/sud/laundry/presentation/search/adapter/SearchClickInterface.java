package com.sud.laundry.presentation.search.adapter;

import android.view.View;
import android.widget.AdapterView;

import com.grocery.presentation.search.model.suggestion.SuggetionListItem;

public interface SearchClickInterface {

    void onItemLongClick( SuggetionListItem result, int position) ;
    void onItemClick(SuggetionListItem result, int position ) ;
}

