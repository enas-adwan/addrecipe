package com.example.hp.navigation.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DATABASES";

    public DBManager(Context context) {
        super(context, "data.db", null, DATABASE_VERSION);

    }
    public Integer findcalory(String s) {

        String rrr="n";
        Integer rr=0;
        String mm;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectStmt = "select Energ_Kcal from foodtable where Shrt_Desc ='"+s+"'";

        Cursor cursor = db.rawQuery(selectStmt, null);

        if (cursor.moveToFirst()) {



            //rrr= cursor.getString(1);
            rr=     cursor.getInt(0);


        }
        // mm=rr.toString();
        return rr;


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