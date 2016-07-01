package com.mphasis.toantag;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.tonetag.tone.SoundRecorder;

import java.util.Date;

public class BeaconListner extends Service {
    SoundRecorder mSoundRecorder;
    Context context;
    public BeaconListner() {

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
       /* handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second*/

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
        mSoundRecorder = new SoundRecorder(context);
        mSoundRecorder.startUltraRecording();
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

       // mSoundRecorder.startUltraRecording();
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {

            mSoundRecorder.startUltraRecording();
            mSoundRecorder.setOnDataFoundListener(new SoundRecorder.OnDataFoundListener() {
                @Override
                public void onDataFound(final String data, final int from, final boolean duplicated, final short soundTagVolume) {
                   //TODO once becan detected request for server data
                    Log.i("Data received", data);
                    requestServerData(data);

                }
                @Override
                public void onDataFound(long data, int from, short soundTagVolume) {
                    // Do Nothing }
                } });

            DisplayLoggingInfo();


            handler.postDelayed(this,2000);
        }
    };

    private void requestServerData(final String becanID) {
        // Tag used to cancel the request
        String  tag_string_req = "string_req";
        final String mBecanId=becanID;
        Log.i("Beacon Id", mBecanId);

String data="[ { \"Product_Discount\": \"10\", \"Product_price\": \"36500\", \"Product_name\": \"Samsung Mobile S4 Model\" },\n" +
        "       { \"Product_Discount\": \"5\", \"Product_price\": \"1500\", \"Product_name\": \"LED Bulb 3 Pairs\" } ]";
        //Dummy data
        switch (mBecanId){
            case AppConstant.BECAN_REGISTER:
                showWelcomeNotification("Welcome","Something interesting happened","Welcome to Offer Zone");
                break;
            case AppConstant.BECAN_1:
                showNotification("Offers","Something interesting happened",data);
                break;
            case AppConstant.BECAN_2:
                showNotification("Offers","Something interesting happened",data);

                break;

        }




           /* String url = AppController.getPreference(this,AppConstant.IP)+":"+AppController.getPreference(this,AppConstant.PORT)+
                AppConstant.BASE_URL+AppConstant.USER_ID+becanID;



        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Server: ", response.toString());

                switch (mBecanId){
                    case AppConstant.BECAN_REGISTER:
                        showWelcomeNotification("Welcome","Something interesting happened","Welcome to Offer Zone");
                        break;
                    case AppConstant.BECAN_1:
                        showNotification("Offers","Something interesting happened",response);
                        break;
                    case AppConstant.BECAN_2:
                        showNotification("Offers","Something interesting happened",response);

                        break;


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);*/
    }

    private void showNotification(String title, String message, String data) {

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setLargeIcon(largeIcon)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        . setAutoCancel(true)
                        .setContentText(message);
        int NOTIFICATION_ID = counter;

        Intent targetIntent = new Intent(this, ProductListActtivity.class);
        targetIntent.putExtra("productData",data);
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());


    }

    private void showWelcomeNotification(String title, String message, String data) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setLargeIcon(largeIcon)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setContentText(message);
        int NOTIFICATION_ID = counter;

        Intent targetIntent = new Intent(this, OffersActivity.class);
        targetIntent.putExtra("productData",data);
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());


    }


    private void DisplayLoggingInfo() {
        Log.d(TAG, "entered DisplayLoggingInfo");
        Log.d(TAG, "counter"+counter);
        Log.d(TAG, "time"+new Date().toLocaleString());
        intent.putExtra("time", new Date().toLocaleString());
        intent.putExtra("counter", String.valueOf(++counter));
        sendBroadcast(intent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

   /* public void onTaskRemoved(Intent rootIntent)
    {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.setExact(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }*/

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);
    }
}
