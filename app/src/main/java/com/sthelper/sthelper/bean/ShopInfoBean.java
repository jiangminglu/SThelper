package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/8/2.
 */
public class ShopInfoBean implements Parcelable {

    /**
     * business_time : 9:00-18:00
     * send_time : 100
     * Freight : 0
     * addr : 美丽
     * photo : 2015/07/16/55a76b42a98bf.jpg
     * shop_name : 吴大总管
     */
    public String business_time;
    public String send_time;
    public String freight;
    public String addr;
    public String photo;
    public float score;
    public String shop_name;
    public String tel;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.business_time);
        dest.writeString(this.send_time);
        dest.writeString(this.freight);
        dest.writeString(this.addr);
        dest.writeFloat(this.score);
        dest.writeString(this.photo);
        dest.writeString(this.shop_name);
        dest.writeString(this.tel);
    }

    public ShopInfoBean() {
    }

    protected ShopInfoBean(Parcel in) {
        this.business_time = in.readString();
        this.send_time = in.readString();
        this.freight = in.readString();
        this.addr = in.readString();
        this.photo = in.readString();
        this.score = in.readFloat();
        this.shop_name = in.readString();
        this.tel = in.readString();
    }

    public static final Creator<ShopInfoBean> CREATOR = new Creator<ShopInfoBean>() {
        public ShopInfoBean createFromParcel(Parcel source) {
            return new ShopInfoBean(source);
        }

        public ShopInfoBean[] newArray(int size) {
            return new ShopInfoBean[size];
        }
    };
}
