package com.example.hp.navigation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.navigation.drawer.activity.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class oldTraking extends BaseActivity  {

    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteListAdapter ListAdapter ;
    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> RecipeNAME_ArrayList = new ArrayList<String>();
    ArrayList<String> calory_ArrayList = new ArrayList<String>();
    ArrayList<String> date_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;
    public TextView calory_sum;
    public  Float sum=0.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_oldtraking, frameLayout);
        mDrawerList.setItemChecked(position, true);
        LISTVIEW = (ListView) findViewById(R.id.listView1);
        calory_sum = (TextView) findViewById(R.id.calorySum);
           //take date selected by user
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            String date2=pref.getString("old_date","defult");
           recipeDbHelper db = new recipeDbHelper(this);

         Log.d("Insert: ", "Inserting ..");
       //    db.addContact(new Contact("pasta",4.0f,"2016-10-14"));
        //  db.addContact(new Contact("pizza",5.0f,date2));
        //  db.addContact(new Contact( "mash potato",6.0f,date2));
        //  db.addContact(new Contact( "crepe",7.0f,date2));
            // Reading all contacts
          Log.d("Reading: ", "Reading all contacts..");
           List<Contact> contacts = db.getAllContacts(date2);
          for (Contact cn : contacts) {
              String log = "Id: " + cn.getID() + " ,;Name: " + cn.getName() +" ,calory:" +cn.getCalory() +",date:"+cn.getDateTime();
              // Writing Contacts to log
              sum +=cn.getCalory();
              ID_ArrayList.add(Integer.toString(cn.getID()));

              RecipeNAME_ArrayList.add(cn.getName());

             calory_ArrayList.add( Float.toString(cn.getCalory()));

              date_ArrayList.add(cn.getDateTime());
              Log.d("Name: ", log);
           }

        ListAdapter = new SQLiteListAdapter(oldTraking.this,

                ID_ArrayList,
                RecipeNAME_ArrayList,
                calory_ArrayList,
                date_ArrayList

        );

        LISTVIEW.setAdapter(ListAdapter);
calory_sum.setText(Float.toString(sum));





    }


    }









