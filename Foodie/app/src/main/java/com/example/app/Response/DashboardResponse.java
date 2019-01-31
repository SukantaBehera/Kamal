package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DashboardResponse {
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

    public ArrayList<DashboardResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<DashboardResult> result) {
        this.result = result;
    }

    @SerializedName("result")
    private ArrayList<DashboardResult> result;
}
