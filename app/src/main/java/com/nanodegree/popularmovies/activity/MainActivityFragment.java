package com.nanodegree.popularmovies.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.popularmovies.App;
import com.nanodegree.popularmovies.BuildConfig;
import com.nanodegree.popularmovies.R;
import com.nanodegree.popularmovies.model.DiscoverMovieResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements Toolbar.OnMenuItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.movie_recycler_view)
    RecyclerView mMovieRecyclerView;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    SortingType MOVIE_SORTING_TYPE = SortingType.POPULAR;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.inflateMenu(R.menu.menu_main_fragment);
        mToolbar.setOnMenuItemClickListener(this);

        mMovieRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadList();
    }

    private void loadList() {
        App.getApi()
                .getPopularMovies(MOVIE_SORTING_TYPE.toString(), BuildConfig.MOVIE_DP_API)
                .enqueue(new Callback<DiscoverMovieResponse>() {
                    @Override
                    public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popularity:
                MOVIE_SORTING_TYPE = SortingType.POPULAR;
                loadList();
                return true;
            case R.id.action_sort_rating:
                MOVIE_SORTING_TYPE = SortingType.TOP_RATED;
                loadList();
                return true;
        }

        return false;
    }

    @Override
    public void onRefresh() {

    }


    public enum SortingType {
        POPULAR("popular"),
        TOP_RATED("top_rated");

        private final String text;

        /**
         * @param text
         */
        SortingType(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }
}
