package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/8/4.
 */
public class MyFavShopBean implements Parcelable {

    /**
     * logo : 2015/07/06/thumb_559a00763d6d2.png
     * shop_id : 1
     * score : 0
     * shop_name : 杨小贤
     */
    public String logo;
    public int shop_id;
    public float score;
    public String shop_name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.logo);
        dest.writeInt(this.shop_id);
        dest.writeFloat(this.score);
        dest.writeString(this.shop_name);
    }

    public MyFavShopBean() {
    }

    protected MyFavShopBean(Parcel in) {
        this.logo = in.readString();
        this.shop_id = in.readInt();
        this.score = in.readFloat();
        this.shop_name = in.readString();
    }

    public static final Parcelable.Creator<MyFavShopBean> CREATOR = new Parcelable.Creator<MyFavShopBean>() {
        public MyFavShopBean createFromParcel(Parcel source) {
            return new MyFavShopBean(source);
        }

        public MyFavShopBean[] newArray(int size) {
            return new MyFavShopBean[size];
        }
    };
}
