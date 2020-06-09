package com.example.foodrecipemvvm.Views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.ViewModels.RecipeDetailsViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class RecipeDetailsActivity extends BaseActivity {

    private static final String TAG = "RecipeDetailsActivity";

    //ui
    private ImageView imageRecipe;
    private TextView titleRecipe, descriptionRecipe, socialRankRecipe;
    private LinearLayout detailsContainer;
    private ScrollView scrollView;
    //vars
    RecipeDetailsViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        initViewModel();
        ui();
        getIncomingIntent();
        observeViewModel();
    }




    private void ui() {
        imageRecipe = findViewById(R.id.recipe_image);
        titleRecipe = findViewById(R.id.recipe_title);
        socialRankRecipe = findViewById(R.id.recipe_social_score);
        detailsContainer = findViewById(R.id.container);
        scrollView = findViewById(R.id.parent);
        descriptionRecipe = findViewById(R.id.recipe_details);

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe info")){
            Recipe recipe = getIntent().getParcelableExtra("recipe info");
            Log.d(TAG, "getIncomingIntent recipe title: " + recipe.getTitle() );
            Log.d(TAG, "getIncomingIntent recipe id: " + recipe.getRecipeID() );
            Log.d(TAG, "getIncomingIntent: --------------------------------------------------");
            requestRecipe(recipe.getRecipeID());
        }
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(RecipeDetailsViewModel.class);
    }

    private void observeViewModel(){
        viewModel.retrieveRecipeDetails().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null){
                    if (recipe.getRecipeID().equals(viewModel.getRecipeIDRequested())){
                        Log.d(TAG, "onChanged: ---------------------------------------------");
                        Log.d(TAG, "onChanged: recipe info fetched: "  + recipe.getTitle() );
                        Log.d(TAG, "onChanged: recipe info fetched: "  + recipe.getRecipeID() );
                        Log.d(TAG, "onChanged: ----------------------------------------------");
                        for (String ingredient : recipe.getIngredients()){
                            Log.d(TAG, "onChanged: ingredient: " + ingredient);
                        }
                    }  else{
                        Log.d(TAG, "onChanged: another recipe retrieved");
                    }
                }else{
                    Log.d(TAG, "onChanged: info came back null");
                }
            }
        });
    }

    private void requestRecipe(String recipeID){
        Log.d(TAG, "requestRecipe: recipe ID sent: " + recipeID);
        viewModel.communicateWithRepo(recipeID);
    }


}
