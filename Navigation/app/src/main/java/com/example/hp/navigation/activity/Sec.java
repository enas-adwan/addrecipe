package com.example.hp.navigation.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.navigation.drawer.activity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.concurrent.TimeUnit;

public class Sec extends BaseActivity implements View.OnClickListener  {
    String title;
    public TextView titl;
    public TextView calor;
    public TextView Username;
    JSONArray peoples = null;
    String myJSON;
    private static final String TAG_RESULTS="result";
    private static final String TAG_name = "name";
    public TextView pre;
    public TextView des;
    public TextView coo;
    public TextView tota;
    public TextView Integ;
    public ImageButton share;
    public String all="";
    public TextView T;
    public Bitmap bmp;
    public static TextView Textcal;
    public static TextView Textpro;
    public static TextView Textcalc;
    public static TextView Textiron;
    public static TextView Textvitc;
    public static TextView Textvitb6;
    public static TextView Textvitb12;
    public static TextView Textvite;
    public  ImageView imagevieww;
    private int position = 0;
    private MediaController mediaController;
    VideoView vidView;
    BroadcastReceiver receiver;
    public static final String mBroadcastStringAction = "com.truiton.broadcast.string";
    private final static String TAG = "BroadcastService";
   public Intent i;

    recipeDbHelper userDbHelper2;
    SQLiteDatabase sqLiteDatabase;
    LinearLayout videoLayout;
    Cursor cursor;
    WebView simpleWebView;
    Button loadWebPage;
    int r=0;

