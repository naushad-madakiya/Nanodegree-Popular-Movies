package com.nanodegree.popularmovies;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(getLoggingInterceptor());

            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
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
}
