package com.sud.laundry.presentation.Home.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.grocery.R;
import com.grocery.presentation.Home.ui.home.adapter.CategoryAdapter;
import com.grocery.presentation.Home.ui.home.adapter.HomeRecyclerViewDataAdapter;
import com.grocery.presentation.Home.ui.home.adapter.HomeRowClickInterface;
import com.grocery.presentation.Home.ui.home.adapter.MainCatAdapter;
import com.grocery.presentation.Home.ui.home.adapter.MainCategoryAdapter;
import com.grocery.presentation.Home.ui.home.adapter.MainSliderAdapterImage;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoriesItem;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.category.SubcatItem;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.ProListItem;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.ProductDetailsItem;
import com.grocery.presentation.category.SubCategoryActivity;
import com.grocery.presentation.newsContent.NewsOfferActivity;
import com.grocery.presentation.product.ProductDetails.ProductDetailsActivity;
import com.grocery.presentation.product.productList.ProductListActivity;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.userDatabase.DbRepository;
import com.grocery.userDatabase.model.LoginModel;
import com.grocery.utils.KeyData;
import com.grocery.utils.lab.NewsflashView;
import com.grocery.utils.lab.model.NewsflashModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.core.util.Preconditions.checkNotNull;

public class HomeFragment extends Fragment implements HomeContract.View, HomeRowClickInterface {

    String TAG = "HomeFragment";

    @BindView(R.id.imageSlider)
    SliderView imageSlider;

    @BindView(R.id.rv_category)
    RecyclerView recyclerView_cat;

    @BindView(R.id.rv_home)
    RecyclerView rv_hame;

    @BindView(R.id.rv_main_category)
    RecyclerView rv_main_category;

    @BindView(R.id.newsflash)
    NewsflashView newsflash;


    CategoryAdapter categoryAdapter;
    MainCategoryAdapter mainCategoryAdapter;
    MainCatAdapter mainCatAdapter;
    List<SubcatItem> categoriesItemList = new ArrayList<>();

    List<CategoriesItem> maincategoriesItemArrayList = new ArrayList<>();

    HomePresenter homePresenter;

    DbRepository dbRepository;

    HomeRecyclerViewDataAdapter homeRecyclerViewDataAdapter;
    MainSliderAdapterImage adapter;
    ArrayList<NewsflashModel> mNewsflashModels = new ArrayList();

    ArrayList<NewsflashModel> mNewsflashModels1 = new ArrayList();

    public static Fragment newInstance() {
        HomeFragment myFragment = new HomeFragment();
        return myFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        dbRepository = new DbRepository(getActivity());
        homePresenter = new HomePresenter(this, getActivity());
        setUi();
        getUserList();

        newsflash.setVisibility(View.GONE);
        homePresenter.HomeListDetall(new JsonObject());
        adapter = new MainSliderAdapterImage(getActivity());
        imageSlider.setSliderAdapter(adapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.RED);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSlider.startAutoCycle();


        newsflash.setOnNewsflashClickListener(new NewsflashView.OnNewsflashClickListener() {
            @Override
            public void onNewsflashClick(int position) {

                String stTitle = mNewsflashModels.get(position).getLabel();
                String stBody = mNewsflashModels.get(position).getContent();
                Intent intent = new Intent(getActivity(), NewsOfferActivity.class);
                intent.putExtra(KeyData.KEY_TITLE, stTitle);
                intent.putExtra(KeyData.KEY_BODY, stBody);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, mNewsflashModels.get(position).getContent(), Toast.LENGTH_SHORT).show();
            }
        });
        newsflash.startFlipping();


