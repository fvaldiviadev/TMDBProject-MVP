package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Presenter;

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

public class PopularMoviesPresenter implements PopularMoviesContract.Presenter {

    private PopularMoviesContract.View view;
    private int page;
    private int totalPages;


    @Override
    public void setView(PopularMoviesContract.View view) {
        this.view=view;

        page=1;
    }

    @Override
    public void loadPopularMovieList() {


        view.addToList(null);


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
                switch (response.code()) {
                    case 200:

                        //   remove progress item
                        view.removeLastElement();

                        PopularMoviesFeed data = response.body();

                        totalPages = data.getTotalPages();

                        List<PopularMovie> newPopularMovieList = data.getPopularMovies();
                        for (int i = 0; i < newPopularMovieList.size(); i++) {
                            view.addToList(newPopularMovieList.get(i));
                        }

                        view.setLoading(false);

                        if (totalPages==0) {
                            view.hideList(true);
                        } else {
                            view.hideList(false);
                        }

                        break;
                    case 401:
                        break;
                    default:
                        view.showError(" - Error: " + response.code() + " - " + response.message() + " : " + call.request().url().url());
                        break;
                }
            }

            @Override
            public void onFailure(Call<PopularMoviesFeed> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    @Override
    public void onLoadMoreMovies() {
        int nextPage = page + 1;
        if (nextPage < totalPages) {
            page=nextPage;
            loadPopularMovieList();
        }
    }

    @Override
    public void startSearch() {
        view.navigateToSearchActivity();
    }
}
