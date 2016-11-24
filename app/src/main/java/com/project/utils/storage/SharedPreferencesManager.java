package com.project.utils.storage;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by anandmishra on 11/11/16.
 */

public class SharedPreferencesManager {

    SharedPreferences sharedPreferences;


    @Inject
    SharedPreferencesManager(SharedPreferences sharedPreferences){

        this.sharedPreferences = sharedPreferences;
    }


}
