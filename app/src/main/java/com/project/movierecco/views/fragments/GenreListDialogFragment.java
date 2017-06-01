package com.project.movierecco.views.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.project.models.Genre;
import com.project.movierecco.MainActivity;
import com.project.movierecco.R;
import com.project.movierecco.adapters.GenreDetailsListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anandmishra on 29/11/16.
 */

public class GenreListDialogFragment extends DialogFragment {

    private GenreDetailsListAdapter genreDetailsListAdapter;


    @BindView (R.id.genre_recycler_view)
    RecyclerView mGenreRecyclerView;
    @BindView (R.id.proceed_bv)
    Button mProceedButton;

    private List<Genre> genreList;


    public GenreListDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.genre_list_dialog_view, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return view;
    }

    @OnClick (R.id.proceed_bv)
    public void proceedButton(){
        dismiss();
        //((MainActivity)getActivity()).passGenreIds();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGenreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        genreDetailsListAdapter = new GenreDetailsListAdapter(getActivity(), genreList);
        mGenreRecyclerView.setAdapter(genreDetailsListAdapter);
    }

    public void setData(List<Genre> genres) {
        genreList = genres;
    }
}
