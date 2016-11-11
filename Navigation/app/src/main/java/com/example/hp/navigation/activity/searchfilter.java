package com.example.hp.navigation.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.navigation.drawer.activity.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.widget.SeekBar;
public class searchfilter extends BaseActivity {
    public TextView titl;
    public TextView calor;
    public TextView lis;
    public TextView tota;
    public String title = "";
    public String calory = "";
    public String list = "";
    public String cook = "";
    public String qan = "";
    public String desc = "";
    public static ListView listV;
    private ImageButton Getall;
    private Spinner spinner1;
    SeekBar simpleSeekBar;
    SQLiteDatabase sqLiteDatabase;
    public String total = "";
    public Button Search;
    public  TextView Text;
    public String[] myDataadesc;
    recipeDbHelper userDbHelper3;
    String val="";
    public AutoCompleteTextView autoCom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfilter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titl = (TextView) findViewById(R.id.title);
        Text= (TextView) findViewById(R.id.totaltime);
        calor = (TextView) findViewById(R.id.calory);
       // lis = (TextView) findViewById(R.id.list);
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        String quintity= String.valueOf(spinner1.getSelectedItem());

        switch(quintity){

            case "less than":
        qan="less";
        break;
            case "more than":
                qan="more";
                break;
            case "equal":
                qan="equal";
                break;
        }
       // tota = (TextView) findViewById(R.id.total);
       Search = (Button) findViewById(R.id.search);
        autoCom = (AutoCompleteTextView) findViewById(R.id.auto);
        final DBManager myDb = new DBManager(searchfilter.this);
        autoCom.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String s1 = s.toString();
                try {
                    final String[] myData = myDb.SelectAllData(s1);
                    //   final AutoCompleteTextView autoCom = (AutoCompleteTextView)findViewById(R.id.auto);


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(searchfilter.this,

                            android.R.layout.simple_dropdown_item_1line, myData);
                    autoCom.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        simpleSeekBar=(SeekBar)findViewById(R.id.simpleSeekBar);
        // perform seek bar change listener event used for getting the progress value
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                 val=String.valueOf(progressChangedValue);
                Text.setText("total time < "+val + " Min");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(searchfilter.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
       Getall = (ImageButton) findViewById(R.id.getall);

        Getall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor;
                Cursor cursor1;
                Cursor cursor2;
                int sum = 1;
                ListDataAdptersearch listDataAdpter;
                SQLiteDatabase sqLiteDatabase;
                recipeDbHelper userDbHelper;
                recipeDbHelper userDbHelper2;
                String food = autoCom.getText().toString().trim();
                listV = (ListView) findViewById(R.id.list_view);
                userDbHelper = new recipeDbHelper(getApplicationContext());
                sqLiteDatabase = userDbHelper.getWritableDatabase();
                userDbHelper.addinnformationsearch(food, sqLiteDatabase);

                listDataAdpter = new ListDataAdptersearch(getApplicationContext(), R.layout.row_layout1);
                listV.setAdapter(listDataAdpter);
                userDbHelper2 = new recipeDbHelper(getApplicationContext());
                sqLiteDatabase = userDbHelper2.getReadableDatabase();
                cursor2=userDbHelper2.getinformationidsearch(sqLiteDatabase);
                cursor=userDbHelper2.getinformationsearch(sqLiteDatabase);

                if(cursor2.moveToFirst()&&cursor.moveToFirst())
                {
                    do {
                        String descname=cursor.getString(0);
                        int id=cursor2.getInt(0);
                        DataProviderdesc dataProvider = new DataProviderdesc(descname,id);
                        listDataAdpter.add(dataProvider);


                    }while (cursor2.moveToNext()&&cursor.moveToNext());

                }

                setListViewHeightBasedOnItems(listV);
                // jso1(numberAsString);

            }

        });
        Search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                title=titl.getText().toString();
                calory=  calor.getText().toString();
//                total=  tota.getText().toString();
//                list=  lis.getText().toString();
                Intent i=new Intent(searchfilter.this,searchactivity.class);
                i.putExtra("title", title);
                i.putExtra("calory", calory);
                userDbHelper3=new recipeDbHelper(getApplicationContext());
                sqLiteDatabase=userDbHelper3.getWritableDatabase();
                myDataadesc = userDbHelper3.SelectAllsearch();
                list=(Arrays.toString(myDataadesc));
               i.putExtra("list", list);
                i.putExtra("total", val);
                i.putExtra("qan", qan);
                i.putExtra("type", "search");
                startActivity(i);
                // String m=b.getText().toString();
// potentially add data to the intent

            }
        });


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            int itemPos = 0;
            for (; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

}
