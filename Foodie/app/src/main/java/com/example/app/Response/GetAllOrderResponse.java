package com.example.app.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAllOrderResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private ArrayList<Result> result;


    public class  Result{

        @SerializedName("delivered_by_empId")
        private String delivered_by_empId;

        @SerializedName("delivered_by_empName")
        private String delivered_by_empName;

        @SerializedName("delivery_date")
        private String delivery_date;

        @SerializedName("dispatched_by_empId")
        private String dispatched_by_empId;

        @SerializedName("dispatched_by_empName")
        private String dispatched_by_empName;

        @SerializedName("dispatch_date")
        private String dispatch_date;



        @SerializedName("itemQOmEmbed")
        private ArrayList<ItemQOmEmbed>itemQOmEmbed;


        @SerializedName("orderby_custId")
        private int orderby_custId;

        @SerializedName("orderDate")
        private String orderDate;

        @SerializedName("order_deliv_status")
        private String order_deliv_status;

        @SerializedName("order_id")
        private int order_id;

        @SerializedName("total_price")
        private double total_price;

        @SerializedName("userName")
        private String userName;

        @SerializedName("userRoleCode")
        private String userRoleCode;

        @SerializedName("user_active_status")
        private String user_active_status;



    }
}
