package com.example.eodwan.showrecipe;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
public class Sec extends AppCompatActivity {
    String title;
    public TextView titl;
    public TextView calor;
    public TextView pre;
    public TextView des;
    public TextView coo;
    public TextView tota;
    public TextView Integ;
    public TextView T;

    BroadcastReceiver receiver;
    public static final String mBroadcastStringAction = "com.truiton.broadcast.string";
    private final static String TAG = "BroadcastService";
   public Intent i;
String flag="0";
    int ii;
    CountDownTimer countdown= null;;
    public MediaPlayer mp;

    int r=0;
    int count = 0;
    boolean[] timerProcessing = { false };
    boolean[] timerStarts = { false };
   // public MyCount timer;
    public Button b;
    public Button s;
    public Button Go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b = (Button) findViewById(R.id.totalbutton);
       // Go = (Button) findViewById(R.id.go);
        title=getIntent().getStringExtra("title");
        titl = (TextView) findViewById(R.id.title1);
        calor = (TextView) findViewById(R.id.calory1);
        des = (TextView) findViewById(R.id.desc1);
        pre = (TextView) findViewById(R.id.prep1);
        coo = (TextView) findViewById(R.id.cook1);
        tota = (TextView) findViewById(R.id.total1);
        Integ = (TextView) findViewById(R.id.integ);
        i= new Intent(getApplicationContext(), MyService.class);
        //.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
        jso(title);
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
            public String desc = "";
            public String total = "";
            public String image = "";
            public String list = "";
            public Integer id;
            public String all="";
            public String name1 = "";
            private TextView Text;
            ArrayList<Record> records;
            Vivsadapter vivsadapter;
            String json;
            String rating="";
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

                try {
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
                        rating = c.getString("rating");

                      //  JSONArray it = c.getJSONArray("list");
                    //    list = it.toString();
                   list = c.getString("list");
                        userDbHelper3.addsqliterecipeoffline(title,list,desc,calory,prep,cook,total,image,rating,sqLiteDatabase);
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


                } catch (Exception e) {
                    e.printStackTrace();

                }



            }

            @Override
            public String doInBackground(String... params) {
                Text = (TextView) findViewById(R.id.textt);

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
              Context context=Vivsadapter.context;
                userDbHelper3=new  recipeDbHelper(context);
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
