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
    private boolean viewingRecipes;
    private boolean performingQuery;

    public RecipeListViewModel() {
        recipeRepo = RecipeRepo.initRepo();
        viewingRecipes = false; //set it as false because this is not the first view to show when user launches the app
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
        viewingRecipes = true;
        performingQuery = true;
        recipeRepo.connectionWithAPI(query, pageNumber);
    }

    //getter
    public boolean isPerformingQuery() {
        return performingQuery;
    }

    //setter
    public void setPerformingQuery(boolean performingQuery) {
        this.performingQuery = performingQuery;
    }

    //getter
    public boolean isViewingRecipes() {
        return viewingRecipes;
    }

    //setter
    public void setViewingRecipes(boolean viewingRecipes) {
        this.viewingRecipes = viewingRecipes;
    }

    public boolean backButtonPressed(){
        if (performingQuery){
            //here we cancel request
            performingQuery = false;
            recipeRepo.cancelQuery();
        }
        if (viewingRecipes){
            viewingRecipes = false;
            return false;
        }
        return false;
    }

    public void loadNextPage(){

        if (!performingQuery && viewingRecipes ){
            recipeRepo.loadNextPage();
        }

    }




}
