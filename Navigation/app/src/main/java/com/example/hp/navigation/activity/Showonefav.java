package com.example.hp.navigation.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.navigation.drawer.activity.R;

import java.util.concurrent.TimeUnit;

public class Showonefav extends AppCompatActivity {
    String title;
    recipeDbHelper userDbHelper2;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Cursor cursor1;
    Cursor cursor2;
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
    public Bitmap bmp;
    public String all="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showonefav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     title=getIntent().getStringExtra("title");
        b = (Button) findViewById(R.id.totalbutton);
        //Go = (Button) findViewById(R.id.go);
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
                imageview.setImageBitmap(bmp);
                //  names.add(title);

                // record = new Record(title, id);


                //  records.add(record);







            titl.setText(title);
            des.setText(descc);
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
    }}
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

}
