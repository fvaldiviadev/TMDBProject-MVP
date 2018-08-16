package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Interactors;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories.MoviesRepository;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;

import retrofit2.Response;

public class PopularMoviesInteractor implements PopularMoviesContract.Interactor{

    private PopularMoviesContract.Presenter presenter;

    @Override
    public void setPresenter(PopularMoviesContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void requestPopularMovieList(int page) {


        MoviesRepository repository=MoviesRepository.getInstance();


        MoviesRepository.OnResponseRequestPopularMovies onResponseRequestPopularMovies=new MoviesRepository.OnResponseRequestPopularMovies(){

            @Override
            public void onResponseOK(Response<PopularMoviesFeed> response) {
                presenter.onResponseLoadPopularMovieList(response);
            }
        };

        repository.requestPopularMovieList(page,onResponseRequestPopularMovies);

    }
}
