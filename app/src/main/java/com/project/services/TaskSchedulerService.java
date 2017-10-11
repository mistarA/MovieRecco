package com.project.services;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.project.models.ArrGenre;
import com.project.movierecco.MovieReccoApplication;
import com.project.network.views.MovieDbApiInterface;
import com.project.utils.Constants;
import com.project.utils.storage.SharedPreferencesManager;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import javax.inject.Inject;

import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action1;

/**
 * Created by anandmishra on 14/09/17.
 */

public class TaskSchedulerService extends GcmTaskService {

    public static final String ONE_OFF_TASK = "ONE_OFF_TASK";
    public static final String PERIODIC_TASK = "PERIODIC_TASK";

    @Inject
    MovieDbApiInterface movieDbApiInterface;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjection();
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        //Here you can initialize your tasks
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        switch (taskParams.getTag()) {
            case ONE_OFF_TASK:
                //turnOnFlash();
                //Make a call to network and update SharedPreferences
                callMovieDetailsPage();
                return GcmNetworkManager.RESULT_SUCCESS;
            case PERIODIC_TASK:
                return GcmNetworkManager.RESULT_SUCCESS;
            default:
                return GcmNetworkManager.RESULT_FAILURE;
        }
    }

    private void initializeDependencyInjection() {
        ((MovieReccoApplication) getApplication()).getComponent().inject(this);
    }

    private void callMovieDetailsPage() {
        movieDbApiInterface.getSingleGenreList(Constants.API_KEY,Constants.LANGUAGE)
                .subscribe(new SingleSubscriber<ArrGenre>() {
                    @Override
                    public void onSuccess(ArrGenre value) {
                        turnOnFlash();
                        sharedPreferencesManager.putString(value.getGenres().get(0).getName());
                        stopSelf();
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
    }

    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Parameters params;

    private void turnOnFlash() {
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
    }
}
