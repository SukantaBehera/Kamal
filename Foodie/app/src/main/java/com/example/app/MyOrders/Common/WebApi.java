package com.example.app.MyOrders.Common;


import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.datamodels.OrderRequest;
import com.example.app.Request.LoginRequest;
import com.example.app.Request.UpdateQomRequest;
import com.example.app.Response.GetAllQomReponse;
import com.example.app.Response.LoginResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.UpdateQomResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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




}
