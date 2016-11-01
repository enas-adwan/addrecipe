package com.example.hp.navigation.activity;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.net.ConnectivityManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.net.NetworkInfo;

import com.navigation.drawer.activity.R;

public class ShowRecipe extends BaseActivity {
    recipeDbHelper userDbHelper2;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ArrayList<Record> records;
    Cursor cursor1;
    Cursor cursor2;
    Cursor cursor4;
    public static ListView listView;
    Record record;

    Vivsadapter vivsadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.showrecipe, frameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=(ListView)findViewById(R.id.lv);
        records= new ArrayList<Record>();
        //vivs=new Vivsadapter(getApplicationContext(),R.layout.single_row);
        userDbHelper2=new recipeDbHelper(getApplicationContext());
        sqLiteDatabase=userDbHelper2.getReadableDatabase();
        cursor=userDbHelper2.getinformationsqlite(sqLiteDatabase);
        cursor1=userDbHelper2.getinformation2sqlite(sqLiteDatabase);
        cursor2=userDbHelper2.getinformationidsqlite(sqLiteDatabase);
        cursor4=userDbHelper2.getinformationidsqliteranking(sqLiteDatabase);


        if(cursor.moveToFirst()&&cursor1.moveToFirst()&&cursor2.moveToFirst()&&cursor4.moveToFirst())
        {
            do {
                int id=cursor2.getInt(0);
                String title=cursor.getString(0);
                String image =cursor1.getString(0);


                String rating=cursor4.getString(0);;

                record = new Record(title, id,image,rating);


                records.add(record);
                vivsadapter = new Vivsadapter(ShowRecipe.this,records);
                listView.setAdapter(vivsadapter);
                listView.setItemsCanFocus(true);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i=new Intent(ShowRecipe.this,Showonefav.class);
                        i.putExtra("title",vivsadapter.gettitle(position));
                        startActivity(i);
                        //Toast.makeText(getApplicationContext(),vivsadapter.gettitle(position), Toast.LENGTH_LONG).show();
                    }
                });
            }while (cursor.moveToNext()&&cursor1.moveToNext()&&cursor2.moveToNext()&&cursor4.moveToNext());

        }

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/









    }


}
