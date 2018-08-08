package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search;

import android.view.KeyEvent;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Models.FoundMovie;

public interface SearchContract {

    interface View{
        void addToList(FoundMovie foundMovie);
        void clearList();
        void setLoading(boolean loading);
        void showError(String error);
        void hideList(boolean hide);

    }

    interface Presenter{
        void setView(View view);
        void search(String searchText, int searchPage, final boolean firstSearch);
        void onLoadMoreMovies(String searchText);
        void onKeySearch(KeyEvent keyEvent,String currentSearch);
    }
}
