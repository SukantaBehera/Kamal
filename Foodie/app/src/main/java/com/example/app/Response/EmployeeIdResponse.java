package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EmployeeIdResponse {
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

    public ArrayList<EmployeeIDResultResponse> getResult() {
        return result;
    }

    public void setResult(ArrayList<EmployeeIDResultResponse> result) {
        this.result = result;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ArrayList<EmployeeIDResultResponse> result;
}
