package com.project.movierecco.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.project.models.ArrGenre;
import com.project.movierecco.R;
import com.project.movierecco.adapters.GenreDetailsListAdapter;
import com.project.utils.GridSpacingItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anandmishra on 30/05/17.
 */

public class GenreListActivity extends AppCompatActivity {

    public static final String INTENT_GENRE_LIST = "INTENT_GENRE_LIST";
    public static final String INTENT_GENRE_IDS = "INTENT_GENRE_IDS";

    ArrGenre mArrGenre;
    private GenreDetailsListAdapter genreDetailsListAdapter;

    @BindView(R.id.genre_list_rv)
    RecyclerView mGenreRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_genre_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null){
            Bundle bundle =  getIntent().getExtras();
            mArrGenre =   bundle.getParcelable(INTENT_GENRE_LIST);
            initializeView();
        }
    }

    @OnClick (R.id.submit_genres_bv)
    public void submitGenres() {
        Intent intent = new Intent();
        intent.putExtra(GenreListActivity.INTENT_GENRE_IDS, genreDetailsListAdapter.getSelectedIds());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initializeView() {
        genreDetailsListAdapter = new GenreDetailsListAdapter(this, mArrGenre.getGenres());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mGenreRv.addItemDecoration(new GridSpacingItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.grid_card_center_space), false));
        mGenreRv.setLayoutManager(gridLayoutManager);
        mGenreRv.setAdapter(genreDetailsListAdapter);
    }
}
