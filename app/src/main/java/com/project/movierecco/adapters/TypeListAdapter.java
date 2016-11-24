package com.project.movierecco.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.movierecco.MainActivity;
import com.project.movierecco.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anandmishra on 13/05/16.
 */
public class TypeListAdapter extends RecyclerView.Adapter<TypeListAdapter.TypeViewHolder>  {


    private Context context;
    private List<String> typeList;

    public TypeListAdapter(Context context, List<String> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.row_genre_details, parent, false);
        final TypeViewHolder typeViewHolder = new TypeViewHolder(v);
        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        holder.detailFieldValue.setText(typeList.get(position));
    }

    @Override
    public int getItemCount() {
        return typeList != null ? typeList.size() : 0;
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.field_name)
        TextView detailFieldValue;

        public TypeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((MainActivity) context).setTypeItemClicked(typeList.get(getAdapterPosition()));
        }
    }

    public void addAllItems(List<String> typeList) {
        typeList.addAll(typeList);
        notifyDataSetChanged();
    }

}
