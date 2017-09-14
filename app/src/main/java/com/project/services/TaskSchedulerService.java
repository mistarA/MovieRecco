package com.project.services;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

/**
 * Created by anandmishra on 14/09/17.
 */

public class TaskSchedulerService extends GcmTaskService {

    public static final String ONE_OFF_TASK = "ONE_OFF_TASK";
    public static final String PERIODIC_TASK = "PERIODIC_TASK";

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
                return GcmNetworkManager.RESULT_SUCCESS;
            case PERIODIC_TASK:
                return GcmNetworkManager.RESULT_SUCCESS;
            default:
                return GcmNetworkManager.RESULT_FAILURE;
        }
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
