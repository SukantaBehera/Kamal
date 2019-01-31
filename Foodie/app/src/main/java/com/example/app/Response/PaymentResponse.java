package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
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

    public PaymentResult getResult() {
        return result;
    }

    public void setResult(PaymentResult result) {
        this.result = result;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private PaymentResult result;

}
