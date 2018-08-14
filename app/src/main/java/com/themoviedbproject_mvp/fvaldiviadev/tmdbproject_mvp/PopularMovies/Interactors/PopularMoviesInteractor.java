package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Interactors;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Network.TheMovieDB_MovieService;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularMoviesInteractor implements PopularMoviesContract.Interactor{

    private PopularMoviesContract.Presenter presenter;

    @Override
    public void setPresenter(PopularMoviesContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void requestPopularMovieList(int page) {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TheMovieDB_MovieService theMovieDBMovieService = retrofit.create(TheMovieDB_MovieService.class);
        Map<String, String> data = new HashMap<>();
        data.put("api_key", Constants.API_KEY);
        data.put("language", Constants.LANGUAGE_GET_REQUEST);
        data.put("page", String.valueOf(page));
        Call<PopularMoviesFeed> call = theMovieDBMovieService.getData(data);

        call.enqueue(new Callback<PopularMoviesFeed>() {
            @Override
            public void onResponse(Call<PopularMoviesFeed> call, Response<PopularMoviesFeed> response) {
                presenter.onResponseLoadPopularMovieList(response);
            }

            @Override
            public void onFailure(Call<PopularMoviesFeed> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }
}
