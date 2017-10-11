package com.project.di.components;

import com.project.di.modules.ApplicationModule;
import com.project.di.modules.NetModule;
import com.project.movierecco.MainActivity;
import com.project.movierecco.views.activity.MovieListActivity;
import com.project.services.ExampleService;
import com.project.services.TaskSchedulerService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anandmishra on 11/11/16.
 */

@Singleton
@Component (modules = { ApplicationModule.class, NetModule.class })
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(MovieListActivity movieListActivity);

    void inject(TaskSchedulerService taskSchedulerService);

    void inject(ExampleService exampleService);
}
