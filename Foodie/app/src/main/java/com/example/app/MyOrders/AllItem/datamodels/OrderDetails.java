package com.example.app.MyOrders.AllItem.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderDetails implements Parcelable {

        Integer  cust_id;
        String  status;
        String  date;


    public OrderDetails(Integer cust_id, String status, String date) {
        this.cust_id = cust_id;
        this.status = status;
        this.date = date;
    }

    protected OrderDetails(Parcel in) {
        if (in.readByte() == 0) {
            cust_id = null;
        } else {
            cust_id = in.readInt();
        }
        status = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (cust_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cust_id);
        }
        dest.writeString(status);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDetails> CREATOR = new Creator<OrderDetails>() {
        @Override
        public OrderDetails createFromParcel(Parcel in) {
            return new OrderDetails(in);
        }

        @Override
        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };
}
