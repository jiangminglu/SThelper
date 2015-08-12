package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by luffy on 15/7/15.
 */
public class OrderItem implements Parcelable {

    public OrderMainInfo mainInfo;
    public List<OrderGoodsItem> goodsInfo;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mainInfo, 0);
        dest.writeTypedList(goodsInfo);
    }

    public OrderItem() {
    }

    protected OrderItem(Parcel in) {
        this.mainInfo = in.readParcelable(OrderMainInfo.class.getClassLoader());
        this.goodsInfo = in.createTypedArrayList(OrderGoodsItem.CREATOR);
    }

    public static final Parcelable.Creator<OrderItem> CREATOR = new Parcelable.Creator<OrderItem>() {
        public OrderItem createFromParcel(Parcel source) {
            return new OrderItem(source);
        }

        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}
