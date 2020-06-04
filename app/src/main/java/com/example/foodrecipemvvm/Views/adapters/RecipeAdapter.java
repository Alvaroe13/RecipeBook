package com.example.foodrecipemvvm.Views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.Views.adapters.viewHolders.ViewHolderRecipe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This is an adapter that extends the Generic RecyclerView Adapter class because in this app we're
 * going to user one adapter and show one specific ViewHolder depending and the layout
 */
public class RecipeAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private List<Recipe> recipeList;
    private RecipeOnClickListener recipeOnClickListener;

    public RecipeAdapter(RecipeOnClickListener recipeOnClickListener) {
        this.recipeOnClickListener = recipeOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);

        return new ViewHolderRecipe(view , recipeOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //lets set the UI elements
        ((ViewHolderRecipe)holder).title.setText(recipeList.get(position).getTitle());
        ((ViewHolderRecipe)holder).publisherName.setText(recipeList.get(position).getPublisher());
        //here we cast from float to String and round up the value ad they will come with a bunch of decimals when fetched from the server
        ((ViewHolderRecipe)holder).score.setText(String.valueOf(Math.round(recipeList.get(position).getSocialRank())));


        //GLIDE
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(options)
                .load(recipeList.get(position).getImageUrl())
                .into(((ViewHolderRecipe)holder).image);

    }

    @Override
    public int getItemCount() {
        if (recipeList != null){
            return recipeList.size();
        }
        return 0;

    }

    /**
     * we use this method to pass the info fetched from the API client into the layout
     * @param recipes
     */
    public void setRecipes(List<Recipe> recipes){
        recipeList = recipes;
        notifyDataSetChanged();
    }
}
