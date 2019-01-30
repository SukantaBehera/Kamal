package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentResult {
    public PaymentdetailsResponse getPaymentdetails() {
        return paymentdetails;
    }

    public void setPaymentdetails(PaymentdetailsResponse paymentdetails) {
        this.paymentdetails = paymentdetails;
    }

    public OrderdetailsResponse getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(OrderdetailsResponse orderdetails) {
        this.orderdetails = orderdetails;
    }

    public ArrayList<OrderitemmapResponse> getOrderitemmap() {
        return orderitemmap;
    }

    public void setOrderitemmap(ArrayList<OrderitemmapResponse> orderitemmap) {
        this.orderitemmap = orderitemmap;
    }

    @SerializedName("paymentdetails")
    private PaymentdetailsResponse paymentdetails;
    @SerializedName("orderdetails")
    private OrderdetailsResponse orderdetails;

    @SerializedName("orderitemmap")
    private ArrayList<OrderitemmapResponse> orderitemmap;

    public class OrderitemmapResponse{
        @SerializedName("order_id")
        private String  order_id;
        @SerializedName("id")
        private String  id;
        @SerializedName("item_id")
        private int item_id;
        @SerializedName("item_count")
        private int item_count;
        @SerializedName("total_price")
        private int total_price;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public int getItem_count() {
            return item_count;
        }

        public void setItem_count(int item_count) {
            this.item_count = item_count;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public long getOrder_date() {
            return order_date;
        }

        public void setOrder_date(long order_date) {
            this.order_date = order_date;
        }

        @SerializedName("order_status")
        private String  order_status;

        @SerializedName("order_date")
        private long order_date;

    }
    public class OrderdetailsResponse{
        @SerializedName("id")
        private String  id;
        @SerializedName("cust_id")
        private int cust_id;
        @SerializedName("payment_id")
        private int payment_id;
        @SerializedName("date")
        private long date;
        @SerializedName("distributor_id")
        private int distributor_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCust_id() {
            return cust_id;
        }

        public void setCust_id(int cust_id) {
            this.cust_id = cust_id;
        }

        public int getPayment_id() {
            return payment_id;
        }

        public void setPayment_id(int payment_id) {
            this.payment_id = payment_id;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public int getDistributor_id() {
            return distributor_id;
        }

        public void setDistributor_id(int distributor_id) {
            this.distributor_id = distributor_id;
        }

        public String getPurchase_stat() {
            return purchase_stat;
        }

        public void setPurchase_stat(String purchase_stat) {
            this.purchase_stat = purchase_stat;
        }

        @SerializedName("purchase_stat")
        private String purchase_stat;

    }
    public class PaymentdetailsResponse{
        @SerializedName("id")
        private String  id;
        @SerializedName("pay_mode_id")
        private int pay_mode_id;


        @SerializedName("amount")
        private int amount;
        @SerializedName("status")
        private String  status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPay_mode_id() {
            return pay_mode_id;
        }

        public void setPay_mode_id(int pay_mode_id) {
            this.pay_mode_id = pay_mode_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String  getStatus() {
            return status;
        }

        public void setStatus(String  status) {
            this.status = status;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public long getPay_date() {
            return pay_date;
        }

        public void setPay_date(long pay_date) {
            this.pay_date = pay_date;
        }

        @SerializedName("transaction_id")
        private String transaction_id;
        @SerializedName("pay_date")
        private long  pay_date;

    }
}
