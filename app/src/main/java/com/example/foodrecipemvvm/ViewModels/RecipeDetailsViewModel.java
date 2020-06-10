package com.example.foodrecipemvvm.ViewModels;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.Repositories.RecipeRepo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RecipeDetailsViewModel extends ViewModel {

    private RecipeRepo recipeRepo;
    private String recipeIDRequested;
    private boolean recipeFetched;

    public RecipeDetailsViewModel() {
        recipeRepo = RecipeRepo.initRepo();
        recipeFetched = false;
    }


    public LiveData<Recipe> retrieveRecipeDetails(){
        return recipeRepo.getRecipeDetails();
    }

    public void communicateWithRepo(String recipeID){
        recipeIDRequested = recipeID;
        recipeRepo.singleRecipeRequestAPI(recipeID);
    }

    public String getRecipeIDRequested() {
        return recipeIDRequested;
    }

    public LiveData<Boolean> networkTimedOut(){
        return recipeRepo.getNetworkTimedOut();
    }

    public boolean isRecipeFetched() {
        return recipeFetched;
    }

    public void setRecipeFetched(boolean recipeRetrieved) {
        recipeFetched = recipeRetrieved;
    }
}
