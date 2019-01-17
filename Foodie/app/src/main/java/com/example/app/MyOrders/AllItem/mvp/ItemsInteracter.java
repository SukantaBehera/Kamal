package com.example.app.MyOrders.AllItem.mvp;


import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.datamodels.OrderRequest;

public interface ItemsInteracter {
    interface ItemListener {
        void onSuccess(OrderPlacedResponse response);

        void onError(String message);
    }

    void  placeOrder(OrderRequest request, String acess_token, ItemListener listener);




}
