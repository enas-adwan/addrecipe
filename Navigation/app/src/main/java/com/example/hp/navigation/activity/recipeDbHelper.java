package com.example.hp.navigation.activity;
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
    private static final String CREATE_QUERY_desc= "CREATE TABLE IF NOT EXISTS desc (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT) ";
    private static final String CREATE_QUERY_search= "CREATE TABLE IF NOT EXISTS search (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT) ";
    private static final String CREATE_QUERY_sqlite= "CREATE TABLE IF NOT EXISTS recipe (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT, " +
            "list TEXT, " +
            "descc TEXT, " +
            "calory TEXT, " +
            "prep TEXT, " +
            "cook TEXT, " +
            "total TEXT, " +
            "image TEXT, " +


            "ranking TEXT)";
    private static final String CREATE_QUERY_sqlite_offline= "CREATE TABLE IF NOT EXISTS recipeoffline (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT, " +
            "list TEXT, " +
            "descc TEXT, " +
            "calory TEXT, " +
            "prep TEXT, " +
            "cook TEXT, " +
            "total TEXT, " +
            "image TEXT, " +

            "ranking TEXT)";
    private static final String CREATE_QUERY_ranking= "CREATE TABLE IF NOT EXISTS ranking (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +


            "title TEXT)";

    public recipeDbHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.e("DATABASE OPERATION", "Database created / opened.....");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY_sqlite);
        db.execSQL(CREATE_QUERY_sqlite_offline);
        db.execSQL(CREATE_QUERY_ranking);
        Log.e("DATABASE OPERATION", "Table create..."+CREATE_QUERY);
    }
    public boolean istabledescexist( SQLiteDatabase db){
    boolean empty = true;
    Cursor cur = db.rawQuery("SELECT COUNT(*) FROM desc", null);
    if (cur != null && cur.moveToFirst()) {
        empty = (cur.getInt (0) == 0);
    }
    cur.close();

    return empty;
    }
    public boolean istabledescexistsearch( SQLiteDatabase db){
        boolean empty = true;
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM search", null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt (0) == 0);
        }
        cur.close();

        return empty;
    }
    public void addinnformation(Float caloryNum, String name, SQLiteDatabase db){
        db.execSQL(CREATE_QUERY);
        ContentValues contentValue = new ContentValues();
        contentValue.put("name",name);
        contentValue.put("calory",caloryNum);

        db.insert("project",null,contentValue);
        Log.e("DATABASE OPERATION", "One row is insert");
    }
    public void addinnformationdesc( String name, SQLiteDatabase db){
        db.execSQL(CREATE_QUERY_desc);
        ContentValues contentValue = new ContentValues();
        contentValue.put("name",name);


        db.insert("desc",null,contentValue);
        Log.e("DATABASE OPERATION", "One row is insert");
    }
    public void addinnformationsearch( String name, SQLiteDatabase db){
        db.execSQL(CREATE_QUERY_search);
        ContentValues contentValue = new ContentValues();
        contentValue.put("name",name);


        db.insert("search",null,contentValue);
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
    public Cursor getinformationdesc(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"name"};

        cursor= db.query("desc", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationsearch(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"name"};

        cursor= db.query("search", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationid(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"_id"};

        cursor= db.query("project", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationiddesc(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"_id"};

        cursor= db.query("desc", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationidsearch(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"_id"};

        cursor= db.query("search", projections, null, null, null, null, null);
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

    public void deletedesc(SQLiteDatabase db, int id) {
        String where = "_id ='" + id+"'";
        db.delete("desc", where, null);
        Log.e("DATABASE OPERATION", "delete.");
        // String delQuery = "DELETE FROM project WHERE name='"+name+"' ";

        // db.rawQuery(delQuery , null);
    }
    public void deletesearch(SQLiteDatabase db, int id) {
        String where = "_id ='" + id+"'";
        db.delete("search", where, null);
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
    public void dropdesc(SQLiteDatabase db) {

        Log.e("droppp", "drop");
        db.execSQL("DROP TABLE IF EXISTS desc");

    }
    public void droptables(SQLiteDatabase db) {

        Log.e("droppp", "drop");
        db.execSQL("DROP TABLE IF EXISTS recipe");
        db.execSQL("DROP TABLE IF EXISTS recipeoffline");

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
    public String[] SelectAlldesc() {

        // TODO Auto-generated method stub

        try {
            String arrData[] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data



            String strSQL = "SELECT name FROM desc ";

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
    public String[] SelectAllsearch() {

        // TODO Auto-generated method stub

        try {
            String arrData[] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data



            String strSQL = "SELECT name FROM search ";

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

                        arrData[i] = "-"+cursor.getString(0);
                        i++;
                    } while (cursor.moveToNext());
                 //   arrData[i] = "-";
                }
            }

            cursor.close();

            return arrData;

        } catch (Exception e) {
            return null;
        }

    }
    public void addsqliterecipe(String title,String list,String descc,String calory,String prep,String cook,String total,String image,String rating, SQLiteDatabase db){

        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY_sqlite);
        db.execSQL(CREATE_QUERY_sqlite_offline);
        db.execSQL(CREATE_QUERY_ranking);
        ContentValues contentValue = new ContentValues();
        contentValue.put("title",title);
        contentValue.put("list",list);
        contentValue.put("descc",descc);
        contentValue.put("calory",calory);
        contentValue.put("prep",prep);
        contentValue.put("cook",cook);
        contentValue.put("total",total);
        contentValue.put("image",image);
        contentValue.put("ranking",rating);


        db.insert("recipe",null,contentValue);
        Log.e("DATABASE OPERATION", "One row is insert");
    }

    public void addsqliterecipeoffline(String title,String list,String descc,String calory,String prep,String cook,String total,String image,String rating, SQLiteDatabase db){
        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY_sqlite);
        db.execSQL(CREATE_QUERY_sqlite_offline);
        db.execSQL(CREATE_QUERY_ranking);

        String strSQL = "SELECT * FROM recipeoffline";
        strSQL += " WHERE title ='"+title+"'";
        Cursor cursor = db.rawQuery(strSQL, null);
        if(cursor.getCount() <= 0){

            ContentValues contentValue = new ContentValues();
            contentValue.put("title",title);
            contentValue.put("list",list);
            contentValue.put("descc",descc);
            contentValue.put("calory",calory);
            contentValue.put("prep",prep);
            contentValue.put("cook",cook);
            contentValue.put("total",total);
            contentValue.put("image",image);
            contentValue.put("ranking",rating);


            db.insert("recipeoffline",null,contentValue);
            Log.e("DATABASE OPERATION", "One row is insert");
            cursor.close();
        }else{
        cursor.close();
        Log.e("DATABASE OPERATION", "not insert");}

    }
    public Cursor getinformationsqlite(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"title"};

        cursor= db.query("recipe", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationidsqlite(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"_id"};

        cursor= db.query("recipe", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationidsqliteranking(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"ranking"};

        cursor= db.query("recipe", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformation2sqlite(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"image"};

        cursor= db.query("recipe", projections,null , null, null, null, null);
        return cursor;
    }
    public Cursor getinformationsqliteoffline(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"title"};

        cursor= db.query("recipeoffline", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationidsqliteoffline(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"_id"};

        cursor= db.query("recipeoffline", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformationsqliteofflinerating(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"ranking"};

        cursor= db.query("recipeoffline", projections, null, null, null, null, null);
        return cursor;
    }
    public Cursor getinformation2sqliteoffline(SQLiteDatabase db){
        Cursor cursor;
        String[] projections={"image"};

        cursor= db.query("recipeoffline", projections,null , null, null, null, null);
        return cursor;
    }
    public static boolean CheckIsDataAlreadyInDBorNot( String fieldValue,SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY_sqlite);
        db.execSQL(CREATE_QUERY_sqlite_offline);
        db.execSQL(CREATE_QUERY_ranking);
        String strSQL = "SELECT * FROM recipe";
        strSQL += " WHERE title ='"+fieldValue+"'";
        Cursor cursor = db.rawQuery(strSQL, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public static boolean Checkranking( String fieldValue,SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY_sqlite);
        db.execSQL(CREATE_QUERY_sqlite_offline);
        db.execSQL(CREATE_QUERY_ranking);
        String strSQL = "SELECT * FROM ranking";
        strSQL += " WHERE title ='"+fieldValue+"'";
        Cursor cursor = db.rawQuery(strSQL, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public Cursor SelectAllData(String searchTerm,SQLiteDatabase db) {

        // TODO Auto-generated method stub

        try {


            db = this.getReadableDatabase(); // Read Data



            String strSQL = "SELECT * FROM recipe";
            strSQL += " WHERE title ='"+searchTerm+"'";

            Cursor cursor = db.rawQuery(strSQL, null);




            return cursor;

        } catch (Exception e) {
            return null;
        }

    }
    public Cursor SelectAllDataoffline(String searchTerm,SQLiteDatabase db) {

        // TODO Auto-generated method stub

        try {


            db = this.getReadableDatabase(); // Read Data



            String strSQL = "SELECT * FROM recipeoffline";
            strSQL += " WHERE title ='"+searchTerm+"'";

            Cursor cursor = db.rawQuery(strSQL, null);




            return cursor;

        } catch (Exception e) {
            return null;
        }

    }
    public void deletefav(SQLiteDatabase db, String title) {
        String where = "title ='" +title+"'";
        db.delete("recipe", where, null);
        Log.e("DATABASE OPERATION", "delete.");
        // String delQuery = "DELETE FROM project WHERE name='"+name+"' ";

        // db.rawQuery(delQuery , null);
    }
    public void titleranking(SQLiteDatabase db, String title) {
        db.execSQL(CREATE_QUERY_ranking);
        ContentValues contentValue = new ContentValues();
        contentValue.put("title",title);

        db.insert("ranking",null,contentValue);
        Log.e("DATABASE OPERATION", "One row is insert");
        // String delQuery = "DELETE FROM project WHERE name='"+name+"' ";

        // db.rawQuery(delQuery , null);
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