package com.example.hp.navigation.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.navigation.drawer.activity.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class profile extends BaseActivity {
    CallbackManager callbackManager;TextView name;
    TextView gender;TextView phone;TextView email;TextView location;
    JSONArray peoples = null;
    String myJSON;
    ImageView Img;
    public Bitmap bmp;
    private static final String TAG_RESULTS="result";
    private static final String TAG_name = "name";
    private static final String TAG_picture="picture";
    private static final String TAG_gender = "gender";
    private static final String TAG_email = "email";
    private static final String TAG_phone = "phone";
    private static final String TAG_location = "location";
   // String id="10208910928025315";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences type_user = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String user=type_user.getString("user_type","");
            super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
      //  setContentView(R.layout.face_profile);
        if(user.equals("app")) {
            getLayoutInflater().inflate(R.layout.app_profile, frameLayout);
        }
        else
        {
            getLayoutInflater().inflate(R.layout.face_profile, frameLayout);
        }

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        FacebookSdk.sdkInitialize(getApplicationContext());
        String face="facebook";
        String app="app";
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        name=(TextView) findViewById(R.id.user);
        gender=(TextView) findViewById(R.id.sex);
        phone=(TextView) findViewById(R.id.Number_app);
        email=(TextView) findViewById(R.id.email_app);
        location=(TextView) findViewById(R.id.location_app);
        Img = (ImageView)findViewById(R.id.profile);
        String type = pref.getString("user_type", "nothing");
if(type.equals(face)) {
    GetDataJSON g = new GetDataJSON();
    g.execute();
}
    else if(type.equals(app))
      {
    Log.d("type app",app);
    GetAPPDataJSON g = new GetAPPDataJSON();
        g.execute();
     }
    }
    public class GetDataJSON extends AsyncTask<String,Void,String> {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String id=pref.getString("face_id","");
        protected String doInBackground(String... params) {
            String login_url = "http://10.0.2.2/getface_user.php";
            try
            {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null)
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute()
        {
            String s="start";
            Log.d("get face user",s );
        }
        @Override
        protected void onPostExecute(String result)
        {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            if (pref.contains("face_id"))
            {
                String id=pref.getString("face_id","");
                Log.d("face", id);
                Log.d("face", result);

            }
            myJSON=result;
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i=0;i<peoples.length();i++){
                    JSONObject c = peoples.getJSONObject(i);
                    String db_name = c.getString(TAG_name);
                    String db_gender = c.getString(TAG_gender);
                    String db_phone = c.getString(TAG_phone);
                    String db_location = c.getString(TAG_location);
                    name.setText(db_name);
                    gender.setText(db_gender);
                    phone.setText(db_phone);
                    location.setText(db_location);
                    ProfilePictureView profilePictureView;
                    profilePictureView = (ProfilePictureView) findViewById(R.id.picture);
                    profilePictureView.setProfileId(id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    public class GetAPPDataJSON extends AsyncTask<String,Void,String> {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String user=pref.getString("user_email","");
        protected String doInBackground(String... params) {
            String login_url = "http://10.0.2.2/getapp_user.php";
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            String s="start";
            Log.d("name",s );
        }
        @Override
        protected void onPostExecute(String result) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

            if (pref.contains("user_email"))
            {
                String name=pref.getString("user_email","");
                Log.d("yes", name);
            }
            myJSON=result;
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i=0;i<peoples.length();i++){
                    JSONObject c = peoples.getJSONObject(i);
                    String db_name = c.getString(TAG_name);
                    String db_gender = c.getString(TAG_gender);
                    String db_email= c.getString(TAG_email);
                    String db_phone = c.getString(TAG_phone);
                    String db_location = c.getString(TAG_location);
                    String db_image = c.getString("image");
                    name.setText(db_name);
                    gender.setText(db_gender);
                    phone.setText(db_phone);
                    email.setText(db_email);
                    location.setText(db_location);
                    String[] safe = db_image.split("=");
                    byte[] qrimage = Base64.decode(safe[0], Base64.NO_PADDING);

                    bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                    Img.setImageBitmap(bmp);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}