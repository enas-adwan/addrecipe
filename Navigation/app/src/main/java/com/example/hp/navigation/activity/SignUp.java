
package com.example.hp.navigation.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.navigation.drawer.activity.R;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText Password;
    private Button BUtton;
    private EditText ConfirmPassword;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    String valid_flag=null;
    String valid_flag1=null;
    String valid_flag2=null;
    String valid_flag3=null;
    String valid_flag4=null;


    private static final String REGISTER_URL = "http://10.0.2.2/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        radioSexGroup = (RadioGroup) findViewById(R.id.gender);
        email = (EditText) findViewById(R.id.email);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                String Em = email.getText().toString().trim();
               email.setError(null);
                valid_flag4=null;
                if (!isValidemail(Em)) {
                    email.setError("Wrong email");
                    valid_flag4="err";
                }
                else{
                    valid_flag4=null;
                }
            }
        });
        username = (EditText) findViewById(R.id.username);
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                String name = username.getText().toString().trim();
              //  username.setError(null);
                valid_flag1=null;
                if (!isValidname(name)) {

                    username.setError("Wrong Name");
                    valid_flag1="err";
                }
                else{
                    valid_flag1=null;
                }
            }
        });

        Password = (EditText) findViewById(R.id.password);
        Password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                String passw = Password.getText().toString().trim();
            //    Password.setError(null);
                valid_flag2=null;
                if (!isValidPassword(passw)) {

                    Password.setError("Wrong password");
                    valid_flag2="err";
                }
                else{
                    valid_flag2=null;
                }
            }
        });
        ConfirmPassword = (EditText) findViewById(R.id.confirmpassword);
        ConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                String confirmpassword = ConfirmPassword.getText().toString().trim();
                valid_flag3=null;
                if (!isValidConfirmPassword(confirmpassword)) {
                    ConfirmPassword.setError("Invalid password");
                    valid_flag3="err";
                }else{
                    valid_flag3=null;
                }
            }
        });

        BUtton = (Button) findViewById(R.id.button);
        BUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser( valid_flag1, valid_flag2,valid_flag3, valid_flag4);

             /*   new SignUpActivity(this,status,role,0).execute(username,password);*/


            }
        });
    }

    private void registerUser(String valid_flag1,String valid_flag2,String valid_flag3,String valid_flag4) {
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
// find the radiobutton by returned id
        radioSexButton = (RadioButton) findViewById(selectedId);
        String gender=radioSexButton.getText().toString();
        Log.d("gender",gender);
        String name =username.getText().toString().trim();
        String Em =email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String confirmpassword = ConfirmPassword.getText().toString().trim();

        if (!isValidname(name)) {
            username.setError("Wrong Name");
            valid_flag1="err";
        }
        else {
            username.setError(null);
            valid_flag1=null;
        }
        if (!isValidemail(Em)) {
           email.setError("Wrong email");
            valid_flag4="err";
        }
        else {
            email.setError(null);
            valid_flag4=null;
        }



        if (!isValidPassword(password)) {
            Password.setError("Invalid password");
            valid_flag2="err";
        }
        else {
            Password.setError(null);
            valid_flag2=null;
        }

        if (!isValidConfirmPassword(confirmpassword)) {
            ConfirmPassword.setError("Invalid password");
            valid_flag3="err";
        }else{
            ConfirmPassword.setError(null);
            valid_flag3=null;
        }



        if(valid_flag1==null&&valid_flag2==null&&valid_flag3==null&&valid_flag4==null){


            String secret="3CH6knCsenas2va8GrHk4mf3JqmUctCM";
            register(name, password,gender,Em,secret,valid_flag);
            SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("user_email",Em);
            editor.commit();
        }
        else{

            Toast.makeText(getApplicationContext(),"please enter the right requirment",Toast.LENGTH_LONG).show();
        }


    }


    private boolean isValidname(String name) {
        String name_PATTERN = "^[a-z0-9_-]{3,15}$";

        Pattern pattern = Pattern.compile(name_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    private boolean isValidemail(String Em) {
        String email_PATTERN = "[a-zA-Z0-9+._%-+]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                "(" +
                "." +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                ")+";

        Pattern pattern = Pattern.compile(email_PATTERN);
        Matcher matcher = pattern.matcher(Em);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length()> 6) {
            return true;
        }
        return false;
    }
    private boolean isValidConfirmPassword(String pass) {
        String password = Password.getText().toString().trim();
        Pattern pattern = Pattern.compile(password);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();

    }
    private void register(String name, String password,String gender,String Em ,String secret, final String valid_flag) {
       /* String urlSuffix = "?name=" + name + "&password=" + password;*/





        class RegisterUser extends AsyncTask<String, Void, String> {

            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
String succ="successfully registered";

                Toast.makeText(getApplicationContext(),s+valid_flag,Toast.LENGTH_LONG).show();
                if(s.equals(succ)){
                    SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_type", "app");
                    editor.putString("valid_user", "logged");
                    editor.commit();
                    String n=pref.getString("user_type","defult");
                    Log.d("user_type",n);
                    startActivity(new Intent(SignUp.this, ShowRecipe.class));

                }
                else{
                    SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    String n=pref.getString("user_type","defult");
                    Log.d("user_type exist",n);
                }

            }

            @Override
            protected String doInBackground(String... params) {
              /*  String s = params[0];
              Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(REGISTER_URL+s));
                startActivity(i);*/
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);

                data.put("password",params[1]);
                data.put("gender",params[2]);
                data.put("email",params[3]);
                data.put("secret",params[4]);


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;

               /* BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return "no";
                }*/
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,password,gender,Em,secret);

       /* ru.execute(urlSuffix);*/
    }}
