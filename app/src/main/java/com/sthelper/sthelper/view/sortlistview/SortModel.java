package com.sthelper.sthelper.view.sortlistview;

import android.os.Parcel;
import android.os.Parcelable;

public class SortModel implements Parcelable {

    /**
     * cate_id : 2
     * stone_id : 2
     * stone_name
     */
    public int cate_id;
    public int stone_id;
    public String stone_name;
    public String sortLetters;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cate_id);
        dest.writeInt(this.stone_id);
        dest.writeString(this.stone_name);
        dest.writeString(this.sortLetters);
    }

    public SortModel() {
    }

    protected SortModel(Parcel in) {
        this.cate_id = in.readInt();
        this.stone_id = in.readInt();
        this.stone_name = in.readString();
        this.sortLetters = in.readString();
    }

    public static final Parcelable.Creator<SortModel> CREATOR = new Parcelable.Creator<SortModel>() {
        public SortModel createFromParcel(Parcel source) {
            return new SortModel(source);
        }

        public SortModel[] newArray(int size) {
            return new SortModel[size];
        }
    };
}