   // public MyCount timer;
    public Button b;
    public Button s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_sec, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b = (Button) findViewById(R.id.totalbutton);
        share = (ImageButton) findViewById(R.id.share);
     imagevieww = (ImageView) findViewById(R.id.imagev);
        vidView = (VideoView)findViewById(R.id.myVideo);
        Textvitc=(TextView) findViewById(R.id.text_c);
        Textcal=(TextView) findViewById(R.id.textcal);
        Textpro=(TextView) findViewById(R.id.text_pro);
        Textiron=(TextView) findViewById(R.id.text_iron);
        Textcalc=(TextView) findViewById(R.id.text_calc);
        Textvitb6=(TextView) findViewById(R.id.text_b6);
        Textvitb12=(TextView) findViewById(R.id.text_b12);
        Textvite=(TextView) findViewById(R.id.text_e);
         videoLayout=(LinearLayout)this.findViewById(R.id.videolayout);
        loadWebPage = (Button) findViewById(R.id.loadWebPage);
        loadWebPage.setOnClickListener(this);
        simpleWebView = (WebView) findViewById(R.id.simpleWebView);
        simpleWebView.getSettings().setSupportZoom(true);
        simpleWebView.getSettings().setBuiltInZoomControls(true);
        simpleWebView.getSettings().setDisplayZoomControls(false);
        // Go = (Button) findViewById(R.id.go);
        title=getIntent().getStringExtra("title");
        titl = (TextView) findViewById(R.id.title1);
        calor = (TextView) findViewById(R.id.calory1);
        des = (TextView) findViewById(R.id.desc1);
        pre = (TextView) findViewById(R.id.prep1);
        coo = (TextView) findViewById(R.id.cook1);
        tota = (TextView) findViewById(R.id.total1);
        Integ = (TextView) findViewById(R.id.integ);
        Username= (TextView) findViewById(R.id.username);
        i= new Intent(getApplicationContext(), MyService.class);
        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
        if(getIntent().getStringExtra("type").equalsIgnoreCase("sec")){
            Toast.makeText(getApplicationContext(), "sec", Toast.LENGTH_LONG).show();
        jso(title);

        }else if(getIntent().getStringExtra("type").equalsIgnoreCase("fav")){
            String alldesc="";
            Toast.makeText(getApplicationContext(), "fav", Toast.LENGTH_LONG).show();
            userDbHelper2=new recipeDbHelper(getApplicationContext());
            sqLiteDatabase=userDbHelper2.getReadableDatabase();
            cursor=userDbHelper2.SelectAllData(title,sqLiteDatabase);

            if(cursor.moveToFirst())
            {
                do {
                    int id=cursor.getInt(0);
                    String  title=cursor.getString(1);
                    String list =cursor.getString(2);
                    String descc =cursor.getString(3);
                    String calory =cursor.getString(4);
                    String prep =cursor.getString(5);
                    String cook =cursor.getString(6);
                    String total =cursor.getString(7);
                    String image =cursor.getString(8);

                    byte[] qrimage = Base64.decode(image.getBytes(), 0);
                    bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                    ImageView imageview = (ImageView) findViewById(R.id.imagev);
                    String[] separated = list.split("-");
                    int ji = 1;
                    for (; ji < separated.length-1; ji++) {
                        int ind=separated[ji ].lastIndexOf(",");
                        if( ind>=0 )
                            separated[ji ] = new StringBuilder(separated[ji ]).replace(ind, ind+1,".").toString();
                        all+=ji+"-"+separated[ji ]+"\n";

                    }
                    String[] separated1 = descc.split(",");
                    int ji1 = 1;
                    for (; ji1 < separated1.length; ji1++) {
                        int ind=separated1[ji1 ].lastIndexOf(",");
                        if( ind>=0 )
                            separated1[ji1 ] = new StringBuilder(separated1[ji1 ]).replace(ind, ind+1,".").toString();
                     alldesc+=ji1+"-"+separated1[ji1 ]+"\n";

                    }
                    imageview.setImageBitmap(bmp);
                    //  names.add(title);

                    // record = new Record(title, id);


                    //  records.add(record);







                    titl.setText(title);
                    des.setText(     alldesc);
                    calor.setText(calory);
                    pre.setText(prep);
                    tota.setText(total);
                    coo.setText(cook);
                    Integ.setText(all);

                    Toast.makeText(getApplicationContext(),list, Toast.LENGTH_LONG).show();

                }while (cursor.moveToNext());
     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
            }
        }else if(getIntent().getStringExtra("type").equalsIgnoreCase("offline"))
        {
           String alldesc="";
            Toast.makeText(getApplicationContext(), "offline", Toast.LENGTH_LONG).show();

            userDbHelper2=new recipeDbHelper(getApplicationContext());
            sqLiteDatabase=userDbHelper2.getReadableDatabase();
            cursor=userDbHelper2.SelectAllDataoffline(title,sqLiteDatabase);

            if(cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String list = cursor.getString(2);
                    String descc = cursor.getString(3);
                    String calory = cursor.getString(4);
                    String prep = cursor.getString(5);
                    String cook = cursor.getString(6);
                    String total = cursor.getString(7);
                    String image = cursor.getString(8);
                    byte[] qrimage = Base64.decode(image.getBytes(), 0);
                    bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                    ImageView imageview = (ImageView) findViewById(R.id.imagev);
                    String[] separated = list.split("-");
                    int ji = 0;
                    for (; ji < separated.length ; ji++) {
                        int ind = separated[ji].lastIndexOf(",");
                        if (ind >= 0)
                            separated[ji] = new StringBuilder(separated[ji]).replace(ind, ind + 1, ".").toString();
                        all += ji + "-" + separated[ji] + "\n";

                    }
                    String[] separated1 = descc.split(",");
                    int ji1 = 1;
                    for (; ji1 < separated1.length; ji1++) {
                        int ind=separated1[ji1 ].lastIndexOf(",");
                        if( ind>=0 )
                            separated1[ji1 ] = new StringBuilder(separated1[ji1 ]).replace(ind, ind+1,".").toString();
                        alldesc+=ji1+"-"+separated1[ji1 ]+"\n";

                    }
                    imageview.setImageBitmap(bmp);
                    //  names.add(title);

                    // record = new Record(title, id);


                    //  records.add(record);


                    titl.setText(title);
                    des.setText( alldesc);



                    calor.setText(calory);
                    pre.setText(prep);
                    tota.setText(total);
                    coo.setText(cook);
                    Integ.setText(all);

                    Toast.makeText(getApplicationContext(), list, Toast.LENGTH_LONG).show();

                } while (cursor.moveToNext());
     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
            }

        }
        T = (TextView) findViewById(R.id.t);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)  {
                String m=  tota.getText().toString();

                stopService(i.addCategory(MyService.COPA_MESSAGE));
                i.putExtra("m", m);
               // i.putExtra("flag", "1");
                i.putExtra("title",titl.getText().toString());
                getApplicationContext().startService(i.addCategory(MyService.COPA_MESSAGE));
            }});


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
              //  Toast.makeText(getApplicationContext(),intent.getStringExtra("titlee")+titl.getText().toString(), Toast.LENGTH_LONG).show();
                if(intent.getStringExtra("titlee").equalsIgnoreCase(titl.getText().toString())) {
                    String s = intent.getStringExtra(MyService.COPA_MESSAGE);

                    if(!s.equalsIgnoreCase("done")){
                        Integer s1 = Integer.parseInt(s);
                    b.setText("" + String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(s1),
                            TimeUnit.MILLISECONDS.toSeconds(s1) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(s1))));}
                    else{
                        b.setText("done");


                    }
                    // Intent i = new Intent(getApplicationContext(), MyService.class);

                    // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    // do something here.
                }  }};
        s= (Button) findViewById(R.id.stop);

        s.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(), "timer has been stoped", Toast.LENGTH_LONG).show();
                b.setText("Restart Timer");
              stopService(i.addCategory(MyService.COPA_MESSAGE));

                // String m=b.getText().toString();
