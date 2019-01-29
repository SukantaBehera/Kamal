package com.example.app.Request;

public class MyOrderUpdateDeliveryRequest {
    private int order_id;

    public MyOrderUpdateDeliveryRequest(int order_id, String delivery_date, String delivered_by, String status) {
        this.order_id = order_id;
        this.delivery_date = delivery_date;
        this.delivered_by = delivered_by;
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivered_by() {
        return delivered_by;
    }

    public void setDelivered_by(String delivered_by) {
        this.delivered_by = delivered_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String delivery_date;
    private String delivered_by;
    private String status;

}
