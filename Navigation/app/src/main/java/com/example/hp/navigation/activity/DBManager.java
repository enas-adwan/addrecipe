package com.example.hp.navigation.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DATABASES";

    public DBManager(Context context) {
        super(context, "data.db", null, DATABASE_VERSION);

    }
    public ArrayList<String> findcalory(String s) {

        String rrr="n";
        ArrayList<String> array = new ArrayList<String>();
        Integer rr=0;
        String mm;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectStmt = "select Energ_Kcal , Vit_C , Calcium , Iron , Protein , Vit_B6 , Vit_B12 , Vit_E  from foodtable where Shrt_Desc ='"+s+"'";

        Cursor cursor = db.rawQuery(selectStmt, null);

        if (cursor.moveToFirst()) {



            //rrr= cursor.getString(1);
           array.add(cursor.getString(0));
           array.add(cursor.getString(1));
            array.add(cursor.getString(2));
            array.add(cursor.getString(3));
            array.add(cursor.getString(4));
            array.add(cursor.getString(5));
            array.add(cursor.getString(6));
            array.add(cursor.getString(7));
           // rr=     cursor.getInt(1);

        }
        // mm=rr.toString();
        return array;


    }
    public String[] SelectAllData(String searchTerm) {

        // TODO Auto-generated method stub

        try {
            String arrData[] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data



            String strSQL = "SELECT Shrt_Desc FROM foodtable";
            strSQL += " WHERE Shrt_Desc LIKE '"+searchTerm+"%'";

            Cursor cursor = db.rawQuery(strSQL, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()];
                    /***
                     *  [x] = Name
                     */
                    int i= 0;
                    do {
                        arrData[i] = cursor.getString(0);
                        i++;
                    } while (cursor.moveToNext());

                }
            }
            cursor.close();

            return arrData;

        } catch (Exception e) {
            return null;
        }

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG,"On create Called:"+db.getPath());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}