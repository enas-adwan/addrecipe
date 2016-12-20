package com.example.hp.navigation.activity;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Base64;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Service;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.IBinder;
import java.util.Timer;
import android.app.NotificationManager;
import android.app.PendingIntent;
import java.util.logging.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.BroadcastReceiver;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import android.os.CountDownTimer;
import java.util.concurrent.TimeUnit;
import android.media.MediaPlayer;
import android.util.Log;
import android.app.Notification;
import android.support.v4.content.LocalBroadcastManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Button;
import android.util.Base64;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Service;
import android.graphics.Bitmap;
import android.os.IBinder;
import java.util.Timer;
import java.util.logging.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import android.os.CountDownTimer;
import java.util.concurrent.TimeUnit;
import android.media.MediaPlayer;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import android.util.Log;
import android.support.v4.content.LocalBroadcastManager;

import com.navigation.drawer.activity.R;

/**
 * Created by eodwan on 18‏/9‏/2016.
 */
public class MyService extends Service {
    // constant

    int i;

    public MediaPlayer mp;
    private final static String TAG = "BroadcastService";
    LocalBroadcastManager broadcaster;
    static final public String COPA_RESULT = "com.controlj.copame.backend.COPAService.REQUEST_PROCESSED";

    static final public String COPA_MESSAGE = "com.controlj.copame.backend.COPAService.COPA_MSG";
    public static final String COUNTDOWN_BR = "Sec";
    Intent bi = new Intent(Sec.mBroadcastStringAction);

    CountDownTimer cdt = null;
    int r=0;
    int flago;
    String flag;
    int count = 0;
     String  titlee="";


    private Timer mTimer = null;
    @Override
    public void onCreate() {
          broadcaster = LocalBroadcastManager.getInstance(this);

        r=R.raw.alarm;
        mp= MediaPlayer.create(getBaseContext(),r);
//stopSelf();

        // run on another Thread to avoid crash
        //  public Handler mHandler = new Handler();
        // timer handling

        // cancel if already existed

        // schedule task
        // mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
      /*  String m = intent.getStringExtra("m");
        i=Integer.parseInt(m);
        new CountDownTimer(i*60000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (flag=="1"){
                    //  b.setText(""+String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                    //  b.setText("seconds remaining: " + millisUntilFinished / 1000);
                }else{

                    // b.setText("start timer");
                    if(mp!=null){
                        mp.stop();
                        Intent i= new Intent(getApplicationContext(), MyService.class);
                        // String m=b.getText().toString();
// potentially add data to the intent
                        i.putExtra("time", m);
                        getApplicationContext().stopService(i);
                    }}
            }

            public void onFinish() {
                if (flag=="1"){
                    r=R.raw.alarm;
                    mp= MediaPlayer.create(getBaseContext(),r);
                    // if(mp!=null)
                    //   mp.stop();

                    mp.start();
                    //  b.setText("done!");}
                    else{
                        //   b.setText("start timer");
                        if(mp!=null)
                            mp.stop();
                    }
                }
            }.start();


        }*/

    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        String m = intent.getStringExtra("m");
        titlee = intent.getStringExtra("title");
        i=Integer.parseInt(m);
        //flago =Integer.parseInt(flag);
        cdt=  new CountDownTimer(i*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                //   bi.putExtra("count", millisUntilFinished);
                //   broadcaster.sendBroadcast(bi);
                Intent intent = new Intent(COPA_RESULT);
                String m= String.valueOf(millisUntilFinished);
                intent.putExtra(COPA_MESSAGE,m);
                intent.putExtra("titlee",titlee);
                broadcaster.sendBroadcast(intent);


            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
                Intent i = new Intent(COPA_RESULT);

                i.putExtra(COPA_MESSAGE,"done");
                i.putExtra("titlee",titlee);
                broadcaster.sendBroadcast(i);

                // if(mp!=null)
                //   mp.stop();


                Intent intent = new Intent(MyService.this, NotificationReceiverActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(MyService.this, (int) System.currentTimeMillis(), intent, 0);

                // Build notification
                // Actions are just fake
                Notification noti = new Notification.Builder(MyService.this)
                        .setContentTitle("Timer has been finished")
                        .setContentText("hope it will be delicious").setSmallIcon(R.drawable.common_signin_btn_icon_light)
                        .setContentIntent(pIntent).build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // hide the notification after its selected
                noti.flags |= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(0, noti);
                mp.start();
                //   b.setText("done!");}
            }

        };
        cdt.start();
    /*    if(cdt!=null){
            cdt.cancel();
        }
        String m = intent.getStringExtra("m");
   flag = intent.getStringExtra("flag");
     titlee = intent.getStringExtra("title");
        i=Integer.parseInt(m);
        flago =Integer.parseInt(flag);

        cdt=  new CountDownTimer(i*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                    Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                 //   bi.putExtra("count", millisUntilFinished);
             //   broadcaster.sendBroadcast(bi);
                Intent intent = new Intent(COPA_RESULT);
String m= String.valueOf(millisUntilFinished);
                    intent.putExtra(COPA_MESSAGE,m);
                intent.putExtra("titlee",titlee);
                broadcaster.sendBroadcast(intent);


            }
            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
            }
        };
if(flago==1){
        cdt.start();}else if(flago==0){
    cdt.cancel();

}*/
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        //TODO for communication return IBinder implementation

        return null;
    }
    public void onDestroy() {

        cdt.cancel();
if(mp!=null)
    mp.stop();
        Log.i(TAG, "Service onDestroy");
    }


}

