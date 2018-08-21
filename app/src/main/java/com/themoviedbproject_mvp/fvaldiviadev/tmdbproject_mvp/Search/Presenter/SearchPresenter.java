package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.Presenter;

import android.util.Log;
import android.view.KeyEvent;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.SearchResults;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.TheMovieDB_MovieService;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.Interactor.SearchInteractor;
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

public class SearchPresenter implements SearchContract.Presenter,SearchContract.Interactor.ResponseRequestSearchInteractor {

    private SearchContract.View view;
    private SearchContract.Interactor interactor;

    private int totalPages;

    int page;

    Call<SearchResults> call;

    String currentSearch = "";


    public void SearchPresenter(SearchContract.View view){

        this.view=view;

        interactor=new SearchInteractor(this);
    }


    @Override
    public void search(String searchText, int searchPage, final boolean firstSearch) {

        interactor.requestSearch(searchText,searchPage,firstSearch);



    }

    @Override
    public void loadMoreMovies(String searchText) {
        int nextPage = page + 1;
        if (nextPage < totalPages) {
            search(searchText,nextPage, false);
        }
    }

    @Override
    public void onKeySearch(KeyEvent keyEvent,String newSearch) {
        if (keyEvent.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            if (!currentSearch.equals(newSearch) && newSearch.length() > 1) {
                search(newSearch,1, true);
                currentSearch = newSearch;
            }
        }
    }

    @Override
    public void onSuccessInteractor(List<FoundMovie> foundMovieList) {

    }

    @Override
    public void onFailureInteractor(String error) {

    }
}
