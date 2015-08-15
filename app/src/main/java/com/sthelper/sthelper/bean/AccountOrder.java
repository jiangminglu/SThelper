package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/8/15.
 */
public class AccountOrder implements Parcelable {


    /**
     * shop_id : 7
     * create_time : 2015-08-15 12:58:12
     * order_id : 107
     * photo : 2015/08/15/55ced23888def.jpg
     * total_price : 50.00
     * order_code : 1439614692107
     * shop_name : 翠味拉面
     */
    public int shop_id;
    public String create_time;
    public String order_id;
    public String photo;
    public String total_price;
    public String order_code;
    public String shop_name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.shop_id);
        dest.writeString(this.create_time);
        dest.writeString(this.order_id);
        dest.writeString(this.photo);
        dest.writeString(this.total_price);
        dest.writeString(this.order_code);
        dest.writeString(this.shop_name);
    }

    public AccountOrder() {
    }

    protected AccountOrder(Parcel in) {
        this.shop_id = in.readInt();
        this.create_time = in.readString();
        this.order_id = in.readString();
        this.photo = in.readString();
        this.total_price = in.readString();
        this.order_code = in.readString();
        this.shop_name = in.readString();
    }

    public static final Parcelable.Creator<AccountOrder> CREATOR = new Parcelable.Creator<AccountOrder>() {
        public AccountOrder createFromParcel(Parcel source) {
            return new AccountOrder(source);
        }

        public AccountOrder[] newArray(int size) {
            return new AccountOrder[size];
        }
    };
}
