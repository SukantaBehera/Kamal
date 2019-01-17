package com.example.app.USERLIST.MODEL;

public class Franchisordetail {


    String fran_id;
    String company_name;
    String phone_no;
    String email_id;
    String unit_address;
    String resident_address;
    String status;
    String user_id;

    public Franchisordetail(String fran_id, String company_name, String phone_no, String email_id, String unit_address, String resident_address, String status, String user_id) {
        this.fran_id = fran_id;
        this.company_name = company_name;
        this.phone_no = phone_no;
        this.email_id = email_id;
        this.unit_address = unit_address;
        this.resident_address = resident_address;
        this.status = status;
        this.user_id = user_id;
    }

    public String getFran_id() {
        return fran_id;
    }

    public void setFran_id(String fran_id) {
        this.fran_id = fran_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUnit_address() {
        return unit_address;
    }

    public void setUnit_address(String unit_address) {
        this.unit_address = unit_address;
    }

    public String getResident_address() {
        return resident_address;
    }

    public void setResident_address(String resident_address) {
        this.resident_address = resident_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}