// potentially add data to the intent

                }
        });


      /*  Go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent i=new Intent(Sec.this,Showfav.class);

               startActivity(i);

                // String m=b.getText().toString();
// potentially add data to the intent

            }
        });*/

      /*  receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(),intent.getStringExtra("titlee")+titl.getText().toString(), Toast.LENGTH_LONG).show();
                if(intent.getStringExtra("titlee").equalsIgnoreCase(titl.getText().toString())) {
                    String s = intent.getStringExtra(MyService.COPA_MESSAGE);
                    Integer s1 = Integer.parseInt(s);
                    b.setText("" + String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(s1),
                            TimeUnit.MILLISECONDS.toSeconds(s1) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(s1))));
                    // Intent i = new Intent(getApplicationContext(), MyService.class);

                   // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    // do something here.
                }  }};

   /*     b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)  {
                flag="1";
              String m=  tota.getText().toString();
               i=Integer.parseInt(m);
                new CountDownTimer(i*60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        if (flag=="1"){
                      b.setText(""+String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            Intent i= new Intent(getApplicationContext(), MyService.class);
                            String m=b.getText().toString();
// potentially add data to the intent
                            i.putExtra("time", m);
                            getApplicationContext().startService(i);
                      //  b.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }else{

                            b.setText("start timer");
                            if(mp!=null){
                            mp.stop();
                                Intent i= new Intent(getApplicationContext(), MyService.class);
                                String m=b.getText().toString();
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
                        b.setText("done!");}
                        else{
                            b.setText("start timer");
                            if(mp!=null)
                            mp.stop();
                        }
                    }
                }.start();


            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)  {
              i= new Intent(getApplicationContext(), MyService.class);
               // String m=b.getText().toString();
// potentially add data to the intent
               // i.putExtra("time", m);
                String m=  tota.getText().toString();
                i.putExtra("m", m);
                i.putExtra("flag", "1");
                i.putExtra("title",titl.getText().toString());
                getApplicationContext().startService(i);
                //  b.setText("seconds remaining: " + millisUntilFinished / 1000);
                flag="1";

                ii=Integer.parseInt(m);

              countdown=  new CountDownTimer(ii*60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        if (flag=="1") {

                            b.setText("" + String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                           // Intent i = new Intent(getApplicationContext(), MyService.class);

                            //  b.setText("seconds remaining: " + millisUntilFinished / 1000);

                        } else{

                            b.setText("start timer");
                            if(mp!=null){
                                mp.stop();

                            }
                            }
                    }

                    public void onFinish() {
                        if (flag == "1") {
                            r = R.raw.alarm;
                            mp = MediaPlayer.create(getBaseContext(), r);
                            // if(mp!=null)
                            //   mp.stop();

                            mp.start();
                            b.setText("done!");
                            Intent intent = new Intent(Sec.this, NotificationReceiverActivity.class);
                            PendingIntent pIntent = PendingIntent.getActivity(Sec.this, (int) System.currentTimeMillis(), intent, 0);

                            // Build notification
                            // Actions are just fake
                            Notification noti = new Notification.Builder(Sec.this)
                                    .setContentTitle("New mail from " + "test@gmail.com")
                                    .setContentText("Subject").setSmallIcon(R.drawable.common_signin_btn_icon_dark)
                                    .setContentIntent(pIntent).build();

                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            // hide the notification after its selected
                            noti.flags |= Notification.FLAG_AUTO_CANCEL;

                            notificationManager.notify(0, noti);
                        } else {
                            countdown.cancel();
                            b.setText("start timer");
                            if (mp != null)
                                mp.stop();
                        }
                    }
                }.start();
            }
        });
        s= (Button) findViewById(R.id.stop);

     s.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // String m=b.getText().toString();
// potentially add data to the intent
                // i.putExtra("time", m);
                String m=  tota.getText().toString();
                if(m!=null){
                i.putExtra("m", m);
          i.putExtra("flag", "0");
                getApplicationContext().startService(i);
                flag="0";
               // countdown.cancel();
                b.setText("start timer");
                if(mp!=null)
                    mp.stop();

            }}
        });


    }
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service



        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MyService.COPA_RESULT)
        );
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }
  /*  private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
          //  if (intent.getAction().equals(mBroadcastStringAction)) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    //}
    };*/


