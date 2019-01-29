package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

public class EmployeeIDResultResponse {
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @SerializedName("userID")
    private int userID;
    @SerializedName("userName")
    private String userName;
}
