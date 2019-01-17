package com.example.app.MyOrders.AllItem.datamodels;

import java.util.ArrayList;

public class OrderRequest {

    OrderDetails orderdetails;
    PaymentDetails paymentdetails;
    ArrayList<OrderItem> orderitemmap;


    public OrderRequest(OrderDetails orderdetails, PaymentDetails paymentDetails, ArrayList<OrderItem> orderitemmap) {
        this.orderdetails = orderdetails;
        this.paymentdetails = paymentDetails;
        this.orderitemmap = orderitemmap;
    }



}
