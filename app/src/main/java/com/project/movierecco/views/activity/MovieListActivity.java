package com.project.movierecco.views.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.project.models.MovieResultsDiscover;
import com.project.movierecco.MovieReccoApplication;
import com.project.movierecco.R;
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

    public static final String INTENT_GENRES_STRING = "INTENT_GENRES_STRING";


    @Inject
    MovieListPresenter mMovieListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null){
            genres = getIntent().getExtras().getString(INTENT_GENRES_STRING);
        }
        initializeDependencyInjection();
        mMovieListPresenter.start();
        mMovieListPresenter.setMovieListView(this);
        mMovieListPresenter.getMovieListFromGenres(genres);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Move Title");
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
        Toast.makeText(this,movieResultsDiscover.getResults().get(0).getOriginalTitle(),Toast.LENGTH_SHORT).show();
    }
}
