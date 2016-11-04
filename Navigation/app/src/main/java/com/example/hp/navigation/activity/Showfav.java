package com.example.hp.navigation.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ListView;

import com.navigation.drawer.activity.R;

import java.util.ArrayList;

public class Showfav extends BaseActivity {
    recipeDbHelper userDbHelper2;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ArrayList<Record> records;
    Cursor cursor1;
    Cursor cursor2;
    Cursor cursor4;
    public static ListView listView;
    Record record;
   static RecyclerView mRecyclerView;
    Vivsadapter vivsadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_showfav, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // listView=(ListView)findViewById(R.id.lv);
        records= new ArrayList<Record>();
        mRecyclerView = (RecyclerView) findViewById(R.id.masonry_grid);
        //vivs=new Vivsadapter(getApplicationContext(),R.layout.single_row);
        userDbHelper2=new recipeDbHelper(getApplicationContext());
        sqLiteDatabase=userDbHelper2.getReadableDatabase();
        cursor=userDbHelper2.getinformationsqlite(sqLiteDatabase);
        cursor1=userDbHelper2.getinformation2sqlite(sqLiteDatabase);
        cursor2=userDbHelper2.getinformationidsqlite(sqLiteDatabase);
        cursor4=userDbHelper2.getinformationidsqliteranking(sqLiteDatabase);


        if(cursor.moveToFirst()&&cursor2.moveToFirst()&&cursor4.moveToFirst()&& cursor1.moveToFirst())
        {
            do {

                int id=cursor2.getInt(0);
             String  title=cursor.getString(0);
         String image =cursor1.getString(0);


                String rating=cursor4.getString(0);;

                record = new Record(title, id,image,rating);


                records.add(record);

                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

                Vivsadapter adapter = new Vivsadapter(Showfav.this,records);
                mRecyclerView.setTag("fav");
                mRecyclerView.setAdapter(adapter);
               // SpacesItemDecoration decoration = new SpacesItemDecoration(16);


            }while (cursor.moveToNext()&&cursor2.moveToNext()&&cursor4.moveToNext()&&cursor1.moveToNext());

        }

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/









    }
    protected void onStop() {
        mRecyclerView.setTag("nnfav");
        // TODO Auto-generated method stub
        listView=null;
        super.onStop();
    }

}
