package com.project.utils.storage;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by anandmishra on 11/11/16.
 */

public class SharedPreferencesManager {

    private SharedPreferences mSharedPreferences;
    private final String TAG_FOR_TASK = "TAG_FOR_TASK";

    @Inject
    SharedPreferencesManager(SharedPreferences mSharedPreferences){
        this.mSharedPreferences = mSharedPreferences;
    }

    public void putString(String value){
          mSharedPreferences.edit()
                  .putString(TAG_FOR_TASK, value)
                  .apply();
    }

    public void getString(String key){
        mSharedPreferences.getString(key,null);
    }

    public String getTagForTask(){
        return mSharedPreferences.getString(TAG_FOR_TASK,null);
    }
}
