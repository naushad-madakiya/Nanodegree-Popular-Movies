package com.nanodegree.popularmovies;

import com.nanodegree.popularmovies.model.DiscoverMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Naushad on 21/12/15.
 */
public interface RestApi {

    @GET("/3/movie/{type}")
    Call<DiscoverMovieResponse> getPopularMovies(@Path("type") String sortingType,
                                                 @Query("page") int page,
                                                 @Query("api_key") String apiKey);
}
