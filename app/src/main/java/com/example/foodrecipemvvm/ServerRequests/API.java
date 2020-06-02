package com.example.foodrecipemvvm.ServerRequests;

import com.example.foodrecipemvvm.ServerRequests.ResponsesModel.RecipeResponse;
import com.example.foodrecipemvvm.ServerRequests.ResponsesModel.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    //SEARCH
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe( @Query("q") String type,   @Query("page") String pageNumber ) ;                                             //?
    //"@Query" in this link is equivalent to     //?        and           //&  respectively

    //Fetch recipe from the server
    @GET("api/get")
    Call<RecipeResponse> recipeFetched( @Query("rId") String recipeID  );
                                        //?


}
