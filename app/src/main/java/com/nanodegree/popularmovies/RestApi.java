package com.nanodegree.popularmovies;

import com.nanodegree.popularmovies.model.DiscoverMovieResponse;

import java.util.Objects;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Naushad on 21/12/15.
 */
public interface RestApi {

    @GET("discover/movie")
    Call<DiscoverMovieResponse> getFeed(@Query("sort_by") String sortBy,
                          @Query("api_key") String apiKey
    );
}
