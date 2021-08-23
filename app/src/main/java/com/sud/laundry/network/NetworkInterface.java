package com.sud.laundry.network;

import com.google.gson.JsonObject;
import com.grocery.presentation.Home.ui.Search.shopModel.ShopListItem;
import com.grocery.presentation.Home.ui.Search.shopModel.ShopListResponse;
import com.grocery.presentation.Home.ui.Search.shopModel.shopPro.ShopProductResponse;
import com.grocery.presentation.Home.ui.home.homeModel.category.CategoryRes;
import com.grocery.presentation.Home.ui.home.homeModel.homeList.HomeListResponse;
import com.grocery.presentation.Home.ui.shopPro.ShopProductActivity;
import com.grocery.presentation.account.model.UserDetals.UsersDataResponse;
import com.grocery.presentation.account.model.blockAdd.BlockAddRes;
import com.grocery.presentation.account.model.login.DataResponse;
import com.grocery.presentation.account.model.loginSuccModel.LoginSuccessRes;
import com.grocery.presentation.account.model.rewardPoints.RewardsPointRes;
import com.grocery.presentation.account.model.stateCityModels.StateCityBlockRes;
import com.grocery.presentation.cart.cartModel.CartRes;
import com.grocery.presentation.order.model.OrderInfo.OrderInfoResponse;
import com.grocery.presentation.order.model.orderAdd.OrderAddResponse;
import com.grocery.presentation.order.model.orderDetails.OrderDetailsResponse;
import com.grocery.presentation.product.ProductModel.cartadd.CartResponse;
import com.grocery.presentation.product.ProductModel.productDetails.ProductDetailsResponse;
import com.grocery.presentation.product.ProductModel.productList.ProductListResponse;
import com.grocery.presentation.search.model.SearchListResponse;
import com.grocery.presentation.search.model.suggestion.SuggetionResponse;
import com.grocery.presentation.socalnetwork.friend.model.friendList.MyFriendListRes;
import com.grocery.presentation.socalnetwork.group.model.CreateGroupRes;
import com.grocery.presentation.socalnetwork.group.model.chatModel.MessageListResponse;
import com.grocery.presentation.socalnetwork.group.model.groupInfo.GroupInfoResponse;
import com.grocery.presentation.socalnetwork.group.model.grouplist.GroupListRes;
import com.grocery.presentation.socalnetwork.main.model.SocialDetailsRes;
import com.grocery.presentation.socalnetwork.search.model.SocialSearchRes;
import com.grocery.presentation.socalnetwork.search.model.addFriends.AddFriendResponse;
import com.grocery.presentation.socalnetwork.socialnotification.model.SocialNotiyRes;
import com.sud.laundry.presentation.account.model.LoginResponse;

import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface NetworkInterface {


    @Headers("Content-Type: application/json")
    @POST("ClientIspmateApi/login")
    Observable<LoginResponse> login(@Body JsonObject body);



}
