package com.project.mvp.views;

import com.project.models.ArrGenre;

/**
 * Created by anandmishra on 15/11/16.
 */

public interface IMainActivityView {

    void showMessage(String message);

    void initializeGenreList(ArrGenre arrGenre);
}
