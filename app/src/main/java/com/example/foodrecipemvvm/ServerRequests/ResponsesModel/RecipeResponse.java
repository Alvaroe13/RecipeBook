package com.example.foodrecipemvvm.ServerRequests.ResponsesModel;

import com.example.foodrecipemvvm.Model.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *  this object class is for when we fetch info from the server
 */
public class RecipeResponse {

    @SerializedName("recipe")       //retrofit
    @Expose                         //Gson
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }
}
