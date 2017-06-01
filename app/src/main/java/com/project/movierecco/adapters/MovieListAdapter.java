package com.project.movierecco.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.models.Genre;
import com.project.models.MovieResultsDiscover;
import com.project.models.Result;
import com.project.movierecco.R;
import com.project.utils.AspectRatioImageView;
import com.project.utils.Constants;
import com.project.utils.GenreIdMapper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anandmishra on 13/05/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {


    private Context context;
    private List<Result> mMovieResultsDiscovers;

    public MovieListAdapter(Context context) {
        mMovieResultsDiscovers = new ArrayList<>();
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
        Picasso.with(context)
                .load(Constants.imageBseUrl.concat(result.getPosterPath()))
                .placeholder(R.drawable.moviecollage)
                .fit()
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mMovieResultsDiscovers != null ? mMovieResultsDiscovers.size() : 0;
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.movie_poster)
        AspectRatioImageView mPoster;

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

    public void addAllItems(List<Result> movieResultsDiscovers){
        mMovieResultsDiscovers.clear();
        mMovieResultsDiscovers.addAll(movieResultsDiscovers);
        notifyDataSetChanged();
    }

}
