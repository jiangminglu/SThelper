package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by luffy on 15/7/6.
 */
public class Address implements Parcelable {
    public int area_id;
    public String name;
    public int business_id;
    public int is_default;
    public int closed;
    public String addr;
    public int user_id;
    public int addr_id;
    public String mobile;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.area_id);
        dest.writeString(this.name);
        dest.writeInt(this.business_id);
        dest.writeInt(this.is_default);
        dest.writeInt(this.closed);
        dest.writeString(this.addr);
        dest.writeInt(this.user_id);
        dest.writeInt(this.addr_id);
        dest.writeString(this.mobile);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.area_id = in.readInt();
        this.name = in.readString();
        this.business_id = in.readInt();
        this.is_default = in.readInt();
        this.closed = in.readInt();
        this.addr = in.readString();
        this.user_id = in.readInt();
        this.addr_id = in.readInt();
        this.mobile = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
