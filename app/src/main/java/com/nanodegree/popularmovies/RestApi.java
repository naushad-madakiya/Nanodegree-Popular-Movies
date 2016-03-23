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

    @GET("/3/movie/popular")
    Call<DiscoverMovieResponse> getPopularMovies(
            @Query("api_key") String apiKey);
}
