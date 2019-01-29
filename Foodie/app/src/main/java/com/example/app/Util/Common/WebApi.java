package com.example.app.Util.Common;


import android.app.Application;

import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.datamodels.OrderRequest;
import com.example.app.Request.CashfreeMerchantRequest;
import com.example.app.Request.DeleteUserRequest;
import com.example.app.Request.LoginRequest;
import com.example.app.Request.MyOrderUpdateDeliveryRequest;
import com.example.app.Request.MyOrderUpdateRequest;
import com.example.app.Request.UpdateDistributorRequest;
import com.example.app.Request.UpdateEmployeeRequest;
import com.example.app.Request.UpdateFranchisorRequest;
import com.example.app.Request.UpdateQomRequest;
import com.example.app.Response.CashfreePaymantResponse;
import com.example.app.Response.ChangePasswordResponse;
import com.example.app.Response.DeleteUserResponse;
import com.example.app.Response.GetAllQomReponse;
import com.example.app.Response.LoginResponse;
import com.example.app.Response.MyOrderUpdateResponse;
import com.example.app.Response.OrderResponse;
import com.example.app.Response.PendingReportResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.UpdateQomResponse;
import com.example.app.Response.UpdateUserResponse;
import com.example.app.Response.ViewAllItemResponse;
import com.google.gson.JsonObject;

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
            "x-client-id:3337dc7156012958cc5c3d1f7333",
            "x-client-secret:2c7919671808922dff110e6aeb7be60e4c010249"
    })
    @POST(AppConstants.CASHFREE)
    Call<CashfreePaymantResponse> getCashFreeToken(@Body CashfreeMerchantRequest cashfreeMerchantRequest);

    @GET(AppConstants.ALLITEMS)
    Call<ViewAllItemResponse> getAllItem(@Query("access_token") String access_token);

    @GET(AppConstants.ORDERALLITEMS)
    Call<OrderResponse> getOrderAllItem(@Query("access_token") String access_token);

    @GET(AppConstants.ORDERALLITEMS_BYID)
    Call<OrderResponse> getOrderById(@Query("access_token") String access_token,@Query("custId") String custId);


    @POST(AppConstants.DELETEDISTRIBUTOR_BYID)
    Call<DeleteUserResponse> deletedistributor(@Query("access_token") String access_token, @Query("userId") String userId);

    @POST(AppConstants.DELETEFRANCHISOR_BYID)
    Call<DeleteUserResponse> deletefranchisor(@Query("access_token") String access_token, @Query("userId") String userId);

    @POST(AppConstants.DELETEEMPLOYEE_BYID)
    Call<DeleteUserResponse> deleteemployee(@Query("access_token") String access_token, @Query("userId") String userId);


    @Headers("Content-Type: application/json")
    @POST(AppConstants.STATUS_UPDATE)
    Call<MyOrderUpdateResponse> getUpdateDispatchResponse(@Query("access_token") String access_token,@Body MyOrderUpdateRequest jsonObject);

  /*  @Headers("Content-Type: application/json")
    @POST(AppConstants.STATUS_UPDATE)
    Call<MyOrderUpdateResponse> getUpdateDeliveryResponse(@Query("access_token") String access_token,@Body MyOrderUpdateDeliveryRequest myOrderUpdateRequest);*/

    @Headers("Content-Type: application/json")
    @POST(AppConstants.STATUS_UPDATE)
    Call<MyOrderUpdateResponse> getUpdateDeliveryResponse(@Query("access_token") String access_token, @Body MyOrderUpdateDeliveryRequest jsonObject);


    @GET(AppConstants.ORDER_REPORT_PENDING)
    Call<PendingReportResponse> getPendingReport(@Query("access_token") String access_token);

    @GET(AppConstants.ORDER_REPORT_DISPATCHED)
    Call<PendingReportResponse> getDispatchedReport(@Query("access_token") String access_token);

    @GET(AppConstants.ORDER_REPORT_DELIVERED)
    Call<PendingReportResponse> getDeliveredReport(@Query("access_token") String access_token);

    @GET(AppConstants.ORDER_REPORT_DATEWISE)
    Call<PendingReportResponse> getDatewiseReport(@Query("access_token") String access_token,@Query("startdate") String startdate,@Query("enddate") String enddate);


}
