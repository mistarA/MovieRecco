package com.project.movierecco.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.models.Genre;
import com.project.movierecco.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anandmishra on 13/05/16.
 */
public class GenreDetailsListAdapter extends RecyclerView.Adapter<GenreDetailsListAdapter.GenreDetailViewHolder> {


    private Context context;
    private List<Genre> genreList;
    private ArrayList<Integer> ids;

    public GenreDetailsListAdapter(Context context, List<Genre> genreList) {
        this.context = context;
        this.genreList = genreList;
        this.ids = new ArrayList<>();
    }

    @Override
    public GenreDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.genre_item_view, parent, false);
        return new GenreDetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GenreDetailViewHolder holder, final int position) {
        holder.mGenreName.setText(genreList.get(position).getName());
        if (genreList.get(position).isChecked()) {
            holder.mGenreName.setAlpha(0.6f);
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightning_yellow));
        }
        else {
            holder.mGenreName.setAlpha(1f);
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_genre_selected));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (genreList.get(position).isChecked()) {
                    genreList.get(position).setChecked(false);
                    ids.remove(genreList.get(position).getId());
                }
                else {
                    genreList.get(position).setChecked(true);
                    ids.add(genreList.get(position).getId());
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return genreList != null ? genreList.size() : 0;
    }

    public class GenreDetailViewHolder extends RecyclerView.ViewHolder  {
        @BindView (R.id.genre_item_tv)
        TextView mGenreName;


        public GenreDetailViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public String getSelectedIds() {

        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : ids) {
            stringBuilder.append(id.toString());
            if (!id.equals(ids.get(ids.size() - 1))) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

}
