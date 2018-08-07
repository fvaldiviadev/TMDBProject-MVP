package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies;

public interface PopularMoviesContract {

    interface View{
        void showPopularMovieList();

    }

    interface Presenter{
        void setView(View view);
        void loadPopularMovieList(int page);
    }
}
