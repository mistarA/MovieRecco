package com.project.movierecco;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.models.ArrGenre;
import com.project.models.Genre;
import com.project.movierecco.views.fragments.GenreListDialogFragment;
import com.project.mvp.views.IMainActivityView;
import com.project.services.ExampleService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestMainActivity extends AppCompatActivity implements IMainActivityView {


    ArrGenre mArrGenre;
    String[] mType;
    private List<String> typeList;
    StringBuilder genreIds;

    LocationManager locationManager;
    MyResultReceiver myResultReceiver;
    MyBroadcastReceiver myBroadcastReceiver;
    ExampleService mBoundService;
    boolean isServiceBound;

    @BindView (R.id.genre_hint_and_value_tv)
    TextView mGenreSelected;
    @BindView (R.id.genre_ll)
    LinearLayout mLL;

    boolean isNetwork;
    boolean isGps;


    GenreListDialogFragment genreListDialogFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.test_activity_main);
        ButterKnife.bind(this);
        mType = getResources().getStringArray(R.array.type);
        initUi();
        myBroadcastReceiver = new MyBroadcastReceiver();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        myResultReceiver = new MyResultReceiver(new Handler());

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcastReceiver);
    }


    private void initUi() {
        typeList = new ArrayList<>();
        String[] typeArray = getResources().getStringArray(R.array.type);
        for (int i = 0; i < typeArray.length; i++) {
            typeList.add(typeArray[i]);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyBroadcastReceiver.ACTION_RECEIVE_DATA);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        //LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(TestMainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initializeGenreList(ArrGenre arrGenre) {
        mArrGenre = arrGenre;
        genreListDialogFragment = new GenreListDialogFragment();
        genreListDialogFragment.setData(mArrGenre.getGenres());

    }

    public void setGenreItemClicked(Genre genre) {
        mGenreSelected.setText(genre.getName());

    }

    public void setTypeItemClicked(String s) {
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void passGenreIds() {
        genreListDialogFragment.dismiss();
        genreIds = new StringBuilder();
        for (Genre temp : mArrGenre.getGenres()) {
            if (temp.isChecked()) {
                genreIds.append(temp.getId() + ",");
            }
        }
        Toast.makeText(this, genreIds, Toast.LENGTH_SHORT).show();

    }


    @OnClick (R.id.service_method_button)
    public void callServiceMethod() {
        mBoundService.startBackgroundThread();

    }


    @OnClick (R.id.location_button)
    public void getLocation() {
        showMessage(String.valueOf(isNetwork));
        /*Thread thread = new Thread(runnable);
        thread.start();*/

        //for bound Service,
        Intent intent = new Intent(this, ExampleService.class);
        intent.putExtra(ExampleService.RECEIVER_TAG,myResultReceiver);
        intent.putExtra("Operation", ExampleService.PERFORM_SLEEP);
        startService(intent);
        //bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((ExampleService.LocalBinder)service).getService();
            showMessage("Service Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false;
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);

                //Either this or the below one will run it in the background
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("I Ran On UI Thread");
                    }
                });
                //Or
                Message msg = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("Message", "I Ran On Android's Handler Thread");
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            showMessage(msg.getData().getString("Message"));
        }
    };

    class MyResultReceiver extends ResultReceiver{

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == RESULT_OK && resultData != null){
                String result = resultData.getString(ExampleService.RESULT);
                showMessage(result);
            }
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver{

        public static final String ACTION_RECEIVE_DATA = "com.intent.action.MESSAGE_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getExtras().getString(ExampleService.RESULT_ACTION);
            showMessage(result);
        }
    }

}
