package com.example.app.QOM.Model;

public class QomModel {

    String name;
    String description;
    double price;
    String status;
    Integer unit_id;
    String entered_by;
    String id;
    String qom_status;
    Integer quantity_avail;




    public QomModel(String name, String description, double price, String status, Integer unit_id, String entered_by, String id, String qom_status, Integer quantity_avail) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.unit_id = unit_id;
        this.entered_by = entered_by;
        this.id = id;
        this.qom_status = qom_status;
        this.quantity_avail = quantity_avail;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Integer unit_id) {
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

    public String getQom_status() {
        return qom_status;
    }

    public void setQom_status(String qom_status) {
        this.qom_status = qom_status;
    }

    public Integer getQuantity_avail() {
        return quantity_avail;
    }

    public void setQuantity_avail(Integer quantity_avail) {
        this.quantity_avail = quantity_avail;
    }
}

