package com.sthelper.sthelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luffy on 15/8/18.
 */
public class Comment implements Parcelable {


    /**
     * content : 233
     * create_time : 2015-08-10 17:25:49
     * score : 5
     * account : 13308663578
     */
    public String content;
    public String create_time;
    public float score;
    public String account;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.create_time);
        dest.writeFloat(this.score);
        dest.writeString(this.account);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.content = in.readString();
        this.create_time = in.readString();
        this.score = in.readFloat();
        this.account = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
