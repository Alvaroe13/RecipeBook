package com.example.foodrecipemvvm.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.ViewModels.RecipeListViewModel;
import com.example.foodrecipemvvm.Views.adapters.MainAdapter;
import com.example.foodrecipemvvm.Views.adapters.OnClickListeners;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeListActivity extends BaseActivity implements OnClickListeners {

    private static final String TAG = "RecipeListActivity";

    //ui
    private RecyclerView recipeRecyclerView;
    private MainAdapter adapter;
    private SearchView searchView;
    //vars
    private RecipeListViewModel viewModelRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        initViewModel();
        bindUI();
        initRecyclerView();

        initObserver();
        initSearchBar();
        setDefaultView();

    }

    private void initViewModel(){
        viewModelRecipeList = new ViewModelProvider(this).get(RecipeListViewModel.class);
    }
    private void bindUI(){
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
    }

    private void setDefaultView(){
        if (!viewModelRecipeList.isViewingRecipes()){
            //show categories view
            showCategoriesView();
        }
    }
    private void initRecyclerView() {
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this);
        recipeRecyclerView.setAdapter(adapter);
        loadMoreRecipes();
    }

    private void loadMoreRecipes() {
        recipeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if (!recyclerView.canScrollVertically(1)) {
                    //load next page
                    viewModelRecipeList.loadNextPage();
                }

            }
        });
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
                    if (viewModelRecipeList.isViewingRecipes()){
                        Log.d(TAG, "onChanged: we pass the info fetched to the adapter");
                        viewModelRecipeList.setPerformingQuery(false);
                        adapter.setRecipes(recipes);
                    }
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

    private void initSearchBar(){

        searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: text inserted: " + query);
                lossFocusUI();
                adapter.displayLoading();
                connectionWithViewModel(query, 1); //we make search for page 1 by default
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void openRecipeOnClick(int position) {

        Recipe recipeInfo = adapter.recipeSelected(position);

        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra("recipe info", recipeInfo);
        startActivity(intent);
    }

    @Override
    public void openCategoryOnClick(String category) {
        lossFocusUI();
        adapter.displayLoading();
        connectionWithViewModel(category, 1); //we make search for page 1 by default
    }

    /**
     * this is the view in charge of telling the viewModel that we're showing the categories view
     * (the one to be open  first by default every time the app is launched).
     */
    private void showCategoriesView() {
        viewModelRecipeList.setViewingRecipes(false);
        adapter.displayCategories();
    }

    /**
     * this is to remove focus on UI element once is pressed by the user
     */
    private void lossFocusUI(){
        searchView.clearFocus();
        searchView.setIconified(true);
    }

    @Override
    public void onBackPressed() {
        if (viewModelRecipeList.backButtonPressed()){
            super.onBackPressed();
        } else{
            showCategoriesView();
        }

    }
}









