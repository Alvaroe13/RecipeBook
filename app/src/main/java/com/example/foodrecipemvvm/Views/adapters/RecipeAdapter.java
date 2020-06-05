package com.example.foodrecipemvvm.Views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.Views.adapters.viewHolders.LoadingDottedView;
import com.example.foodrecipemvvm.Views.adapters.viewHolders.ViewHolderRecipe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This is an adapter that extends the Generic RecyclerView Adapter class because in this app we're
 * going to user one adapter and show one specific ViewHolder depending and the layout
 */
public class RecipeAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int RECIPE_VIEW_TYPE = 1;
    public static final int LOADING_VIEW_TYPE = 2;


    private List<Recipe> recipeList;
    private RecipeOnClickListener recipeOnClickListener;

    public RecipeAdapter(RecipeOnClickListener recipeOnClickListener) {
        this.recipeOnClickListener = recipeOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here we inflate a certain view depending on certain circumstances

        View layoutView = null;

        switch (viewType){
            case RECIPE_VIEW_TYPE:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);
                return new ViewHolderRecipe(layoutView , recipeOnClickListener);
            case LOADING_VIEW_TYPE:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_dotted_view, parent, false);
                return new LoadingDottedView(layoutView );
            default: {
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);
                return new ViewHolderRecipe(layoutView , recipeOnClickListener);
            }

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_VIEW_TYPE){
            //we display recipes in the view
            viewTypeRecipe(holder, position);
        }


    }

    @Override
    public int getItemCount() {
        if (recipeList != null){
            return recipeList.size();
        }
        return 0;

    }

    @Override
    public int getItemViewType(int position) {
        if (recipeList.get(position).getTitle().equals("FETCHING...")){
            return  LOADING_VIEW_TYPE;
        } else {
            return RECIPE_VIEW_TYPE;
        }
    }

    /**
     * here we set the title as "fetching" when we're retrieving info
     */
    public void displayLoading(){
        if (!isLoading()){
            Recipe recipe = new Recipe();
            recipe.setTitle("FETCHING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            recipeList = loadingList;
            notifyDataSetChanged();
        }
    }

    /**
     * this method checks if the app is fetching info from the server or not, this one will be the one
     * in charge of showing either the recipe view or the dot progress view.
     * @return
     */
    private Boolean isLoading(){
        if ( recipeList != null ){
            if (recipeList.size() > 0 ){
                if (recipeList.get(recipeList.size()).getTitle().equals("FETCHING...")){
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * this method shows the view with recipes
     * @param holder
     * @param position
     */
    private void viewTypeRecipe(RecyclerView.ViewHolder holder, int position){
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

    /**
     * we use this method to pass the info fetched from the API client into the layout
     * @param recipes
     */
    public void setRecipes(List<Recipe> recipes){
        recipeList = recipes;
        notifyDataSetChanged();
    }
}
