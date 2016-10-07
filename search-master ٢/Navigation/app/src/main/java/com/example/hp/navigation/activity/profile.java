package com.example.hp.navigation.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Button share,logout;
    ShareDialog shareDialog;
    CallbackManager callbackManager;
    TextView name;
    TextView gender;

    JSONArray peoples = null;
    String myJSON;
    public static final String MyPREFERENCES = "MyPref" ;
    private static final String TAG_RESULTS="result";
    private static final String TAG_name = "name";
    private static final String TAG_picture="picture";
    private static final String TAG_gender = "gender";
   // String id="10208910928025315";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
      //  setContentView(R.layout.profile);
        getLayoutInflater().inflate(R.layout.profile, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        FacebookSdk.sdkInitialize(getApplicationContext());
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        name=(TextView) findViewById(R.id.name);
        gender=(TextView) findViewById(R.id.gender);

        GetDataJSON g = new GetDataJSON();
        g.execute();
        shareDialog = new ShareDialog(this);
        share = (Button) findViewById(R.id.share);
        logout = (Button) findViewById(R.id.logout);



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
          String string1=name.getText().toString();

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT,"http://10.0.2.2/share.php?recipeName="+string1);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
                                 }

        );
        logout.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick (View view){

                                         LoginManager.getInstance().logOut();
                                         pref.edit().clear().commit();
                                         if(AccessToken.getCurrentAccessToken()!=null)
                                         {
                                             Log.v("User is login","YES");

                                         }
                                         else
                                         {
                                             Log.v("User is not login","OK");
                                             Intent intent = new Intent(profile.this, MainActivity.class);
                                             startActivity(intent);

                                         }



                                     }
                                 }

        );


    }

    public class GetDataJSON extends AsyncTask<String,Void,String> {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        String id=pref.getString("face_id","");
        protected String doInBackground(String... params) {



            String login_url = "http://10.0.2.2/getface_user.php";

            try {

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
                    String db_name = c.getString(TAG_name);
                    String db_gender = c.getString(TAG_gender);
                    name.setText(db_name);
                    gender.setText(db_gender);
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

}