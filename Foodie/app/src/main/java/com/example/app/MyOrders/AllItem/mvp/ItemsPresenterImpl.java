package com.example.app.MyOrders.AllItem.mvp;


import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.datamodels.OrderDetails;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.AllItem.datamodels.OrderRequest;
import com.example.app.MyOrders.AllItem.datamodels.PaymentDetails;

import java.util.ArrayList;

public class ItemsPresenterImpl implements ItemsPresenter, ItemsInteracter.ItemListener {

    ItemsView view;
    ItemsInteracterImpl interacter;

    public ItemsPresenterImpl(ItemsView view) {
        this.view = view;
        interacter = new ItemsInteracterImpl();
    }



    @Override
    public void onSuccess(OrderPlacedResponse response) {
        if (view != null) {
            view.hideDialog();
            view.onLoad(response);
        }
    }

    @Override
    public void onError(String message) {
        if (view != null) {
            view.hideDialog();
            view.showError(message);
        }
    }



    @Override
    public void createOrder(OrderDetails orderDetails, String acess_token, PaymentDetails paymentDetails, ArrayList<OrderItem> itemDetail) {

        OrderRequest request =new OrderRequest(orderDetails,paymentDetails,itemDetail);
        interacter.placeOrder(request,acess_token,this);
    }

}
