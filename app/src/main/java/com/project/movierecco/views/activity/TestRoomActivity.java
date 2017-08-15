package com.project.movierecco.views.activity;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.movierecco.R;
import com.project.room.models.MovieModel;
import com.project.room.viewmodels.MovieListViewModel;

import java.util.Date;
import java.util.List;

/**
 * Created by anandmishra on 11/08/17.
 */

public class TestRoomActivity extends LifecycleActivity {

    private MovieListViewModel viewModel;

    Button mRoomDataButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_room_activity_main);
        mRoomDataButton = (Button) findViewById(R.id.room_get_data);
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);

        viewModel.addMovie(new MovieModel("Dunkirk", new Date()));
        viewModel.addMovie(new MovieModel("Arrival", new Date()));
        viewModel.addMovie(new MovieModel("Dunkirk", new Date()));

        mRoomDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getItemAndMovieList().observe(TestRoomActivity.this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(@Nullable List<MovieModel> movieModels) {
                        if (movieModels != null) {
                            for (MovieModel movieModel : movieModels) {
                                Toast.makeText(TestRoomActivity.this, movieModel.getName(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}
