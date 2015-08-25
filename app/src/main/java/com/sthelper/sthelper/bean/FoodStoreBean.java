package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/6/13.
 */
public class FoodStoreBean implements Parcelable {


    /**
     * tel : 0755-8888888
     * score : 0
     * addr : 呼呼呼
     * d1 : 0
     * seo_description : null
     * d2 : 0
     * d3 : 0
     * card_photo : null
     * seo_keywords : null
     * bank :
     * shop_id : 3
     * yuyue_total : 0
     * cate_id : 1
     * seo_title : null
     * wei_date : 2015-08-31
     * weixin_msg : null
     * theme_id : 0
     * yuyue_date : 0000-00-00
     * business_time :
     * price : 0
     * near :
     * create_time : 1437035520
     * orderby : 100
     * app_id : null
     * view : 19
     * ranking : 0
     * menus : null
     * area_id : 1
     * sitelogo : null
     * theme_expir_time : null
     * icp : null
     * lng :
     * is_mall : 1
     * contact :
     * details : null
     * wei_pic : null
     * token : null
     * discounts : null
     * business_id : 3
     * user_id : 5
     * closed : 1
     * score_num : 0
     * lat :
     * tags :
     * logo : 2015/07/16/55a76bed1917c.jpg
     * create_ip : 218.17.231.174
     * audit : 1
     * bao_date : 2015-09-30
     * is_dingyue : 0
     * app_key : null
     * photo : 2015/07/16/55a76bf182938.jpg
     * shop_name : 饺子
     * extension :
     * card_date : 0000-00-00
     */
    public String tel;
    public int score;
    public String addr;
    public String d1;
    public String seo_description;
    public String d2;
    public String d3;
    public String card_photo;
    public String seo_keywords;
    public String bank;
    public int shop_id;
    public String yuyue_total;
    public String cate_id;
    public String seo_title;
    public String wei_date;
    public String weixin_msg;
    public String theme_id;
    public String yuyue_date;
    public String business_time;
    public String price;
    public String near;
    public String create_time;
    public String orderby;
    public String app_id;
    public String view;
    public String ranking;
    public String menus;
    public String area_id;
    public String sitelogo;
    public String theme_expir_time;
    public String icp;
    public String lng;
    public String is_mall;
    public String contact;
    public String details;
    public String wei_pic;
    public String token;
    public String discounts;
    public String business_id;
    public String user_id;
    public int closed;
    public String score_num;
    public String lat;
    public String tags;
    public String logo;
    public String create_ip;
    public String audit;
    public String bao_date;
    public String is_dingyue;
    public String app_key;
    public String photo;
    public String shop_name;
    public String extension;
    public String card_date;
    public String intro;
    public String distribution;
    public String since_money;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tel);
        dest.writeInt(this.score);
        dest.writeString(this.addr);
        dest.writeString(this.d1);
        dest.writeString(this.seo_description);
        dest.writeString(this.d2);
        dest.writeString(this.d3);
        dest.writeString(this.card_photo);
        dest.writeString(this.seo_keywords);
        dest.writeString(this.bank);
        dest.writeInt(this.shop_id);
        dest.writeString(this.yuyue_total);
        dest.writeString(this.cate_id);
        dest.writeString(this.seo_title);
        dest.writeString(this.wei_date);
        dest.writeString(this.weixin_msg);
        dest.writeString(this.theme_id);
        dest.writeString(this.yuyue_date);
        dest.writeString(this.business_time);
        dest.writeString(this.price);
        dest.writeString(this.near);
        dest.writeString(this.create_time);
        dest.writeString(this.orderby);
        dest.writeString(this.app_id);
        dest.writeString(this.view);
        dest.writeString(this.ranking);
        dest.writeString(this.menus);
        dest.writeString(this.area_id);
        dest.writeString(this.sitelogo);
        dest.writeString(this.theme_expir_time);
        dest.writeString(this.icp);
        dest.writeString(this.lng);
        dest.writeString(this.is_mall);
        dest.writeString(this.contact);
        dest.writeString(this.details);
        dest.writeString(this.wei_pic);
        dest.writeString(this.token);
        dest.writeString(this.discounts);
        dest.writeString(this.business_id);
        dest.writeString(this.user_id);
        dest.writeInt(this.closed);
        dest.writeString(this.score_num);
        dest.writeString(this.lat);
        dest.writeString(this.tags);
        dest.writeString(this.logo);
        dest.writeString(this.create_ip);
        dest.writeString(this.audit);
        dest.writeString(this.bao_date);
        dest.writeString(this.is_dingyue);
        dest.writeString(this.app_key);
        dest.writeString(this.photo);
        dest.writeString(this.shop_name);
        dest.writeString(this.extension);
        dest.writeString(this.card_date);
        dest.writeString(this.intro);
        dest.writeString(this.distribution);
        dest.writeString(this.since_money);
    }

    public FoodStoreBean() {
    }

    protected FoodStoreBean(Parcel in) {
        this.tel = in.readString();
        this.score = in.readInt();
        this.addr = in.readString();
        this.d1 = in.readString();
        this.seo_description = in.readString();
        this.d2 = in.readString();
        this.d3 = in.readString();
        this.card_photo = in.readString();
        this.seo_keywords = in.readString();
        this.bank = in.readString();
        this.shop_id = in.readInt();
        this.yuyue_total = in.readString();
        this.cate_id = in.readString();
        this.seo_title = in.readString();
        this.wei_date = in.readString();
        this.weixin_msg = in.readString();
        this.theme_id = in.readString();
        this.yuyue_date = in.readString();
        this.business_time = in.readString();
        this.price = in.readString();
        this.near = in.readString();
        this.create_time = in.readString();
        this.orderby = in.readString();
        this.app_id = in.readString();
        this.view = in.readString();
        this.ranking = in.readString();
        this.menus = in.readString();
        this.area_id = in.readString();
        this.sitelogo = in.readString();
        this.theme_expir_time = in.readString();
        this.icp = in.readString();
        this.lng = in.readString();
        this.is_mall = in.readString();
        this.contact = in.readString();
        this.details = in.readString();
        this.wei_pic = in.readString();
        this.token = in.readString();
        this.discounts = in.readString();
        this.business_id = in.readString();
        this.user_id = in.readString();
        this.closed = in.readInt();
        this.score_num = in.readString();
        this.lat = in.readString();
        this.tags = in.readString();
        this.logo = in.readString();
        this.create_ip = in.readString();
        this.audit = in.readString();
        this.bao_date = in.readString();
        this.is_dingyue = in.readString();
        this.app_key = in.readString();
        this.photo = in.readString();
        this.shop_name = in.readString();
        this.extension = in.readString();
        this.card_date = in.readString();
        this.intro = in.readString();
        this.distribution = in.readString();
        this.since_money = in.readString();
    }

    public static final Creator<FoodStoreBean> CREATOR = new Creator<FoodStoreBean>() {
        public FoodStoreBean createFromParcel(Parcel source) {
            return new FoodStoreBean(source);
        }

        public FoodStoreBean[] newArray(int size) {
            return new FoodStoreBean[size];
        }
    };
}
