package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.Presenter;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Models.SearchResults;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Network.TheMovieDB_MovieService;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.SearchContract;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;

    private int totalPages;

    int page;

    Call<SearchResults> call;

    String currentSearch = "";

    @Override
    public void setView(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onLoadMoreMovies(String searchText) {
        int nextPage = page + 1;
        if (nextPage < totalPages) {
            search(searchText, nextPage, false);
        }
    }

    @Override
    public void onKeySearch(String newSearch) {
        if (!currentSearch.equals(newSearch) && newSearch.length() > 1) {
            search(newSearch, 1, true);
            currentSearch = newSearch;
        }
    }

    public void search(String searchText, int searchPage, final boolean firstSearch) {
        view.hideList(false);

        if (call != null && call.isExecuted()) {
            call.cancel();
        }
        page = searchPage;
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
        data.put("query", searchText);
        data.put("page", String.valueOf(page));
        call = theMovieDBMovieService.getSearchResults(data);


        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                switch (response.code()) {
                    case 200:
                        view.hideList(true);

                        SearchResults data = response.body();

                        if (firstSearch) {
                            view.clearList();
                        }

                        totalPages = data.getTotalPages();

                        List<FoundMovie> newFoundMovieList = data.getResults();
                        for (int i = 0; i < newFoundMovieList.size(); i++) {
                            view.addToList(newFoundMovieList.get(i));
                            view.setLoading(false);
                        }

                        break;
                    case 401:
                        view.hideList(true);
                        break;
                    default:
                        view.showError(" - Error: " + response.code() + " - " + response.message() + " : " + call.request().url().url());

                        break;
                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }
}
