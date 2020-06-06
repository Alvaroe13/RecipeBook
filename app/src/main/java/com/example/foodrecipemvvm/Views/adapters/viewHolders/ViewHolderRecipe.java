package com.example.foodrecipemvvm.Views.adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.Views.adapters.OnClickListeners;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderRecipe extends RecyclerView.ViewHolder implements View.OnClickListener {

    //ui
    //make them public since ViewHolders are in a different packages than the Adapter
    public TextView title, publisherName, score;
    public AppCompatImageView image;
    // on click event listener
    public OnClickListeners recipeOnClickListener;

    public ViewHolderRecipe(@NonNull View itemLayout , OnClickListeners recipeOnClickListener) {
        super(itemLayout);
        title = itemLayout.findViewById(R.id.recipe_title);
        publisherName = itemLayout.findViewById(R.id.recipe_publisher);
        score = itemLayout.findViewById(R.id.recipe_social_score);
        image = itemLayout.findViewById(R.id.recipe_image);

        this.recipeOnClickListener = recipeOnClickListener;
        //clickable the entire view
        itemLayout.setOnClickListener(this);
    }


    /**
     * Any onClick event happening in this ViewHolder must be located within this method
     * (best practices). This is trigger when the layout is pressed
     * @param v
     */
    @Override
    public void onClick(View v) {
        //here we get access to the method inside the interface
        recipeOnClickListener.openRecipeOnClick(getAdapterPosition());
    }


}

