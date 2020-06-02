package com.example.foodrecipemvvm.ServerRequests.ResponsesModel;

import com.example.foodrecipemvvm.Model.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * object class for when we get a response from the server when we search for a recipe
 */
public class RecipeSearchResponse {



    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes;

    public int getCount() {
        return count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
