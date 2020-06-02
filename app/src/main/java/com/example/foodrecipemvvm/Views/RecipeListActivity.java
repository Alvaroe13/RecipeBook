package com.example.foodrecipemvvm.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.ServerRequests.API;
import com.example.foodrecipemvvm.ServerRequests.ServiceRetrofitGenerator;
import com.example.foodrecipemvvm.ViewModels.RecipeListViewModel;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class RecipeListActivity extends BaseActivity {

    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel viewModelRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //init ViewModel
        viewModelRecipeList = new ViewModelProvider(this).get(RecipeListViewModel.class);
        initObserver();

    }

    /**
     * this is the observer observing the observable object in the ViewModel
     * AKA "retrieveRecipeList" method.
     */
    private void initObserver(){
        viewModelRecipeList.retrieveRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                //this method is called anytime there is a change in the MutableLiveData var in the ViewModel
            }
        });
    }


}





    /*

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked");
                testRetrofit();
            }
        });
*/
    /*
    private void testRetrofit() {
        API apiRequest = ServiceRetrofitGenerator.getApi();
        */
    /* Call<RecipeSearchResponse> responseCall = apiRequest.searchRecipe("vegan" , "1");

        responseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                Log.d(TAG, "onResponse: called");
                if (response.code() == 200 ){
                    List<Recipe> recipeList = new ArrayList<>(response.body().getRecipes());
                    for (Recipe recipe: recipeList){
                        Log.d(TAG, "onResponse: info fetched: " + recipe.getTitle());
                    }
                } else{
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    }catch (IOException e){
                        Log.d(TAG, "onResponse: error= " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

            }
        });
*/
    // --------------------------------------------- wnfojegobnerg ----------------------------
    /*
       Call<RecipeResponse> recipeSingle = apiRequest.recipeFetched("41470");

      recipeSingle.enqueue(new Callback<RecipeResponse>() {
           @Override
           public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
               Log.d(TAG, "onResponse: called");
               if (response.code() == 200){
                   Recipe recipe = response.body().getRecipe();
                   Log.d(TAG, "onResponse: info fetched= " + recipe.getTitle());
               } else{
                   try {
                       Log.d(TAG, "onResponse: recipe: " + response.errorBody().string());
                   }catch (IOException e){
                       e.printStackTrace();
                   }
               }
           }

           @Override
           public void onFailure(Call<RecipeResponse> call, Throwable t) {

           }
       });

          }*/


