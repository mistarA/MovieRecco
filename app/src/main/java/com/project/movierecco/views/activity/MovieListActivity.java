package com.project.movierecco.views.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.models.MovieResultsDiscover;
import com.project.movierecco.MovieReccoApplication;
import com.project.movierecco.R;
import com.project.movierecco.adapters.MovieListAdapter;
import com.project.mvp.presenters.MovieListPresenter;
import com.project.mvp.views.IMovieListView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anandmishra on 31/05/17.
 */

public class MovieListActivity extends AppCompatActivity implements IMovieListView{


    @BindView(R.id.movie_list_rv)
    RecyclerView mMovieListRv;

    @BindView(R.id.movie_list_toolbar)
    Toolbar mToolbar;

    private String genres;
    private String type;
    private MovieListAdapter movieListAdapter;

    public static final String INTENT_GENRES_STRING = "INTENT_GENRES_STRING";
    public static final String INTENT_DISCOVER_TYPE = "INTENT_DISCOVER_TYPE";


    @Inject
    MovieListPresenter mMovieListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null){
            genres = intent.getExtras().getString(INTENT_GENRES_STRING);
            type = intent.getExtras().getString(INTENT_DISCOVER_TYPE);
        }
        initializeDependencyInjection();
        mMovieListPresenter.start();
        mMovieListPresenter.setMovieListView(this);

        //ToDo Pagination - Second Parameter
        mMovieListPresenter.getMovieListFromGenres(type,genres, 1);

        initUi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUi() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Move List");
        movieListAdapter = new MovieListAdapter(this);
        mMovieListRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mMovieListRv.setAdapter(movieListAdapter);

    }

    private void initializeDependencyInjection() {
        ((MovieReccoApplication)getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMovieListPresenter.stop();
    }

    @Override
    public void addDiscoverMovieLists(MovieResultsDiscover movieResultsDiscover) {
        movieListAdapter.addAllItems(movieResultsDiscover.getResults());
    }
}
