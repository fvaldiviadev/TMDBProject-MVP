package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Interactors;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories.MoviesRepository;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;

import java.util.List;

import retrofit2.Response;

public class PopularMoviesInteractor implements PopularMoviesContract.Interactor,MoviesRepository.PopularMoviesRepositoryListener{

    PopularMovieInteractorListener listener;

    public PopularMoviesInteractor(PopularMovieInteractorListener listener){
        this.listener=listener;
    }

    @Override
    public void requestPopularMovieList(int page) {


        MoviesRepository repository=MoviesRepository.getInstance();

        repository.requestPopularMovieList(page);

    }

    @Override
    public void onResponseOK(List<PopularMovie> newPopularMovieList) {
        listener.onSuccess(newPopularMovieList);
    }
}
