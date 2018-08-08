package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Presenter.OnLoadMoreMoviesListener;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Presenter.TheMovieDB_MovieService;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.R;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.SearchActivity;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularMoviesActivity extends AppCompatActivity implements PopularMoviesContract.View {

    @Inject PopularMoviesContract.Presenter presenter;

    private TextView tvEmptyView;
    private RecyclerView rvPopularMovieList;
    private PopularMovieListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<PopularMovie> popularMovieList;
    private int page;
    private int totalPages;

    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.setView(this);

        loadView();

        presenter.loadPopularMovieList();

        setAdapter();


    }

    private void loadView(){
        tvEmptyView = (TextView) findViewById(R.id.tv_nomovies);
        rvPopularMovieList = (RecyclerView) findViewById(R.id.rv_popularmovielist);
        popularMovieList = new ArrayList<PopularMovie>();
        handler = new Handler();

        rvPopularMovieList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        rvPopularMovieList.setLayoutManager(linearLayoutManager);

        page = 1;
    }

    private void setAdapter() {
        adapter = new PopularMovieListAdapter(popularMovieList, rvPopularMovieList);

        rvPopularMovieList.setAdapter(adapter);

        adapter.setLoading(true);


        if (popularMovieList.isEmpty()) {
            rvPopularMovieList.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            rvPopularMovieList.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

        adapter.setOnLoadMoreMoviesListener(new OnLoadMoreMoviesListener() {
            @Override
            public void onLoadMoreMovies() {
                presenter.onLoadMoreMovies();


            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:



                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void showPopularMovieList() {

    }

    @Override
    public void addToList(PopularMovie popularMovie) {
        popularMovieList.add(popularMovie);
        if (adapter != null) {
            adapter.notifyItemInserted(popularMovieList.size() - 1);
        }
    }

    @Override
    public void removeLastElement() {
        popularMovieList.remove(popularMovieList.size() - 1);
        adapter.notifyItemRemoved(popularMovieList.size());
    }

    @Override
    public void notifyChangesToAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setLoading(boolean loading) {
        adapter.setLoading(loading);
    }

    @Override
    public void showError(String error) {
        tvEmptyView.append(error);
    }
}
