package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.UI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.R;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.Presenter.SearchPresenter;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.SearchContract;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private SearchContract.Presenter presenter;

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SearchMovieListAdapter adapter;
    private TextView nomoviesfoundTextView;
    private ProgressBar pb_searchlist;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        //TODO usar Dagger2
        presenter = new SearchPresenter(this);

        loadView();

    }

    private void loadView() {
        searchEditText = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.rv_searchmovielist);
        nomoviesfoundTextView = findViewById(R.id.tv_searchnomovies);
        pb_searchlist = findViewById(R.id.pb_searchlist);


        searchEditText.setOnKeyListener(listenerSearchEditText());

        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);

        setAdapter();

    }

    private void setAdapter() {
        adapter = new SearchMovieListAdapter(recyclerView);

        recyclerView.setAdapter(adapter);

        setLoading(true);


        adapter.setOnLoadMoreMoviesListener(new OnLoadMoreSearchMoviesListener() {
            @Override
            public void onLoadMoreMovies() {
                presenter.loadMoreMovies(searchEditText.getText().toString());
            }
        });
    }

    private View.OnKeyListener listenerSearchEditText() {
        View.OnKeyListener listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                presenter.onKeySearch(keyEvent, searchEditText.getText().toString());

                return false;
            }
        };
        return listener;
    }

    @Override
    public void clearList() {
        adapter.clearList();
    }

    @Override
    public void addList(List<FoundMovie> newFoundMovieList) {

        adapter.addList(newFoundMovieList);
        setLoading(false);
    }

    @Override
    public void setLoading(boolean loading) {

        adapter.setLoading(loading);
    }

    @Override
    public void showError(String error) {
        nomoviesfoundTextView.append(error);
    }

    @Override
    public void hideList(boolean hide) {
        if (hide) {
            pb_searchlist.setVisibility(View.GONE);
        } else {
            pb_searchlist.setVisibility(View.VISIBLE);
        }
    }
}
