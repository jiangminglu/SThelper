package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/7/15.
 */
public class OrderItem implements Parcelable {


    /**
     * id : 105
     * num : 2
     * price : 1
     * shop_id : 5
     * tui_uid : 0
     * is_daofu : 0
     * goods_id : 57
     * create_ip : null
     * create_time : 2015-07-25 19:01:14
     * order_id : 49
     * total_price : 2
     */
    public int id;
    public int num;
    public double price;
    public int shop_id;
    public int tui_uid;
    public int is_daofu;
    public int goods_id;
    public String create_ip;
    public String create_time;
    public int order_id;
    public double total_price;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.num);
        dest.writeDouble(this.price);
        dest.writeInt(this.shop_id);
        dest.writeInt(this.tui_uid);
        dest.writeInt(this.is_daofu);
        dest.writeInt(this.goods_id);
        dest.writeString(this.create_ip);
        dest.writeString(this.create_time);
        dest.writeInt(this.order_id);
        dest.writeDouble(this.total_price);
    }

    public OrderItem() {
    }

    protected OrderItem(Parcel in) {
        this.id = in.readInt();
        this.num = in.readInt();
        this.price = in.readDouble();
        this.shop_id = in.readInt();
        this.tui_uid = in.readInt();
        this.is_daofu = in.readInt();
        this.goods_id = in.readInt();
        this.create_ip = in.readString();
        this.create_time = in.readString();
        this.order_id = in.readInt();
        this.total_price = in.readDouble();
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
