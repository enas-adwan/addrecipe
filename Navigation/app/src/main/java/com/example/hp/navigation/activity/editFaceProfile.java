package com.example.hp.navigation.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editFaceProfile extends BaseActivity {
     Button faceEditPhone;  Button faceEditLocation;
    CallbackManager callbackManager;TextView name;
    TextView gender;TextView phone;TextView email;TextView location;
    JSONArray peoples = null;
    String myJSON;
    EditText edit_face_phone,edit_face_location;
    private static final String TAG_RESULTS="result";
    private static final String TAG_phone = "phone";
    private static final String TAG_location = "location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

            getLayoutInflater().inflate(R.layout.edit_face_profile, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        FacebookSdk.sdkInitialize(getApplicationContext());
        String face="facebook";
        String app="app";
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        phone=(TextView) findViewById(R.id.face_Number_app);
        location=(TextView) findViewById(R.id.face_location_app);
        faceEditPhone = (Button) findViewById(R.id.changePhone);
        faceEditLocation = (Button) findViewById(R.id.changeLocation);
        edit_face_location = (EditText)findViewById(R.id.editFaceLocation);
        edit_face_phone = (EditText)findViewById(R.id.editFacePhone);

        GetDataJSON g = new GetDataJSON();
        g.execute();

        faceEditLocation.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick (View view)
                                               {

                                                   if(edit_face_location.getText().toString().trim().length() > 0)
                                                   {
                                                       final  String new_location=edit_face_location.getText().toString().trim();
                                                       if (!isValidlocation(new_location)) {
                                                           edit_face_location.setError("Wrong location");
                                                       }
                                                       else {
                                                           edit_face_location.setError(null);
                                                           Log.d("edit location", new_location);
                                                           editelocationDataJSON g = new editelocationDataJSON();
                                                           g.execute(new_location);
                                                           GetDataJSON g1 = new GetDataJSON();
                                                           g1.execute();
                                                       }
                                                   }
                                                   else {   Toast.makeText(getApplicationContext(), "Please Enter the New information!", Toast.LENGTH_LONG).show();}
                                               }
                                           }
        );
        faceEditPhone.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick (View view)
                                                {

                                                    if(edit_face_phone.getText().toString().trim().length() > 0)
                                                    {
                                                        final String new_phone=edit_face_phone.getText().toString().trim();
                                                        if (!isValidphone(new_phone)) {
                                                            edit_face_phone.setError("Wrong phone");
                                                        }
                                                        else {
                                                            edit_face_phone.setError(null);
                                                            Log.d("edit phone", new_phone);
                                                            editephoneDataJSON g = new editephoneDataJSON();
                                                            g.execute(new_phone);
                                                            GetDataJSON g1 = new GetDataJSON();
                                                            g1.execute();
                                                        }
                                                    }
                                                    else {   Toast.makeText(getApplicationContext(), "Please Enter the New information!", Toast.LENGTH_LONG).show();}




                                                }
                                            }
        );

    }
    private boolean isValidphone(String phone) {
        String name_PATTERN = "^[0-9]{8,16}$";
        Pattern pattern = Pattern.compile(name_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return (matcher.matches());
    }
    private boolean isValidlocation(String location) {
        String name_PATTERN = "^[a-zA-Z]{3,20}$";
        Pattern pattern = Pattern.compile(name_PATTERN);
        Matcher matcher = pattern.matcher(location);
        return (matcher.matches());
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
            Log.d("name",s );
        }
        @Override
        protected void onPostExecute(String result)
        {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            if (pref.contains("face_id"))
            {
                String id=pref.getString("face_id","");
                Log.d("yes", id);
            }
            myJSON=result;
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i=0;i<peoples.length();i++){
                    JSONObject c = peoples.getJSONObject(i);
                    String db_phone = c.getString(TAG_phone);
                    String db_location = c.getString(TAG_location);
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
    public class editephoneDataJSON extends AsyncTask<String,Void,String> {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String id=pref.getString("face_id","");
        protected String doInBackground(String... params) {
            String login_url = "http://10.0.2.2/editphoneFace.php";
            try {
                String newphone = params[0];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("new","UTF-8")+"="+URLEncoder.encode(newphone,"UTF-8");
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
            String s="edit phone";
            Log.d("task",s );
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "result"+result, Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class editelocationDataJSON extends AsyncTask<String,Void,String> {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String id=pref.getString("face_id","");
        protected String doInBackground(String... params) {
            String login_url = "http://10.0.2.2/editlocationFace.php";
            try {
                String newlocation = params[0];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("new","UTF-8")+"="+URLEncoder.encode(newlocation,"UTF-8");
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
            String s="edit location";
            Log.d("task",s );
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "result"+result, Toast.LENGTH_LONG).show();
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