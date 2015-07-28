package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 购物车列表item
 * Created by luffy on 15/7/27.
 */
public class CartGoodsItem implements Parcelable {

    /**
     * month_num : 0
     * shop_id : 2
     * product_id : 64
     * record_time : 2015-07-27 22:37:48
     * sold_num : 0
     * create_ip : 218.17.230.131
     * product_name : 阿武
     * cate_id : 25
     * photo : 2015/07/25/55b313df3122a.jpg
     * shop_name : 吴大总管
     * record_id : 63
     * record_ip : null
     * is_new : 0
     * num : 2
     * price : 1.00
     * goods_id : 64
     * create_time : 1437799394
     * is_hot : 0
     * is_tuijian : 0
     * user_id : 6
     * closed : 0
     */
    public int month_num;
    public int shop_id;
    public int product_id;
    public String record_time;
    public int sold_num;
    public String create_ip;
    public String product_name;
    public int cate_id;
    public String photo;
    public String shop_name;
    public int record_id;
    public String record_ip;
    public int is_new;
    public int num;
    public double price;
    public int goods_id;
    public String create_time;
    public int is_hot;
    public int is_tuijian;
    public int user_id;
    public int closed;
    public boolean isSelect;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.month_num);
        dest.writeInt(this.shop_id);
        dest.writeInt(this.product_id);
        dest.writeString(this.record_time);
        dest.writeInt(this.sold_num);
        dest.writeString(this.create_ip);
        dest.writeString(this.product_name);
        dest.writeInt(this.cate_id);
        dest.writeString(this.photo);
        dest.writeString(this.shop_name);
        dest.writeInt(this.record_id);
        dest.writeString(this.record_ip);
        dest.writeInt(this.is_new);
        dest.writeInt(this.num);
        dest.writeDouble(this.price);
        dest.writeInt(this.goods_id);
        dest.writeString(this.create_time);
        dest.writeInt(this.is_hot);
        dest.writeInt(this.is_tuijian);
        dest.writeInt(this.user_id);
        dest.writeInt(this.closed);
        dest.writeByte(isSelect ? (byte) 1 : (byte) 0);
    }

    public CartGoodsItem() {
    }

    protected CartGoodsItem(Parcel in) {
        this.month_num = in.readInt();
        this.shop_id = in.readInt();
        this.product_id = in.readInt();
        this.record_time = in.readString();
        this.sold_num = in.readInt();
        this.create_ip = in.readString();
        this.product_name = in.readString();
        this.cate_id = in.readInt();
        this.photo = in.readString();
        this.shop_name = in.readString();
        this.record_id = in.readInt();
        this.record_ip = in.readString();
        this.is_new = in.readInt();
        this.num = in.readInt();
        this.price = in.readDouble();
        this.goods_id = in.readInt();
        this.create_time = in.readString();
        this.is_hot = in.readInt();
        this.is_tuijian = in.readInt();
        this.user_id = in.readInt();
        this.closed = in.readInt();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CartGoodsItem> CREATOR = new Parcelable.Creator<CartGoodsItem>() {
        public CartGoodsItem createFromParcel(Parcel source) {
            return new CartGoodsItem(source);
        }

        public CartGoodsItem[] newArray(int size) {
            return new CartGoodsItem[size];
        }
    };
}
