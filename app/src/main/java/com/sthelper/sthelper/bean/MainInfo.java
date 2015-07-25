package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/7/25.
 */
public class MainInfo implements Parcelable {

    /**
     * area_id : 1
     * tel : 12345678932
     * score : 0
     * addr : 水头
     * lng :
     * order_id : 49
     * is_mall : 1
     * contact : XXX
     * d1 : 0
     * d2 : 0
     * d3 : 0
     * business_id : 3
     * closed : 0
     * user_id : 2
     * score_num : 0
     * lat :
     * tags :
     * logo : 2015/07/25/55b26accdf19c.jpg
     * shop_id : 5
     * status : 0
     * yuyue_total : 0
     * is_daofu : null
     * create_ip : 120.33.128.208
     * audit : 1
     * cate_id : 53
     * bao_date : 0000-00-00
     * addr_id : 22
     * total_price : 2.00
     * photo : 2015/07/25/55b26ab0553cb.jpg
     * wei_date : 0000-00-00
     * shop_name : 小庄大排档
     * extension :
     * card_date : 0000-00-00
     * yuyue_date : 0000-00-00
     * create_time : 1437621735
     * orderby : 7
     * view : 2
     * ranking : 0
     */
    public int area_id;
    public String tel;
    public int score;
    public String addr;
    public String lng;
    public int order_id;
    public String is_mall;
    public String contact;
    public String d1;
    public String d2;
    public String d3;
    public int business_id;
    public int closed;
    public int user_id;
    public int score_num;
    public String lat;
    public String tags;
    public String logo;
    public int shop_id;
    public int status;
    public String yuyue_total;
    public String is_daofu;
    public String create_ip;
    public String audit;
    public int cate_id;
    public String bao_date;
    public int addr_id;
    public double total_price;
    public String photo;
    public String wei_date;
    public String shop_name;
    public String extension;
    public String card_date;
    public String yuyue_date;
    public String create_time;
    public String orderby;
    public String view;
    public String ranking;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.area_id);
        dest.writeString(this.tel);
        dest.writeInt(this.score);
        dest.writeString(this.addr);
        dest.writeString(this.lng);
        dest.writeInt(this.order_id);
        dest.writeString(this.is_mall);
        dest.writeString(this.contact);
        dest.writeString(this.d1);
        dest.writeString(this.d2);
        dest.writeString(this.d3);
        dest.writeInt(this.business_id);
        dest.writeInt(this.closed);
        dest.writeInt(this.user_id);
        dest.writeInt(this.score_num);
        dest.writeString(this.lat);
        dest.writeString(this.tags);
        dest.writeString(this.logo);
        dest.writeInt(this.shop_id);
        dest.writeInt(this.status);
        dest.writeString(this.yuyue_total);
        dest.writeString(this.is_daofu);
        dest.writeString(this.create_ip);
        dest.writeString(this.audit);
        dest.writeInt(this.cate_id);
        dest.writeString(this.bao_date);
        dest.writeInt(this.addr_id);
        dest.writeDouble(this.total_price);
        dest.writeString(this.photo);
        dest.writeString(this.wei_date);
        dest.writeString(this.shop_name);
        dest.writeString(this.extension);
        dest.writeString(this.card_date);
        dest.writeString(this.yuyue_date);
        dest.writeString(this.create_time);
        dest.writeString(this.orderby);
        dest.writeString(this.view);
        dest.writeString(this.ranking);
    }

    public MainInfo() {
    }

    protected MainInfo(Parcel in) {
        this.area_id = in.readInt();
        this.tel = in.readString();
        this.score = in.readInt();
        this.addr = in.readString();
        this.lng = in.readString();
        this.order_id = in.readInt();
        this.is_mall = in.readString();
        this.contact = in.readString();
        this.d1 = in.readString();
        this.d2 = in.readString();
        this.d3 = in.readString();
        this.business_id = in.readInt();
        this.closed = in.readInt();
        this.user_id = in.readInt();
        this.score_num = in.readInt();
        this.lat = in.readString();
        this.tags = in.readString();
        this.logo = in.readString();
        this.shop_id = in.readInt();
        this.status = in.readInt();
        this.yuyue_total = in.readString();
        this.is_daofu = in.readString();
        this.create_ip = in.readString();
        this.audit = in.readString();
        this.cate_id = in.readInt();
        this.bao_date = in.readString();
        this.addr_id = in.readInt();
        this.total_price = in.readDouble();
        this.photo = in.readString();
        this.wei_date = in.readString();
        this.shop_name = in.readString();
        this.extension = in.readString();
        this.card_date = in.readString();
        this.yuyue_date = in.readString();
        this.create_time = in.readString();
        this.orderby = in.readString();
        this.view = in.readString();
        this.ranking = in.readString();
    }

    public static final Parcelable.Creator<MainInfo> CREATOR = new Parcelable.Creator<MainInfo>() {
        public MainInfo createFromParcel(Parcel source) {
            return new MainInfo(source);
        }

        public MainInfo[] newArray(int size) {
            return new MainInfo[size];
        }
    };
}
