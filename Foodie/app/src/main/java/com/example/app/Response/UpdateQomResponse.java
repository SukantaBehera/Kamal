package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class UpdateQomResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private String result;
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
