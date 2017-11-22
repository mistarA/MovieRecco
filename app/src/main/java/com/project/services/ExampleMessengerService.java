package com.project.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.project.models.ArrGenre;
import com.project.movierecco.MainActivity;
import com.project.movierecco.MovieReccoApplication;
import com.project.movierecco.R;
import com.project.movierecco.TestMainActivity;
import com.project.network.views.MovieDbApiInterface;
import com.project.utils.Constants;
import com.project.utils.storage.SharedPreferencesManager;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.SingleSubscriber;

/**
 * Created by anandmishra on 28/05/17.
 */

public class ExampleMessengerService extends Service {


    public static final String PERFORM_SLEEP = "performSleep";
    public static final String RESULT = "RESULT";
    public static final String RESULT_ACTION = "RESULT_ACTION";
    public static final String RECEIVER_TAG = "RECEIVER_TAG";
    public static final int SAY_HELLO = 1;
    ResultReceiver resultReceiver;
    public IBinder localBinder = new LocalBinder();

    @Inject
    MovieDbApiInterface movieDbApiInterface;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    Messenger clientMessenger;
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    WeakReference<IncomingHandler> incomingHandlerWeakReference = new WeakReference<>(new IncomingHandler());
    Messenger messenger = new Messenger(incomingHandlerWeakReference.get());

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification = buildNotification();
        startForeground(1234, notification);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1234, notification);
        return START_STICKY;
    }

    private Notification buildNotification() {
        //RemoteViews customView = LayoutInflater.from(this).
        Intent launchIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setCustomContentView(new RemoteViews(getPackageName(), R.layout.dummy_include_layout))
                .setAutoCancel(false)
                .setContentText("Kaa be")
                .setSmallIcon(R.drawable.moviecollage)
                .setContentTitle("Dummy Notification")
                .build();
        return notification;
    }

    private class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            clientMessenger = msg.replyTo;

            try {
                clientMessenger.send(Message.obtain(null,  SAY_HELLO, 0, 0));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }

            switch (msg.what) {

                case SAY_HELLO:
                    Toast.makeText(ExampleMessengerService.this, "HelloServer", Toast.LENGTH_SHORT).show();

                default:
                    super.handleMessage(msg);
            }
        }
    }

    public void startBackgroundThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Toast.makeText(ExampleMessengerService.this, "Thread Ended", Toast.LENGTH_SHORT).show();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            Message message = Message.obtain(null,0,"I am done");
            clientMessenger.send(Message.obtain());
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {
        public ExampleMessengerService getService() {
            return ExampleMessengerService.this;
        }
    }
}