/*
    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("count", 0);
            Log.i(TAG, "Countdown : " +  millisUntilFinished / 1000);
        }*/
        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(Sec.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(vidView);


            // Set MediaController for VideoView
            vidView.setMediaController(mediaController);
        }

        // When the video file ready for playback.
        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                vidView.seekTo(position);
                //    if (position == 0) {
                //        vidView.start();
                //   }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(vidView);

                    }
                });
            }
        });

    }


    @Override
    public void onClick(View v) {
        String t=title;
                simpleWebView.setWebViewClient(new MyWebViewClient());
                String url = "http://10.0.2.2/index.php?recipe="+t;
                simpleWebView.getSettings().setJavaScriptEnabled(true);
                simpleWebView.loadUrl(url); // load a web page in a web view
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", vidView.getCurrentPosition());
        vidView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        vidView.seekTo(position);
    }
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service



        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MyService.COPA_RESULT)
        );
    }


    public void jso(String title) {


        class RegisterUser extends AsyncTask<String, Void, String> {
            public String title = "";
            public String calory = "";
            public String prep = "";
            public String cook = "";
            public String photo = "";
            public String desc = "";
            public String total = "";
            public String image = "";
            public String video = "";
            public String list = "";
            public String User = "";
            public String vitc = "";
            public String pro = "";
            public String iron = "";
            public String calc = "";
            public String vitb6 = "";
            public String vitb12 = "";
            public String vite = "";
            public Integer id;
            ImageView imageview;
            public String all="";
            public String alldesc="";
            public String name1 = "";

            private LoginManager loginManager;
            ArrayList<Record> records;
            Vivsadapter vivsadapter;
            String json;
            String rating="";

            SQLiteDatabase sqLiteDatabase;
            recipeDbHelper userDbHelper3;


            ArrayAdapter<String> itemsAdapter;
            Record record;


            @Override
            public void onPreExecute() {
                //   new  as.execute();
                super.onPreExecute();


            }

            @Override
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                //listVieww=(ListView)findViewById(R.id.lv);
              //  records= new ArrayList<Record>();
Log.d("result",s);
                try {
                    userDbHelper3=new recipeDbHelper(getApplicationContext());
                    sqLiteDatabase=userDbHelper3.getWritableDatabase();

                    // Text.setText(s);
                    //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(s);
                    String j = jObj.toString();
                    JSONArray itemm = jObj.getJSONArray("result");
                    String item = itemm.toString();
                  // Text.setText(item);
                    for (int i = 0; i < itemm.length(); i++) {
                        JSONObject c = itemm.getJSONObject(i);
                        title = c.getString("title");
                        desc = c.getString("desc");
                        calory = c.getString("calory");
                        prep = c.getString("prep");
                        total = c.getString("total");
                        cook = c.getString("cook");
                        image = c.getString("image");
                        video=c.getString("video");
                        rating = c.getString("rating");
                        photo = c.getString("photo");
                        User = c.getString("username");
                        vitc = c.getString("vitc");
                        pro = c.getString("pro");
                        calc = c.getString("calc");
                        iron = c.getString("iron");
                        vitb6 = c.getString("vitb6");
                        vitb12 = c.getString("vitb12");
                        vite = c.getString("vite");

                      //  JSONArray it = c.getJSONArray("list");
                    //    list = it.toString();
                   list = c.getString("list");
                        userDbHelper3.addsqliterecipeoffline(title,list,desc,calory,prep,cook,total,image,rating,sqLiteDatabase);
                        userDbHelper3.close();
                        byte[] qrimage = Base64.decode(image.getBytes(), i);
                        bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                      imageview = (ImageView) findViewById(R.id.imagev);
                       list.replaceAll("]",".");
                        String[] separated = list.split("-");
                        int ji = 1;
                        for (; ji < separated.length; ji++) {
                            int ind=separated[ji ].lastIndexOf(",");
                            if( ind>=0 )
                                separated[ji ] = new StringBuilder(separated[ji ]).replace(ind, ind+1,".").toString();
                            all+=ji+"-"+separated[ji ]+"\n";

                        }
                        desc.replaceAll("]",".");
                        String[] separated1 = desc.split(",");
                        int ji1 = 0;
                        for (; ji1 < separated1.length; ji1++) {


                            alldesc+=ji1+1+"-"+separated1[ji1 ]+"\n";

                        }


                       // File f3=new File(Environment.getExternalStorageDirectory()+"/inpaint/"+"seconds"+".png");
                       // if(!f3.exists()){
                         //   f3.createNewFile();
                        //}
                      //  OutputStream outStream = null;
                       /* File file = new File(Environment.getExternalStorageDirectory() + "/inpaint/"+"seconds"+".png");
                        try {
                            outStream = new FileOutputStream(file);
                            bmp.compress(Bitmap.CompressFormat.PNG, 85, outStream);
                            outStream.flush();
                            outStream.close();

                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                       // Toast.makeText(getApplicationContext(),qrimage.toString(), Toast.LENGTH_SHORT).show();
                        imageview.setImageBitmap(bmp);
                        //  names.add(title);

                       // record = new Record(title, id);


                      //  records.add(record);






                    }

                    video=video.replaceAll("\\s","");
                    if(video.equals("novideo")){
                        videoLayout.setVisibility(LinearLayout.GONE);
                    }
                    else {
                        String vidAddress = "http://10.0.2.2/upload/" + video;
                        Uri vidUri = Uri.parse(vidAddress);
                        vidView.setVideoURI(vidUri);

                        vidView.requestFocus();
                    }
                    titl.setText(title);
                    des.setText(alldesc);
                    calor.setText(calory);
                    pre.setText(prep);
                    tota.setText(total);
                    coo.setText(cook);
                    Textcal.setText(calory);
                    Integ.setText(all);
                    Username.setText(User);
                    Textvitb6.setText(vitb6);

                    Textvitb12.setText(vitb12);
                    Textvite.setText(vite);
                    Textpro.setText(pro);
                    Textiron.setText(iron);
                    Textcalc.setText(calc);
                    Textvitc.setText(vitc);
                    Toast.makeText(getApplicationContext(), cook, Toast.LENGTH_LONG).show();
                    vivsadapter = new Vivsadapter(Sec.this,records);

                     /*   JSONObject list = jObj.getJSONObject("list");
                        String item = list.toString();
                        JSONArray itemm = list.getJSONArray("item");
                        for (int i = 0; i < itemm.length(); i++) {
                            JSONObject c = itemm.getJSONObject(i);
                            id = c.getInt("ndbno");

                            name = c.getString("name");


                            name1 = name + System.lineSeparator() + name1;
                            names.add(name1);

                            ids.add(id);
                         //   if (name.length() < 25) {
                             //   Record record = new Record(name, id);
                             //   db.addContact(record);
                          //  }

                        }*/
                   //  Text.setText(j);


                } catch (Exception e) {
                    e.printStackTrace();

                }

                Username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetchifData g = new GetchifData();
                        g.execute(User,title);
                    }
                });


                share.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0)  {
                        FacebookSdk.sdkInitialize(getApplicationContext());

                        String calo=calor.getText().toString();
                        CallbackManager callbackManager = CallbackManager.Factory.create();
                        ShareDialog shareDialog = new ShareDialog(Sec.this);
                        AlertDialog.Builder shareDialog2 = new AlertDialog.Builder(Sec.this);
                        ShareDialog shareDialog1 = new ShareDialog(Sec.this);
                        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                            @Override
                            public void onSuccess(Sharer.Result result) {
                                Log.d("d", "success");
                            }

                            @Override
                            public void onError(FacebookException error) {
                                Log.d("d", "error");
                            }

                            @Override
                            public void onCancel() {
                                Log.d("d", "cancel");
                            }
                        });
             /*   FacebookSdk.sdkInitialize(getApplicationContext());

                callbackManager = CallbackManager.Factory.create();

                List<String> permissionNeeds = Arrays.asList("publish_actions");

                //this loginManager helps you eliminate adding a LoginButton to your UI
                LoginManager  manager = LoginManager.getInstance();

                //  manager.logInWithPublishPermissions(this, permissionNeeds);

                manager.registerCallback(callbackManager, new FacebookCallback< LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        Log.d("d", "success");
                    }

                    @Override
                    public void onCancel()
                    {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception)
                    {
                        System.out.println("onError");
                    }
                });
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Log.d("d", "success");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("d", "error");
                    }

                    @Override
                    public void onCancel() {
                        Log.d("d", "cancel");
                    }
                });*/
            /*    Uri selectedImage= Uri.parse("/"+Environment.getExternalStorageDirectory()+"/inpaint/"+"seconds"+".png");

                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver() , Uri.parse("file:///"+Environment.getExternalStorageDirectory()+"/inpaint/"+"seconds"+".png"));
                    Toast.makeText(getApplicationContext(), bmp.toString(), Toast.LENGTH_LONG).show();


                }
                catch (Exception e)
                {
                    //handle exception
                }
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bmp)
                        .setUserGenerated(true)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("share")
                        .putString("title",title)
                        .putPhoto("image", photo)
                        .build();*/
                    try{
                        /*String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                "/MyApp/";

                        File dir = new File(file_path);
                        dir.mkdirs();
                        File file = new File(dir, "myPic.png");
                        FileOutputStream fOut = new FileOutputStream(file);

                        bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                        fOut.flush();
                        fOut.close();*/

// Share
                      ShareLinkContent content = new ShareLinkContent.Builder()
                               .setContentUrl(Uri.parse("http://10.0.2.2/share.php?recipeName="+title)).setContentTitle(title).setContentDescription("calory: "+calo).setImageUrl(Uri.parse("http://10.0.2.2/androidimages/"+photo))//.setImageUrl(Uri.fromFile(file))//.setImageUrl(Uri.parse("http://192.168.1.7/androidimages/IMG_1474711234.jpg"))//.setImageUrl(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/inpaint/"+"seconds"+".png")))
                              .build();

                        shareDialog.show(Sec.this,content);
                      /*  Intent share = new Intent(android.content.Intent.ACTION_SEND);

                        share.setType("text/plain");

                        share.putExtra(Intent.EXTRA_TEXT,"http://192.168.1.7/share.php?recipeName=hhhh");
                        share.putExtra(Intent.EXTRA_REFERRER_NAME,"http://192.168.1.7/share.php?recipeName=hhhh");

                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                       // share.putExtra(Intent.EXTRA_TEXT, "My Image");

                     startActivity(Intent.createChooser(  share, "Share via"));*/
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                      //  SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(bmp).build();

                        //SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder().addPhoto(sharePhoto).build();

                        // ShareDialog shareDialog = new ShareDialog(MainActivity.this);
                        // Toast.makeText(getApplicationContext(), sharePhotoContent.toString(), Toast.LENGTH_LONG).show();
                       // shareDialog.show(sharePhotoContent);
                        /*shareDialog2.setTitle("hhh");
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://192.168.1.7/share.php?recipeName="+title)).setContentTitle(title).setContentDescription("calory: "+calo).setImageUrl(Uri.parse("http://www.bigstockphoto.com/image-121417376/stock-photo-healthy-food-choice"))//.setImageUrl(Uri.parse("http://192.168.1.7/androidimages/IMG_1474711234.jpg"))//.setImageUrl(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/inpaint/"+"seconds"+".png")))
                        .build();
                shareDialog.show(content);*/
                        // Toast.makeText(getApplicationContext(), Environment.getExternalStorageDirectory()+"/inpaint/"+"seconds"+".png".toString(), Toast.LENGTH_LONG).show();
           /*    ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://192.168.1.7/share.php?recipeName="+title)).setContentTitle(title).setContentDescription("calory: "+calo).setImageUrl(Uri.parse("http://www.bigstockphoto.com/image-121417376/stock-photo-healthy-food-choice"))//.setImageUrl(Uri.parse("http://192.168.1.7/androidimages/IMG_1474711234.jpg"))//.setImageUrl(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/inpaint/"+"seconds"+".png")))
                        .build();

                shareDialog.show(content);*/



                    }});



            }

            @Override
            public String doInBackground(String... params) {


                try {
                    String line, newjson = "";

                    URL url = new URL("http://10.0.2.2/recipe.php");

                    String number = params[0];


                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(number,"UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                        while ((line = reader.readLine()) != null) {
                            newjson += line;

                        }

                        json = newjson.toString();
                        //JSONObject jObj = new JSONObject(json);

                        // return json;
                        return json;


                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();

                    return "false";
                }

            }
        }



        RegisterUser ru = new RegisterUser();
        ru.execute(title);


    }


    public class GetchifData extends AsyncTask<String,Void,String> {
        protected String doInBackground(String... params) {
            String login_url = "http://10.0.2.2/getchif_data.php";
            try
            {
                String chif = params[0];
                String recipe_title = params[1];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("chif","UTF-8")+"="+URLEncoder.encode(chif,"UTF-8")+"&"
                        +URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(recipe_title,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null)
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute()
        {
            String s="start";
            Log.d("get chif data",s );
        }
        @Override
        protected void onPostExecute(String result)
        {
            Log.d("get chif data",result);
            myJSON=result;
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i=0;i<peoples.length();i++){
                    JSONObject c = peoples.getJSONObject(i);
                    String db_type = c.getString("chif_type");
                    String db_appid = c.getString("chif_appid");
                    String db_faceid = c.getString("chif_faceid");
                    SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("chif_type", db_type);
                    if(db_appid.equals("noid")){
                        editor.putString("chif_id", db_faceid);
                    }
                    if(db_faceid.equals("noid")){
                        editor.putString("chif_id", db_appid);
                    }



                    editor.commit();
                }
                startActivity(new Intent(Sec.this, chifProfile.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



    public static void jsorating(String title, String rating) {


        class RegisterUser extends AsyncTask<String, Void, String> {
            public String title = "";
            public String calory = "";
            public String prep = "";
            public String cook = "";
            public String desc = "";
            public String total = "";
            public String image = "";
            public String list = "";
            public Integer id;
            public String all = "";
            public String name1 = "";
            private TextView Text;
            ArrayList<Record> records;
            Vivsadapter vivsadapter;
            String json;
            public Bitmap bmp;
            SQLiteDatabase sqLiteDatabase;
            recipeDbHelper userDbHelper3;


            ArrayAdapter<String> itemsAdapter;
            Record record;


            @Override
            public void onPreExecute() {
                //   new  as.execute();
                super.onPreExecute();


            }

            @Override
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                //listVieww=(ListView)findViewById(R.id.lv);
                //  records= new ArrayList<Record>();

              /*  try {
                    userDbHelper3=new  recipeDbHelper(getApplicationContext());
                    sqLiteDatabase=userDbHelper3.getWritableDatabase();

                    // Text.setText(s);
                    //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(s);
                    String j = jObj.toString();
                    JSONArray itemm = jObj.getJSONArray("result");
                    String item = itemm.toString();
                    // Text.setText(item);
                    for (int i = 0; i < itemm.length(); i++) {
                        JSONObject c = itemm.getJSONObject(i);
                        title = c.getString("title");
                        desc = c.getString("desc");
                        calory = c.getString("calory");
                        prep = c.getString("prep");
                        total = c.getString("total");
                        cook = c.getString("cook");
                        image = c.getString("image");

                        //  JSONArray it = c.getJSONArray("list");
                        //    list = it.toString();
                        list = c.getString("list");
                        userDbHelper3.addsqliterecipeoffline(title,list,desc,calory,prep,cook,total,image,sqLiteDatabase);
                        userDbHelper3.close();
                        byte[] qrimage = Base64.decode(image.getBytes(), i);
                        bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                        ImageView imageview = (ImageView) findViewById(R.id.imagev);
                        String[] separated = list.split("-");
                        int ji = 1;
                        for (; ji < separated.length-1; ji++) {
                            int ind=separated[ji ].lastIndexOf(",");
                            if( ind>=0 )
                                separated[ji ] = new StringBuilder(separated[ji ]).replace(ind, ind+1,".").toString();
                            all+=ji+"-"+separated[ji ]+"\n";

                        }
                        imageview.setImageBitmap(bmp);
                        //  names.add(title);

                        // record = new Record(title, id);


                        //  records.add(record);






                    }
                    titl.setText(title);
                    des.setText(desc);
                    calor.setText(calory);
                    pre.setText(prep);
                    tota.setText(total);
                    coo.setText(cook);
                    Integ.setText(all);
                    vivsadapter = new Vivsadapter(Sec.this,records);

                     /*   JSONObject list = jObj.getJSONObject("list");
                        String item = list.toString();
                        JSONArray itemm = list.getJSONArray("item");
                        for (int i = 0; i < itemm.length(); i++) {
                            JSONObject c = itemm.getJSONObject(i);
                            id = c.getInt("ndbno");

                            name = c.getString("name");


                            name1 = name + System.lineSeparator() + name1;
                            names.add(name1);

                            ids.add(id);
                         //   if (name.length() < 25) {
                             //   Record record = new Record(name, id);
                             //   db.addContact(record);
                          //  }

                        }*/
                //  Text.setText(j);


            //}

            //catch(
           // Exception e
           // )

            //{
               // e.printStackTrace();

            //}



       // }
            }

            @Override
            public String doInBackground(String... params) {


                try {
                    String line, newjson = "";

                    URL url = new URL("http://10.0.2.2/reciperating.php");

                    String number = params[0];
                    String rating = params[1];

                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(number,"UTF-8")+"&"+URLEncoder.encode("rating","UTF-8")+"="+URLEncoder.encode(rating,"UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                    while ((line = reader.readLine()) != null) {
                        newjson += line;

                    }

                    json = newjson.toString();
                    //JSONObject jObj = new JSONObject(json);

                    // return json;
                    return json;


                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();

                    return "false";
                }

            }
        }



        RegisterUser ru = new RegisterUser();
        ru.execute(title,rating);


    }
    public static void jsosqlite(String title) {


        class RegisterUser extends AsyncTask<String, Void, String> {
            public String title = "";
            public String calory = "";
            public String prep = "";
            public String cook = "";
            public String desc = "";
            public String total = "";
            public String image = "";
            public String list = "";
            public Integer id;
            public String all="";
            String rating="";

            recipeDbHelper userDbHelper3;
            recipeDbHelper userDbHelper2;
            SQLiteDatabase sqLiteDatabase;
            Cursor cursor;
            //Context context;
            public String name1 = "";

            ArrayList<Record> records;
            Vivsadapter vivsadapter;
            String json;
            public Bitmap bmp;

            ArrayAdapter<String> itemsAdapter;
            Record record;


            @Override
            public void onPreExecute() {
                //   new  as.execute();
                super.onPreExecute();


            }

            @Override
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                //listVieww=(ListView)findViewById(R.id.lv);
                //  records= new ArrayList<Record>();
              Context context= Vivsadapter.context;
                userDbHelper3=new recipeDbHelper(context);
                sqLiteDatabase=userDbHelper3.getWritableDatabase();
                try {
if(vivsadapter.isOnline()){
                    // Text.setText(s);
                    //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(s);
                    String j = jObj.toString();
                    JSONArray itemm = jObj.getJSONArray("result");
                    String item = itemm.toString();
                    // Text.setText(item);
                    for (int i = 0; i < itemm.length(); i++) {
                        JSONObject c = itemm.getJSONObject(i);
                        title = c.getString("title");
                        desc = c.getString("desc");
                        calory = c.getString("calory");
                        prep = c.getString("prep");
                        total = c.getString("total");
                        cook = c.getString("cook");
                        image = c.getString("image");
                        list = c.getString("list");
                        rating = c.getString("rating");
                        userDbHelper3.addsqliterecipe(title,list,desc,calory,prep,cook,total,image,rating,sqLiteDatabase);
                        userDbHelper3.close();
                        //  JSONArray it = c.getJSONArray("list");
                       // Toast.makeText(Vivsadapter.context, cook, Toast.LENGTH_LONG).show();
                        //    list = it.toString();

                        byte[] qrimage = Base64.decode(image.getBytes(), i);
                        bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);

                       // ImageView imageview = (ImageView) findViewById(R.id.imagev);
                        String[] separated = list.split("-");
                        int ji = 1;
                        for (; ji < separated.length-1; ji++) {
                            int ind=separated[ji ].lastIndexOf(",");
                            if( ind>=0 )
                                separated[ji ] = new StringBuilder(separated[ji ]).replace(ind, ind+1,".").toString();
                            all+=ji+"-"+separated[ji ]+"\n";

                        }

                       // imageview.setImageBitmap(bmp);
                        //  names.add(title);

                        // record = new Record(title, id);


                        //  records.add(record);






                    }}else{


    userDbHelper2=new recipeDbHelper( context);
    sqLiteDatabase=userDbHelper2.getReadableDatabase();
    cursor=userDbHelper2.SelectAllDataoffline(s,sqLiteDatabase);

    if(cursor.moveToFirst()) {
        do {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String list = cursor.getString(2);
            String descc = cursor.getString(3);
            String calory = cursor.getString(4);
            String prep = cursor.getString(5);
            String cook = cursor.getString(6);
            String total = cursor.getString(7);
            String image = cursor.getString(8);
            String rating = cursor.getString(9);

            userDbHelper2.addsqliterecipe(title,list,descc,calory,prep,cook,total,image,rating,sqLiteDatabase);
            userDbHelper2.close();
        } while (cursor.moveToNext());
     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }




}

                //    vivsadapter = new Vivsadapter(Sec.this,records);

                     /*   JSONObject list = jObj.getJSONObject("list");
                        String item = list.toString();
                        JSONArray itemm = list.getJSONArray("item");
                        for (int i = 0; i < itemm.length(); i++) {
                            JSONObject c = itemm.getJSONObject(i);
                            id = c.getInt("ndbno");

                            name = c.getString("name");


                            name1 = name + System.lineSeparator() + name1;
                            names.add(name1);

                            ids.add(id);
                         //   if (name.length() < 25) {
                             //   Record record = new Record(name, id);
                             //   db.addContact(record);
                          //  }

                        }*/
                    //  Text.setText(j);


                } catch (Exception e) {
                    e.printStackTrace();

                }



            }

            @Override
            public String doInBackground(String... params) {


                try {
                    if(vivsadapter.isOnline()) {
                        String line, newjson = "";

                        URL url = new URL("http://10.0.2.2/recipe.php");

                        String number = params[0];


                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");

                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                        while ((line = reader.readLine()) != null) {
                            newjson += line;

                        }

                        json = newjson.toString();
                        //JSONObject jObj = new JSONObject(json);

                        // return json;
                        return json;
                    }else{
                      return params[0];
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
return "false";

                }

            }
        }



        RegisterUser ru = new RegisterUser();
        ru.execute(title);


    }


}
