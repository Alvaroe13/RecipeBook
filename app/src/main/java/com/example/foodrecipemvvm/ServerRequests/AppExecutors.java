package com.example.foodrecipemvvm.ServerRequests;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Class created for the purpose of working in a background thread
 */
public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors getInstance(){
        if (instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    //create a threadPool
    private final ScheduledExecutorService networkExecutor = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getNetworkExecutor(){
        return networkExecutor;
    }
}
