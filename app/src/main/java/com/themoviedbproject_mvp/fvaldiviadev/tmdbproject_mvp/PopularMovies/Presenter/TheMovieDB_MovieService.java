package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Presenter;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.SearchResults;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface TheMovieDB_MovieService {
    @GET("/3/movie/popular")
    Call<PopularMoviesFeed> getData(
            @QueryMap Map<String, String> parameters
    );

    @GET("/3/search/movie")
    Call<SearchResults> getSearchResults(
            @QueryMap Map<String, String> parameters
    );

}
