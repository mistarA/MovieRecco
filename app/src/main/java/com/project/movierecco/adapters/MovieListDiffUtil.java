package com.project.movierecco.adapters;

import android.support.v7.util.DiffUtil;

import com.project.models.Result;

import java.util.List;

/**
 * Created by anandmishra on 07/10/17.
 */

public class MovieListDiffUtil extends DiffUtil.Callback {

    private List<Result> mMovieOldResults;
    private List<Result> mMovieNewResults;

    public MovieListDiffUtil(List<Result> mMovieOldResults, List<Result> mMovieNewResults) {
        this.mMovieOldResults = mMovieOldResults;
        this.mMovieNewResults = mMovieNewResults;
    }

    @Override
    public int getOldListSize() {
        return mMovieOldResults.size();
    }

    @Override
    public int getNewListSize() {
        return mMovieNewResults.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mMovieNewResults.get(newItemPosition).equals(mMovieOldResults.get(oldItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mMovieNewResults.get(newItemPosition).getId().equals(mMovieOldResults.get(oldItemPosition).getId());
    }
}
