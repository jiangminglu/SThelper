package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/8/10.
 */
public class OrderMainInfo implements Parcelable {


    /**
     * area_id : 1
     * tel : 0755-1666666
     * score : 3
     * lng :
     * addr : hha
     * is_mall : 1
     * order_id : 76
     * contact : 撞但
     * order_code : 143835033476
     * d1 : 0
     * d2 : 0
     * d3 : 0
     * name : jsm
     * business_id : 0
     * closed : 0
     * user_id : 6
     * score_num : 0
     * lat :
     * tags :
     * logo : 2015/07/16/55a76b3b599c9.jpg
     * transport_fee : 0
     * shop_id : 2
     * status : 1
     * yuyue_total : 0
     * is_daofu : null
     * create_ip : 218.17.231.174
     * is_default : 0
     * audit : 1
     * cate_id : 53
     * bao_date : 0000-00-00
     * addr_id : 27
     * total_price : 1.00
     * photo : 2015/07/16/55a76b42a98bf.jpg
     * wei_date : 0000-00-00
     * shop_name : 吴大总管
     * extension :
     * yuyue_date : 0000-00-00
     * card_date : 0000-00-00
     * create_time : 1437035404
     * orderby : 3
     * view : 18
     * ranking : 0
     * mobile : 123456789
     */
    public int area_id;
    public String tel;
    public float score;
    public String lng;
    public String addr;
    public int is_mall;
    public int order_id;
    public String contact;
    public String order_code;
    public String d1;
    public String d2;
    public String d3;
    public String name;
    public String business_id;
    public int closed;
    public int user_id;
    public int score_num;
    public String lat;
    public String tags;
    public String logo;
    public float transport_fee;
    public int shop_id;
    public int status;
    public String yuyue_total;
    public int is_daofu;
    public String create_ip;
    public int is_default;
    public String audit;
    public int cate_id;
    public String bao_date;
    public int addr_id;
    public float total_price;
    public String photo;
    public String wei_date;
    public String shop_name;
    public String extension;
    public String yuyue_date;
    public String card_date;
    public long create_time;
    public String orderby;
    public String view;
    public String ranking;
    public String mobile;
    public String tips;
    public int is_comment;//0 和 1分别是未评价和已评价

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.area_id);
        dest.writeString(this.tel);
        dest.writeFloat(this.score);
        dest.writeString(this.lng);
        dest.writeString(this.addr);
        dest.writeInt(this.is_mall);
        dest.writeInt(this.order_id);
        dest.writeString(this.contact);
        dest.writeString(this.order_code);
        dest.writeString(this.d1);
        dest.writeString(this.d2);
        dest.writeString(this.d3);
        dest.writeString(this.name);
        dest.writeString(this.business_id);
        dest.writeInt(this.closed);
        dest.writeInt(this.user_id);
        dest.writeInt(this.score_num);
        dest.writeString(this.lat);
        dest.writeString(this.tags);
        dest.writeString(this.logo);
        dest.writeFloat(this.transport_fee);
        dest.writeInt(this.shop_id);
        dest.writeInt(this.status);
        dest.writeString(this.yuyue_total);
        dest.writeInt(this.is_daofu);
        dest.writeString(this.create_ip);
        dest.writeInt(this.is_default);
        dest.writeString(this.audit);
        dest.writeInt(this.cate_id);
        dest.writeString(this.bao_date);
        dest.writeInt(this.addr_id);
        dest.writeFloat(this.total_price);
        dest.writeString(this.photo);
        dest.writeString(this.wei_date);
        dest.writeString(this.shop_name);
        dest.writeString(this.extension);
        dest.writeString(this.yuyue_date);
        dest.writeString(this.card_date);
        dest.writeLong(this.create_time);
        dest.writeString(this.orderby);
        dest.writeString(this.view);
        dest.writeString(this.ranking);
        dest.writeString(this.mobile);
        dest.writeString(this.tips);
        dest.writeInt(this.is_comment);
    }

    public OrderMainInfo() {
    }

    protected OrderMainInfo(Parcel in) {
        this.area_id = in.readInt();
        this.tel = in.readString();
        this.score = in.readFloat();
        this.lng = in.readString();
        this.addr = in.readString();
        this.is_mall = in.readInt();
        this.order_id = in.readInt();
        this.contact = in.readString();
        this.order_code = in.readString();
        this.d1 = in.readString();
        this.d2 = in.readString();
        this.d3 = in.readString();
        this.name = in.readString();
        this.business_id = in.readString();
        this.closed = in.readInt();
        this.user_id = in.readInt();
        this.score_num = in.readInt();
        this.lat = in.readString();
        this.tags = in.readString();
        this.logo = in.readString();
        this.transport_fee = in.readFloat();
        this.shop_id = in.readInt();
        this.status = in.readInt();
        this.yuyue_total = in.readString();
        this.is_daofu = in.readInt();
        this.create_ip = in.readString();
        this.is_default = in.readInt();
        this.audit = in.readString();
        this.cate_id = in.readInt();
        this.bao_date = in.readString();
        this.addr_id = in.readInt();
        this.total_price = in.readFloat();
        this.photo = in.readString();
        this.wei_date = in.readString();
        this.shop_name = in.readString();
        this.extension = in.readString();
        this.yuyue_date = in.readString();
        this.card_date = in.readString();
        this.create_time = in.readLong();
        this.orderby = in.readString();
        this.view = in.readString();
        this.ranking = in.readString();
        this.mobile = in.readString();
        this.tips = in.readString();
        this.is_comment = in.readInt();
    }

    public static final Creator<OrderMainInfo> CREATOR = new Creator<OrderMainInfo>() {
        public OrderMainInfo createFromParcel(Parcel source) {
            return new OrderMainInfo(source);
        }

        public OrderMainInfo[] newArray(int size) {
            return new OrderMainInfo[size];
        }
    };
}
