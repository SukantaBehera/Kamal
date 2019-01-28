package com.example.app.Request;

public class MyOrderUpdateRequest {
    private int order_id;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }



    public String getDispatched_by() {
        return dispatched_by;
    }

    public void setDispatched_by(String dispatched_by) {
        this.dispatched_by = dispatched_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDispatch_date() {
        return dispatch_date;
    }

    public void setDispatch_date(String dispatch_date) {
        this.dispatch_date = dispatch_date;
    }

    private String dispatch_date;
    private String dispatched_by;
    private String status;

}
