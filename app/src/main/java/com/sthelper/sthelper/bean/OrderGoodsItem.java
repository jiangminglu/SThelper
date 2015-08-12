package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/8/10.
 */
public class OrderGoodsItem implements Parcelable {

    /**
     * month_num : 0
     * shop_id : 2
     * product_id : 64
     * sold_num : 0
     * is_daofu : 0
     * create_ip : 218.17.230.131
     * product_name : 阿武
     * cate_id : 25
     * order_id : 76
     * photo : 2015/07/25/55b313df3122a.jpg
     * total_price : 1
     * shop_name : 吴大总管
     * id : 152
     * is_new : 0
     * num : 1
     * price : 1.00
     * tui_uid : 0
     * goods_id : 64
     * create_time : 1437799394
     * is_tuijian : 0
     * is_hot : 0
     * closed : 0
     */
    public int month_num;
    public int shop_id;
    public int product_id;
    public int sold_num;
    public int is_daofu;
    public String create_ip;
    public String product_name;
    public int cate_id;
    public int order_id;
    public String photo;
    public float total_price;
    public String shop_name;
    public int id;
    public int is_new;
    public int num;
    public float price;
    public int tui_uid;
    public int goods_id;
    public String create_time;
    public String is_tuijian;
    public String is_hot;
    public int closed;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.month_num);
        dest.writeInt(this.shop_id);
        dest.writeInt(this.product_id);
        dest.writeInt(this.sold_num);
        dest.writeInt(this.is_daofu);
        dest.writeString(this.create_ip);
        dest.writeString(this.product_name);
        dest.writeInt(this.cate_id);
        dest.writeInt(this.order_id);
        dest.writeString(this.photo);
        dest.writeFloat(this.total_price);
        dest.writeString(this.shop_name);
        dest.writeInt(this.id);
        dest.writeInt(this.is_new);
        dest.writeInt(this.num);
        dest.writeFloat(this.price);
        dest.writeInt(this.tui_uid);
        dest.writeInt(this.goods_id);
        dest.writeString(this.create_time);
        dest.writeString(this.is_tuijian);
        dest.writeString(this.is_hot);
        dest.writeInt(this.closed);
    }

    public OrderGoodsItem() {
    }

    protected OrderGoodsItem(Parcel in) {
        this.month_num = in.readInt();
        this.shop_id = in.readInt();
        this.product_id = in.readInt();
        this.sold_num = in.readInt();
        this.is_daofu = in.readInt();
        this.create_ip = in.readString();
        this.product_name = in.readString();
        this.cate_id = in.readInt();
        this.order_id = in.readInt();
        this.photo = in.readString();
        this.total_price = in.readFloat();
        this.shop_name = in.readString();
        this.id = in.readInt();
        this.is_new = in.readInt();
        this.num = in.readInt();
        this.price = in.readFloat();
        this.tui_uid = in.readInt();
        this.goods_id = in.readInt();
        this.create_time = in.readString();
        this.is_tuijian = in.readString();
        this.is_hot = in.readString();
        this.closed = in.readInt();
    }

    public static final Parcelable.Creator<OrderGoodsItem> CREATOR = new Parcelable.Creator<OrderGoodsItem>() {
        public OrderGoodsItem createFromParcel(Parcel source) {
            return new OrderGoodsItem(source);
        }

        public OrderGoodsItem[] newArray(int size) {
            return new OrderGoodsItem[size];
        }
    };
}
