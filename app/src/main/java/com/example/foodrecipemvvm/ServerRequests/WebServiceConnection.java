package com.example.foodrecipemvvm.ServerRequests;

import com.example.foodrecipemvvm.Model.Recipe;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * This is the class that will retrieve the info from the server (API client)
 */
public class WebServiceConnection    {


    // var to store the info fetched from the server
    private MutableLiveData<List<Recipe>> recipeList;

    //needed for the singleton design pattern
    private static WebServiceConnection instance;

    //singleton design pattern
    public static WebServiceConnection initWebService(){
        if ( instance == null){
            instance = new WebServiceConnection();
        }
        return instance;
    }

    //constructor
    private  WebServiceConnection(){
        recipeList = new MutableLiveData<>();
    }


    //this LiveData method is the one passing the info from the server to the repository layer
    public LiveData<List<Recipe>> infoFromServer(){
        return recipeList;
    }
}
