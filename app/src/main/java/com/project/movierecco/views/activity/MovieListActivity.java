package com.project.movierecco.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.project.models.MovieResultsDiscover;
import com.project.models.Result;
import com.project.movierecco.MovieReccoApplication;
import com.project.movierecco.R;
import com.project.movierecco.adapters.MovieListAdapter;
import com.project.mvp.presenters.MovieListPresenter;
import com.project.mvp.views.IMovieListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anandmishra on 31/05/17.
 */

public class MovieListActivity extends AppCompatActivity implements IMovieListView {


    @BindView (R.id.movie_list_rv)
    RecyclerView mMovieListRv;

    @BindView (R.id.movie_list_toolbar)
    Toolbar mToolbar;

    @BindView (R.id.search_movie_et)
    EditText mSearchEditText;

    @BindView (R.id.search_lin_layout)
    LinearLayout mSearchLinearLayout;


    @BindView (R.id.load_more_progress_bar)
    ProgressBar mProgressLoadMoreItems;

    private String genres;
    private String type;
    private int pageNumber = 0;
    private MovieListAdapter movieListAdapter;

    private List<Result> mOriginalList;
    private List<Result> mFilteredList;


    public static final String INTENT_GENRES_STRING = "INTENT_GENRES_STRING";
    public static final String INTENT_DISCOVER_TYPE = "INTENT_DISCOVER_TYPE";


    @Inject
    MovieListPresenter mMovieListPresenter;
    private int mTotalPages;
    private int mTotalResults;
    boolean isLoaderVisible;
    private boolean isSearchGoingOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            genres = intent.getExtras().getString(INTENT_GENRES_STRING);
            type = intent.getExtras().getString(INTENT_DISCOVER_TYPE);
        }
        initializeDependencyInjection();
        mMovieListPresenter.start();
        mMovieListPresenter.setMovieListView(this);

        initUi();

        loadMoreItems();

        mMovieListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mMovieListRv.getLayoutManager();

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                int totalVisibleItems = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();

                if (((firstVisibleItem + totalVisibleItems) >= totalItemCount)
                        && !isSearchGoingOn && !isLoaderVisible && firstVisibleItem >= 0 && totalItemCount <= mTotalResults && pageNumber <= mTotalPages) {
                    loadMoreItems();
                }

            }
        });
    }

    private void loadMoreItems() {
        pageNumber++;
        mProgressLoadMoreItems.setVisibility(View.VISIBLE);
        isLoaderVisible = true;
        mMovieListPresenter.getMovieListFromGenres(type, genres, pageNumber);
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
        mOriginalList = new ArrayList<>();
        mFilteredList = new ArrayList<>();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.movie_list_text));
        movieListAdapter = new MovieListAdapter(this, mFilteredList);
        mMovieListRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMovieListRv.setAdapter(movieListAdapter);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void initializeDependencyInjection() {
        ((MovieReccoApplication) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMovieListPresenter.stop();
    }

    @Override
    public void addDiscoverMovieLists(MovieResultsDiscover movieResultsDiscover) {
        if (mSearchLinearLayout.getVisibility() == View.GONE) {
            mSearchLinearLayout.setVisibility(View.VISIBLE);
        }
        mProgressLoadMoreItems.setVisibility(View.GONE);
        isLoaderVisible = false;
        mTotalPages = movieResultsDiscover.getTotalPages();
        mTotalResults = movieResultsDiscover.getTotalResults();
        mOriginalList.addAll(movieResultsDiscover.getResults());
        mFilteredList.addAll(movieResultsDiscover.getResults());
        movieListAdapter.notifyDataSetChanged();
    }

    public void filterData(String query) {
        mFilteredList.clear();
        if (query.length() != 0) {
            isSearchGoingOn = true;
            for (Result result : mOriginalList) {
                if (result.getOriginalTitle().toLowerCase().startsWith(query.toLowerCase())) {
                    mFilteredList.add(result);
                }
            }
        }
        else {
            isSearchGoingOn = false;
            mFilteredList.addAll(mOriginalList);
        }
        movieListAdapter.notifyDataSetChanged();
    }
}