        return root;
    }

    private void getUserList() {

        dbRepository.getTasks().observe(getActivity(), new Observer<List<LoginModel>>() {
            @Override
            public void onChanged(@Nullable List<LoginModel> loginModels) {
                if (loginModels.size() > 0) {

                    for (int i = 0; i < loginModels.size(); i++) {
                        Log.e(TAG, "email: " + loginModels.get(i).getEmail());
                        Log.e(TAG, "name: " + loginModels.get(i).getName());
                        Log.e(TAG, "ref: " + loginModels.get(i).getReferralCode());
                        Log.e(TAG, "token: " + loginModels.get(i).getToken());
                    }
                } else {
                    Log.e(TAG, " onChanged: data not found ");
                }
            }
        });
    }


    private void setUi() {
        categoryAdapter = new CategoryAdapter(categoriesItemList, getActivity(), this);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_cat.setLayoutManager(HorizontalLayout);
        recyclerView_cat.setAdapter(categoryAdapter);

        homePresenter.getCategory();

        homeRecyclerViewDataAdapter = new HomeRecyclerViewDataAdapter(getActivity(), this);
        LinearLayoutManager homelaoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_hame.setLayoutManager(homelaoutManager);
        rv_hame.setAdapter(homeRecyclerViewDataAdapter);

//        mainCatAdapter = new MainCatAdapter(maincategoriesItemArrayList, getActivity(), this);
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_main_category.setLayoutManager(layoutManager);
//        rv_main_category.setAdapter(mainCatAdapter);


        mainCategoryAdapter = new MainCategoryAdapter(categoriesItemList, getActivity(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_main_category.setLayoutManager(layoutManager);
        rv_main_category.setAdapter(mainCategoryAdapter);

    }


    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void dataResponse(CategoryRes categoryRes) {

        Log.e(TAG, "DataResponse: " + categoryRes.toString());

        // categoriesItemList.clear();

       // maincategoriesItemArrayList.clear();

        for (int i = 0; i < categoryRes.getCategories().size(); i++) {

           // maincategoriesItemArrayList.add(categoryRes.getCategories().get(i));

            if (categoryRes.getCategories().get(i).getMId() == 10000) {

                for (int j = 0; j < categoryRes.getCategories().get(i).getSubcat().size(); j++) {
                    categoriesItemList.add(categoryRes.getCategories().get(i).getSubcat().get(j));
                }
            }
        }

        categoryAdapter.notifyDataSetChanged();
        //mainCatAdapter.notifyDataSetChanged();
        mainCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void catProListResponse(HomeListResponse homeListResponse) {

        if (homeListResponse.getStatus() == 200) {


            adapter.renewItems(homeListResponse.getBannerList());

            homeRecyclerViewDataAdapter.addAll(homeListResponse.getProductDetails());

            mNewsflashModels.clear();
            for (int i = 0; i < homeListResponse.getHeadlinesDetailsItemList().size(); i++) {

                mNewsflashModels.add(new NewsflashModel(homeListResponse.getHeadlinesDetailsItemList().get(i).getTitle(), homeListResponse.getHeadlinesDetailsItemList().get(i).getBody()));

//                mNewsflashModels.add(new NewsflashModel("Privacy policy", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"));
//                mNewsflashModels.add(new NewsflashModel("About Us", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"));
//                mNewsflashModels.add(new NewsflashModel("Contact us", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"));


            }
            if (mNewsflashModels.size() > 0) {
                newsflash.setVisibility(View.VISIBLE);
                newsflash.setLabeledNewsflash(mNewsflashModels);
            } else {
                newsflash.setVisibility(View.GONE);
            }

        }


    }

    @Override
    public void displayError(String s) {
        Log.e(TAG, "displayError: " + s);

    }

    @Override
    public void showSuccessfulMessage(String message) {
        Log.e(TAG, "showSuccessfulMessage: " + message);

    }

    @Override
    public void showFailedMessage(String message) {
        Log.e(TAG, "showFailedMessage: " + message);

    }


    @OnClick({R.id.v_search_bar})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.v_search_bar:

                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

        }
    }

    @Override
    public void homeRowclick(int ClickStatus, ProductDetailsItem productDetailsItemArrayList, ProListItem proListItemArrayList) {
// click status  0 for parent  , productlist , 1 for product details

        Intent intent;
        if (ClickStatus == 0) {
            if (productDetailsItemArrayList != null) {
                intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(KeyData.CATEGORY_ID, productDetailsItemArrayList.getCategoryId());
                startActivity(intent);
            }
        } else if (ClickStatus == 1) {

            if (proListItemArrayList != null) {
                intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(KeyData.PRODUCT_ID, proListItemArrayList.getProductId());
                startActivity(intent);
            }
        }


    }

    @Override
    public void homeRowclick(SubcatItem subcatItem) {

        Intent intent = new Intent(getActivity(), ProductListActivity.class);
        intent.putExtra(KeyData.CATEGORY_ID, subcatItem.getCategoryId());
        startActivity(intent);
    }

    @Override
    public void homeManCatClick(String  name , ArrayList<SubcatItem> subcatItemArrayList) {

        Intent intent = new Intent(getActivity(), SubCategoryActivity.class);
        intent.putExtra(KeyData.CATEGORY_DETAILS, subcatItemArrayList);
        intent.putExtra(KeyData.CATEGORY_NAME, name);
        startActivity(intent);
    }
}
