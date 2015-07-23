package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 店铺详情
 * Created by luffy on 15/7/23.
 */
public class ShopInfo implements Parcelable {

    /**
     * area_id : 1
     * tel : 0595-86362636
     * score : 0
     * addr : 水头镇时代广场钟楼12棟29店（駿锋幼儿园旁）处
     * lng :
     * is_mall : 1
     * contact : 杨小贤
     * d1 : 0
     * d2 : 0
     * d3 : 0
     * business_id : 3
     * closed : 0
     * user_id : 1
     * score_num : 0
     * lat :
     * tags :
     * logo : 2015/07/06/thumb_559a00763d6d2.png
     * shop_id : 1
     * yuyue_total : 0
     * create_ip : 120.42.228.73
     * audit : 1
     * cate_id : 71
     * bao_date : 2015-07-08
     * photo : 2015/07/08/thumb_559ce0ad8d1a6.jpg
     * wei_date : 0000-00-00
     * shop_name : 杨小贤
     * extension :
     * card_date : 0000-00-00
     * yuyue_date : 0000-00-00
     * create_time : 1435635487
     * orderby : 1
     * view : 302
     * ranking : 0
     */
    public int area_id;
    public String tel;
    public String score;
    public String addr;
    public String lng;
    public String is_mall;
    public String contact;
    public String d1;
    public String d2;
    public String d3;
    public String business_id;
    public String closed;
    public int user_id;
    public String score_num;
    public String lat;
    public String tags;
    public String logo;
    public int shop_id;
    public String yuyue_total;
    public String create_ip;
    public String audit;
    public int cate_id;
    public String bao_date;
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
        dest.writeString(this.score);
        dest.writeString(this.addr);
        dest.writeString(this.lng);
        dest.writeString(this.is_mall);
        dest.writeString(this.contact);
        dest.writeString(this.d1);
        dest.writeString(this.d2);
        dest.writeString(this.d3);
        dest.writeString(this.business_id);
        dest.writeString(this.closed);
        dest.writeInt(this.user_id);
        dest.writeString(this.score_num);
        dest.writeString(this.lat);
        dest.writeString(this.tags);
        dest.writeString(this.logo);
        dest.writeInt(this.shop_id);
        dest.writeString(this.yuyue_total);
        dest.writeString(this.create_ip);
        dest.writeString(this.audit);
        dest.writeInt(this.cate_id);
        dest.writeString(this.bao_date);
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

    public ShopInfo() {
    }

    protected ShopInfo(Parcel in) {
        this.area_id = in.readInt();
        this.tel = in.readString();
        this.score = in.readString();
        this.addr = in.readString();
        this.lng = in.readString();
        this.is_mall = in.readString();
        this.contact = in.readString();
        this.d1 = in.readString();
        this.d2 = in.readString();
        this.d3 = in.readString();
        this.business_id = in.readString();
        this.closed = in.readString();
        this.user_id = in.readInt();
        this.score_num = in.readString();
        this.lat = in.readString();
        this.tags = in.readString();
        this.logo = in.readString();
        this.shop_id = in.readInt();
        this.yuyue_total = in.readString();
        this.create_ip = in.readString();
        this.audit = in.readString();
        this.cate_id = in.readInt();
        this.bao_date = in.readString();
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

    public static final Parcelable.Creator<ShopInfo> CREATOR = new Parcelable.Creator<ShopInfo>() {
        public ShopInfo createFromParcel(Parcel source) {
            return new ShopInfo(source);
        }

        public ShopInfo[] newArray(int size) {
            return new ShopInfo[size];
        }
    };
}
