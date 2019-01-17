package com.example.app.MyOrders.AllItem.mvp;



import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.datamodels.OrderRequest;
import com.example.app.MyOrders.Common.ApiClient;
import com.example.app.MyOrders.Common.WebApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemsInteracterImpl implements ItemsInteracter {




    @Override
    public void placeOrder(OrderRequest request, String acess_token, final ItemListener listener) {
        Retrofit retrofit = ApiClient.getRetrofit();
        WebApi webApi = retrofit.create(WebApi.class);
        Call<OrderPlacedResponse> call = webApi.takeOrder(request,acess_token);
        call.enqueue(new Callback<OrderPlacedResponse>() {
            @Override
            public void onResponse(Call<OrderPlacedResponse> call, Response<OrderPlacedResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onError("Please Try Again.");
                }
            }

            @Override
            public void onFailure(Call<OrderPlacedResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });

    }

}
