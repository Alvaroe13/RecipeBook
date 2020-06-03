package com.example.foodrecipemvvm.Repositories;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.ServerRequests.WebServiceConnection;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * this is the layer between the ViewModel and the data source (in this case a webservice using retrofit)
 * this class is observing the "WebServiceConnection" class
 */
public class RecipeRepo {

    //var needed for the singleton pattern
    private static RecipeRepo instance;

    private WebServiceConnection webServiceConnection;

    //singleton pattern
    public static RecipeRepo initInstance(){
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
        return webServiceConnection.infoFromServer();
    }


    public void connectionWithAPI(String query, int pageNumber){
        if (pageNumber == 0 ){
            pageNumber = 1;
        }
        webServiceConnection.setConnectionAPI(query, pageNumber);
    }
}
