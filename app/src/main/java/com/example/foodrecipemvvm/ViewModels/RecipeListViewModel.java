package com.example.foodrecipemvvm.ViewModels;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.Repositories.RecipeRepo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * class observing the Repository class
 */
public class RecipeListViewModel extends ViewModel {

    private RecipeRepo recipeRepo;

    public RecipeListViewModel() {
        recipeRepo = RecipeRepo.initRepo();
    }

    /**
     * we store in this method all the value stored in the RecipeRepo var "recipeInfo"
     * this is to be retrieves later by the observer in the activity.
     * @return
     */
    public LiveData<List<Recipe>> retrieveRecipeList(){        //this is an observable
        return  recipeRepo.fetchRecipes();
    }

    /**
     * pass query coming from view to the repo
     * @param query
     * @param pageNumber
     */
    public void connectionWithRepo(String query, int pageNumber){
        recipeRepo.connectionWithAPI(query, pageNumber);
    }



}
