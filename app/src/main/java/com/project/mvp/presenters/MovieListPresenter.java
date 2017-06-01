package com.project.mvp.presenters;

import android.util.Log;

import com.project.models.MovieResultsDiscover;
import com.project.mvp.views.IMovieListView;
import com.project.network.views.MovieDbApiInterface;
import com.project.utils.Constants;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anandmishra on 31/05/17.
 */

public class MovieListPresenter extends Presenter {

    private MovieDbApiInterface movieDbApiInterface;
    private IMovieListView movieListView;

    @Inject
    MovieListPresenter(MovieDbApiInterface movieDbApiInterface){
        this.movieDbApiInterface = movieDbApiInterface;
    }

    public void setMovieListView(IMovieListView iMovieListView){
        this.movieListView = iMovieListView;
    }

    public void getMovieListFromGenres(String genres) {
        compositeSubscription.add(movieDbApiInterface.getMovieDiscoverList(Constants.API_KEY, Constants.LANGUAGE, genres)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResultsDiscover>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.toString());
                    }

                    @Override
                    public void onNext(MovieResultsDiscover movieResultsDiscover) {
                        movieListView.addDiscoverMovieLists(movieResultsDiscover);
                    }
                })
        );
    }

}
