package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品详情
 * Created by luffy on 15/7/23.
 */
public class GoodsInfo implements Parcelable {

    /**
     * area_id : 1
     * shop_id : 1
     * instructions :  啊啊啊啊</p>
     * sold_num : 0
     * mall_price : 100
     * create_ip : 183.92.240.14
     * audit : 1
     * cate_id : 3
     * shopcate_id : 1
     * is_mall : 1
     * photo : 2015/07/01/thumb_5593b39e34fa9.jpg
     * share : 0
     * end_date : 2015-07-29
     * title : 商品1
     * price : 100
     * details :  啊啊啊</p>
     * views : 0
     * goods_id : 2
     * create_time : 1435743149
     * business_id : 3
     * orderby : 4
     * closed : 1
     * commission : 0
     * cate_name : 鲜泡健康茶饮
     */
    public String product_name;
    public double price;
    public int product_id;
    public int area_id;
    public int shop_id;
    public int cate_id;
    public int business_id;
    public int shopcate_id;
    public String instructions;
    public String photo;
    public boolean isSelect;
    public int num;
    public int sold_num;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.product_name);
        dest.writeDouble(this.price);
        dest.writeInt(this.product_id);
        dest.writeInt(this.area_id);
        dest.writeInt(this.shop_id);
        dest.writeInt(this.cate_id);
        dest.writeInt(this.business_id);
        dest.writeInt(this.shopcate_id);
        dest.writeString(this.instructions);
        dest.writeString(this.photo);
        dest.writeByte(isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.num);
        dest.writeInt(this.sold_num);
    }

    public GoodsInfo() {
    }

    protected GoodsInfo(Parcel in) {
        this.product_name = in.readString();
        this.price = in.readDouble();
        this.product_id = in.readInt();
        this.area_id = in.readInt();
        this.shop_id = in.readInt();
        this.cate_id = in.readInt();
        this.business_id = in.readInt();
        this.shopcate_id = in.readInt();
        this.instructions = in.readString();
        this.photo = in.readString();
        this.isSelect = in.readByte() != 0;
        this.num = in.readInt();
        this.sold_num = in.readInt();
    }

    public static final Creator<GoodsInfo> CREATOR = new Creator<GoodsInfo>() {
        public GoodsInfo createFromParcel(Parcel source) {
            return new GoodsInfo(source);
        }

        public GoodsInfo[] newArray(int size) {
            return new GoodsInfo[size];
        }
    };
}
