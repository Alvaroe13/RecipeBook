package com.example.foodrecipemvvm.ServerRequests;

import com.example.foodrecipemvvm.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRetrofitGenerator {

    //singleton pattern

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                                                .baseUrl(Constants.BASE_URL)
                                                .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    //we do this since we're not going to instantiate a retrofit objects in any activity in this app
    private static API api = retrofit.create(API.class);

    //since API object created above is private lets create a get method for it
    public static API getApi(){
        return api;
    }

}
