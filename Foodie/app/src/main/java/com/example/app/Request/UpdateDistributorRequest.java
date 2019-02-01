package com.example.app.Request;

public class UpdateDistributorRequest {
    private String dist_id;
    private String company_name;
    private String unit_holder_name;
    private String unit_address;
    private String permanent_address;
    private String phone_no;
    private String email_id;
    private String status;

    public UpdateDistributorRequest(String dist_id, String company_name,
                                    String unit_holder_name, String unit_address,
                                    String permanent_address, String phone_no, String email_id, String status) {
        this.dist_id = dist_id;
        this.company_name = company_name;
        this.unit_holder_name = unit_holder_name;
        this.unit_address = unit_address;
        this.permanent_address = permanent_address;
        this.phone_no = phone_no;
        this.email_id = email_id;
        this.status = status;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getUnit_holder_name() {
        return unit_holder_name;
    }

    public void setUnit_holder_name(String unit_holder_name) {
        this.unit_holder_name = unit_holder_name;
    }

    public String getUnit_address() {
        return unit_address;
    }

    public void setUnit_address(String unit_address) {
        this.unit_address = unit_address;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
