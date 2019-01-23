package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class ViewResultCart {

    private String description;

    private int entered_by;

    private String franch_view_flag;

    private int id;

    private String is_active;

    private int item_id;

    private String name;

    private double price;

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

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
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

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }


    private int unit_id;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private double totalPrice;

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    private int totalQuantity;



}
