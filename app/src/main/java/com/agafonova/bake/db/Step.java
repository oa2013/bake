package com.agafonova.bake.db;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Olga Agafonova on 7/21/18.
 */

public class Step implements Parcelable {

    private String mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public Step() {

    }

    public Step(String iId, String iShortDescription, String iDescription, String iVideoUrl, String iThumbnailUrl) {
        this.mId = iId;
        this.mShortDescription = iShortDescription;
        this.mDescription = iDescription;
        this.mVideoUrl = iVideoUrl;
        this.mThumbnailUrl = iThumbnailUrl;
    }

    private Step(Parcel in) {
        mId = in.readString();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl =  in.readString();
        mThumbnailUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
        dest.writeString(mThumbnailUrl);
    }

    static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
