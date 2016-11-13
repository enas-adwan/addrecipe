package com.example.hp.navigation.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.ByteArrayOutputStream;
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
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editAppProfile extends BaseActivity {
    private static int RESULT_LOAD_IMG = 1;
    private Button loadpic,uploadpic;
    ImageView Img;
    public Bitmap bmp;
    String image_str;
    public ImageView imgVieww;
    Button performEditName;  Button performEditPhone;  Button performEditLocation;
    CallbackManager callbackManager;TextView name;
    TextView gender;TextView phone;TextView email;TextView location;
    JSONArray peoples = null;
    String myJSON;
    EditText edit_user, edit_phone,edit_location;
    private static final String TAG_RESULTS="result";
    private static final String TAG_name = "name";
    private static final String TAG_phone = "phone";
    private static final String TAG_location = "location";
    private static final String URL = "http://10.0.2.2/saveimage.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

            getLayoutInflater().inflate(R.layout.edit_app_profile, frameLayout);


        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        FacebookSdk.sdkInitialize(getApplicationContext());
        name=(TextView) findViewById(R.id.name);
        phone=(TextView) findViewById(R.id.Numberapp);
        location=(TextView) findViewById(R.id.locationapp);
        performEditName = (Button) findViewById(R.id.performEditName);
        performEditPhone = (Button) findViewById(R.id.performEditPhone);
        performEditLocation = (Button) findViewById(R.id.performEditLocation);
        edit_location = (EditText)findViewById(R.id.editlocation);
        edit_phone = (EditText)findViewById(R.id.editphone);
        edit_user = (EditText)findViewById(R.id.editname);
        Img = (ImageView)findViewById(R.id.imgView);


            GetAPPDataJSON g = new GetAPPDataJSON();
            g.execute();
        loadpic = (Button) findViewById(R.id.loadpic);

        loadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);*/
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                // startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"),RESULT_LOAD_IMG);



            }
        });



       performEditName.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick (View view)
                                      {

                                          if(edit_user.getText().toString().trim().length() > 0)
                                          {
                                              final  String new_name=edit_user.getText().toString().trim();
                                              if (!isValidname(new_name)) {
                                                  edit_user.setError("Wrong Name");
                                              }
                                              else {
                                                  edit_user.setError(null);
                                              editeuserDataJSON g = new editeuserDataJSON();
                                              g.execute(new_name);
                                              GetAPPDataJSON g1 = new GetAPPDataJSON();
                                              g1.execute();
                                              }

                                          }
                                          else {   Toast.makeText(getApplicationContext(), "Please Enter the New information!", Toast.LENGTH_LONG).show();}
                                      }
                                  }
        );
        performEditPhone.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick (View view)
                                               {

                                                   if(edit_phone.getText().toString().trim().length() > 0)
                                                   {

                                                       final String new_phone=edit_phone.getText().toString().trim();
                                                       if (!isValidphone(new_phone)) {
                                                           edit_phone.setError("Wrong phone");
                                                       }
                                                       else {
                                                           edit_phone.setError(null);
                                                           editephoneDataJSON g = new editephoneDataJSON();
                                                           g.execute(new_phone);
                                                           GetAPPDataJSON g1 = new GetAPPDataJSON();
                                                           g1.execute();
                                                       }
                                                   }
                                                   else {   Toast.makeText(getApplicationContext(), "Please Enter the New information!", Toast.LENGTH_LONG).show();}




                                               }
                                           }
        );
        performEditLocation.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick (View view)
                                               {


                                                   if(edit_location.getText().toString().trim().length() > 0)
                                                   {
                                                       final String new_location=edit_location.getText().toString().trim();
                                                       if (!isValidlocation(new_location)) {
                                                           edit_location.setError("Wrong location");
                                                       }
                                                       else {
                                                           edit_location.setError(null);
                                                           editelocationDataJSON g = new editelocationDataJSON();
                                                           g.execute(new_location);
                                                           GetAPPDataJSON g1 = new GetAPPDataJSON();
                                                           g1.execute();
                                                       }
                                                   }
                                                   else {   Toast.makeText(getApplicationContext(), "Please Enter the New information!", Toast.LENGTH_LONG).show();}

                                               }
                                           }
        );
       uploadpic = (Button) findViewById(R.id.uploadpic);

        uploadpic.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick (View view)
                                                   {

                                                       class save extends AsyncTask<String, Void, String> {
                                                           SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                                           String user=pref.getString("user_email","");
                                                           editProfileHelper ruc = new editProfileHelper();
                                                           @Override
                                                           protected void onPreExecute() {
                                                               super.onPreExecute();
                                                           }
                                                           @Override
                                                           protected void onPostExecute(String s)
                                                           {
                                                               super.onPostExecute(s);
                                                               Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                                                           }

                                                           @Override
                                                           protected String doInBackground(String... params) {
                                                               Long tsLong = System.currentTimeMillis() / 1000;
                                                               String timestamp = tsLong.toString();
                                                               String nameimage ="IMG_"+timestamp;
                                                               HashMap<String, String> data = new HashMap<String,String>();
                                                               data.put("user",user);
                                                               data.put("image",image_str);
                                                               data.put("nameimage",nameimage);
                                                               String result = ruc.sendPostRequest(URL,data);
                                                               return  result;
                                                           }
                                                       }

                                                       save ru = new save();
                                                       ru.execute();
                                                       GetAPPDataJSON g1 = new GetAPPDataJSON();
                                                       g1.execute();


                                                   }
                                               }
        );

    }



    private boolean isValidname(String name) {
        String name_PATTERN = "^[a-z0-9_-]{3,20}$";
        Pattern pattern = Pattern.compile(name_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return (matcher.matches());
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

    public class editeuserDataJSON extends AsyncTask<String,Void,String> {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String user=pref.getString("user_email","");
        protected String doInBackground(String... params) {
            String login_url = "http://10.0.2.2/editNameApp.php";
            try {
                String newname = params[0];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"
                        +URLEncoder.encode("new","UTF-8")+"="+URLEncoder.encode(newname,"UTF-8");
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
            String s="edit name";
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
            String s="get user information";
            Log.d("taske",s );
        }
        @Override
        protected void onPostExecute(String result) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

            if (pref.contains("user_email"))
            {
                String email=pref.getString("user_email","");
                Log.d("yes", email);
            }
            myJSON=result;
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i=0;i<peoples.length();i++){
                    JSONObject c = peoples.getJSONObject(i);
                    String db_name = c.getString(TAG_name);
                    String db_phone = c.getString(TAG_phone);
                    String db_location = c.getString(TAG_location);
                    String db_image = c.getString("image");
                    name.setText(db_name);
                    phone.setText(db_phone);
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
    public class editephoneDataJSON extends AsyncTask<String,Void,String> {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String user=pref.getString("user_email","");
        protected String doInBackground(String... params) {
            String login_url = "http://10.0.2.2/editphoneApp.php";
            try {
                String newphone = params[0];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"
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
        String user=pref.getString("user_email","");
        protected String doInBackground(String... params) {
            String login_url = "http://10.0.2.2/editlocationApp.php";
            try {
                String newlocation = params[0];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"
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
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data && data.getData() != null) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                // profile_image=selectedImage.toString();
                //   URL newurl = new URL(selectedImage);
                // Uri selectedImage = data.getData();
                //in = new BufferedInputStream(new URL(selectedImage).openStream(), IO_BUFFER_SIZE);
                //  String     selectedFilePath = FilePath.getPath(this,selectedImage);

                // Get the cursor
             /*  Cursor cursor = getContentResolver().query(selectedImage,
                     filePathColumn, null, null, null);
           //    Move to first row
            cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
               imgPath = cursor.getString(columnIndex);
               cursor.close();

                Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString( idx);
                */
                String[] filePathColumn={MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                //Bitmap bitmap1=BitmapFactory.decodeFile(picturePath);
                cursor.close();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                byte [] byte_arr = stream.toByteArray();
                image_str = Base64.encodeToString(byte_arr,Base64.DEFAULT);


                //    Bitmap bitmap=BitmapFactory.decodeFile(picturePath);

                //return cursor.getString(idx);
                imgVieww = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView
                imgVieww.setImageBitmap(bitmap);

                //  String fileNameSegments[] = imgPath.split("/");
                //fileName = fileNameSegments[fileNameSegments.length - 1];
                // Text.setText( image_str);
                //  imgVieww.setImageBitmap(BitmapFactory
                //    .decodeFile(imgPath));
                Toast.makeText(this, "worked",
                        Toast.LENGTH_LONG).show();
                // Get the Image's file name
                // String fileNameSegments[] = imgPath.split("/");
                //   fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
                //   params.put("filename", fileName);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}