package com.example.foodrecipemvvm.Views;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.ViewModels.RecipeListViewModel;
import com.example.foodrecipemvvm.Views.adapters.RecipeAdapter;
import com.example.foodrecipemvvm.Views.adapters.RecipeOnClickListener;

import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeListActivity extends BaseActivity implements RecipeOnClickListener {

    private static final String TAG = "RecipeListActivity";

    //ui
    private RecyclerView recipeRecyclerView;
    private RecipeAdapter adapter;
    private SearchView searchView;
    //vars
    private RecipeListViewModel viewModelRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //init ViewModel
        viewModelRecipeList = new ViewModelProvider(this).get(RecipeListViewModel.class);
        bindUI();
        initRecyclerView();

        initObserver();
        initSearchBar();
    }   

    private void bindUI(){
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
    }

    private void initRecyclerView() {
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter(this);
        recipeRecyclerView.setAdapter(adapter);
    }

    /**
     * this is the observer observing the observable object in the ViewModel
     * AKA "retrieveRecipeList" method.
     * this method is called anytime there is a change in the MutableLiveData var in the ViewModel and
     * updates de UI. (THIS METHOD FETCHES INFO COMING FROM THE WEBSERVICE class)
     */
    private void initObserver(){
        viewModelRecipeList.retrieveRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null ){
                    Log.d(TAG, "onChanged: we pass the info fetched to the adapter");
                     adapter.setRecipes(recipes);       
                } else{
                    Toast.makeText(RecipeListActivity.this, "info null from the server", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This method sends info to the Webservice class (going through viewModel/repository classes) when searching for a recipe
     * @param query
     * @param pageNumber
     */
    private void connectionWithViewModel(String query, int pageNumber){
        viewModelRecipeList.connectionWithRepo(query , pageNumber);
    }


    
    @Override
    public void searchRecipeOnClick(int position) {
        
    }

    @Override
    public void openRecipeOnClick() {

    }

    private void initSearchBar(){

        searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: text inserted: " + query);
                connectionWithViewModel(query, 1); //we make search for page 1 by default
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /*   *//**
     * this method contains the logic for fetching info from the server
     *//*
    private void testRetrofit() {
        Log.d(TAG, "testRetrofit: called");
        connectionWithViewModel("vegan", 1 ) ;
    }*/

}









