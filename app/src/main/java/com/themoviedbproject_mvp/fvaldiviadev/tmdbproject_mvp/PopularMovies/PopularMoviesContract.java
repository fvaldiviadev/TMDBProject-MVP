package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Models.PopularMoviesFeed;

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
        void setView(View view);
        void setInteractor();
        void loadPopularMovieList();
        void onLoadMoreMovies();
        void startSearch();
        void onResponseLoadPopularMovieList(Response<PopularMoviesFeed> response);
    }

    interface Interactor{
        void setPresenter(Presenter presenter);
        void requestPopularMovieList(int page);
    }
}
