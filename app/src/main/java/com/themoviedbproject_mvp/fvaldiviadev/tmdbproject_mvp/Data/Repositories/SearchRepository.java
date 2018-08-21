package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.SearchContract;

import java.util.List;

public class SearchRepository  {

    ResponseRequestSearchRepository listener;

    public SearchRepository(ResponseRequestSearchRepository listener){
        this.listener=listener;

    }

    public interface ResponseRequestSearchRepository {
        void onResponseOKRepository(List<FoundMovie> newPopularMovieList, int totalPages);
        void onFailureRepository(int responseCode,String responseMessage);
    }
}
