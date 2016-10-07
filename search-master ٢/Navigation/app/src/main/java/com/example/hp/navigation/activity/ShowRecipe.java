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
    ArrayList<Integer> ids = new ArrayList<Integer>();
    ArrayList<String> names = new ArrayList<String>();
    private TextView Text;
    private Button Getall;
    boolean connected = false;
    recipeDbHelper userDbHelper2;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ArrayList<Record> records;
    Cursor cursor1;
    Cursor cursor2;
    Cursor cursor4;
    Vivsadapter vivsadapter1;
    public static ListView listVieww;
    public static ListView listView;
    Record record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.showrecipe, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        listVieww=(ListView)findViewById(R.id.lv);
        //  Text = (TextView) findViewById(R.id.text);
    /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
if(isOnline()){
            //we are connected to a network
            connected = true;
            Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_SHORT).show();
            jso();
        } else {
            connected = false;
sqliteoffline();
            Toast.makeText(getApplicationContext(), "notconnected", Toast.LENGTH_SHORT).show();
        }






    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void sqliteoffline() {


        records= new ArrayList<Record>();
        //vivs=new Vivsadapter(getApplicationContext(),R.layout.single_row);
        userDbHelper2=new recipeDbHelper(getApplicationContext());
        sqLiteDatabase=userDbHelper2.getReadableDatabase();
        cursor=userDbHelper2.getinformationsqliteoffline(sqLiteDatabase);
        cursor1=userDbHelper2.getinformation2sqliteoffline(sqLiteDatabase);
        cursor2=userDbHelper2.getinformationidsqliteoffline(sqLiteDatabase);

        cursor4=userDbHelper2.getinformationsqliteofflinerating(sqLiteDatabase);
        if(cursor.moveToFirst()&&cursor1.moveToFirst()&&cursor2.moveToFirst()&&cursor4.moveToFirst())
        {
            do {
                int id=cursor2.getInt(0);
                String  title=cursor.getString(0);
                String image =cursor1.getString(0);

String rating=cursor4.getString(0);

                record = new Record(title, id,image,rating);


                records.add(record);
                vivsadapter1 = new Vivsadapter(ShowRecipe.this,records);
                listVieww.setAdapter(vivsadapter1);
                listVieww.setItemsCanFocus(true);
                listVieww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i=new Intent(ShowRecipe.this,Showonesqliteoffline.class);
                        i.putExtra("title",vivsadapter1.gettitle(position));
                        startActivity(i);
                        //Toast.makeText(getApplicationContext(),vivsadapter.gettitle(position), Toast.LENGTH_LONG).show();
                    }
                });
            }while (cursor.moveToNext()&&cursor1.moveToNext()&&cursor2.moveToNext()&&cursor4.moveToNext());

        }



    }
    protected void onStop() {
        // TODO Auto-generated method stub
        userDbHelper2=new recipeDbHelper(getApplicationContext());
        sqLiteDatabase=userDbHelper2.getReadableDatabase();
    //  userDbHelper2.droptables(sqLiteDatabase);
        super.onStop();
    }

    public void jso() {


        class RegisterUser extends AsyncTask<String, Void, String> {
            public String title = "";
            public Integer id;
            public String name1 = "";
            private TextView Text;
            ArrayList<Record> records;
            Vivsadapter vivsadapter;
            String json;
            String rating="";
            public String image = "";
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
               // listVieww=(ListView)findViewById(R.id.lv);
                records= new ArrayList<Record>();

                    try {
                       // Text.setText(s);
                      //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                        JSONObject jObj = new JSONObject(s);
                        String j = jObj.toString();
                        JSONArray itemm = jObj.getJSONArray("result");
                        String item = itemm.toString();

                        for (int i = 0; i < itemm.length(); i++) {
                            JSONObject c = itemm.getJSONObject(i);
                            title = c.getString("title");
                            names.add(title);
                            id = c.getInt("id");
                            image = c.getString("image");
                            rating = c.getString("rating");
                           // byte[] qrimage = Base64.decode(image.getBytes(), i);
                            //bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                            record = new Record(title, id,image,rating);


                            records.add(record);






                        }
                        vivsadapter = new Vivsadapter(ShowRecipe.this,records);
                        listVieww.setAdapter(vivsadapter);
                        listVieww.setItemsCanFocus(true);
                        listVieww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent i=new Intent(ShowRecipe.this,Sec.class);
                                i.putExtra("title",vivsadapter.gettitle(position));
                                startActivity(i);
                            }
                        });

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

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public String doInBackground(String... params) {
                Text = (TextView) findViewById(R.id.text);

                try {
                    String line, newjson = "";
                    URL urls = new URL("http://10.0.2.2/showrecipe.php");
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8"))) {
                        while ((line = reader.readLine()) != null) {
                            newjson += line;

                        }

                         json = newjson.toString();
                        //JSONObject jObj = new JSONObject(json);

                       // return json;
                        return json;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();

                    return "false";
                }

            }
        }



        RegisterUser ru = new RegisterUser();
        ru.execute();


    }
}
