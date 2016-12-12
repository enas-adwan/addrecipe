package com.example.hp.navigation.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class searchactivity extends BaseActivity{
    String title;
    String list;
    String total;
    String calory;
    String qan;
    String c;
    public static RecyclerView mRecyclerView;
    public static ListView listVieww;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_searchactivity, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle("search activity");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=getIntent().getStringExtra("title");
        list=getIntent().getStringExtra("list");
        calory=getIntent().getStringExtra("calory");
        total=getIntent().getStringExtra("total");
        qan=getIntent().getStringExtra("qan");
        c=getIntent().getStringExtra("ch");
        // Toast.makeText(getApplicationContext(), list, Toast.LENGTH_LONG).show();
        //  Log.e("DATABASE OPERATION", list);
        //  listVieww=(ListView)findViewById(R.id.lv);
        jso(title,calory,list,total,qan);
        /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    public void jso(String title,String calory,String list,String total,String qan1) {


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

                    // Text.setText(s);
                    //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
//
                    JSONObject jObj = new JSONObject(s);
                    String j = jObj.toString();
                    JSONArray itemm = jObj.getJSONArray("result1");
                    String item = itemm.toString();

                    for (int i = 0; i < itemm.length(); i++) {
                        JSONObject c = itemm.getJSONObject(i);
                        title = c.getString("title");

                        id = c.getInt("id");
                        image = c.getString("image");
                        rating = c.getString("rating");
                        // byte[] qrimage = Base64.decode(image.getBytes(), i);
                        //bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                        record = new Record(title, id, image, rating);


                        records.add(record);

                        //                 String item = itemm.toString();
                        //Log.e("DATABASE OPERATION", "Table create..." + s);
                        // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    }
                    Vivsadapter adapter = new Vivsadapter(searchactivity.this,records);
                    mRecyclerView = (RecyclerView) findViewById(R.id.masonry_grid);




                    //mRecyclerView.addItemDecoration(new SpacesItemDecoration(ShowRecipe.this, StaggeredGridLayoutManager.VERTICAL));
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    SpacesItemDecoration decoration = new SpacesItemDecoration(100);

                    mRecyclerView.addItemDecoration(decoration);
                    mRecyclerView.setTag("sec");
                    adapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(adapter);
                    //JSONArray itemm = jObj.getJSONArray("result");
                    //  String item = itemm.toString();

                    //  for (int i = 0; i < itemm.length(); i++) {
                    //    JSONObject c = itemm.getJSONObject(i);
                    //  title = c.getString("title");
                    //  names.add(title);
                    //  id = c.getInt("id");
                    // image = c.getString("image");
                    //   rating = c.getString("rating");

                    // byte[] qrimage = Base64.decode(image.getBytes(), i);
                    //bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                    //  record = new Record(title, id,image,rating);


                    //  records.add(record);






                    // }
                    //  Vivsadapter adapter = new Vivsadapter(searchactivity.this,records);



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

                    URL url = new URL("http://10.0.2.2/searchmulti.php");

                    String number = params[0];

                    String calory = params[1];
                    String list= params[2];
                    String total= params[3];
                    String qan= params[4];
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("title","UTF-8")+"="+ URLEncoder.encode(number,"UTF-8")+"&"+ URLEncoder.encode("calory","UTF-8")+"="+ URLEncoder.encode(calory,"UTF-8")+"&"+ URLEncoder.encode("list","UTF-8")+"="+ URLEncoder.encode(list,"UTF-8")+"&"+ URLEncoder.encode("total","UTF-8")+"="+ URLEncoder.encode(total,"UTF-8")+"&"+ URLEncoder.encode("qan","UTF-8")+"="+ URLEncoder.encode(qan,"UTF-8")+ "&"+URLEncoder.encode("c","UTF-8")+"="+ URLEncoder.encode(c,"UTF-8");
                    //Toast.makeText(getApplicationContext(),post_data, Toast.LENGTH_LONG).show();
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
//                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();

                    return "false";
                }

            }
        }



        RegisterUser ru = new RegisterUser();
        ru.execute(title,calory,list,total,qan1);


    }


}
