package com.example.app.MyOrders.AllItem.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderPlacedResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private OrderRequest detail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public OrderRequest getDetail() {
        return detail;
    }

    public void setDetail(OrderRequest detail) {
        this.detail = detail;
    }
}
