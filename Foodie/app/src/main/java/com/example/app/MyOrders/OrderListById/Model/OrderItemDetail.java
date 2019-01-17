package com.example.app.MyOrders.OrderListById.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.app.MyOrders.AllItem.datamodels.OrderItem;

import java.util.ArrayList;

public class OrderItemDetail implements Parcelable {


    Double total_price;
    String order_id;
    String cust_id;
    String order_date;
    String status;
    String order_status;
    String name;
    String desc;
    Double price;
    String itemcount;
    ArrayList arrayList;
    ArrayList<OrderItem> arrayListItem;

    public OrderItemDetail(Double total_price, String order_id, String cust_id, String order_date, String status, String name, ArrayList arrayList, ArrayList<OrderItem> arrayList1) {
        this.total_price = total_price;
        this.order_id = order_id;
        this.cust_id = cust_id;
        this.status = status;
        this.order_date = order_date;
        this.name = name;
        this.arrayList= arrayList;
        this.arrayListItem= arrayList1;
    }

    public OrderItemDetail(String name, String desc, Double price, String itemcount) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.itemcount = itemcount;
    }

    public OrderItemDetail(Double total_price, String order_id, String cust_id, String order_date, String order_status, String name, String desc, Double price, String itemcount) {
        this.total_price = total_price;
        this.order_id = order_id;
        this.cust_id = cust_id;
        this.status = status;
        this.order_date = order_date;
        this.order_status = order_status;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.itemcount = itemcount;
    }

    protected OrderItemDetail(Parcel in) {
        if (in.readByte() == 0) {
            total_price = null;
        } else {
            total_price = in.readDouble();
        }
        order_id = in.readString();
        cust_id = in.readString();
        order_date = in.readString();
        status = in.readString();
        order_status = in.readString();
        name = in.readString();
        desc = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        itemcount = in.readString();
        arrayListItem = in.createTypedArrayList(OrderItem.CREATOR);
    }

    public static final Creator<OrderItemDetail> CREATOR = new Creator<OrderItemDetail>() {
        @Override
        public OrderItemDetail createFromParcel(Parcel in) {
            return new OrderItemDetail(in);
        }

        @Override
        public OrderItemDetail[] newArray(int size) {
            return new OrderItemDetail[size];
        }
    };

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getItemcount() {
        return itemcount;
    }

    public void setItemcount(String itemcount) {
        this.itemcount = itemcount;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<OrderItem> getArrayListItem() {
        return arrayListItem;
    }

    public void setArrayListItem(ArrayList<OrderItem> arrayListItem) {
        this.arrayListItem = arrayListItem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (total_price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(total_price);
        }
        parcel.writeString(order_id);
        parcel.writeString(cust_id);
        parcel.writeString(order_date);
        parcel.writeString(status);
        parcel.writeString(order_status);
        parcel.writeString(name);
        parcel.writeString(desc);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeString(itemcount);
        parcel.writeTypedList(arrayListItem);
    }
}