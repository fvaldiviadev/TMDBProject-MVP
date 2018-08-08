package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Models.PopularMovie;

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
        void setView(View view);
        void loadPopularMovieList();
        void onLoadMoreMovies();
        void startSearch();
    }
}
