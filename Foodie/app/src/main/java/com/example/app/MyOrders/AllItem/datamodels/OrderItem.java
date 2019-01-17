package com.example.app.MyOrders.AllItem.datamodels;


import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {

    Integer item_id;
    Integer item_count;
    Double total_price;
    String order_date;
    String order_status;

    String item_name;
    String description;
    Double price;
    String itemCount;
    String status;


    public OrderItem(String item_name, String description, Double price, String itemCount, String status) {
        this.item_name = item_name;
        this.description = description;
        this.price = price;
        this.itemCount = itemCount;
        this.status = status;
    }

    public OrderItem(Integer item_id, Integer item_count, Double total_price, String order_date, String order_status, String itemName) {
        this.item_id = item_id;
        this.item_count = item_count;
        this.total_price = total_price;
        this.order_date = order_date;
        this.order_status = order_status;
        this.item_name = itemName;
    }


    protected OrderItem(Parcel in) {
        if (in.readByte() == 0) {
            item_id = null;
        } else {
            item_id = in.readInt();
        }
        if (in.readByte() == 0) {
            item_count = null;
        } else {
            item_count = in.readInt();
        }
        if (in.readByte() == 0) {
            total_price = null;
        } else {
            total_price = in.readDouble();
        }
        order_date = in.readString();
        order_status = in.readString();
        item_name = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        itemCount = in.readString();
        status = in.readString();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
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

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Creator<OrderItem> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (item_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(item_id);
        }
        if (item_count == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(item_count);
        }
        if (total_price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(total_price);
        }
        parcel.writeString(order_date);
        parcel.writeString(order_status);
        parcel.writeString(item_name);
        parcel.writeString(description);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeString(itemCount);
        parcel.writeString(status);
    }
}
