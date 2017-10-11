package com.project.movierecco.adapters;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.models.Result;
import com.project.movierecco.R;
import com.project.utils.Constants;
import com.project.utils.GenreIdMapper;
import com.project.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anandmishra on 13/05/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {


    private Context context;
    private List<Result> mMovieResultsDiscovers;

    public MovieListAdapter(Context context, List<Result> mResultsList) {
        mMovieResultsDiscovers = new ArrayList<>();
        this.mMovieResultsDiscovers = mResultsList;
        this.context = context;
    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieItemViewHolder holder, final int position) {
        final Result result = mMovieResultsDiscovers.get(position);
        holder.mItemName.setText(result.getOriginalTitle());
        holder.mItemGenre.setText(GenreIdMapper.getGenreNamesFromIds(result.getGenreIds()));
        holder.mItemRating.setText(result.getVoteAverage().toString());
        if (result.getPosterPath() != null) {
            Picasso.with(context)
                    .load(Constants.imageBseUrl.concat(result.getPosterPath()))
                    .transform(new RoundedTransformation(50,4))
                    .placeholder(R.drawable.moviecollage)
                    .fit()
                    .into(holder.mPoster);
        }
    }

    @Override
    public int getItemCount() {
        return mMovieResultsDiscovers != null ? mMovieResultsDiscovers.size() : 0;
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder {

        @BindView (R.id.movie_poster)
        ImageView mPoster;

        @BindView (R.id.item_name)
        TextView mItemName;

        @BindView (R.id.item_genre)
        TextView mItemGenre;

        @BindView (R.id.item_rating)
        TextView mItemRating;

        public MovieItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void updateItems(final List<Result> newData){

        Log.d("DiffUtil", String.valueOf(newData.get(0)) + String.valueOf(newData.get(newData.size() - 1)));
        Observable.fromCallable(new Callable<DiffUtil.DiffResult>() {
            @Override
            public DiffUtil.DiffResult call() throws Exception {
                return getDiffResult(newData);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<DiffUtil.DiffResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DiffUtil.DiffResult diffResult) {
                diffResult.dispatchUpdatesTo(MovieListAdapter.this);
            }
        });

    }

    public DiffUtil.DiffResult getDiffResult(List<Result> newData){
        return DiffUtil.calculateDiff(new MovieListDiffUtil(mMovieResultsDiscovers, newData));
    }
}
