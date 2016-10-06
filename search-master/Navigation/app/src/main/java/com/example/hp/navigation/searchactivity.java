package com.example.hp.navigation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.navigation.drawer.activity.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class searchactivity extends AppCompatActivity {
String title;
    public static ListView listVieww;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title=getIntent().getStringExtra("title");
        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
        listVieww=(ListView)findViewById(R.id.lv);
        jso(title);
        /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
                records= new ArrayList<Record>();
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

                    for (int i = 0; i < itemm.length(); i++) {
                        JSONObject c = itemm.getJSONObject(i);
                        title = c.getString("title");
                      //  names.add(title);
                        id = c.getInt("id");
                        image = c.getString("image");
                        rating = c.getString("rating");

                        // byte[] qrimage = Base64.decode(image.getBytes(), i);
                        //bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                        record = new Record(title, id,image,rating);


                        records.add(record);






                    }
                    vivsadapter = new Vivsadapter(searchactivity.this,records);
                    listVieww.setAdapter(vivsadapter);
                    listVieww.setItemsCanFocus(true);
                    listVieww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent i=new Intent(searchactivity.this,Sec.class);
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
                    String post_data = URLEncoder.encode("title","UTF-8")+"="+ URLEncoder.encode(number,"UTF-8");

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


}
