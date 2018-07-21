package com.agafonova.bake.db;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Olga Agafonova on 7/21/18.
 */

public class Ingredient implements Parcelable {

    private String mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredient() {

    }

    public Ingredient(String iQuantity, String iMeasure, String iIngredient) {
        this.mQuantity = iQuantity;
        this.mMeasure = iMeasure;
        this.mIngredient = iIngredient;
    }

    private Ingredient(Parcel in) {
        mQuantity = in.readString();
        mMeasure = in.readString();
        mIngredient =  in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }

    static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
