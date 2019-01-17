package com.example.app.MyOrders.Common;


import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.datamodels.OrderRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebApi {




    @POST(AppConstants.takeOrder)
    Call<OrderPlacedResponse> takeOrder(@Body OrderRequest request, @Query("access_token") String param1);




}
