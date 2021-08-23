package com.sud.laundry.presentation.Home.ui.Search.adapter;


import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ProListItem;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ProductDetailsItem;
import com.grocery.presentation.Home.ui.home.homeModel.category.SubcatItem;

public interface ShopRowClickInterface {
    public  void  homeRowclick(int Clickstatus, ProductDetailsItem productDetailsItemArrayList, ProListItem proListItemArrayList) ;

    public  void  homeRowclick(SubcatItem subcatItem) ;

}
