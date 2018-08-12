package com.agafonova.bake.db;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Recipe implements Parcelable{

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("ingredients")
    private ArrayList<Ingredient> mIngredients = null;

    @SerializedName("steps")
    private ArrayList<Step> mSteps = null;

    @SerializedName("servings")
    private String mServings;

    @SerializedName("image")
    private String mImage;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Ingredient> getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(ArrayList<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public ArrayList<Step> getmSteps() {
        return mSteps;
    }

    public void setmSteps(ArrayList<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public String getmServings() {
        return mServings;
    }

    public void setmServings(String mServings) {
        this.mServings = mServings;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public static Creator<Recipe> getCREATOR() {
        return CREATOR;
    }

	public Recipe(String iId, String iName, ArrayList<Ingredient> iIngredients, ArrayList<Step> iSteps,
                  String iServings, String iImage) {

		this.mId = iId;
		this.mName = iName;
		this.mIngredients = iIngredients;
		this.mSteps = iSteps;
		this.mServings = iServings;
		this.mImage = iImage;
	}

    private Recipe(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mIngredients = new ArrayList<>();
        in.readTypedList(mIngredients, Ingredient.CREATOR);
        mSteps = new ArrayList<>();
        in.readTypedList(mSteps, Step.CREATOR);
        mServings = in.readString();
        mImage = in.readString();
    }

    //Used for testing UI only
    public static ArrayList<Recipe> getFakeItems() {

	    ArrayList<Recipe> fakeItemList = new ArrayList<Recipe>();
	    ArrayList<Step> fakeSteps = new ArrayList<Step>();
	    ArrayList<Ingredient> fakeIngredients = new ArrayList<Ingredient>();

	    Step fakeStep = new Step("1", "Eat cake", "Get cake out of fridge, then eat it", "", "");
	    Ingredient fakeIngredient = new Ingredient("1", "1", "cocoa powder");

	    fakeSteps.add(fakeStep);
	    fakeIngredients.add(fakeIngredient);

	    Recipe fakeItemOne = new Recipe("1", "Chocolate Pie Recipe", fakeIngredients, fakeSteps, "1 serving", "");
        fakeItemList.add(fakeItemOne);

	    return fakeItemList;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeTypedList(mIngredients);
        dest.writeTypedList(mSteps);
        dest.writeString(mServings);
        dest.writeString(mImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

}
