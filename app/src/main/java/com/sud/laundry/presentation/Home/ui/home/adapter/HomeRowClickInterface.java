package com.sud.laundry.presentation.Home.ui.home.adapter;

import com.grocery.presentation.Home.ui.home.homeModel.category.CategoriesItem;
import com.grocery.presentation.Home.ui.home.homeModel.category.SubcatItem;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.ProListItem;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.ProductDetailsItem;

import java.util.ArrayList;
import java.util.List;

public interface HomeRowClickInterface {
    public void homeRowclick(int Clickstatus, ProductDetailsItem productDetailsItemArrayList, ProListItem proListItemArrayList);

    public void homeRowclick(SubcatItem subcatItem);

    public void homeManCatClick(String  name , ArrayList<SubcatItem> subcatItemArrayList);

}
