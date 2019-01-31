package com.example.app.Request;

public class PaymentdetailsRequest {
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public long getPay_mode_id() {
        return pay_mode_id;
    }

    public void setPay_mode_id(long pay_mode_id) {
        this.pay_mode_id = pay_mode_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    private String pay_date;
    private long pay_mode_id;
    private String status;
    private int transaction_id;
}
