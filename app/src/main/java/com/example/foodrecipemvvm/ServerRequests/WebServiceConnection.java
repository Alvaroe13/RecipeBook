package com.example.foodrecipemvvm.ServerRequests;

import android.util.Log;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.ServerRequests.ResponsesModel.RecipeResponse;
import com.example.foodrecipemvvm.ServerRequests.ResponsesModel.RecipeSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.foodrecipemvvm.util.Constants.NETWORK_TIME_OUT;

/**
 * This is the class that will retrieve the info from the server (API client)
 */
public class WebServiceConnection    {

    private static final String TAG = "WebServiceConnection";


    // var to store the info fetched from the server
    private MutableLiveData<List<Recipe>> recipeList;
    private MutableLiveData<Recipe> recipeDetails;
    //needed for the singleton design pattern
    private static WebServiceConnection instance;
    private ConnectionAPIBackground backgroundRunnable;
    private SingleRecipeRunnable singleRecipeRunnable;

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
        recipeDetails = new MutableLiveData<>();
    }


    //this LiveData method is the one passing the info from the server to the repository layer
    public LiveData<List<Recipe>> infoFromServerRecipeList(){
        return recipeList;
    }

    public LiveData<Recipe> infoFromServerRecipe(){
        return recipeDetails;
    }

    /**
     * We execute the query coming from the View/viewModel/Repository classes.
     * @param query
     * @param pageNumber
     */
    public void setConnectionAPIRecipeList(String query, int pageNumber){
        //we make sure to make this background thread null in case is not
        if (backgroundRunnable != null){
            backgroundRunnable = null;
        }
        //then we instantiate this background thread in this thread
        backgroundRunnable = new ConnectionAPIBackground(query, pageNumber);
        //we save it's value in this Future var
        final Future handler = AppExecutors.getInstance().getNetworkExecutor().submit(backgroundRunnable);


        //This portion of code handles the TIME_OUT part of the logic of retrieving data from the API client.
        AppExecutors.getInstance().getNetworkExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIME_OUT, TimeUnit.MILLISECONDS);


    }

    public void setConnectionAPIRecipe(String recipeID){
        if ( singleRecipeRunnable != null ){
             singleRecipeRunnable = null;
        }

        singleRecipeRunnable = new SingleRecipeRunnable(recipeID);

        final Future responseRunnable = AppExecutors.getInstance().getNetworkExecutor().submit(singleRecipeRunnable);


        AppExecutors.getInstance().getNetworkExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                responseRunnable.cancel(true);
            }
        }, NETWORK_TIME_OUT, TimeUnit.MILLISECONDS);
    }


    /**
     * method in charge of telling the runnable to stop performing query to the API client.
     */
    public void cancelQuery(){
        if (backgroundRunnable != null ){
            backgroundRunnable.stopRequest();
        }
        if (singleRecipeRunnable != null){
            singleRecipeRunnable.stopRequest();
        }
    }



    /**
     * class in charge of creating a background threadPool and put in motion the query to
     * the server
     */
    private class ConnectionAPIBackground implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public ConnectionAPIBackground(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }


        @Override
        public void run() {

            try {
                //Response type of var is from Retrofit library
                Response responseFromNetwork = requestRecipes(query, pageNumber).execute();

                if (cancelRequest){
                    return;
                }
                if (responseFromNetwork.code() == 200 ){
                    //here we store in the list the info fetched from the API client
                    List<Recipe> recipe = new ArrayList<>( ((RecipeSearchResponse)responseFromNetwork.body()).getRecipes() );
                    if (pageNumber == 1){
                        //when we get the page 1 we pass that info to the liveData var
                        recipeList.postValue(recipe);  // here we set the value in the LiveData var to be passed to Repo/ViewModel/View
                    } else{
                        //here we store all the recipes already fetched from the API client in a var
                        List<Recipe> currentRecipeList = recipeList.getValue();
                        currentRecipeList.addAll(recipe);
                        recipeList.postValue(currentRecipeList);    //here we append more recipes in the LiveData to be passed to Repo/ViewModel/View
                    }
                } else {
                    //if request answer is not 200 something went wrong
                    String error = responseFromNetwork.errorBody().string();
                    Log.d(TAG, "run: some error occurred: " + error );
                    recipeList.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "run: error in the web: " + e.getMessage());
            }
        }

        /**
         * Here is where technically we make the connection (request) with the API client
         * @param query
         * @param pageNumber
         * @return
         */
        private Call<RecipeSearchResponse> requestRecipes(String query, int pageNumber){
            return ServiceRetrofitGenerator.getApi().searchRecipe( query, String.valueOf(pageNumber) );
        }


        private void stopRequest(){
            Log.d(TAG, "stopRequest: request cancelled");
            cancelRequest = true;
        }
    }


    /**
     * class in charge of creating a background threadPool and put in motion the query to
     * the server
     */
    private class SingleRecipeRunnable implements Runnable{

        private String recipeID;
        boolean cancelRequest;

        public SingleRecipeRunnable(String recipeID ) {
            this.recipeID = recipeID;
            cancelRequest = false;
        }


        @Override
        public void run() {

            try {
                //Response type of var is from Retrofit library
                Response responseFromNetwork = getRecipeDetails(recipeID).execute();

                if (cancelRequest){
                    return;
                }
                if (responseFromNetwork.code() == 200 ){
                    //here we store in the list the info fetched from the API client
                    Recipe recipeFetched = ((RecipeResponse)responseFromNetwork.body()).getRecipe();
                    Log.d(TAG, "run: recipeID fetched " + recipeFetched.getRecipeID());
                    Log.d(TAG, "run: title fetched " + recipeFetched.getTitle());
                    recipeDetails.postValue(recipeFetched);

                } else {
                    String error = responseFromNetwork.errorBody().string();
                    Log.d(TAG, "run: some error occurred: " + error );
                    recipeList.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "run: error in the web: " + e.getMessage());
            }
        }

        /**
         * Here is where technically we make the connection (request) with the API client
         * @param recipeID
         * @return
         */
        private Call<RecipeResponse> getRecipeDetails(String recipeID){
            return ServiceRetrofitGenerator.getApi().fetchRecipe(recipeID);
        }


        private void stopRequest(){
            Log.d(TAG, "stopRequest: request cancelled");
            cancelRequest = true;
        }
    }

}
