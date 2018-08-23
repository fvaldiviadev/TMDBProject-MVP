package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.Interactor;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories.MoviesRepository;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.SearchContract;

import java.util.List;

public class SearchInteractor implements SearchContract.Interactor,MoviesRepository.ResponseRequestSearchRepository {

    ResponseRequestSearchInteractor listener;
    MoviesRepository repository;

    private int totalPages;

    int page;
    String currentSearch;


    public SearchInteractor(ResponseRequestSearchInteractor listener){
        this.listener=listener;

        repository= MoviesRepository.getInstance();
        repository.setResponseRequestSearchRepository(this);

        page=1;
        currentSearch = "";
    }

    @Override
    public void requestSearch(String searchText, int searchPage, boolean firstSearch) {
        listener.hideList(false);
        page=searchPage;
        
        repository.requestSearch(searchText,searchPage,firstSearch);

    }

    @Override
    public void requestLoadMoreMovies(String searchText) {
        int nextPage = page + 1;
        if (nextPage < totalPages) {
            requestSearch(searchText,nextPage, false);
        }
    }

    @Override
    public void onKeySearch(String newSearch) {
            if (!currentSearch.equals(newSearch) && newSearch.length() > 1) {
                requestSearch(newSearch,1, true);
                currentSearch = newSearch;
            }

    }

    @Override
    public void onResponseOKSearchRepository(List<FoundMovie> newPopularMovieList, int totalPages) {

        this.totalPages=totalPages;

        listener.hideList(true);

        listener.onSuccessInteractor(newPopularMovieList);
    }

    @Override
    public void onFailureSearchRepository(int responseCode, String responseMessage) {

        listener.hideList(true);
        listener.onFailureInteractor("Code error: "+responseCode+" " +responseMessage);
    }

    @Override
    public void clearList() {
        listener.clearList();
    }
}
