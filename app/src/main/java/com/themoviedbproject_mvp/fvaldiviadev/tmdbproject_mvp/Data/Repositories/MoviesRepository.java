package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.DAO.MoviesDAO;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;

import java.util.List;

import retrofit2.Response;

/**
 * Created by francisco.valdivia on 16/08/2018.
 */

public class MoviesRepository implements MoviesDAO.ResponseRequestPopularMoviesDAO {

    private MoviesDAO moviesDAO;
    private ResponseRequestPopularMoviesRepository listener;

    public MoviesRepository(ResponseRequestPopularMoviesRepository listener){

        this.listener = listener;

        moviesDAO=new MoviesDAO(this);
    };

    public void requestPopularMovieList(int page) {


        moviesDAO.requestPopularMovieList(page);

    }

    @Override
    public void onResponseDAO(Response<PopularMoviesFeed> response) {

        switch (response.code()) {
            case 200:
                PopularMoviesFeed data = response.body();

                List<PopularMovie> newPopularMovieList = data.getPopularMovies();
                int totalPages=data.getTotalPages();

                listener.onResponseOKRepository(newPopularMovieList,totalPages);

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

    public interface ResponseRequestPopularMoviesRepository {
        void onResponseOKRepository(List<PopularMovie> newPopularMovieList,int totalPages);
        void onFailureRepository(int responseCode,String responseMessage);
    }

}
