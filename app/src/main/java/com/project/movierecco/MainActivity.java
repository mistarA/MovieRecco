package com.project.movierecco;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.models.ArrGenre;
import com.project.models.Genre;
import com.project.models.MovieResultsTopRated;
import com.project.movierecco.adapters.GenreDetailsListAdapter;
import com.project.movierecco.adapters.TypeListAdapter;
import com.project.mvp.presenters.MainActivityPresenter;
import com.project.mvp.views.IMainActivityView;
import com.project.network.views.MovieDbApiInterface;
import com.project.utils.Constants;
import com.project.utils.storage.SharedPreferencesManager;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends Activity implements IMainActivityView {


    ArrGenre mArrGenre;
    String[] mType;
    private GenreDetailsListAdapter genreDetailsListAdapter;
    private TypeListAdapter typeListAdapter;
    private List<String> typeList;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    @BindView (R.id.type_recycler_view)
    RecyclerView mTypeRecyclerView;

    @BindView (R.id.genre_recycler_view)
    RecyclerView mGenreRecyclerView;

    @BindView (R.id.type_hint_and_value_tv)
    TextView mTypeSelected;

    @BindView (R.id.genre_hint_and_value_tv)
    TextView mGenreSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDependencyInjector();
        ButterKnife.bind(this);
        mainActivityPresenter.setMainActivityView(this);
        mainActivityPresenter.getGenreListAndInflateDataToUi();
        mType = getResources().getStringArray(R.array.type);
        initUi();
//        mainActivityPresenter.requestTopRatedMovieList();
    }

    private void initUi() {
        typeList = new ArrayList<String>();
        String[] typeArray = getResources().getStringArray(R.array.type);
        for (int i = 0; i < typeArray.length; i++) {
            typeList.add(typeArray[i]);
        }
        typeListAdapter = new TypeListAdapter(this, typeList);
        mTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTypeRecyclerView.setAdapter(typeListAdapter);
    }

    public void initializeDependencyInjector() {
        ((MovieReccoApplication) getApplication()).
                getComponent().
                inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initializeGenreList(ArrGenre arrGenre) {
        mArrGenre = arrGenre;
        mGenreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        genreDetailsListAdapter = new GenreDetailsListAdapter(this, mArrGenre.getGenres());
        mGenreRecyclerView.setAdapter(genreDetailsListAdapter);
    }

    public void setGenreItemClicked(Genre genre) {
        mGenreSelected.setText(genre.getName());
        mGenreRecyclerView.setVisibility(View.GONE);

    }

    public void setTypeItemClicked(String s) {
        mTypeSelected.setText(s);
        mTypeRecyclerView.setVisibility(View.GONE);
    }

    @OnClick (R.id.type_ll)
    public void openTypeList() {
        mGenreRecyclerView.setVisibility(View.GONE);
        mTypeRecyclerView.setVisibility(View.VISIBLE);
    }

    @OnClick (R.id.genre_ll)
    public void openGenreList() {
        mTypeRecyclerView.setVisibility(View.GONE);
        mGenreRecyclerView.setVisibility(View.VISIBLE);
    }
}
