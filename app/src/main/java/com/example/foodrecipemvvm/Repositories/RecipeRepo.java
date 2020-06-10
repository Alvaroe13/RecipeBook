package com.example.foodrecipemvvm.Repositories;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.ServerRequests.WebServiceConnection;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * this is the layer between the ViewModel and the data source (in this case a webservice using retrofit)
 * this class is observing the "WebServiceConnection" class
 */
public class RecipeRepo {


    //single source of truth
    private MutableLiveData<Boolean> queryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mediator = new MediatorLiveData<>();

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
        initMediators();
    }

    //connection between the server and the ViewModel
    public LiveData<List<Recipe>> fetchRecipes(){
        return mediator;

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
        queryTitle = query;
        queryPage= pageNumber;
        queryExhausted.setValue(false);

        webServiceConnection.infoFromServerRecipeList();
        webServiceConnection.setConnectionAPIRecipeList(query, pageNumber);


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

    public void initMediators(){
        LiveData<List<Recipe>> recipesFromServer = webServiceConnection.infoFromServerRecipeList();
        mediator.addSource(recipesFromServer, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null){
                    mediator.setValue(recipes);
                    queryDone(recipes);
                } else{
                    queryDone(null);
                    //we use room library to make the query
                }
            }
        });
    }

    public void queryDone(List<Recipe> list){
        if (list != null){
            if (list.size() < 30 ){
                queryExhausted.setValue(true);
            }
        } else {
            queryExhausted.setValue(true);
        }
    }

    public MutableLiveData<Boolean> getQueryExhausted() {
        return queryExhausted;
    }

    public void setQueryExhausted(MutableLiveData<Boolean> queryExhausted) {
        this.queryExhausted = queryExhausted;
    }


}
