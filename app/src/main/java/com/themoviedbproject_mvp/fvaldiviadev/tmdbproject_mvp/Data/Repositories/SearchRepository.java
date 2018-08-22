package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.DAO.MoviesDAO;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.SearchResults;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.SearchContract;

import java.util.List;

import retrofit2.Response;

public class SearchRepository implements MoviesDAO.ResponseRequestSearchDAO {

    ResponseRequestSearchRepository listener;

    MoviesDAO moviesDAO;

    boolean firstSearch;

    public SearchRepository(ResponseRequestSearchRepository listener){
        this.listener=listener;

        moviesDAO=new MoviesDAO(this);

    }


    public void requestSearch(String searchText, int searchPage,  boolean firstSearch) {

        moviesDAO.requestSearch(searchText,searchPage);

        this.firstSearch=firstSearch;
    }

    @Override
    public void onResponseDAO(Response<SearchResults> response) {
        switch (response.code()) {
            case 200:

                SearchResults data = response.body();

                if (firstSearch) {
                    listener.clearList();
                }

                int totalPages = data.getTotalPages();

                List<FoundMovie> newFoundMovieList = data.getResults();

                listener.onResponseOKRepository(newFoundMovieList,totalPages);

                break;
            default:
                listener.onFailureRepository(response.code(),response.message());

                break;
        }
    }

    @Override
    public void onFailureDAO(String error) {
        listener.onFailureRepository(0,error);
    }

    public interface ResponseRequestSearchRepository {
        void onResponseOKRepository(List<FoundMovie> newPopularMovieList, int totalPages);
        void onFailureRepository(int responseCode,String responseMessage);
        void clearList();
    }
}
