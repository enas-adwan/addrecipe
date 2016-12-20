package com.example.hp.navigation.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.navigation.drawer.activity.R;

import static android.content.Context.MODE_PRIVATE;

public class fragment_comment extends Fragment {
    WebView simpleWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        simpleWebView = (WebView) view.findViewById(R.id.simpleWebView);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        String title=pref.getString("recipetitle","defult");
            String t=title;

            String url = "http://10.0.2.2/index.php?recipe="+t;
            //  simpleWebView.getSettings().setJavaScriptEnabled(true);
            //  simpleWebView.getSettings().setUseWideViewPort(true);
            //simpleWebView.getSettings().setLoadWithOverviewMode(true);
            WebSettings settings = simpleWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            simpleWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            simpleWebView.getSettings().setLoadWithOverviewMode(true);
            simpleWebView.getSettings().setUseWideViewPort(true);
            simpleWebView.setWebViewClient(new MyWebViewClient());
            simpleWebView.loadUrl(url); // load a web page in a web view



        return view;
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}