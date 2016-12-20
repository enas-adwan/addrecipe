package com.example.hp.navigation.activity;
      import android.annotation.TargetApi;
      import android.content.Context;
      import android.content.SharedPreferences;
      import android.database.Cursor;
      import android.database.sqlite.SQLiteDatabase;
      import android.net.ConnectivityManager;
      import android.net.NetworkInfo;
      import android.os.AsyncTask;
      import android.os.Build;
      import android.os.Bundle;
      import android.support.v7.widget.RecyclerView;
      import android.support.v7.widget.StaggeredGridLayoutManager;
      import android.util.Log;
      import android.widget.ArrayAdapter;
      import android.widget.Button;
      import android.widget.ListView;
      import android.widget.TextView;
      import android.widget.Toast;
      import com.navigation.drawer.activity.R;
      import org.json.JSONArray;
      import org.json.JSONObject;
      import java.io.BufferedReader;
      import java.io.InputStreamReader;
      import java.net.URL;
      import java.util.ArrayList;

            public class ShowMyRecipe extends BaseActivity {
            ArrayList<String> names = new ArrayList<String>();
            boolean connected = false;
            recipeDbHelper userDbHelper2;
            SQLiteDatabase sqLiteDatabase;
            Cursor cursor;
            public static RecyclerView mRecyclerView;
             @Override
             protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             getLayoutInflater().inflate(R.layout.showrecipe, frameLayout);
             mDrawerList.setItemChecked(position, true);
             setTitle("My Recipe");
             mRecyclerView = (RecyclerView) findViewById(R.id.masonry_grid);
                 if(isOnline())
                 {
                     connected = true;
                     Toast.makeText(getApplicationContext(),"connected",Toast.LENGTH_SHORT).show();
                     SharedPreferences type_user = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                      String user=type_user.getString("user_type","");
                     String face="facebook";
                     String app="app";
                      if(user.equals(app))
                      {
                              showapprecipe ru = new showapprecipe();
                          ru.execute();
                            }
                      else if(user.equals(face))

                    {
              Log.d("type app",face);
              showfacerecipe g = new showfacerecipe();
              g.execute();
          }
      }
          else
          {
          connected = false;
          Toast.makeText(getApplicationContext(), "notconnected", Toast.LENGTH_SHORT).show();
      }
    }
    protected void onStop()
    {
        // TODO Auto-generated method stub
        mRecyclerView.setTag("nnsec");
        userDbHelper2=new recipeDbHelper(getApplicationContext());
        sqLiteDatabase=userDbHelper2.getReadableDatabase();
        super.onStop();
    }
    public boolean isOnline()
    {
                  ConnectivityManager cm =
                 (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                  NetworkInfo netInfo = cm.getActiveNetworkInfo();
                 return netInfo != null && netInfo.isConnectedOrConnecting();
    }
        class showapprecipe extends AsyncTask<String, Void, String> {
            public String title = "";
            public Integer id;
            private TextView Text;
            ArrayList<Record> records;
            String json;
            String rating="";
            public String image = "";
            Record record;
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            String user=pref.getString("user_email","");
            @Override
            public void onPreExecute()
            {
                super.onPreExecute();
            }
            @Override
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("show recipe ",s);
                records= new ArrayList<Record>();
                try {
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
                        record = new Record(title, id,image,rating);
                        records.add(record);
                    }
                    Vivsadapter adapter = new Vivsadapter(ShowMyRecipe.this,records);
                    mRecyclerView = (RecyclerView) findViewById(R.id.masonry_grid);
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    SpacesItemDecoration decoration = new SpacesItemDecoration(100);
                    mRecyclerView.addItemDecoration(decoration);
                    mRecyclerView.setTag("sec");
                    adapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(adapter);
                } catch (Exception e)

                {
                    e.printStackTrace();
                }
            }
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public String doInBackground(String... params) {
                Text = (TextView) findViewById(R.id.text);
                try {
                    String line, newjson = "";
                    URL urls = new URL("http://10.0.2.2/showapprecipe.php?key="+user);
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8"))) {
                        while ((line = reader.readLine()) != null) {
                            newjson += line;
                        }
                        json = newjson.toString();
                        return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "false";
                }
            }
        }
    class showfacerecipe extends AsyncTask<String, Void, String> {
        public String title = "";
        public Integer id;
        public String name1 = "";
        private TextView Text;
        ArrayList<Record> records;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String face_id=pref.getString("face_id","");
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
            Log.d("show recipe ",s);
            records= new ArrayList<Record>();
            try {
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
                    record = new Record(title, id,image,rating);
                    records.add(record);
                }
                Vivsadapter adapter = new Vivsadapter(ShowMyRecipe.this,records);
                mRecyclerView = (RecyclerView) findViewById(R.id.masonry_grid);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                SpacesItemDecoration decoration = new SpacesItemDecoration(100);
                mRecyclerView.addItemDecoration(decoration);
                mRecyclerView.setTag("sec");
                adapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(adapter);
             } catch (Exception e) {
                e.printStackTrace();
            }
        }
                 @TargetApi(Build.VERSION_CODES.KITKAT)
                 @Override
                   public String doInBackground(String... params)
                       {
                               Text = (TextView) findViewById(R.id.text);
                          try
            {
                String line, newjson = "";
                URL urls = new URL("http://10.0.2.2/showfacerecipe.php?key="+face_id);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8"))) {
                    while ((line = reader.readLine()) != null)
                    {
                        newjson += line;
                    }
                    json = newjson.toString();
                    return json;
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "false";
            }
        }
    }

     }
