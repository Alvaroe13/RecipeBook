package com.example.foodrecipemvvm.Views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;

import androidx.annotation.Nullable;

public class RecipeDetailsActivity extends BaseActivity {

    private static final String TAG = "RecipeDetailsActivity";

    //ui
    private ImageView imageRecipe;
    private TextView titleRecipe, descriptionRecipe, socialRankRecipe;
    private LinearLayout detailsContainer;
    private ScrollView scrollView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ui();
        getIncomingIntent();
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
            Log.d(TAG, "getIncomingIntent recipe: " + recipe );
        }
    }


}
