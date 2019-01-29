package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderViewResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

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

    public ArrayList<ResultOrderView> getResult() {
        return result;
    }

    public void setResult(ArrayList<ResultOrderView> result) {
        this.result = result;
    }

    @SerializedName("result")
    private ArrayList<ResultOrderView> result;
}
