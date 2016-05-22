package com.nanodegree.popularmovies;

import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Naushad on 21/12/15.
 */
public class App extends Application {

    static RestApi service;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
    }

    /**
     * context of application
     *
     * @return
     */
    public static Context getContext() {
        return sContext;
    }


    public static synchronized RestApi getApi() {
        if (service == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(getLoggingInterceptor());

            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(getDefaultGsonBuilder()))
                    .baseUrl("http://api.themoviedb.org/")
                    .build();

            service = retrofit.create(RestApi.class);
        }
        return service;
    }

    /**
     * Returns the logging interceptor, for release builds the logging is turned off
     *
     * @return Appropriate Loglevel
     */
    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

    /**
     * Default Gson builder object.
     *
     * @return Gson
     */
    public static Gson getDefaultGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
