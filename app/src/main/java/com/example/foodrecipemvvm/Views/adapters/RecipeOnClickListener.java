package com.example.foodrecipemvvm.Views.adapters;

public interface RecipeOnClickListener {

    //click click method is for recipe search view
    void searchRecipeOnClick(int position);
    //this click method is to open a recipe view
    void openRecipeOnClick();

}
