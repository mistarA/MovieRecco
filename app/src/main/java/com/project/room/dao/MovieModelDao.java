package com.project.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.project.room.models.MovieModel;
import com.project.room.utils.DateConverter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


/**
 * Created by anandmishra on 11/08/17.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface MovieModelDao {

    @Query ("select * from MovieModel")
    LiveData<List<MovieModel>> getAllMovieModels();

    @Insert(onConflict = REPLACE)
    void addMovie(MovieModel movieModel);

    @Query("select * from MovieModel where name = :name")
    LiveData<MovieModel> getMovieByName(String name);
}
