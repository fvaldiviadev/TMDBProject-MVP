package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories;

import android.graphics.Movie;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.DAO.MoviesDAO;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.TheMovieDB_MovieService;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by francisco.valdivia on 16/08/2018.
 */

public class MoviesRepository implements MoviesDAO.OnResponseRequestPopularMovies {

    private static MoviesRepository repository;
    private MoviesDAO moviesDAO;

    private MoviesRepository(){
        moviesDAO=new MoviesDAO(this);
    };

    public static  MoviesRepository getInstance(){
        if(repository==null){
            repository=new MoviesRepository();
        }
        return repository;
    }

    public void requestPopularMovieList(int page) {


        moviesDAO.requestPopularMovieList(page);

    }

    @Override
    public void onResponseOK(Response<PopularMoviesFeed> response) {

        PopularMoviesFeed data = response.body();


        List<PopularMovie> newPopularMovieList = data.getPopularMovies();

    }

    public interface PopularMoviesRepositoryListener {
        void onResponseOK(List<PopularMovie> newPopularMovieList);
    }

}
