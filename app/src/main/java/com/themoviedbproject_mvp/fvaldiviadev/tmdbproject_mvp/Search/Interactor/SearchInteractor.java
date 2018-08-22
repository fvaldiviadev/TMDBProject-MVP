package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.Interactor;

import android.util.Log;
import android.view.KeyEvent;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.SearchResults;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.TheMovieDB_MovieService;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories.MoviesRepository;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories.SearchRepository;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;
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

public class SearchInteractor implements SearchContract.Interactor,SearchRepository.ResponseRequestSearchRepository {

    ResponseRequestSearchInteractor listener;
    SearchRepository repository;

    private int totalPages;

    int page;
    String currentSearch;


    public SearchInteractor(ResponseRequestSearchInteractor listener){
        this.listener=listener;
        repository=new SearchRepository(this);

        page=1;
        currentSearch = "";
    }

    @Override
    public void requestSearch(String searchText, int searchPage, boolean firstSearch) {
        listener.hideList(false);
        page=searchPage;
        repository.requestSearch(searchText,searchPage,firstSearch);

    }

    @Override
    public void requestLoadMoreMovies(String searchText) {
        int nextPage = page + 1;
        if (nextPage < totalPages) {
            requestSearch(searchText,nextPage, false);
        }
    }

    @Override
    public void onKeySearch(KeyEvent keyEvent, String newSearch) {
        if (keyEvent.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            if (!currentSearch.equals(newSearch) && newSearch.length() > 1) {
                requestSearch(newSearch,1, true);
                currentSearch = newSearch;
            }
        }
    }

    @Override
    public void onResponseOKRepository(List<FoundMovie> newPopularMovieList, int totalPages) {

        this.totalPages=totalPages;

        listener.hideList(true);

        listener.onSuccessInteractor(newPopularMovieList);
    }

    @Override
    public void onFailureRepository(int responseCode, String responseMessage) {

        listener.hideList(true);
        listener.onFailureInteractor("Code error: "+responseCode+" " +responseMessage);
    }

    @Override
    public void clearList() {
        listener.clearList();
    }
}
