package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAllItemsResponse {
    @SerializedName("message")
    private String message;

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




    @SerializedName("status")
    private String status;

    public ArrayList<AllItemResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<AllItemResult> result) {
        this.result = result;
    }

    @SerializedName("result")
    private ArrayList<AllItemResult> result;


}
