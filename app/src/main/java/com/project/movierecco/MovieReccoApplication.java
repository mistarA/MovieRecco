package com.project.movierecco;

import android.app.Application;

import com.project.di.components.ApplicationComponent;
import com.project.di.components.DaggerApplicationComponent;
import com.project.di.modules.ApplicationModule;
import com.project.di.modules.NetModule;

/**
 * Created by anandmishra on 11/11/16.
 */

public class MovieReccoApplication extends Application{

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .netModule(new NetModule())
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
