package com.example.eodwan.cookingrecipe;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hp on 03/09/2016.
 */
public class recipeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="project";
    private static final int DATABASE_VERSION=5;
   // private static final String CREATE_QUERY="CREATE TABLE "+ ShowCalory.NewrecipeInfo.TABLE_NAME+"("+
        //    ShowCalory.NewrecipeInfo.recipe_calory+" FLOAT );";
    private static final String CREATE_QUERY= "CREATE TABLE IF NOT EXISTS project (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +

            "calory  FLOAT)";

    public recipeDbHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.e("DATABASE OPERATION", "Database created / opened.....");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATION", "Table create..."+CREATE_QUERY);
    }
    public void addinnformation(Float caloryNum, String name, SQLiteDatabase db){
        db.execSQL(CREATE_QUERY);
        ContentValues contentValue = new ContentValues();
        contentValue.put("name",name);
        contentValue.put("calory",caloryNum);

        db.insert("project",null,contentValue);
        Log.e("DATABASE OPERATION", "One row is insert");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getinformation(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"name"};

        cursor= db.query("project", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationid(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"_id"};

        cursor= db.query("project", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformation2(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"calory"};

        cursor= db.query("project", projections,null , null, null, null, null);
        return cursor;
    }

    public void delete(SQLiteDatabase db, int id) {
        String where = "_id ='" + id+"'";
        db.delete("project", where, null);
        Log.e("DATABASE OPERATION", "delete.");
   // String delQuery = "DELETE FROM project WHERE name='"+name+"' ";

   // db.rawQuery(delQuery , null);
    }
    public float getTotal(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery(
                "SELECT SUM(calory) FROM project", null);
        if(cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }
        return cursor.getFloat(0);

    }
    public void drop(SQLiteDatabase db) {

        Log.e("droppp", "drop");
    db.execSQL("DROP TABLE IF EXISTS project");

}

    public String[] SelectAll() {

        // TODO Auto-generated method stub

        try {
            String arrData[] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data



            String strSQL = "SELECT name FROM project ";

            strSQL +=  " ORDER BY _id DESC ";

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

}
/*


 layoutHandler.deleteBtn = (ImageButton)row.findViewById(R.id.trash);


            layoutHandler.deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
              String m=     getn(position);
                   SQLiteDatabase sqLiteDatabase;
                  //  DataProvider dataProvider=(DataProvider)this.getTtem(position);
                  // layoutHandler.calory.setText(dataProvider.getName());
                   //return list.get(position);
                    //do something
                    //AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();

                    list.remove(position); //or some other task
                    recipeDbHelper userDbHelper;
                    userDbHelper=new  recipeDbHelper(getContext());

                    sqLiteDatabase=userDbHelper.getWritableDatabase();
                   userDbHelper.delete(sqLiteDatabase,m);

                    notifyDataSetChanged();
                    Toast.makeText(getContext(), "deleted"  , Toast.LENGTH_LONG).show();
                }
            });
 */