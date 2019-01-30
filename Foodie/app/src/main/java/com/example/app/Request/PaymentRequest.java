package com.example.app.Request;

import java.util.ArrayList;

public class PaymentRequest {

    private PaymentdetailsRequest paymentdetails;
    private OrderdetailsRequest orderdetails;

    public PaymentRequest(PaymentdetailsRequest paymentdetails, OrderdetailsRequest orderdetails, ArrayList<OrderitemmapRequest> orderitemmap) {
        this.paymentdetails = paymentdetails;
        this.orderdetails = orderdetails;
        this.orderitemmap = orderitemmap;
    }

    public PaymentdetailsRequest getPaymentdetails() {
        return paymentdetails;
    }

    public void setPaymentdetails(PaymentdetailsRequest paymentdetails) {
        this.paymentdetails = paymentdetails;
    }

    public OrderdetailsRequest getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(OrderdetailsRequest orderdetails) {
        this.orderdetails = orderdetails;
    }

    public ArrayList<OrderitemmapRequest> getOrderitemmap() {
        return orderitemmap;
    }

    public void setOrderitemmap(ArrayList<OrderitemmapRequest> orderitemmap) {
        this.orderitemmap = orderitemmap;
    }

    private ArrayList<OrderitemmapRequest> orderitemmap;
}
