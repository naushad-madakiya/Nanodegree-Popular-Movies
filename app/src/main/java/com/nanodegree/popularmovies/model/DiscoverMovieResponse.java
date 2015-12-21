package com.nanodegree.popularmovies.model;

import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naushad on 21/12/15.
 */
public class DiscoverMovieResponse implements Parcelable {

    private Integer page;

    private List<Movie> results = new ArrayList<Movie>();

    private Integer totalResults;

    private Integer totalPages;

    protected DiscoverMovieResponse(Parcel in) {
    }

    public static final Creator<DiscoverMovieResponse> CREATOR = new Creator<DiscoverMovieResponse>() {
        @Override
        public DiscoverMovieResponse createFromParcel(Parcel in) {
            return new DiscoverMovieResponse(in);
        }

        @Override
        public DiscoverMovieResponse[] newArray(int size) {
            return new DiscoverMovieResponse[size];
        }
    };

    /**
     * @return The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return The results
     */
    public List<Movie> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Movie> results) {
        this.results = results;
    }

    /**
     * @return The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults The total_results
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
