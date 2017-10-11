package com.project.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;

import com.project.models.ArrGenre;
import com.project.movierecco.MovieReccoApplication;
import com.project.movierecco.TestMainActivity;
import com.project.network.views.MovieDbApiInterface;
import com.project.utils.Constants;
import com.project.utils.storage.SharedPreferencesManager;

import javax.inject.Inject;

import rx.SingleSubscriber;

/**
 * Created by anandmishra on 28/05/17.
 */

public class ExampleService extends Service {


    public static final String PERFORM_SLEEP = "performSleep";
    public static final String RESULT = "RESULT";
    public static final String RESULT_ACTION = "RESULT_ACTION";
    public static final String RECEIVER_TAG = "RECEIVER_TAG";
    ResultReceiver resultReceiver;
    public IBinder localBinder = new LocalBinder();

    @Inject
    MovieDbApiInterface movieDbApiInterface;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       /* if (intent != null && intent.getExtras() != null) {
            resultReceiver = intent.getParcelableExtra(RECEIVER_TAG);
            String operation = intent.getExtras().getString("Operation");
            if (operation != null && operation.equals(PERFORM_SLEEP)) {
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }*/
       initializeDependencyInjection();
        callMovieDetailsPage();
        return START_STICKY;
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                Message msg = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("Message", "I came from ExampleService");
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(TestMainActivity.MyBroadcastReceiver.ACTION_RECEIVE_DATA);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(RESULT_ACTION, msg.getData().getString("Message"));
            LocalBroadcastManager.getInstance(ExampleService.this).sendBroadcast(broadcastIntent);
            //resultReceiver.send(Activity.RESULT_OK, bundle);
            stopSelf();
        }
    };

    public void startBackgroundThread() {
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public class LocalBinder extends Binder {
        public ExampleService getService() {
            return ExampleService.this;
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
                        sharedPreferencesManager.putString(value.getGenres().get(4).getName());
                        stopSelf();
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
    }
}
