package com.project.room.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.project.room.database.AppDatabase;
import com.project.room.models.MovieModel;

import java.util.List;

/**
 * Created by anandmishra on 11/08/17.
 */

public class MovieListViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;
    private final LiveData<List<MovieModel>> itemAndMovieList;

    public MovieListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        itemAndMovieList = appDatabase.itemAndMovieModel().getAllMovieModels();
    }

    public LiveData<List<MovieModel>> getItemAndMovieList() {
        return itemAndMovieList;
    }

    public void addMovie(MovieModel movieModel) {
        appDatabase.itemAndMovieModel().addMovie(movieModel);
    }
}
