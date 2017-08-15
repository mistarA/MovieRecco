package com.project.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anandmishra on 11/11/16.
 */

@Singleton
@Module
public class ApplicationModule {


  Application application;

    public ApplicationModule(Application application) {

        this.application=application;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
