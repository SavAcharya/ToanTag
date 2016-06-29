package com.mphasis.toantag;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.tonetag.tone.SoundRecorder;

import java.util.Date;

public class BeaconListner extends Service {
    SoundRecorder mSoundRecorder;
    Context context;
    public BeaconListner(Context mContext) {
        super();
        this.context=mContext;
    }
    private static final String TAG = "BeaconListner";

    private final Handler handler = new Handler();
    Intent intent;
    int counter = 0;


    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(AppConstant.BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

    }
    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {

            mSoundRecorder = new SoundRecorder(context);
            mSoundRecorder.setOnDataFoundListener(new SoundRecorder.OnDataFoundListener() {
                @Override
                public void onDataFound(final String data, final int from, final boolean duplicated, final short soundTagVolume) {
                   //TODO send data to ui
                    switch (data){
                        case AppConstant.BECAN_REGISTER:
                            requestServerData(AppConstant.BECAN_REGISTER);
                            break;
                        case AppConstant.BECAN_1:
                            requestServerData(AppConstant.BECAN_1);
                            break;
                        case AppConstant.BECAN_2:
                            requestServerData(AppConstant.BECAN_2);

                            break;


                    }


                }
                @Override
                public void onDataFound(long data, int from, short soundTagVolume) {
                    // Do Nothing }
                } });

            DisplayLoggingInfo();

            mSoundRecorder.startUltraRecording();
        }
    };

    private void requestServerData(String becanID) {
        // Tag used to cancel the request
        String  tag_string_req = "string_req";

        String url = AppController.getPreference(context,AppConstant.IP)+":"+AppController.getPreference(context,AppConstant.PORT)+
                AppConstant.BASE_URL+AppConstant.USER_ID+becanID;



        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void DisplayLoggingInfo() {
        Log.d(TAG, "entered DisplayLoggingInfo");

        intent.putExtra("time", new Date().toLocaleString());
        intent.putExtra("counter", String.valueOf(++counter));
        sendBroadcast(intent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
