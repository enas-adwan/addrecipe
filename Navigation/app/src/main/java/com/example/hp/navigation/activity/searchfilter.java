package com.example.hp.navigation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


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
public class searchfilter extends BaseActivity {
    public TextView titl;
    public TextView calor;
    public TextView lis;
    public TextView tota;
    public String title = "";
    public String calory = "";
    public String list = "";
    public String cook = "";
    public String desc = "";
    public String total = "";
    public Button Search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfilter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titl = (TextView) findViewById(R.id.title);
        calor = (TextView) findViewById(R.id.calory);
        lis = (TextView) findViewById(R.id.list);
        tota = (TextView) findViewById(R.id.total);
       Search = (Button) findViewById(R.id.search);
        Search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                title=titl.getText().toString();
                calory=  calor.getText().toString();
                total=  tota.getText().toString();
                list=  lis.getText().toString();
                Intent i=new Intent(searchfilter.this,searchactivity.class);
                i.putExtra("title", title);
                i.putExtra("calory", calory);
                i.putExtra("list", list);
                i.putExtra("total", total);
                i.putExtra("type", "search");
                startActivity(i);
                // String m=b.getText().toString();
// potentially add data to the intent

            }
        });


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
