package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class AllItemResult {
    @SerializedName("description")
    private String description;
    @SerializedName("entered_by")
    private int entered_by;

    @SerializedName("franch_view_flag")
    private String franch_view_flag;

    @SerializedName("id")
    private int id;
    @SerializedName("is_active")
    private String is_active;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEntered_by() {
        return entered_by;
    }

    public void setEntered_by(int entered_by) {
        this.entered_by = entered_by;
    }

    public String getFranch_view_flag() {
        return franch_view_flag;
    }

    public void setFranch_view_flag(String franch_view_flag) {
        this.franch_view_flag = franch_view_flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getQom_status() {
        return qom_status;
    }

    public void setQom_status(String qom_status) {
        this.qom_status = qom_status;
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

    @SerializedName("name")
    private String name;



    @SerializedName("price")
    private double price;

    @SerializedName("qom_status")
    private String qom_status;


    @SerializedName("quantity")
    private int quantity;

    @SerializedName("unit_id")
    private int unit_id;


}
