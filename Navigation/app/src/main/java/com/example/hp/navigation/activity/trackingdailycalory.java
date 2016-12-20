package com.example.hp.navigation.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.navigation.drawer.activity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class trackingdailycalory extends BaseActivity {
    public static TextView Text;
    public static TextView textR;
    public static TextView textcalory;
    public SearchView searchView;
    public static ListView listView;
    public static ListView listV;
    public static ImageButton Getall;
    public static ImageButton Getall1;
    public static Spinner spinner1;
    public static Cursor cursorr;
    public static recipeDbHelper userDbHelper2;
    public static Cursor cursor1;
    public static Cursor cursor2;
    public static String food1;
    public static  ListDataAdptertrack listDataAdpter;
    public static SQLiteDatabase sqLiteDatabase;
    public static recipeDbHelper userDbHelper;
    public static Spinner spinner2;
    public static Spinner spinner3;
    public static Button Suggest;
    public static class MyFragment1 extends Fragment {

        private String[] strArrData = {"No Suggestions"};

        public AutoCompleteTextView autoCom;
        public AutoCompleteTextView autoCom1;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.content_trackingdailycalory, null);

            Text = (TextView) view.findViewById(R.id.text);
            spinner1 = (Spinner) view.findViewById(R.id.spinner1);
            spinner2 = (Spinner) view.findViewById(R.id.spinner2);
            listView = (ListView) view.findViewById(R.id.list_view);

            spinner3 = (Spinner) view.findViewById(R.id.spinner3);
            new AsyncFetch().execute();
            SQLiteDatabase sqLiteDatabase;
            recipeDbHelper userDbHelper;
            Cursor cursor;
            userDbHelper = new recipeDbHelper(getActivity().getApplicationContext());
            sqLiteDatabase = userDbHelper.getReadableDatabase();
            cursor = userDbHelper.getinformationtracking(sqLiteDatabase);
            textcalory = (TextView) view.findViewById(R.id.textc);
            textR = (TextView) view.findViewById(R.id.textr);

            listView = (ListView) view.findViewById(R.id.list_view);


            if (cursor.moveToFirst()) {
                do {
                    String bmr = cursor.getString(0);
                    Double b = Double.valueOf(bmr);
                    Double bm = Math.ceil(b);
                    String bmr1 = bm.toString();
                    Toast.makeText(getActivity(), bmr1, Toast.LENGTH_LONG).show();
                    Text.setText(bmr1);
                    textR.setText(bmr1);
                    trackingdailycalory.textR.setTextColor(Color.BLACK);
                } while (cursor.moveToNext());

            }



            userDbHelper = new recipeDbHelper(getActivity());
            sqLiteDatabase = userDbHelper.getReadableDatabase();
            cursor = userDbHelper.getinformationtracking(sqLiteDatabase);
            listDataAdpter = new ListDataAdptertrack(getActivity(), R.layout.row_layout);
            listView.setAdapter(listDataAdpter);
            userDbHelper2 = new recipeDbHelper(getActivity());
            sqLiteDatabase = userDbHelper2.getReadableDatabase();
            cursorr = userDbHelper2.getinformationtrack(sqLiteDatabase);
            cursor1 = userDbHelper2.getinformation2track(sqLiteDatabase);
            cursor2 = userDbHelper2.getinformationidtrack(sqLiteDatabase);
            Suggest = (Button) view.findViewById(R.id.suggest);


            if (cursorr.moveToFirst() && cursor1.moveToFirst() && cursor2.moveToFirst()) {
                do {
                    int id = cursor2.getInt(0);
                    Float M = cursor1.getFloat(0);
                    String caloryvalue = M.toString();
                    String calory = cursorr.getString(0);

                    ArrayList<Float> sums = userDbHelper2.getTotaltrack(sqLiteDatabase);
                    Float calorysum = sums.get(0);


                    textcalory.setText(String.valueOf(calorysum));
                    String c = textcalory.getText().toString();
                    String ca = Text.getText().toString();
                    Float c1 = Float.valueOf(c);
                    Float ca1 = Float.valueOf(ca);
                    Float re = ca1 - c1;
                    String remain = re.toString();
                    if (re > 0) {

                      textR.setTextColor(Color.BLACK);
                    } else {
                        textR.setTextColor(Color.RED);
                    }
                   textR.setText(remain);
                    DataProvider dataProvider = new DataProvider(calory, caloryvalue, id);
                    listDataAdpter.add(dataProvider);

                }
                while (cursorr.moveToNext() && cursor1.moveToNext() && cursor2.moveToNext());

            }
            setListViewHeightBasedOnItems(listView);

            autoCom = (AutoCompleteTextView) view.findViewById(R.id.auto);
            final DBManager myDb = new DBManager(getActivity());
            autoCom.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String s1 = s.toString();
                    try {
                        final String[] myData = myDb.SelectAllData(s1);
                        //   final AutoCompleteTextView autoCom = (AutoCompleteTextView)findViewById(R.id.auto);


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),

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
            Suggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                String sugg=    textR.getText().toString();
                    Intent i=new Intent(view.getContext(),searchactivity.class);
                    i.putExtra("title", "");
                    i.putExtra("calory", sugg);

                    i.putExtra("list", "");
                    i.putExtra("total", "");
                    i.putExtra("qan", "less");
                    i.putExtra("ch", "");
                    i.putExtra("type", "search");
                    startActivity(i);
                }

            });

            final String[] from = new String[]{"title"};
            final int[] to = new int[]{android.R.id.text1};

            autoCom1 = (AutoCompleteTextView) view.findViewById(R.id.auto1);
            //   final DBManager myDb1 = new DBManager(trackingdailycalory.this);
            autoCom1.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String s1 = s.toString();
                    try {
                        //  final String[] myData = myDb.SelectAllData(s1);
                        //   final AutoCompleteTextView autoCom = (AutoCompleteTextView)findViewById(R.id.auto);


                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),

                                android.R.layout.simple_dropdown_item_1line, strArrData);
                        autoCom1.setAdapter(adapter1);
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
            Getall = (ImageButton) view.findViewById(R.id.getall);

            Getall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor;

                    Cursor cursor2;
                    ListDataAdpterdesc listDataAdpter;
                    SQLiteDatabase sqLiteDatabase;
                    recipeDbHelper userDbHelper;
                    recipeDbHelper userDbHelper2;
                    userDbHelper = new recipeDbHelper(getActivity());
                    listView = (ListView) view.findViewById(R.id.list_view);


                    food1 = autoCom.getText().toString().trim();
                    ArrayList<String> m = myDb.findcalory(food1);
                    String cal = m.get(0);
                    if (cal == null) {
                        cal = "0";


                    }
                    calorycalc(cal);
            /*  userDbHelper.addinnformationtrack(food, calory,sqLiteDatabase);

                listDataAdpter = new ListDataAdpterdesc(getApplicationContext(), R.layout.row_layout1);
                listV.setAdapter(listDataAdpter);
                userDbHelper2 = new recipeDbHelper(getApplicationContext());
                sqLiteDatabase = userDbHelper2.getReadableDatabase();
                cursor2=userDbHelper2.getinformationiddesc(sqLiteDatabase);
                cursor=userDbHelper2.getinformationdesc(sqLiteDatabase);

                if(cursor2.moveToFirst()&&cursor.moveToFirst())
                {
                    do {
                        String descname=cursor.getString(0);
                        int id=cursor2.getInt(0);
                        DataProviderdesc dataProvider = new DataProviderdesc(descname,id);
                        listDataAdpter.add(dataProvider);


                    }while (cursor2.moveToNext()&&cursor.moveToNext());

                }

                setListViewHeightBasedOnItems(listV);*/

                    // setListViewHeightBasedOnItems(listV);
                    // jso1(numberAsString);

                }

            });
            Getall1 = (ImageButton) view.findViewById(R.id.getall2);

            Getall1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor;

                    Cursor cursor2;

                    ListDataAdpterdesc listDataAdpter;
                    SQLiteDatabase sqLiteDatabase;
                    recipeDbHelper userDbHelper;
                    recipeDbHelper userDbHelper2;
                    //String food = Desc.getText().toString().trim();


                    String food = autoCom1.getText().toString().trim();
                    jso(food);


                }

            });

            return view;
        }


        public void jso(String title) {


            class RegisterUser extends AsyncTask<String, Void, String> {
                public String title = "";
                public String calory = "";

                public Integer id;
                ImageView imageview;
                public String all = "";
                public String alldesc = "";
                public String name1 = "";

                private LoginManager loginManager;
                ArrayList<Record> records;
                Vivsadapter vivsadapter;
                String json;
                String rating = "";

                SQLiteDatabase sqLiteDatabase;
                recipeDbHelper userDbHelper3;
                recipeDbHelper userDbHelper2;
                ListDataAdptertrack listDataAdpter;
                recipeDbHelper userDbHelper;
                Float amount;
                Cursor cursor;
                Cursor cursor1;
                Cursor cursor2;

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


                    String quintity = String.valueOf(spinner3.getSelectedItem());

                    userDbHelper = new recipeDbHelper(getActivity());
                    sqLiteDatabase = userDbHelper.getWritableDatabase();
                    //   Text.setText(valuo);


                    switch (quintity) {
                        case "1":
                            amount = 1f;
                            break;
                        case "1/4":
                            amount = 1 / 4f;
                            break;
                        case "1/3":
                            amount = 1 / 3f;
                            break;
                        case "1/2":
                            amount = 1 / 2f;
                            break;
                        case "3/4":
                            amount = 3 / 4f;
                            break;
                        case "1/8":
                            amount = 1 / 8f;
                            break;
                        case "2/3":
                            amount = 2 / 3f;
                            break;


                    }
                   // Log.d("result", s);
                    try {
                        userDbHelper3 = new recipeDbHelper(getActivity());
                        sqLiteDatabase = userDbHelper3.getWritableDatabase();

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

                            calory = c.getString("calory");
                            Float value=Float.parseFloat(calory)*amount;

                            userDbHelper.addinnformationtrack(value, "- " + quintity + " recipe of " + title, sqLiteDatabase);
                            listDataAdpter = new ListDataAdptertrack(getActivity(), R.layout.row_layout);
                            listView.setAdapter(listDataAdpter);
                            userDbHelper2 = new recipeDbHelper(getActivity());
                            sqLiteDatabase = userDbHelper2.getReadableDatabase();



                            cursor = userDbHelper2.getinformationtrack(sqLiteDatabase);
                            cursor1 = userDbHelper2.getinformation2track(sqLiteDatabase);
                            cursor2 = userDbHelper2.getinformationidtrack(sqLiteDatabase);


                            if (cursor.moveToFirst() && cursor1.moveToFirst() && cursor2.moveToFirst()) {
                                do {
                                    int id = cursor2.getInt(0);
                                    calory = cursor.getString(0);
                                    Float M = cursor1.getFloat(0);
                                    String  caloryvalue = M.toString();
                                    ArrayList<Float> sums = userDbHelper2.getTotaltrack(sqLiteDatabase);
                                    Float calorysum = sums.get(0);

                                    textcalory.setText(String.valueOf(calorysum));
                                    String c2=textcalory.getText().toString();

                                    String ca=Text.getText().toString();
                                    Float c1 =Float.valueOf(c2);
                                    Float ca1 =Float.valueOf(ca);
                                    Float re=ca1-c1;
                                    String remain=re.toString();
                                    if    (re>0){

                                     textR.setTextColor(Color.BLACK);
                                    } else{
                                   textR.setTextColor(Color.RED);
                                    }
                                  textR.setText(remain);

                                    DataProvider dataProvider = new DataProvider(calory, caloryvalue, id);
                                    listDataAdpter.add(dataProvider);

                                    setListViewHeightBasedOnItems(listView);
                                }
                                while (cursor.moveToNext() && cursor1.moveToNext() && cursor2.moveToNext());

                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public String doInBackground (String...params) {


                    try {
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


                    } catch (Exception e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                        return json;

                    }
                }
            }


            RegisterUser ru = new RegisterUser();
            ru.execute(title);


        }


        private void calorycalc(String calory) {


            class RegisterUser extends AsyncTask<String, Void, String> {


                Float value;

                Float valuevitc;
                String food;

                Cursor cursor;
                Cursor cursor1;
                Cursor cursor2;
                ListDataAdptertrack listDataAdpter;
                SQLiteDatabase sqLiteDatabase;
                recipeDbHelper userDbHelper;
                recipeDbHelper userDbHelper2;

                Float amount;

                Integer r = 0;


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);


                    // spinner1 = (Spinner) findViewById(R.id.spinner1);
                    // spinner2 = (Spinner) findViewById(R.id.spinner2);
                    String quintity = String.valueOf(spinner1.getSelectedItem());
                    String quintity1 = String.valueOf(spinner2.getSelectedItem());
                    userDbHelper = new recipeDbHelper(getActivity());
                    sqLiteDatabase = userDbHelper.getWritableDatabase();

                    String calory;
                    String caloryvalue;


                    try {

                        //   Text.setText(valuo);

                        value = Float.parseFloat(s);

                        switch (quintity1) {
                            case "1":
                                amount = 1f;
                                break;
                            case "1/4":
                                amount = 1 / 4f;
                                break;
                            case "1/3":
                                amount = 1 / 3f;
                                break;
                            case "1/2":
                                amount = 1 / 2f;
                                break;
                            case "3/4":
                                amount = 3 / 4f;
                                break;
                            case "1/8":
                                amount = 1 / 8f;
                                break;
                            case "2/3":
                                amount = 2 / 3f;
                                break;


                        }
                        //  String  foodtoeat = autoCom1.getText().toString().trim();
                        //Toast.makeText(getApplicationContext(), food , Toast.LENGTH_LONG).show();


                        switch (quintity) {

                            case "Spoon":
                                value = (value / 6.666666666666667f) * amount;

                                userDbHelper.addinnformationtrack(value, "- " + quintity1 + " Spoon of " + food1, sqLiteDatabase);

                                listDataAdpter = new ListDataAdptertrack(getActivity(), R.layout.row_layout);
                                listView.setAdapter(listDataAdpter);
                                userDbHelper2 = new recipeDbHelper(getActivity());
                                sqLiteDatabase = userDbHelper2.getReadableDatabase();
                                cursor = userDbHelper2.getinformationtrack(sqLiteDatabase);
                                cursor1 = userDbHelper2.getinformation2track(sqLiteDatabase);
                                cursor2 = userDbHelper2.getinformationidtrack(sqLiteDatabase);

                                if (cursor.moveToFirst() && cursor1.moveToFirst() && cursor2.moveToFirst()) {
                                    do {
                                        int id = cursor2.getInt(0);
                                        Float M = cursor1.getFloat(0);
                                        caloryvalue = M.toString();
                                        calory = cursor.getString(0);

                                        ArrayList<Float> sums = userDbHelper2.getTotaltrack(sqLiteDatabase);
                                        Float calorysum = sums.get(0);

                                        textcalory.setText(String.valueOf(calorysum));
                                        String c=textcalory.getText().toString();
                                        String ca=Text.getText().toString();
                                        Float c1 =Float.valueOf(c);
                                        Float ca1 =Float.valueOf(ca);
                                        Float re=ca1-c1;
                                        String remain=re.toString();
                                        if    (re>=0){
                                        textR.setTextColor(Color.BLACK);
                                        } else{
                                         textR.setTextColor(Color.RED);
                                        }
                                     textR.setText(remain);



                                        DataProvider dataProvider = new DataProvider(calory, caloryvalue, id);
                                        listDataAdpter.add(dataProvider);

                                    }
                                    while (cursor.moveToNext() && cursor1.moveToNext() && cursor2.moveToNext());

                                }
                                //  userDbHelper2.close();
                                break;

                            case "Cup":
                                value = (value * 1.1f) * amount;

                                userDbHelper.addinnformationtrack(value, "- " + quintity1 + " Cup of" + food1, sqLiteDatabase);

                                listDataAdpter = new ListDataAdptertrack(getActivity(), R.layout.row_layout);
                                listView.setAdapter(listDataAdpter);
                                userDbHelper2 = new recipeDbHelper(getActivity());
                                sqLiteDatabase = userDbHelper2.getReadableDatabase();
                                cursor = userDbHelper2.getinformationtrack(sqLiteDatabase);
                                cursor1 = userDbHelper2.getinformation2track(sqLiteDatabase);

                                cursor2 = userDbHelper2.getinformationidtrack(sqLiteDatabase);

                                if (cursor.moveToFirst() && cursor1.moveToFirst() && cursor2.moveToFirst()) {
                                    do {
                                        int id = cursor2.getInt(0);
                                        Float M = cursor1.getFloat(0);
                                        caloryvalue = M.toString();
                                        calory = cursor.getString(0);
                                        ArrayList<Float> sums = userDbHelper2.getTotaltrack(sqLiteDatabase);
                                        Float calorysum = sums.get(0);

                                     textcalory.setText(String.valueOf(calorysum));
                                        String c=textcalory.getText().toString();
                                        String ca=Text.getText().toString();
                                        Float c1 =Float.valueOf(c);
                                        Float ca1 =Float.valueOf(ca);
                                        Float re=ca1-c1;
                                        String remain=re.toString();
                                        textR.setText(String.valueOf(remain));
                                        if    (re>0){

                                         textR.setTextColor(Color.BLACK);
                                        } else{
                                            textR.setTextColor(Color.RED);
                                        }


                                        DataProvider dataProvider = new DataProvider(calory, caloryvalue, id);
                                        listDataAdpter.add(dataProvider);

                                    }
                                    while (cursor.moveToNext() && cursor1.moveToNext() && cursor2.moveToNext());

                                }
                                break;
                            case "Pound":
                                valuevitc = valuevitc * 453.59237f * amount;
                                value = value * 453.59237f * amount;

                                userDbHelper.addinnformationtrack(value, "- " + quintity1 + " pounds" + food1, sqLiteDatabase);

                                listDataAdpter = new ListDataAdptertrack(getActivity(), R.layout.row_layout);
                                listView.setAdapter(listDataAdpter);
                                userDbHelper2 = new recipeDbHelper(getActivity());
                                sqLiteDatabase = userDbHelper2.getReadableDatabase();
                                cursor = userDbHelper2.getinformationtrack(sqLiteDatabase);
                                cursor1 = userDbHelper2.getinformation2track(sqLiteDatabase);
                                cursor2 = userDbHelper2.getinformationidtrack(sqLiteDatabase);


                                if (cursor.moveToFirst() && cursor1.moveToFirst() && cursor2.moveToFirst()) {
                                    do {
                                        int id = cursor2.getInt(0);
                                        calory = cursor.getString(0);
                                        Float M = cursor1.getFloat(0);
                                        caloryvalue = M.toString();
                                        ArrayList<Float> sums = userDbHelper2.getTotaltrack(sqLiteDatabase);
                                        Float calorysum = sums.get(0);

                                        textcalory.setText(String.valueOf(calorysum));
                                        String c=textcalory.getText().toString();
                                        String ca=Text.getText().toString();
                                        Float c1 =Float.valueOf(c);
                                        Float ca1 =Float.valueOf(ca);
                                        Float re=ca1-c1;
                                        String remain=re.toString();
                                        if    (re>0){

                                        textR.setTextColor(Color.BLACK);
                                        } else{
                                            textR.setTextColor(Color.RED);
                                        }
                                        textR.setText(remain);

                                        DataProvider dataProvider = new DataProvider(calory, caloryvalue, id);
                                        listDataAdpter.add(dataProvider);

                                    }
                                    while (cursor.moveToNext() && cursor1.moveToNext() && cursor2.moveToNext());

                                }
                                break;
                            case "Ounce":
                                valuevitc = valuevitc * 28.3495231f * amount;
                                value = value * 28.3495231f * amount;

                                userDbHelper.addinnformationtrack(value, "- " + quintity1 + " Ounce of " + food1, sqLiteDatabase);

                                listDataAdpter = new ListDataAdptertrack(getActivity(), R.layout.row_layout);
                                listView.setAdapter(listDataAdpter);

                                userDbHelper2 = new recipeDbHelper(getActivity());
                                sqLiteDatabase = userDbHelper2.getReadableDatabase();
                                cursor = userDbHelper2.getinformationtrack(sqLiteDatabase);
                                cursor1 = userDbHelper2.getinformation2track(sqLiteDatabase);
                                cursor2 = userDbHelper2.getinformationidtrack(sqLiteDatabase);


                                if (cursor.moveToFirst() && cursor1.moveToFirst() && cursor2.moveToFirst()) {
                                    do {
                                        int id = cursor2.getInt(0);
                                        calory = cursor.getString(0);
                                        Float M = cursor1.getFloat(0);
                                        caloryvalue = M.toString();
                                        ArrayList<Float> sums = userDbHelper2.getTotaltrack(sqLiteDatabase);
                                        Float calorysum = sums.get(0);

                                        textcalory.setText(String.valueOf(calorysum));
                                        String c=textcalory.getText().toString();
                                        String ca=Text.getText().toString();
                                        Float c1 =Float.valueOf(c);
                                        Float ca1 =Float.valueOf(ca);
                                        Float re=ca1-c1;
                                        String remain=re.toString();
                                        if    (re>0){

                                           textR.setTextColor(Color.BLACK);
                                        } else{
                                            textR.setTextColor(Color.RED);
                                        }
                                        textR.setText(remain);

                                        DataProvider dataProvider = new DataProvider(calory, caloryvalue, id);
                                        listDataAdpter.add(dataProvider);

                                    }
                                    while (cursor.moveToNext() && cursor1.moveToNext() && cursor2.moveToNext());

                                }
                                break;


                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    setListViewHeightBasedOnItems(listView);

                }


                @Override
                protected String doInBackground(String... params) {
                    return params[0];

                }
            }

            RegisterUser ru = new RegisterUser();
            ru.execute(calory);


        }



        private class AsyncFetch extends AsyncTask<String, String, String> {

            ProgressDialog pdLoading = new ProgressDialog(getActivity());
            HttpURLConnection conn;
            URL url = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("wait", "the result");
                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();

            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    // Enter URL address where your php file resides or your JSON file address
                    url = new URL("http://10.0.2.2/fetch_all.php");

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return e.toString();
                }
                try {

                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // setDoOutput to true as we receive data
                    conn.setDoOutput(true);
                    conn.connect();

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return e1.toString();
                }

                try {

                    int response_code = conn.getResponseCode();

                    // Check if successful connection made
                    if (response_code == HttpURLConnection.HTTP_OK) {

                        // Read data sent from server
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        // Pass data to onPostExecute method
                        return (result.toString());

                    } else {
                        return ("Connection error");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                } finally {
                    conn.disconnect();
                }


            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("finish", "the result");
                //this method will be running on UI thread
                ArrayList<String> dataList = new ArrayList<String>();
                pdLoading.dismiss();
                if (result.equals("no rows")) {

                    // Do some action if no data from database

                } else {

                    try {

                        JSONArray jArray = new JSONArray(result);

                        // Extract data from json and store into ArrayList
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            dataList.add(json_data.getString("title"));
                        }

                        strArrData = dataList.toArray(new String[dataList.size()]);
                        String strint = String.valueOf(strArrData.length);
                        Toast.makeText(getActivity(), strint, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        // You to understand what actually error is and handle it appropriately
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG).show();
                    }

                }

            }

        }







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


    public static class MyFragment2 extends Fragment {
        SQLiteDatabase SQLITEDATABASE;
        Cursor cursor;
        SQLiteListAdapter ListAdapter ;
        ArrayList<String> ID_ArrayList = new ArrayList<String>();
        ArrayList<String> RecipeNAME_ArrayList = new ArrayList<String>();
        ArrayList<String> calory_ArrayList = new ArrayList<String>();
        ArrayList<String> date_ArrayList = new ArrayList<String>();
        ListView LISTVIEW;
        public TextView calory_sum;
        public TextView textR;
        public TextView Comment;
        public TextView Text;
        public  Float sum=0.0f;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_oldtraking, null);

            LISTVIEW = (ListView) view.findViewById(R.id.listView1);
            textR = (TextView) view.findViewById(R.id.textr);
            Text = (TextView) view.findViewById(R.id.text);
            Comment= (TextView) view.findViewById(R.id.comment);
            calory_sum = (TextView) view.findViewById(R.id.calorySum);
            SQLiteDatabase sqLiteDatabase;
            recipeDbHelper userDbHelper;
            Cursor cursor;
            userDbHelper = new recipeDbHelper(getActivity().getApplicationContext());
            sqLiteDatabase = userDbHelper.getReadableDatabase();
            cursor = userDbHelper.getinformationtracking(sqLiteDatabase);
            //take date selected by user
            SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
            String date2=pref.getString("old_date","defult");

            recipeDbHelper db = new recipeDbHelper(view.getContext());

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


         //   Log.d("Insert: ", "Inserting ..");
          //     db.addContact(new Contact("pasta",4.0f,"2016-10-14"));
           //   db.addContact(new Contact("pizza",5.0f,date2));
             // db.addContact(new Contact( "mash potato",6.0f,date2));
           //   db.addContact(new Contact( "crepe",7.0f,date2));
            //Reading all contacts


                ID_ArrayList.add(Integer.toString(cn.getID()));

                RecipeNAME_ArrayList.add(cn.getName());

                calory_ArrayList.add( Float.toString(cn.getCalory()));

                date_ArrayList.add(cn.getDateTime());
                Log.d("Name: ", log);
            }


