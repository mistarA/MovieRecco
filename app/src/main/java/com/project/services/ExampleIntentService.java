package com.project.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.project.movierecco.TestMainActivity;

import static com.project.services.ExampleService.RESULT_ACTION;

/**
 * Created by anandmishra on 28/05/17.
 */

public class ExampleIntentService extends IntentService {


    public static final String PERFORM_SLEEP = "performSleep";
    public static final String RESULT = "RESULT";
    public static final String RECEIVER_TAG = "RECEIVER_TAG";
    ResultReceiver resultReceiver;

    public ExampleIntentService()  {
        super("ExampleService.class");
    }
    
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getExtras() != null){
            resultReceiver = intent.getParcelableExtra(RECEIVER_TAG);
            String operation = intent.getExtras().getString("Operation");
            if (operation != null && operation.equals(PERFORM_SLEEP)){
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                Message msg = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("Message", "I came from ExampleIntentService");
                msg.setData(bundle);
                mHandler.sendMessage(msg);            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(TestMainActivity.MyBroadcastReceiver.ACTION_RECEIVE_DATA);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(RESULT_ACTION, msg.getData().getString("Message"));
            sendBroadcast(broadcastIntent);
            //resultReceiver.send(Activity.RESULT_OK, bundle);
        }
    };
}
