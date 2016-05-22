package com.nanodegree.popularmovies.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.nanodegree.popularmovies.adapter.MovieListAdapter;
import com.nanodegree.popularmovies.common.SortingType;
import com.nanodegree.popularmovies.model.DiscoverMovieResponse;
import com.nanodegree.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

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
    private List<Movie> mMovieList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private MovieListAdapter mAdapter;
    private int mPage = 1;
    private GridLayoutManager gridManager;
    private boolean loading = true;

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

        setUpToolbar();
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {

        mAdapter = new MovieListAdapter(mMovieList);
        mMovieRecyclerView.setHasFixedSize(true);
        gridManager = new GridLayoutManager(getContext(), 2);
        mMovieRecyclerView.setLayoutManager(gridManager);

        mMovieRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    int visibleItemCount = gridManager.getChildCount();
                    int totalItemCount = gridManager.getItemCount();
                    int pastVisibleItems = gridManager.findFirstVisibleItemPosition();

                    if (loading)
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            mPage++;
                            loadList();
                        }
                }
            }
        });
    }

    private void setUpToolbar() {
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.inflateMenu(R.menu.menu_main_fragment);
        mToolbar.setOnMenuItemClickListener(this);
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
                .getPopularMovies(MOVIE_SORTING_TYPE.toString(), mPage, BuildConfig.MOVIE_DP_API)
                .enqueue(new Callback<DiscoverMovieResponse>() {
                             @Override
                             public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {

                                 if (response.isSuccessful()) {
                                     mMovieList.addAll(response.body().getResults());
                                     mPage = response.body().getPage();
                                     mAdapter.notifyDataSetChanged();
                                 }
                                 loading = true;
                                 stopRefresh();
                             }

                             @Override
                             public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
                                 stopRefresh();
                             }
                         }
                );
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popularity:
                MOVIE_SORTING_TYPE = SortingType.POPULAR;
                loadSortingMovie();
                return true;
            case R.id.action_sort_rating:
                MOVIE_SORTING_TYPE = SortingType.TOP_RATED;
                loadSortingMovie();
                return true;
        }

        return false;
    }

    private void loadSortingMovie() {
        mMovieList.clear();
        mAdapter.notifyDataSetChanged();
        startRefresh();
        mPage = 1;
        loadList();
    }

    @Override
    public void onRefresh() {
        loadList();
    }

    /**
     * start refreshing of swipe refresh layout
     */
    private void startRefresh() {
        mMovieRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    /**
     * stop refreshing of swipe refresh layout
     */
    private void stopRefresh() {
        mMovieRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