// refresh the View
            LISTVIEW.setAdapter(null);
//            ListAdapter.notifyDataSetChanged();
            ListAdapter = new SQLiteListAdapter(getActivity(),

                    ID_ArrayList,

                    RecipeNAME_ArrayList,


                    calory_ArrayList,


                    date_ArrayList

            );

            LISTVIEW.setAdapter(ListAdapter);
            calory_sum.setText(Float.toString(sum));

            if (cursor.moveToFirst()) {
                do {
                    String bmr = cursor.getString(0);
                    Double b = Double.valueOf(bmr);
                    Double bm = Math.ceil(b);
                    String bmr1 = bm.toString();
                    Toast.makeText(getActivity(), bmr1, Toast.LENGTH_LONG).show();

                    textR.setText(bmr1);
                    Text.setText(bmr1);
                  textR.setTextColor(Color.BLACK);
                    String c =  calory_sum.getText().toString();
                    String ca = Text.getText().toString();
                    Float c1 = Float.valueOf(c);
                    Float ca1 = Float.valueOf(ca);
                    Float re = ca1 - c1;
                    String remain = re.toString();
                    textR.setText(remain);
                    if (re > 0) {

                        textR.setTextColor(Color.BLACK);
                    } else {
                        textR.setTextColor(Color.RED);
                        Comment.setText("Please Try in the coming days to keep track with your daily calory neded.");
                        Comment.setTextColor(Color.RED);
                    }
                } while (cursor.moveToNext());



            }


            return view;

        }
    }



    /////////////////////////////////////////////////////////////
    FrameLayout container;
    FragmentManager myFragmentManager;
    MyFragment1 myFragment1;
    MyFragment2 myFragment2;
    private AppBarLayout mAppBarLayout;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", /*Locale.getDefault()*/Locale.ENGLISH);

    private CompactCalendarView mCompactCalendarView;

    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_trackingdailycalory);

        getLayoutInflater().inflate(R.layout.activity_trackingdailycalory, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);

        container = (FrameLayout)findViewById(R.id.maincontainer);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        // Set up the CompactCalendarView
        mCompactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        // Force English
        mCompactCalendarView.setLocale(TimeZone.getDefault(), Locale.ENGLISH);

        mCompactCalendarView.setShouldDrawDaysHeader(true);

        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
                //get selected date from calender
                String time_str = dateFormat.format(dateClicked);
                String[] s = time_str.split("-");
                int year = Integer.parseInt(s[0]);
                int month = Integer.parseInt(s[1]);
                int day = Integer.parseInt(s[2]);

                // String d=dateFormat.format(dateClicked);
                //  Date f= dateClicked;
                //   int day= f.getDay();
                //  int month=f.getMonth();
                //  int year=f.getYear();

                Log.d("day",day+"");
                Log.d("month",month+"");
                Log.d("year",year+"");
                //get current date
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                //   System.out.println("time => " + dateFormat1.format(cal.getTime()));
                String time_str1 = dateFormat1.format(cal.getTime());

                String[] s1 = time_str1.split(" ");

                int mYear = Integer.parseInt(s1[0].split("/")[0]);
                int mMonth = Integer.parseInt(s1[0].split("/")[1]);
                int mDay = Integer.parseInt(s1[0].split("/")[2]);
                Log.d("mday",mDay+"");
                Log.d("mmonth",mMonth+"");
                Log.d("myear",mYear+"");

                if(year>mYear){
                    setSubtitle(dateFormat.format(dateClicked));
                    Log.d("year larger",year+mYear+"");
                    FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.maincontainer, myFragment1);
                    fragmentTransaction.commit();
                }

                else {

                    if(month>mMonth){
                        setSubtitle(dateFormat.format(dateClicked));
                        Log.d("month larger",month+mMonth+"");
                        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.maincontainer, myFragment1);
                        fragmentTransaction.commit();
                    }

                    else {
                        if(mMonth==month) {
                            if (day > mDay) {
                                setSubtitle(dateFormat.format(dateClicked));
                                Log.d("day larger", day + mDay + "");
                                FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.maincontainer, myFragment1);
                                fragmentTransaction.commit();
                            } else {
                                Log.d("not larger", "");
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                String date=dateFormat.format(dateClicked);
                                editor.putString("old_date",date);
                                // Save the changes in SharedPreferences
                                editor.commit();
                                myFragment1 = new MyFragment1();
                                myFragment2 = new MyFragment2();
                                //   startActivity(new Intent(trackingdailycalory.this, oldTraking.class));
                              //  getLayoutInflater().inflate(R.layout.hello, frameLayout);
                                 FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                                 fragmentTransaction.replace(R.id.maincontainer, myFragment2);
                                 fragmentTransaction.commit();

                            }
                        }
                        else {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            String date=dateFormat.format(dateClicked);
                            editor.putString("old_date",date);
                            // Save the changes in SharedPreferences
                            editor.commit();
                            myFragment1 = new MyFragment1();
                            myFragment2 = new MyFragment2();
                             FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.maincontainer, myFragment2);
                             fragmentTransaction.commit();
                           // startActivity(new Intent(trackingdailycalory.this, oldTraking.class));
                        }

                    }
                }




            }





            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        // Set current date to today
        setCurrentDate(new Date());

        final ImageView arrow = (ImageView) findViewById(R.id.date_picker_arrow);

        RelativeLayout datePickerButton = (RelativeLayout) findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                    mAppBarLayout.setExpanded(false, true);
                    isExpanded = false;
                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                    mAppBarLayout.setExpanded(true, true);
                    isExpanded = true;
                }
            }
        });
        myFragmentManager = getSupportFragmentManager();
        myFragment1 = new MyFragment1();
        myFragment2 = new MyFragment2();


        if(savedInstanceState == null){
            //if's the first time created
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.maincontainer, myFragment1);
            fragmentTransaction.commit();
        }
    }


    public void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));
        if (mCompactCalendarView != null) {
            mCompactCalendarView.setCurrentDate(date);
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = (TextView) findViewById(R.id.title);

        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void setSubtitle(String subtitle) {
        TextView datePickerTextView = (TextView) findViewById(R.id.date_picker_text_view);

        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_main, menu);
        return true;
    }


//////////////////////////////////////////////////////////////////





}