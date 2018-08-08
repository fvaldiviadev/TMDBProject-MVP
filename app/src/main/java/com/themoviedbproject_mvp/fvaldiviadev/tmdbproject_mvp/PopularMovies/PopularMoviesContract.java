package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Models.PopularMovie;

public interface PopularMoviesContract {

    interface View{
        void showPopularMovieList();
        void addToList(PopularMovie popularMovie);
        void removeLastElement();
        void notifyChangesToAdapter();
        void setLoading(boolean loading);
        void showError(String error);

    }

    interface Presenter{
        void setView(View view);
        void loadPopularMovieList();
        void onLoadMoreMovies();
        void startSearchActivity();
    }
}
