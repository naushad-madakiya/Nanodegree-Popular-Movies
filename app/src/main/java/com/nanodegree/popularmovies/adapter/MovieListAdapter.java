package com.nanodegree.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nanodegree.popularmovies.App;
import com.nanodegree.popularmovies.R;
import com.nanodegree.popularmovies.common.Constant;
import com.nanodegree.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public MovieListAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        Picasso.with(App.getContext())
                .load(Constant.MOVIE_POSTER_URL + movie.getPosterPath())
                .priority(Picasso.Priority.HIGH)
                .error(R.drawable.ic_broken_image_black_48dp)
                .fit()
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView poster;

        public MyViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.title);
        }
    }
}