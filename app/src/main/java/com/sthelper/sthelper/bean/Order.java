package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by luffy on 15/7/25.
 */
public class Order implements Parcelable {
    public MainInfo mainInfo;
    public List<OrderItem> goodsInfo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mainInfo, 0);
        dest.writeTypedList(goodsInfo);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.mainInfo = in.readParcelable(MainInfo.class.getClassLoader());
        this.goodsInfo = in.createTypedArrayList(OrderItem.CREATOR);
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
