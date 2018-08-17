package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Presenter;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories.MoviesRepository;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Interactors.PopularMoviesInteractor;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;

import java.util.List;

import retrofit2.Response;

public class PopularMoviesPresenter implements PopularMoviesContract.Presenter,PopularMoviesContract.Interactor.PopularMovieInteractorListener {

    private PopularMoviesContract.View view;
    private PopularMoviesContract.Interactor interactor;
    private int page;
    private int totalPages;

    public PopularMoviesPresenter(PopularMoviesContract.View view){
        this.view=view;
        interactor=new PopularMoviesInteractor(this);

        page=1;
    }


    @Override
    public void loadPopularMovieList() {


        interactor.requestPopularMovieList(page);

    }

    @Override
    public void onLoadMoreMovies() {
        int nextPage = page + 1;
        if (nextPage < totalPages) {
            page=nextPage;
            loadPopularMovieList();
        }
    }

    @Override
    public void startSearch() {
        view.navigateToSearchActivity();
    }

    @Override
    public void onResponseLoadPopularMovieList(Response<PopularMoviesFeed> response) {
        switch (response.code()) {
            case 200:

                               //   remove progress item
                view.removeLastElement();

                PopularMoviesFeed data = response.body();

                totalPages = data.getTotalPages();

                List<PopularMovie> newPopularMovieList = data.getPopularMovies();
                for (int i = 0; i < newPopularMovieList.size(); i++) {
                    view.addToList(newPopularMovieList.get(i));
                }

                view.setLoading(false);

                if (totalPages==0) {
                    view.hideList(true);
                } else {
                    view.hideList(false);
                }

                break;
            case 401:
                break;
            default:
                view.showError(" - Error: " + response.code() + " - " + response.message());
                break;
        }
    }

    @Override
    public void onSuccess(List<PopularMovie> newPopularMovieList) {

    }
}
