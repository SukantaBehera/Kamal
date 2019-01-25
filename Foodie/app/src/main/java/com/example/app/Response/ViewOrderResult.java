package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ViewOrderResult {

        @SerializedName("delivered_by_empId")
        private String delivered_by_empId;

        @SerializedName("delivered_by_empName")
        private String delivered_by_empName;
    @SerializedName("delivery_date")
        private String delivery_date;

        @SerializedName("dispatched_by_empId")
        private String dispatched_by_empId;
        @SerializedName("dispatched_by_empName")
        private String dispatched_by_empName;

        @SerializedName("dispatch_date")
        private String dispatch_date;

        @SerializedName("itemQOmEmbed")
        private ArrayList<ItemQOmEmbed>itemQOmEmbed;

        @SerializedName("orderby_custId")
        private int orderby_custId;

        @SerializedName("orderDate")
        private String orderDate;

        @SerializedName("order_deliv_status")
        private String order_deliv_status;

        @SerializedName("order_id")
        private int order_id;

        @SerializedName("total_price")
        private double total_price;

        @SerializedName("userName")
        private String userName;

    public String getDelivered_by_empId() {
        return delivered_by_empId;
    }

    public void setDelivered_by_empId(String delivered_by_empId) {
        this.delivered_by_empId = delivered_by_empId;
    }

    public String getDelivered_by_empName() {
        return delivered_by_empName;
    }

    public void setDelivered_by_empName(String delivered_by_empName) {
        this.delivered_by_empName = delivered_by_empName;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDispatched_by_empId() {
        return dispatched_by_empId;
    }

    public void setDispatched_by_empId(String dispatched_by_empId) {
        this.dispatched_by_empId = dispatched_by_empId;
    }

    public String getDispatched_by_empName() {
        return dispatched_by_empName;
    }

    public void setDispatched_by_empName(String dispatched_by_empName) {
        this.dispatched_by_empName = dispatched_by_empName;
    }

    public String getDispatch_date() {
        return dispatch_date;
    }

    public void setDispatch_date(String dispatch_date) {
        this.dispatch_date = dispatch_date;
    }

    public ArrayList<ItemQOmEmbed> getItemQOmEmbed() {
        return itemQOmEmbed;
    }

    public void setItemQOmEmbed(ArrayList<ItemQOmEmbed> itemQOmEmbed) {
        this.itemQOmEmbed = itemQOmEmbed;
    }

    public int getOrderby_custId() {
        return orderby_custId;
    }

    public void setOrderby_custId(int orderby_custId) {
        this.orderby_custId = orderby_custId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrder_deliv_status() {
        return order_deliv_status;
    }

    public void setOrder_deliv_status(String order_deliv_status) {
        this.order_deliv_status = order_deliv_status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRoleCode() {
        return userRoleCode;
    }

    public void setUserRoleCode(String userRoleCode) {
        this.userRoleCode = userRoleCode;
    }

    public String getUser_active_status() {
        return user_active_status;
    }

    public void setUser_active_status(String user_active_status) {
        this.user_active_status = user_active_status;
    }

    @SerializedName("userRoleCode")
        private String userRoleCode;

        @SerializedName("user_active_status")
        private String user_active_status;


}

