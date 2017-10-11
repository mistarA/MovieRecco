package com.project.movierecco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.models.ArrGenre;
import com.project.movierecco.views.activity.GenreListActivity;
import com.project.movierecco.views.activity.MovieListActivity;
import com.project.mvp.presenters.MainActivityPresenter;
import com.project.mvp.views.IMainActivityView;
import com.project.utils.Constants;
import com.project.utils.storage.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;

public class MainActivity extends AppCompatActivity implements IMainActivityView {


    private static final int GENRE_LIST_REQUEST_CODE = 111;
    ArrGenre mArrGenre;
    String[] mType;
    private List<String> typeList;
    String genreIds;
    private boolean isMovieSelected;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    @BindView (R.id.genre_hint_and_value_tv)
    TextView mGenreSelected;

    @BindView (R.id.movie_collage)
    ImageView mMainImage;

    @BindView(R.id.button_movie)
    RadioButton mMovieButton;

    @BindView(R.id.button_tv_series)
    RadioButton mTvSeriesButton;

    @BindView (R.id.genre_ll)
    LinearLayout mLL;

    @BindView(R.id.submit_button)
    Button mSubmitButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initializeDependencyInjector();
        ButterKnife.bind(this);
        mainActivityPresenter.setMainActivityView(this);
        mainActivityPresenter.start();
        mainActivityPresenter.getGenreListAndInflateDataToUi();
        mType = getResources().getStringArray(R.array.type);
        initUi();

        //mainActivityPresenter.requestTopRatedMovieList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.stop();
    }

    private void initUi() {
        typeList = new ArrayList<>();
        String[] typeArray = getResources().getStringArray(R.array.type);
        for (int i = 0; i < typeArray.length; i++) {
            typeList.add(typeArray[i]);
        }
    }

    public void initializeDependencyInjector() {
        ((MovieReccoApplication) getApplication()).
                getComponent().
                inject(this);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initializeGenreList(ArrGenre arrGenre) {
        mArrGenre = arrGenre;
    }


    @OnClick (R.id.genre_ll)
    public void openGenreList() {
        Intent intent = new Intent(this, GenreListActivity.class);
        intent.putExtra(GenreListActivity.INTENT_GENRE_LIST, mArrGenre);
        startActivityForResult(intent, GENRE_LIST_REQUEST_CODE);
    }


    @OnClick (R.id.button_movie)
    public void setMovieSelected() {
        isMovieSelected = true;
        mTvSeriesButton.setChecked(false);
    }

    @OnClick (R.id.button_tv_series)
    public void setTvSeriesSelected() {
        isMovieSelected = false;
        mMovieButton.setChecked(false);
    }
    @OnClick (R.id.submit_button)
    public void submitGenresForDiscover() {
        if (!mMovieButton.isChecked() && !mTvSeriesButton.isChecked()){
            Toast.makeText(this,"Please select type", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, MovieListActivity.class);
        intent.putExtra(MovieListActivity.INTENT_GENRES_STRING, genreIds);
        intent.putExtra(MovieListActivity.INTENT_DISCOVER_TYPE,
                isMovieSelected ? Constants.DISCOVER_TYPE_MOVIE : Constants.DISCOVER_TYPE_TV );
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GENRE_LIST_REQUEST_CODE && data != null){
            genreIds = data.getExtras().getString(GenreListActivity.INTENT_GENRE_IDS);
            mSubmitButton.setVisibility(View.VISIBLE);
        }

    }
}