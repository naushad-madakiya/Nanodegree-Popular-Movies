package com.nanodegree.popularmovies;

import android.app.Application;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Naushad on 21/12/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    static RestApi service;

    public static synchronized RestApi getApi() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api.themoviedb.org/")
                    .build();

            service = retrofit.create(RestApi.class);
        }
        return service;
    }
}
