package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search;

import android.view.KeyEvent;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;

import java.util.List;

public interface SearchContract {

    interface View{
        void addToList(FoundMovie foundMovie);
        void clearList();
        void setLoading(boolean loading);
        void showError(String error);
        void hideList(boolean hide);

    }

    interface Presenter{
        void search(String searchText, int searchPage, final boolean firstSearch);
        void loadMoreMovies(String searchText);
        void onKeySearch(KeyEvent keyEvent,String currentSearch);
    }

    interface Interactor{
        void requestSearch(String searchText, int searchPage, final boolean firstSearch);
        void requestLoadMoreMovies(String searchText);
        void onKeySearch(KeyEvent keyEvent,String currentSearch);
        interface ResponseRequestSearchInteractor{
            void onSuccessInteractor(List<FoundMovie> foundMovieList);
            void onFailureInteractor(String error);
        }
    }
}
