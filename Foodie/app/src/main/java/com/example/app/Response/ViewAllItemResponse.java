package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ViewAllItemResponse {
    @SerializedName("status")
    private String status;

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



    @SerializedName("message")
    private String message;

    public ArrayList<ViewResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<ViewResult> result) {
        this.result = result;
    }

    @SerializedName("result")
    private ArrayList<ViewResult> result;


}
