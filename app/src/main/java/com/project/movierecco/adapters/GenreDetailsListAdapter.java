package com.project.movierecco.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.models.Genre;
import com.project.movierecco.MainActivity;
import com.project.movierecco.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anandmishra on 13/05/16.
 */
public class GenreDetailsListAdapter extends RecyclerView.Adapter<GenreDetailsListAdapter.GenreDetailViewHolder>  {


    private Context context;
    private List<Genre> genreList;

    public GenreDetailsListAdapter(Context context, List<Genre> genreList) {
        this.context = context;
        this.genreList = genreList;
    }

    @Override
    public GenreDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.row_genre_details, parent, false);
        final GenreDetailViewHolder genreDetailViewHolder = new GenreDetailViewHolder(v);
        return genreDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(GenreDetailViewHolder holder, int position) {
        holder.detailFieldValue.setText(genreList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return genreList != null ? genreList.size() : 0;
    }

    public class GenreDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.field_name)
        TextView detailFieldValue;

        public GenreDetailViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((MainActivity) context).setGenreItemClicked(genreList.get(getAdapterPosition()));
        }
    }

    public void addAllItems(List<Genre> genres) {
        genreList.addAll(genres);
        notifyDataSetChanged();
    }

}
