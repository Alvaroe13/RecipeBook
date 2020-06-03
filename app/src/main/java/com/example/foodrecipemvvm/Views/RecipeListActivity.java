package com.example.foodrecipemvvm.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;
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


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked");
                testRetrofit();
            }
        });

    }

    /**
     * this is the observer observing the observable object in the ViewModel
     * AKA "retrieveRecipeList" method.
     * this method is called anytime there is a change in the MutableLiveData var in the ViewModel
     */
    private void initObserver(){
        viewModelRecipeList.retrieveRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null ){
                    for (Recipe resultRecipes : recipes){
                        Log.d(TAG, "onChanged: result from the web: " + resultRecipes.getTitle() );
                    }
                }
            }
        });
    }


    private void connectionWithViewModel(String query, int pageNumber){
        viewModelRecipeList.connectionWithRepo(query , pageNumber);
    }


    private void testRetrofit() {
        Log.d(TAG, "testRetrofit: called");
        connectionWithViewModel("vegan", 1 ) ;
    }


}









