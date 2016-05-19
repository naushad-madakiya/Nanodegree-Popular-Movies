package com.nanodegree.popularmovies;

import com.nanodegree.popularmovies.model.DiscoverMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Naushad on 21/12/15.
 */
public interface RestApi {

    @GET("/3/movie/popular")
    Call<DiscoverMovieResponse> getPopularMovies(
            @Query("api_key") String apiKey);
}
