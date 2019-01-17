package com.example.app.MyOrders.AllItem.mvp;

import com.example.app.MyOrders.AllItem.datamodels.OrderDetails;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.AllItem.datamodels.PaymentDetails;

import java.util.ArrayList;

public interface ItemsPresenter {


   void createOrder(OrderDetails orderDetails, String acess_token, PaymentDetails paymentDetails, ArrayList<OrderItem> orderItems);

}
