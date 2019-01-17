package com.example.app.USERLIST.MODEL;

public class Employeedetail {


    String emp_id;
    String name;
    String designation;
    String address;
    String adhharcardno;
    String phone_no;
    String email_id;
    String status;
    String user_id;

    public Employeedetail(String emp_id, String name, String designation, String address, String adhharcardno, String phone_no, String email_id, String status, String user_id) {
        this.emp_id = emp_id;
        this.name = name;
        this.designation = designation;
        this.address = address;
        this.adhharcardno = adhharcardno;
        this.phone_no = phone_no;
        this.email_id = email_id;
        this.status = status;
        this.user_id = user_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdhharcardno() {
        return adhharcardno;
    }

    public void setAdhharcardno(String adhharcardno) {
        this.adhharcardno = adhharcardno;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}