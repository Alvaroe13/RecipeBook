package com.example.foodrecipemvvm.Views.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodrecipemvvm.Model.Recipe;
import com.example.foodrecipemvvm.R;
import com.example.foodrecipemvvm.Views.adapters.viewHolders.CategoryListViewHolder;
import com.example.foodrecipemvvm.Views.adapters.viewHolders.LoadingDottedView;
import com.example.foodrecipemvvm.Views.adapters.viewHolders.LoadingDottedViewTop;
import com.example.foodrecipemvvm.Views.adapters.viewHolders.ViewHolderRecipe;
import com.example.foodrecipemvvm.util.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This is an adapter that extends the Generic RecyclerView Adapter class because in this app we're
 * going to user one adapter and show one specific ViewHolder depending and the layout
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int RECIPE_VIEW_TYPE = 1;
    public static final int LOADING_VIEW_TYPE = 2;
    public static final int CATEGORIES_VIEW_TYPE = 3;
    public static final int LOADING_VIEW_TOP_TYPE = 4;


    private List<Recipe> recipeList;
    private OnClickListeners clickListener;

    public MainAdapter(OnClickListeners clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here we inflate a certain view depending on certain circumstances

        View layoutView = null;

        switch (viewType){
            case RECIPE_VIEW_TYPE:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);
                return new ViewHolderRecipe(layoutView , clickListener);
            case LOADING_VIEW_TYPE:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_dotted_view, parent, false);
                return new LoadingDottedView(layoutView);
            case CATEGORIES_VIEW_TYPE:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list, parent, false);
                return new CategoryListViewHolder(layoutView, clickListener );
            case LOADING_VIEW_TOP_TYPE:
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_dotted_view_top, parent, false);
            return new LoadingDottedViewTop(layoutView);
            default: {
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);
                return new ViewHolderRecipe(layoutView , clickListener);
            }

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);

        if (itemViewType == CATEGORIES_VIEW_TYPE){
            //we display recipes in the view
            viewTypeCategory(holder, position);
        }
        else  if (itemViewType == RECIPE_VIEW_TYPE){
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
        if (recipeList.get(position).getSocialRank() == -1){
            return CATEGORIES_VIEW_TYPE;
        }
        else if (recipeList.get(position).getTitle().equals("FETCHING...")){
            return  LOADING_VIEW_TYPE;
        }
        else if ( position == recipeList.size() -1 && position != 0 &&
                         !recipeList.get(position).getTitle().equals("EXHAUSTED...")){
            //pending to be finished
            return  LOADING_VIEW_TOP_TYPE;
        }else {
            return RECIPE_VIEW_TYPE;
        }
    }

    /**
     * method inflates Categories view
     * @param holder
     * @param position
     */
    private void viewTypeCategory(RecyclerView.ViewHolder holder, int position) {
        //lets set the UI elements
        ((CategoryListViewHolder)holder).categoryText.setText(recipeList.get(position).getTitle());

        Uri imageUri = Uri.parse("android.resource://com.example.foodrecipemvvm/drawable/" + recipeList.get(position).getImageUrl());

        //GLIDE
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_launcher_background);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(options)
                .load(imageUri)
                .into(((CategoryListViewHolder)holder).categoryImage);
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
                .centerCrop()
                .error(R.drawable.ic_launcher_background);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(options)
                .load(recipeList.get(position).getImageUrl())
                .into(((ViewHolderRecipe)holder).image);
    }

    public void displayCategories(){
        List<Recipe> categoryList = new ArrayList<>();
        for ( int i=0 ; i < Constants.CATEGORY_NAMES.length ; i++){
            Recipe category = new Recipe();
            category.setTitle(Constants.CATEGORY_NAMES[i]);
            category.setImageUrl(Constants.CATEGORY_IMAGES[i]);
            category.setSocialRank(-1);
            categoryList.add(category);
        }
        recipeList = categoryList;
        notifyDataSetChanged();
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
                if (recipeList.get(recipeList.size() -1 ).getTitle().equals("FETCHING...")){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * we use this method to pass the info fetched from the API client into the layout
     * @param recipes
     */
    public void setRecipes(List<Recipe> recipes){
        recipeList = recipes;
        notifyDataSetChanged();
    }

    /**
     * method return recipe position, we're going to user this to pass the position of the recipe
     * selected to the DetailsRecipeActivity.
     * @param position
     * @return
     */
    public Recipe recipeSelected(int position){
        if (recipeList != null ){
            if (recipeList.size() >0 ){
                return recipeList.get(position);
            }
        }
        return null;

    }
}
