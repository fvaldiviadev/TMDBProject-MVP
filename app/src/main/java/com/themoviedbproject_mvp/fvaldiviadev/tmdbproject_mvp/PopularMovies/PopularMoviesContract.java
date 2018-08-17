package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;

import java.util.List;

import retrofit2.Response;

public interface PopularMoviesContract {

    interface View{
        void addToList(PopularMovie popularMovie);
        void removeLastElement();
        void setLoading(boolean loading);
        void showError(String error);
        void navigateToSearchActivity();
        void hideList(boolean hide);

    }

    interface Presenter{
        void loadPopularMovieList();
        void onLoadMoreMovies();
        void startSearch();
        void onResponseLoadPopularMovieList(Response<PopularMoviesFeed> response);
    }

    interface Interactor{
        void requestPopularMovieList(int page);
        interface PopularMovieInteractorListener{
            void onSuccess(List<PopularMovie> newPopularMovieList);
        }
    }
}
