package com.project.mvp.presenters;

import android.util.Log;

import com.project.models.ArrGenre;
import com.project.models.MovieResultsTopRated;
import com.project.mvp.views.IMainActivityView;
import com.project.network.views.MovieDbApiInterface;
import com.project.utils.Constants;
import com.project.utils.storage.SharedPreferencesManager;
import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anandmishra on 15/11/16.
 */

public class MainActivityPresenter extends Presenter {

    MovieDbApiInterface movieDbApiInterface;
    IMainActivityView mainActivityView;
    SharedPreferencesManager mSharedPreferencesManager;


    @Inject
    MainActivityPresenter(MovieDbApiInterface movieDbApiInterface , SharedPreferencesManager sharedPreferencesManager) {
        this.mSharedPreferencesManager = sharedPreferencesManager;
        this.movieDbApiInterface = movieDbApiInterface;
    }

    public void setMainActivityView(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void requestTopRatedMovieList() {

        if (mSharedPreferencesManager != null ){
            Log.d("Dagger2","PrefsManager is not null");
        }

        movieDbApiInterface.getMoviesList(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResultsTopRated>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivityView.showMessage("Error");
                    }

                    @Override
                    public void onNext(MovieResultsTopRated movieResultsTopRated) {
                        mainActivityView.showMessage(movieResultsTopRated.getResults().get(4).getTitle());
                    }
                });
    }

    public void getGenreListAndInflateDataToUi(){

        movieDbApiInterface.getGenreList(Constants.API_KEY,Constants.LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrGenre>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArrGenre arrGenre) {
                        mainActivityView.initializeGenreList(arrGenre);
                    }
                });
    }
}
