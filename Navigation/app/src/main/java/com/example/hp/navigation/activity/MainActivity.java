package com.example.hp.navigation.activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MainActivity extends AppCompatActivity {
    static  String value=null;
    String valid_flag=null;
    String valid_flag1=null;
    CallbackManager callbackManager;
    LoginButton login;//fb login
    Button fb;
    private Button BUtton; //app login
    Button signup;
    EditText UsernameEt, PasswordEt;
    public static final String MyPREFERENCES = "MyPrefs" ;//shared prefrence
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String n=pref.getString("valid_user","defult");
        if(n.equals("logged"))
        {
            Intent intent = new Intent(MainActivity.this, ShowRecipe.class);
            startActivity(intent);
       }
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        login = (LoginButton)findViewById(R.id.login_button);
        fb=(Button)findViewById(R.id.fb);
        login.setReadPermissions("public_profile email");//permession to get email .
        login.setReadPermissions("user_location");//permission to get location .
        UsernameEt = (EditText)findViewById(R.id.etUserName);
        UsernameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                String name = UsernameEt.getText().toString().trim();
                UsernameEt.setError(null);
                valid_flag=null;
                if (!isValidname(name)) {
                  UsernameEt.setError("Wrong name");
                    valid_flag="err";
                }
                else{
                    valid_flag=null;
                }
            }
        });
        PasswordEt = (EditText)findViewById(R.id.etPassword);
        PasswordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                String password = PasswordEt.getText().toString().trim();
               PasswordEt.setError(null);
                valid_flag1=null;
                if (!isValidPassword(password)) {
                   PasswordEt.setError("Wrong password");
                    valid_flag1="err";
                }
                else{
                    valid_flag1=null;
                }
            }
        });
        BUtton = (Button) findViewById(R.id.btnLogin);
        BUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser( valid_flag, valid_flag1);
            }
        });
        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
             /*   new SignUpActivity(this,status,role,0).execute(username,password);*/
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.performClick();

            }
        });
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(AccessToken.getCurrentAccessToken() != null){
                    System.out.println("onSuccess");
                    String accessToken = loginResult.getAccessToken().getToken();
                    Log.i("accessToken", accessToken);
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.i("LoginActivity", response.toString());
                            String fullName=object.optString("name");
                            String id=object.optString("id");
                            String gender=object.optString("gender");
                            String email=object.optString("email");
                          //  String currentCity = null;
                      //     try {
                      //          currentCity = object.getJSONObject("location").getString("name");
                       //     }   catch (JSONException e) {
                      //          e.printStackTrace();
                        //    }
                            String profileImageUrl = ImageRequest.getProfilePictureUri(object.optString("id"), 500, 500).toString();
//                           Log.d("location",currentCity);
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.clear();
                            editor.putString("face_id", id);
                            editor.putString("user_type", "facebook");
                            editor.putString("valid_user", "logged ");
                            // Save the changes in SharedPreferences
                            editor.commit();   // commit changes
                            addUser backgroundWorker = new addUser(MainActivity.this);
                            backgroundWorker.execute(id,fullName,gender,email,profileImageUrl);

                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,gender,link,email,picture");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }
            @Override
            public void onCancel() {
                // Function to handle cancel event
                Toast.makeText(getApplicationContext(), "Login cancel", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(FacebookException exception) {
                // Function to handle error
                Toast.makeText(getApplicationContext(), "error in login", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        //  FacebookSdk.sdkInitialize(getApplicationContext());
        //     SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
         //  String n=pref.getString("valid_user","defult");
         //   if(n.equals("logged")) {
         //       Intent intent = new Intent(MainActivity.this, ShowRecipe.class);
           //     startActivity(intent);
         //   }
        //   else{
        //      startActivity(new Intent(this, MainActivity.class));
    //      }
        // Always call the superclass method first
    //   Log.d("onResume","");
    //   if(AccessToken.getCurrentAccessToken() != null ) {
     //      Intent intent = new Intent(MainActivity.this, ShowRecipe.class);
     //   startActivity(intent);
     //  }
    }
    private void LoginUser(String valid_flag,String valid_flag1) {
        String name =UsernameEt.getText().toString().trim();
        String password = PasswordEt.getText().toString().trim();
        if (!isValidname(name)) {
           UsernameEt.setError("Wrong Name");
            valid_flag="err";
        }
        else {
            UsernameEt.setError(null);
            valid_flag=null;
        }
        if (!isValidPassword(password)) {
            PasswordEt.setError("Invalid password");
            valid_flag1="err";
        }
        else {
            PasswordEt.setError(null);
            valid_flag1=null;
        }
        if(valid_flag==null&&valid_flag1==null)
        {
            SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("user_type", "app");
            GetEmail e=new GetEmail(this);
            e.execute(name);
            editor.putString("valid_user", "logged");
            editor.commit();
            String t=pref.getString("user_type","");
            String type = "login";
            LoginActivity backgroundWorker = new LoginActivity(this);
            backgroundWorker.execute(type, name, password);
        }
        else{
            Toast.makeText(getApplicationContext(),"please enter the right requirment",Toast.LENGTH_LONG).show();
        }
    }
    private boolean isValidname(String name) {
        String name_PATTERN = "^[a-z0-9_-]{3,20}$";
       String EMAIL_ADDRESS_PATTERN =
               "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(name_PATTERN);
        Matcher matcher = pattern.matcher(name);
        Pattern pattern2 = Pattern.compile(EMAIL_ADDRESS_PATTERN);
        Matcher matcher2 = pattern2.matcher(name);
        return (matcher.matches()|| matcher2.matches());
    }
    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length()> 6) {
            return true;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}