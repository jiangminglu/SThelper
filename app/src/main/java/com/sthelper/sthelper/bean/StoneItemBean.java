package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/6/17.
 */
public class StoneItemBean implements Parcelable {

    /**
     * createtime : 2015-07-29 13:27:04
     * adress : 今生今世计算机家
     * price : 576766.00
     * product_id : 66
     * tel : 646464646
     * origin : 实话实说
     * user_id : 3
     * my_stone_name : u u 啊叫啊姐姐
     * tips : 继续进行舰载机
     * stone_id : 38
     * shop_name : 记者计算机数据
     * stone_photo :
     */
    public String createtime;
    public String adress;
    public double price;
    public int product_id;
    public String tel;
    public String origin;
    public int user_id;
    public String my_stone_name;
    public String tips;
    public int stone_id;
    public String shop_name;
    public String stone_photo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createtime);
        dest.writeString(this.adress);
        dest.writeDouble(this.price);
        dest.writeInt(this.product_id);
        dest.writeString(this.tel);
        dest.writeString(this.origin);
        dest.writeInt(this.user_id);
        dest.writeString(this.my_stone_name);
        dest.writeString(this.tips);
        dest.writeInt(this.stone_id);
        dest.writeString(this.shop_name);
        dest.writeString(this.stone_photo);
    }

    public StoneItemBean() {
    }

    protected StoneItemBean(Parcel in) {
        this.createtime = in.readString();
        this.adress = in.readString();
        this.price = in.readDouble();
        this.product_id = in.readInt();
        this.tel = in.readString();
        this.origin = in.readString();
        this.user_id = in.readInt();
        this.my_stone_name = in.readString();
        this.tips = in.readString();
        this.stone_id = in.readInt();
        this.shop_name = in.readString();
        this.stone_photo = in.readString();
    }

    public static final Parcelable.Creator<StoneItemBean> CREATOR = new Parcelable.Creator<StoneItemBean>() {
        public StoneItemBean createFromParcel(Parcel source) {
            return new StoneItemBean(source);
        }

        public StoneItemBean[] newArray(int size) {
            return new StoneItemBean[size];
        }
    };
}
