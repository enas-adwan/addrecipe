package com.example.hp.navigation.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class oldTrakingDB extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "projectTraking1";

    // Contacts table name
  //  private static final SimpleDateFormat formatter = new SimpleDateFormat(
    //        "yyyy-MM-dd", Locale.ENGLISH);
    private static final String TABLE_CONTACTS = "traking";

    // Contacts Table Columns names
   // private static final String KEY_ID = "_id";
   // private static final String KEY_NAME = "name";
   // private static final String KEY_Date = "date";
   // private static final String KEY_calory = "calory";

    public oldTrakingDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
     final String CREATE_QUERY_tracking_project= "CREATE TABLE IF NOT EXISTS "+TABLE_CONTACTS+"(" +
              "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
               "name TEXT, " +
               "calory FLOAT, " +
                "date TEXT)";


       // String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
       //         + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + "TEXT,"
       //         + KEY_calory + "FLOAT," + KEY_Date + " DATE" + ")";
       // db.execSQL(CREATE_CONTACTS_TABLE);

       // String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
           ////     + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"+ KEY_calory + "FLOAT,"
            //   + KEY_Date + "DATE" + ")";
        db.execSQL(CREATE_QUERY_tracking_project);
    }




    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", contact.getName()); // Contact Name
        values.put("calory", contact.getCalory()); // Contact calory
       values.put("date", contact.getDateTime());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { "_id",
                        "name","calory","date" }, "_id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = null;

            contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Float.parseFloat(cursor.getString(2))
             ,cursor.getString(3));

        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts(String d) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        selectQuery += " WHERE "+"date"+" LIKE '"+d+"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setCalory(Float.parseFloat(cursor.getString(2)));
                contact.setDate(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("calory",  contact.getCalory());
        values.put("date", contact.getDateTime());

        // updating row
        return db.update(TABLE_CONTACTS, values, "_id" + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, "_id" + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public  void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM old_calory"); //delete all rows in a table
        db.close();

    }

}