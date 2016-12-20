package com.example.hp.navigation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.navigation.drawer.activity.R;

public class main_activity_fragment extends BaseActivity {

    /* Tab identifiers */
    static String TAB_A = "Recipe";
    static String TAB_B = "Vedio";
    static String TAB_C = "Comment";

    TabHost mTabHost;
    Sec fragment1;
    frament_Video fragment2;
    fragment_comment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.main_fragment_recipe, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle("Show Recipe");
        //setContentView(R.layout.main_fragment_recipe);

        fragment1 = new Sec();
        fragment2 = new frament_Video();
        fragment3 = new fragment_comment();

        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();

        initializeTab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_menu, menu);
        return true;
    }

    /*
     * Initialize the tabs and set views and identifiers for the tabs
     */
    public void initializeTab() {

        TabHost.TabSpec spec    =   mTabHost.newTabSpec(TAB_A);
        mTabHost.setCurrentTab(-3);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });
        spec.setIndicator(createTabView(TAB_A, R.drawable.recip));
        mTabHost.addTab(spec);


        spec                    =   mTabHost.newTabSpec(TAB_B);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });

        spec.setIndicator(createTabView(TAB_B, R.drawable.video));
        mTabHost.addTab(spec);



        spec                    =   mTabHost.newTabSpec(TAB_C);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });
        spec.setIndicator(createTabView(TAB_C, R.drawable.comment));
        mTabHost.addTab(spec);

    }

    /*
     * TabChangeListener for changing the tab when one of the tabs is pressed
     */
    TabHost.OnTabChangeListener listener    =   new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {
            /*Set current tab..*/
            if(tabId.equals(TAB_A)){
                pushFragments(tabId, fragment1);
            }else if(tabId.equals(TAB_B)){
                pushFragments(tabId, fragment2);
            }
            else if(tabId.equals(TAB_C)){
                pushFragments(tabId, fragment3);
            }
        }
    };

    /*
     * adds the fragment to the FrameLayout
     */
    public void pushFragments(String tag, Fragment fragment){

        FragmentManager   manager         =   getSupportFragmentManager();
        FragmentTransaction ft            =   manager.beginTransaction();

        ft.replace(android.R.id.tabcontent, fragment);
        ft.commit();
    }

    /*
     * returns the tab view i.e. the tab icon and text
     */
    private View createTabView(final String text, final int id) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
        ImageView imageView =   (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));
        ((TextView) view.findViewById(R.id.tab_text)).setText(text);
        return view;
    }
}