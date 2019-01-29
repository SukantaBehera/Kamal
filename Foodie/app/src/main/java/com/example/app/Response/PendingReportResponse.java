package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PendingReportResponse {
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

    public ArrayList<ResultPending> getResult() {
        return result;
    }

    public void setResult(ArrayList<ResultPending> result) {
        this.result = result;
    }

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<ResultPending> result;

}
