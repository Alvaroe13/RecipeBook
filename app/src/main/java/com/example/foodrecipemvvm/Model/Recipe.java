package com.example.foodrecipemvvm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * this object class is the one that will contain all the info detailed when we fetch info from the server
 */
public class Recipe implements Parcelable {

    @SerializedName("ingredients")
    private String[] ingredients;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("recipe_id")
    private String recipeID;
    @SerializedName("title")
    private String title;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("social_rank")
    private float socialRank;

    public Recipe() {
    }

    public Recipe(String[] ingredients, String publisher, String recipeID, String title, String imageUrl, float socialRank) {
        this.ingredients = ingredients;
        this.publisher = publisher;
        this.recipeID = recipeID;
        this.title = title;
        this.imageUrl = imageUrl;
        this.socialRank = socialRank;
    }



    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getSocialRank() {
        return socialRank;
    }

    public void setSocialRank(float socialRank) {
        this.socialRank = socialRank;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //----------------------------------- parcelable methods-----------------------------


    protected Recipe(Parcel in) {
        ingredients = in.createStringArray();
        publisher = in.readString();
        recipeID = in.readString();
        title = in.readString();
        imageUrl = in.readString();
        socialRank = in.readFloat();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(ingredients);
        dest.writeString(publisher);
        dest.writeString(recipeID);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeFloat(socialRank);
    }


    //-------------- method for making this object parcelable


}
