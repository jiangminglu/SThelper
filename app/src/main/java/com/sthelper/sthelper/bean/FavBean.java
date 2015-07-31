package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/7/15.
 */
public class FavBean implements Parcelable {


    /**
     * month_num : 0
     * product_id : 64
     * shop_id : 2
     * sold_num : 0
     * create_ip : 218.17.230.131
     * product_name : 阿武
     * cate_id : 25
     * photo : 2015/07/25/55b313df3122a.jpg
     * favor_id : 4
     * is_new : 0
     * price : 1.00
     * goods_id : 64
     * create_time : 1437799394
     * is_hot : 0
     * is_tuijian : 0
     * closed : 0
     * user_id : 6
     */
    public String month_num;
    public int product_id;
    public int shop_id;
    public int sold_num;
    public String create_ip;
    public String product_name;
    public int cate_id;
    public String photo;
    public int favor_id;
    public int is_new;
    public float price;
    public int goods_id;
    public String create_time;
    public int is_hot;
    public int is_tuijian;
    public int closed;
    public int user_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.month_num);
        dest.writeInt(this.product_id);
        dest.writeInt(this.shop_id);
        dest.writeInt(this.sold_num);
        dest.writeString(this.create_ip);
        dest.writeString(this.product_name);
        dest.writeInt(this.cate_id);
        dest.writeString(this.photo);
        dest.writeInt(this.favor_id);
        dest.writeInt(this.is_new);
        dest.writeFloat(this.price);
        dest.writeInt(this.goods_id);
        dest.writeString(this.create_time);
        dest.writeInt(this.is_hot);
        dest.writeInt(this.is_tuijian);
        dest.writeInt(this.closed);
        dest.writeInt(this.user_id);
    }

    public FavBean() {
    }

    protected FavBean(Parcel in) {
        this.month_num = in.readString();
        this.product_id = in.readInt();
        this.shop_id = in.readInt();
        this.sold_num = in.readInt();
        this.create_ip = in.readString();
        this.product_name = in.readString();
        this.cate_id = in.readInt();
        this.photo = in.readString();
        this.favor_id = in.readInt();
        this.is_new = in.readInt();
        this.price = in.readFloat();
        this.goods_id = in.readInt();
        this.create_time = in.readString();
        this.is_hot = in.readInt();
        this.is_tuijian = in.readInt();
        this.closed = in.readInt();
        this.user_id = in.readInt();
    }

    public static final Parcelable.Creator<FavBean> CREATOR = new Parcelable.Creator<FavBean>() {
        public FavBean createFromParcel(Parcel source) {
            return new FavBean(source);
        }

        public FavBean[] newArray(int size) {
            return new FavBean[size];
        }
    };
}
