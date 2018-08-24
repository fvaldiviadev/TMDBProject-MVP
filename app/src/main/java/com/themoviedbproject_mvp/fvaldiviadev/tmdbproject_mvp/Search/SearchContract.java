package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;

import java.util.List;

public interface SearchContract {

    interface View{
        void addList(List<FoundMovie> newFoundMovieList);
        void clearList();
        void setLoading(boolean loading);
        void showError(String error);
        void hideList(boolean hide);
        interface OnLoadMoreSearchMoviesListener {
            void onLoadMoreMovies();
        }

    }

    interface Presenter{
        void search(String searchText, int searchPage, final boolean firstSearch);
        void loadMoreMovies(String searchText);
        void onKeySearch(String newSearch);
    }

    interface Interactor{
        void requestSearch(String searchText, int searchPage, final boolean firstSearch);
        void requestLoadMoreMovies(String searchText);
        void onKeySearch(String currentSearch);
        interface ResponseRequestSearchInteractor{
            void onSuccessInteractor(List<FoundMovie> foundMovieList);
            void onFailureInteractor(String error);
            void hideList(boolean hide);
            void clearList();
        }
    }
}
