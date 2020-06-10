package com.example.foodrecipemvvm.Repositories;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.ServerRequests.WebServiceConnection;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * this is the layer between the ViewModel and the data source (in this case a webservice using retrofit)
 * this class is observing the "WebServiceConnection" class
 */
public class RecipeRepo {



    //var needed for the singleton pattern
    private static RecipeRepo instance;
    private WebServiceConnection webServiceConnection;
    private String queryTitle;
    private int queryPage;

    //singleton pattern
    public static RecipeRepo initRepo(){
        if (instance == null){
            instance = new RecipeRepo();
        }
        return instance;
    };

    //constructor
    private RecipeRepo(){
        webServiceConnection = WebServiceConnection.initWebService();  //singleton of class observed
    }

    //connection between the server and the ViewModel
    public LiveData<List<Recipe>> fetchRecipes(){
        return webServiceConnection.infoFromServerRecipeList();
    }

    /**
     * pass query coming from viewModel to the WebService class
     * @param query
     * @param pageNumber
     */
    public void connectionWithAPI(String query, int pageNumber){
        if (pageNumber == 0 ){
            pageNumber = 1;
        }

        webServiceConnection.setConnectionAPIRecipeList(query, pageNumber);

        queryTitle = query;
        queryPage= pageNumber;
    }

    public void cancelQuery(){
        webServiceConnection.cancelQuery();
    }


    public void loadNextPage(){
        webServiceConnection.setConnectionAPIRecipeList(queryTitle , queryPage + 1);
    }

    /**
     * fetch info
     * @return
     */
    public LiveData<Recipe> getRecipeDetails(){
        return webServiceConnection.infoFromServerRecipe();
    }

    /**
     * send query
     * @param recipeID
     */
    public void singleRecipeRequestAPI(String recipeID){
        webServiceConnection.setConnectionAPIRecipe(recipeID);
    }

    public LiveData<Boolean> getNetworkTimedOut(){
        return webServiceConnection.networkError();
    }



}
