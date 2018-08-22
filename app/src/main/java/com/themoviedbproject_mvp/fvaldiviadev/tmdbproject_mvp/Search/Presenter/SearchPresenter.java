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



    public SearchPresenter(SearchContract.View view){

        this.view=view;

        interactor=new SearchInteractor(this);
    }


    @Override
    public void search(String searchText, int searchPage, final boolean firstSearch) {
        interactor.requestSearch(searchText,searchPage,firstSearch);
    }

    @Override
    public void loadMoreMovies(String searchText) {
        interactor.requestLoadMoreMovies(searchText);
    }

    @Override
    public void onKeySearch(KeyEvent keyEvent,String newSearch) {
        interactor.onKeySearch(keyEvent,newSearch);
    }

    @Override
    public void onSuccessInteractor(List<FoundMovie> foundMovieList) {

        view.addList(foundMovieList);
    }

    @Override
    public void onFailureInteractor(String error) {
        view.showError(" - Error: " + error);
    }

    @Override
    public void hideList(boolean hide) {
        view.hideList(hide);
    }

    @Override
    public void clearList() {
        view.clearList();
    }
}
