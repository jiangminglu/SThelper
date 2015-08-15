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
     * record_time : 2015-08-14 15:15:35
     * product_id : 138
     * shop_id : 1
     * sold_num : 0
     * create_ip : 218.17.230.134
     * product_name : 大
     * cate_id : 42
     * photo : 2015/08/13/55cbfb01d864f.png
     * shop_name : 杨小贤
     * record_id : 90
     * record_ip : null
     * num : 2
     * is_new : 0
     * price : 1.00
     * goods_id : 138
     * freight : 0
     * create_time : 1439431430
     * is_tuijian : 0
     * is_hot : 0
     * closed : 0
     * user_id : 6
     */
    public int month_num;
    public String record_time;
    public int product_id;
    public int shop_id;
    public int sold_num;
    public String create_ip;
    public String product_name;
    public int cate_id;
    public String photo;
    public String shop_name;
    public int record_id;
    public String record_ip;
    public int num;
    public int is_new;
    public float price;
    public int goods_id;
    public float freight;
    public String create_time;
    public int is_tuijian;
    public int is_hot;
    public int closed;
    public int user_id;
    public boolean isSelect;
    public String intro;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.month_num);
        dest.writeString(this.record_time);
        dest.writeInt(this.product_id);
        dest.writeInt(this.shop_id);
        dest.writeInt(this.sold_num);
        dest.writeString(this.create_ip);
        dest.writeString(this.product_name);
        dest.writeInt(this.cate_id);
        dest.writeString(this.photo);
        dest.writeString(this.shop_name);
        dest.writeInt(this.record_id);
        dest.writeString(this.record_ip);
        dest.writeInt(this.num);
        dest.writeInt(this.is_new);
        dest.writeFloat(this.price);
        dest.writeInt(this.goods_id);
        dest.writeFloat(this.freight);
        dest.writeString(this.create_time);
        dest.writeInt(this.is_tuijian);
        dest.writeInt(this.is_hot);
        dest.writeInt(this.closed);
        dest.writeInt(this.user_id);
        dest.writeByte(isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.intro);
    }

    public CartGoodsItem() {
    }

    protected CartGoodsItem(Parcel in) {
        this.month_num = in.readInt();
        this.record_time = in.readString();
        this.product_id = in.readInt();
        this.shop_id = in.readInt();
        this.sold_num = in.readInt();
        this.create_ip = in.readString();
        this.product_name = in.readString();
        this.cate_id = in.readInt();
        this.photo = in.readString();
        this.shop_name = in.readString();
        this.record_id = in.readInt();
        this.record_ip = in.readString();
        this.num = in.readInt();
        this.is_new = in.readInt();
        this.price = in.readFloat();
        this.goods_id = in.readInt();
        this.freight = in.readFloat();
        this.create_time = in.readString();
        this.is_tuijian = in.readInt();
        this.is_hot = in.readInt();
        this.closed = in.readInt();
        this.user_id = in.readInt();
        this.isSelect = in.readByte() != 0;
        this.intro = in.readString();
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
