package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class ResultQOM {

    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntered_by() {
        return entered_by;
    }

    public void setEntered_by(int entered_by) {
        this.entered_by = entered_by;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQom_status() {
        return qom_status;
    }

    public void setQom_status(String qom_status) {
        this.qom_status = qom_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("entered_by")
    private int entered_by;
    @SerializedName("price")
    private double price;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("unit_id")
    private int unit_id;
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;
    @SerializedName("qom_status")
    private String qom_status;
    @SerializedName("status")
    private String status;
}
