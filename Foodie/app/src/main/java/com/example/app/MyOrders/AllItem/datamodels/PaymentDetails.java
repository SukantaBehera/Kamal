package com.example.app.MyOrders.AllItem.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentDetails implements Parcelable {


    Integer pay_mode_id;
    Integer transaction_id;
    Double amount;
    String status;
    String pay_date;

    public PaymentDetails(Integer pay_mode_id, Integer transaction_id, Double amount, String status, String pay_date) {
        this.pay_mode_id = pay_mode_id;
        this.transaction_id = transaction_id;
        this.amount = amount;
        this.status = status;
        this.pay_date = pay_date;
    }


    protected PaymentDetails(Parcel in) {
        if (in.readByte() == 0) {
            pay_mode_id = null;
        } else {
            pay_mode_id = in.readInt();
        }
        if (in.readByte() == 0) {
            transaction_id = null;
        } else {
            transaction_id = in.readInt();
        }
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        status = in.readString();
        pay_date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (pay_mode_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pay_mode_id);
        }
        if (transaction_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(transaction_id);
        }
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
        dest.writeString(status);
        dest.writeString(pay_date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaymentDetails> CREATOR = new Creator<PaymentDetails>() {
        @Override
        public PaymentDetails createFromParcel(Parcel in) {
            return new PaymentDetails(in);
        }

        @Override
        public PaymentDetails[] newArray(int size) {
            return new PaymentDetails[size];
        }
    };
}
