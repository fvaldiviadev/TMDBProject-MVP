package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;

import java.util.List;

public interface PopularMoviesContract {

    interface View{
        void addToList(PopularMovie popularMovie);
        void addList(List<PopularMovie> newPopularMovieList);
        void showError(String error);
        void navigateToSearchActivity();
        void hideList(boolean hide);
        interface OnLoadMorePopularMoviesListener {
            void onLoadMoreMovies();
        }

    }

    interface Presenter{
        void loadPopularMovieList();
        void loadMoreMovies();
        void startSearch();
    }

    interface Interactor{
        void requestPopularMovieList();
        void requestLoadMoreMovies();
        interface ResponseRequestPopularMovieInteractor {
            void onSuccessInteractor(List<PopularMovie> newPopularMovieList);
            void onFailureInteractor(String error);
            void hideList(boolean hide);
        }
    }
}
