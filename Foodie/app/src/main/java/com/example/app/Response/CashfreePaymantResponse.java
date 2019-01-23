package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class CashfreePaymantResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("cftoken")
    private String cftoken;

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

    public String getCftoken() {
        return cftoken;
    }

    public void setCftoken(String cftoken) {
        this.cftoken = cftoken;
    }
}

