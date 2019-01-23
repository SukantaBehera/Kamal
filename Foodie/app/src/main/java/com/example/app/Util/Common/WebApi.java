package com.example.app.Util.Common;


import android.app.Application;

import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.datamodels.OrderRequest;
import com.example.app.Request.CashfreeMerchantRequest;
import com.example.app.Request.LoginRequest;
import com.example.app.Request.UpdateDistributorRequest;
import com.example.app.Request.UpdateEmployeeRequest;
import com.example.app.Request.UpdateFranchisorRequest;
import com.example.app.Request.UpdateQomRequest;
import com.example.app.Response.CashfreePaymantResponse;
import com.example.app.Response.ChangePasswordResponse;
import com.example.app.Response.GetAllQomReponse;
import com.example.app.Response.LoginResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.UpdateQomResponse;
import com.example.app.Response.UpdateUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface WebApi {




    @POST(AppConstants.takeOrder)
    Call<OrderPlacedResponse> takeOrder(@Body OrderRequest request, @Query("access_token") String param1);

    @POST(AppConstants.LOGIN)
    Call<LoginResponse> loginAdmin(@Body LoginRequest loginRequest);

    @GET(AppConstants.TOKEN)
    Call<TokenResponse> accessToken(@Query("grant_type") String grant_type,@Query("client_id") String client_id,
                                    @Query("client_secret") String client_secret,@Query("username") String username,
                                    @Query("password") String password);

    @GET(AppConstants.QOMLIST)
    Call<GetAllQomReponse> getAllQom(@Query("access_token") String access_token);

    @PUT(AppConstants.UPDATEQOM)
    Call<UpdateQomResponse> updateQom(@Query("access_token") String access_token, @Body UpdateQomRequest request);

    @POST(AppConstants.CHANGEPASSWORD)
    Call<ChangePasswordResponse> changePassword(@Query("access_token") String token,
                                                @Query("userId") String userId,
                                                @Query("oldPassword") String oldPassword,
                                                @Query("newPassword") String newPassword);

    @POST(AppConstants.UPDATEEMPLOYEE)
    Call<UpdateUserResponse>updateEmployee(@Query("access_token")String access_token, @Body UpdateEmployeeRequest request);

    @POST(AppConstants.UPDATEFRANCHISOR)
    Call<UpdateUserResponse>updateFranchisor(@Query("access_token")String access_token, @Body UpdateFranchisorRequest request);

    @POST(AppConstants.UPDATEDISTRIBUTOR)
    Call<UpdateUserResponse>updateDistributor(@Query("access_token")String access_token, @Body UpdateDistributorRequest request);



    @Headers({
            "Content-Type:application/json",
            "x-client-id:3232d5b14911b2d4d0ffeeb22323",
            "x-client-secret:83215f095dd6c529f54f653f137f3b6b03cc2f74"
    })
    @POST(AppConstants.CASHFREE)
    Call<CashfreePaymantResponse> getCashFreeToken(@Body CashfreeMerchantRequest cashfreeMerchantRequest);

}
