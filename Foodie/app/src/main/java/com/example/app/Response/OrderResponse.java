package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderResponse {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ViewOrderResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<ViewOrderResult> result) {
        this.result = result;
    }

    @SerializedName("message")
    private String message;
     @SerializedName("status")
    private String status;
   @SerializedName("result")
    private ArrayList<ViewOrderResult> result;


}
