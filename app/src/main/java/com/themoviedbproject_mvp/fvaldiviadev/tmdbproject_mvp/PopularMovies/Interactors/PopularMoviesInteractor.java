package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Interactors;

import android.graphics.Movie;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories.MoviesRepository;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;

import java.util.List;

public class PopularMoviesInteractor implements PopularMoviesContract.Interactor,MoviesRepository.ResponseRequestPopularMoviesRepository {

    ResponseRequestPopularMovieInteractor listener;
    MoviesRepository repository;

    int page;
    int totalPages;

    public PopularMoviesInteractor(ResponseRequestPopularMovieInteractor listener){
        this.listener=listener;

        repository= MoviesRepository.getInstance();
        repository.setResponseRequestPopularMoviesRepository(this);

        page=1;
    }

    @Override
    public void requestPopularMovieList() {

        repository.requestPopularMovieList(page);

    }

    @Override
    public void requestLoadMoreMovies() {
        int nextPage = page + 1;
        if (nextPage < totalPages) {
            page=nextPage;
            requestPopularMovieList();
        }
    }

    @Override
    public void onResponseOKPopularMoviesRepository(List<PopularMovie> newPopularMovieList,int totalPages) {

        this.totalPages=totalPages;

        if (totalPages==0) {
            listener.hideList(true);
        } else {
            listener.hideList(false);
        }

        listener.onSuccessInteractor(newPopularMovieList);
    }

    @Override
    public void onFailurePopularMoviesRepository(int responseCode, String responseMessage) {
        listener.onFailureInteractor("Code error: "+responseCode+" " +responseMessage);
    }
}
