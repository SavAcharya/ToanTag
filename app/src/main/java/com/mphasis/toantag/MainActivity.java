package com.mphasis.toantag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  ActivityCompat.OnRequestPermissionsResultCallback  {

    String userPhoneNo;
    TextView tvData;
    private Context mAppContext;
    private static final int REQUEST_CODE_PERMISSION_RECORD_AUDIO = 1;
    private static final int REQUEST_CODE_PERMISSION_MODIFY_AUDIO_SETTINGS = 2;
    private static final int REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 3;


    private View mLayout;


    private String TAG="TOANTAG";
    private Intent intent;
    private Button btnSave;
    private EditText edtPort;
    private EditText edtIP;
String ip;
    String port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppContext = MainActivity.this;


        // start service
        intent = new Intent(this, BeaconListner.class);

        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.root);
        tvData= (TextView) findViewById(R.id.txt);
        btnSave= (Button) findViewById(R.id.btnSave);
        edtIP= (EditText) findViewById(R.id.edtIP);
        edtPort= (EditText) findViewById(R.id.edtPort);

        ip=AppController.getPreference(MainActivity.this,AppConstant.PREF_IP);
        if (ip==null){
            ip=AppConstant.IP;

        }
        port=AppController.getPreference(MainActivity.this,AppConstant.PREF_PORT);
        if (port==null){
            port=AppConstant.PORT;

        }
        edtIP.setText(ip);
        edtPort.setText(port);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.setPreference(MainActivity.this,edtIP.getText().toString(),AppConstant.PREF_IP);
                AppController.setPreference(MainActivity.this,edtPort.getText().toString(),AppConstant.PREF_PORT);
                Toast.makeText(MainActivity.this,"NetworkConfigured",Toast.LENGTH_SHORT);
                moveTaskToBack(true);
            }
        });
     /*   mSoundRecorder = new SoundRecorder(MainActivity.this);
        mSoundRecorder.setOnDataFoundListener(new SoundRecorder.OnDataFoundListener() {
            @Override
            public void onDataFound(final String data, final int from, final boolean duplicated, final short soundTagVolume) {
                runOnUiThread(new Runnable() { // post data on UI/Worker thread
                     @Override public void run() {
                         tvData.setText(data);
                         Toast.makeText(MainActivity.this,"Data Received :"+ data+" from :"+from+" Duplicated :"+duplicated +" soundTagVolume:" +soundTagVolume,Toast.LENGTH_SHORT).show();
                     }
                });
            }
            @Override
            public void onDataFound(long data, int from, short soundTagVolume) {
                // Do Nothing }
            } });*/
       // userPhoneNo=getUserPhoneNo();
      //  mSoundRecorder.startUltraRecording();

        // save user phone no for future reference
        AppController.setPreference(MainActivity.this,AppConstant.PREF_Phone,userPhoneNo );



}



    private String getUserPhoneNo() {

        TelephonyManager tMgr = (TelephonyManager) mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        return mPhoneNumber;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            printLog(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(AppConstant.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
       // stopService(intent);
    }

    private void printLog(Intent intent) {
        String counter = intent.getStringExtra("counter");
        String time = intent.getStringExtra("time");
       /* Log.d(TAG, counter);
        Log.d(TAG, time);*/

        
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:

                moveTaskToBack(true);

                return true;
        }
        return false;
    }

}





