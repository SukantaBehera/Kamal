package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class ResultOrderView {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private int price;
    @SerializedName("item_count")
    private int item_count;

    @SerializedName("order_by_cust_id")
    private String order_by_cust_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public String getOrder_by_cust_id() {
        return order_by_cust_id;
    }

    public void setOrder_by_cust_id(String order_by_cust_id) {
        this.order_by_cust_id = order_by_cust_id;
    }

    public String getPurchase_status() {
        return purchase_status;
    }

    public void setPurchase_status(String purchase_status) {
        this.purchase_status = purchase_status;
    }

    @SerializedName("purchase_status")
    private String purchase_status;
}
