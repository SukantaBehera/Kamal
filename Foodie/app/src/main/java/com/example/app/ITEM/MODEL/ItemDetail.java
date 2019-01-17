package com.example.app.ITEM.MODEL;

public class ItemDetail {



    String itemId;
    String name;
    String description;
    String price;
    String status;
    String unit_id;
    String entered_by;
    String id;
    String buyStatus;

    public ItemDetail(String itemId, String name, String description, String price, String status, String unit_id, String entered_by, String id,String byStatus) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.unit_id = unit_id;
        this.entered_by = entered_by;
        this.id = id;
        this.buyStatus = byStatus;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getEntered_by() {
        return entered_by;
    }

    public void setEntered_by(String entered_by) {
        this.entered_by = entered_by;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(String buyStatus) {
        this.buyStatus = buyStatus;
    }
}
