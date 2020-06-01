package com.example.foodrecipemvvm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RecipeListActivity extends BaseActivity {

    private static final String TAG = "RecipeListActivity";

    private Button text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        text = findViewById(R.id.textTxt);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked");
                    /*if (mProgressBar.getVisibility() == View.VISIBLE){
                        showProgressBar(false);
                    }else {
                        showProgressBar(true);
                    }*/

                    mProgressBar.setVisibility(View.VISIBLE);
       //             showProgressBar(true);
            }
        });


    }


}
