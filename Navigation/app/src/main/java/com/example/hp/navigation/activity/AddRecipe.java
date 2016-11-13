package com.example.hp.navigation.activity;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.navigation.drawer.activity.R;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import android.content.SharedPreferences;
import android.widget.VideoView;
//import android.asynctask.library.JSONParser;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v7.widget.SearchView;

public class AddRecipe extends BaseActivity {

    protected DataBaseHelper db;
    private ImageButton Getall;
    private ImageButton Addd;
    private Spinner spinner1;
    private static int RESULT_LOAD_IMG = 1;
    public static ListView listView;
    public static ListView listV;
    public String namo = "";
    public String valuo = "";
    public String unit = "";
    Float value;
    String image_str;
    public String videoResult="";
    String stringFloat;
    Float value1;
    public Button loadd;
    private int position = 0;
    private MediaController mediaController;
    private Button buttonChoosvideo;
    private Button buttonUploadVideo;
    private Button buttonView;
    private TextView t1;
    String str="";
    private static final int SELECT_video = 2;
    String selectedPath = "";
    private Button Timo;
    String stringFloat1;
    Float value2;
    String stringFloat2;
    Float value3;
    String stringFloat3;
    String food;
    public static TextView Text;
    Cursor cursor;
    ListDataAdpter listDataAdpter;
    SQLiteDatabase sqLiteDatabase;
    recipeDbHelper userDbHelper;
    recipeDbHelper droptable;
    public ImageView imgVieww;
    private EditText Prep;
    private EditText Cook;
    private EditText Total;
    private EditText Title;
    public static EditText Desc;
    public static TextView Textnum;
    public String[] myDataa;
    public String[] myDataadesc;
    recipeDbHelper userDbHelper3;
    private static final String RECIPE_URL = "http://10.0.2.2/addrecipe.php";
    String list;
    String listdesc;
    Integer r = 0;
    private static final String TAG = "DataBaseHelper";

    public AutoCompleteTextView autoCom;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.addrecipe);

        getLayoutInflater().inflate(R.layout.addrecipe, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle("Add Recipe");
        db = new DataBaseHelper(this);

        db.getWritableDatabase();
        Text = (TextView) findViewById(R.id.text);
     /* try{  db.createDataBase();


    } catch (Exception e) {
        e.printStackTrace();
    }*/
        Title = (EditText) findViewById(R.id.t);
        Prep= (EditText) findViewById(R.id.prep);
        // Total= (EditText) findViewById(R.id.total);
        Cook= (EditText) findViewById(R.id.cook);
        t1=(TextView) findViewById(R.id.text1);
        Desc=  (EditText) findViewById(R.id.desc);
        Textnum= (TextView) findViewById(R.id.textnum);
//=======Code For copying Existing Database file to system folder for use====//
        // Copying Existing Database into system folder
        try {

            String destPath = "/data/data/" + getPackageName()
                    + "/databases/data.db";

            File f = new File(destPath);
            if(!f.exists()){
                Log.v(TAG,"File Not Exist");
                InputStream in = getAssets().open("project.sqlite");
                OutputStream out = new FileOutputStream(destPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.v("TAG","ioexeption");
            e.printStackTrace();
        }

        DBManager dbManager =  new DBManager(this);
        Log.v(TAG,"Database is there with version: "+dbManager.getReadableDatabase().getVersion());
        //String sql = "select * from prizes";


        SQLiteDatabase db = dbManager.getReadableDatabase();
        // Cursor cursor = db.rawQuery(sql, null);
        //  Log.v(TAG,"Query Result:"+cursor);


        //  cursor.close();
        db.close();
        dbManager.close();
       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/  autoCom = (AutoCompleteTextView) findViewById(R.id.auto);
        final DBManager myDb = new DBManager(AddRecipe.this);
        autoCom.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String s1 = s.toString();
                try {
                    final String[] myData = myDb.SelectAllData(s1);
                    //   final AutoCompleteTextView autoCom = (AutoCompleteTextView)findViewById(R.id.auto);


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddRecipe.this,

                            android.R.layout.simple_dropdown_item_1line, myData);
                    autoCom.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        Getall = (ImageButton) findViewById(R.id.getall);

        Getall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String food = autoCom.getText().toString().trim();
                Integer numbe  = myDb.findcalory(food);
                String numberAsString = numbe.toString();

                Toast.makeText(getApplicationContext(), numberAsString  , Toast.LENGTH_LONG).show();

                calorycalc(numberAsString);
                // jso1(numberAsString);


            }
        });
        Addd = (ImageButton) findViewById(R.id.addd);

        Addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor;
                Cursor cursor1;
                Cursor cursor2;
                int sum = 1;
                ListDataAdpterdesc listDataAdpter;
                SQLiteDatabase sqLiteDatabase;
                recipeDbHelper userDbHelper;
                recipeDbHelper userDbHelper2;
                String food = Desc.getText().toString().trim();
                listV = (ListView) findViewById(R.id.list_v);
                userDbHelper = new recipeDbHelper(getApplicationContext());
                sqLiteDatabase = userDbHelper.getWritableDatabase();
                userDbHelper.addinnformationdesc(food, sqLiteDatabase);

                listDataAdpter = new ListDataAdpterdesc(getApplicationContext(), R.layout.row_layout1);
                listV.setAdapter(listDataAdpter);
                userDbHelper2 = new recipeDbHelper(getApplicationContext());
                sqLiteDatabase = userDbHelper2.getReadableDatabase();
                cursor2=userDbHelper2.getinformationiddesc(sqLiteDatabase);
                cursor=userDbHelper2.getinformationdesc(sqLiteDatabase);

                if(cursor2.moveToFirst()&&cursor.moveToFirst())
                {
                    do {
                        String descname=cursor.getString(0);
                        int id=cursor2.getInt(0);
                        DataProviderdesc dataProvider = new DataProviderdesc(descname,id);
                        listDataAdpter.add(dataProvider);


                    }while (cursor2.moveToNext()&&cursor.moveToNext());

                }

                setListViewHeightBasedOnItems(listV);
                // jso1(numberAsString);

            }

        });
        // NumberPicker np = (NumberPicker) findViewById(R.id.np);
        //np.setMinValue(0);
        //np.setMaxValue(10);
        //np.setWrapSelectorWheel(true);
        //  Timo = (Button) findViewById(R.id.timo);

        //  Timo.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View v) {
        //Calendar calendar = GregorianCalendar.getInstance();
        //MyTimePickerDialog mTimePicker = new MyTimePickerDialog(this, new MyTimePickerDialog.OnTimeSetListener() {

        //  @Override
        //    public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
        // TODO Auto-generated method stub
                /*time.setText(getString(R.string.time) + String.format("%02d", hourOfDay)+
                        ":" + String.format("%02d", minute) +
                        ":" + String.format("%02d", seconds));  */
        //      }
        //    }, now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
        //      mTimePicker.show();




        //    }
        //  });
        loadd = (Button) findViewById(R.id.bb);

        loadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                // startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"),RESULT_LOAD_IMG);



            }
        });


        buttonChoosvideo = (Button) findViewById(R.id.button); //video
        buttonChoosvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(AddRecipe.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddRecipe.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {

                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select video "), SELECT_video);}
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(AddRecipe.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            int itemPos = 0;
            for (; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
    private void  calorycalc(String calory) {


        class RegisterUser extends AsyncTask<String, Void, String> {

            public String namo = "";
            public String valuo = "";
            public String unit = "";
            Float value;
            String stringFloat;
            Float value1;
            String stringFloat1;
            Float value2;
            String stringFloat2;
            Float value3;
            String stringFloat3;
            String food;
            public  TextView Text;
            Cursor cursor;
            Cursor cursor1;
            Cursor cursor2;
            ListDataAdpter listDataAdpter;
            SQLiteDatabase sqLiteDatabase;
            recipeDbHelper userDbHelper;
            recipeDbHelper userDbHelper2;


            Integer r = 0;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                Context context=AddRecipe.this;
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                String quintity= String.valueOf(spinner1.getSelectedItem());
                listView=(ListView)findViewById(R.id.list_view);
                userDbHelper=new recipeDbHelper(getApplicationContext());
                sqLiteDatabase=userDbHelper.getWritableDatabase();
                Float sum=0.0f;
                String calory;
                String caloryvalue;


                try {

                    //   Text.setText(valuo);
                    value = Float.parseFloat(s);
                    value= value/6.666666666666667f;
                    stringFloat= Float.toString(value);
                    value1 = Float.parseFloat(s);
                    value1= value1*1.1f;
                    stringFloat1= Float.toString(value1);
                    value2 = Float.parseFloat(s);
                    value2= value2*.6f;
                    stringFloat2= Float.toString(value2);
                    value3 = Float.parseFloat(s);
                    value3= value3*.85f;
                    stringFloat3= Float.toString(value3);
                    food = autoCom.getText().toString().trim();
                    //   Toast.makeText(getApplicationContext(), s  , Toast.LENGTH_LONG).show();


                    switch(quintity){

                        case "Spoon":
                            //  Text.setText(stringFloat);
                            userDbHelper.addinnformation(value,"- "+"Spoon of "+food,sqLiteDatabase);

                            listDataAdpter=new ListDataAdpter(getApplicationContext(), R.layout.row_layout);
                            listView.setAdapter(listDataAdpter);
                            userDbHelper2=new recipeDbHelper(getApplicationContext());
                            sqLiteDatabase=userDbHelper2.getReadableDatabase();
                            cursor=userDbHelper2.getinformation(sqLiteDatabase);
                            cursor1=userDbHelper2.getinformation2(sqLiteDatabase);
                            cursor2=userDbHelper2.getinformationid(sqLiteDatabase);

                            if(cursor.moveToFirst()&&cursor1.moveToFirst()&&cursor2.moveToFirst())
                            {
                                do {
                                    int id=cursor2.getInt(0);
                                    Float M=cursor1.getFloat(0);
                                    caloryvalue=M.toString();
                                    calory=cursor.getString(0);
                                    sum=userDbHelper2.getTotal(sqLiteDatabase);
                                    Text.setText(String.valueOf(sum));
                                    DataProvider dataProvider=new DataProvider(calory, caloryvalue,id);
                                    listDataAdpter.add(dataProvider);

                                }while (cursor.moveToNext()&&cursor1.moveToNext()&&cursor2.moveToNext());

                            }
                            //  userDbHelper2.close();
                            break;

                        case "Cup of ":
                            userDbHelper.addinnformation(value1,"- "+"Cup "+food,sqLiteDatabase);

                            listDataAdpter=new ListDataAdpter(getApplicationContext(), R.layout.row_layout);
                            listView.setAdapter(listDataAdpter);
                            userDbHelper2=new recipeDbHelper(getApplicationContext());
                            sqLiteDatabase=userDbHelper2.getReadableDatabase();
                            cursor=userDbHelper2.getinformation(sqLiteDatabase);
                            cursor1=userDbHelper2.getinformation2(sqLiteDatabase);
                            cursor1=userDbHelper2.getinformationid(sqLiteDatabase);
                            cursor2=userDbHelper2.getinformationid(sqLiteDatabase);

                            if(cursor.moveToFirst()&&cursor1.moveToFirst()&&cursor2.moveToFirst())
                            {
                                do {
                                    int id=cursor2.getInt(0);
                                    Float M=cursor1.getFloat(0);
                                    caloryvalue=M.toString();
                                    calory=cursor.getString(0);
                                    sum=userDbHelper2.getTotal(sqLiteDatabase);
                                    Text.setText(String.valueOf(sum));
                                    DataProvider dataProvider=new DataProvider(calory,  caloryvalue,id);
                                    listDataAdpter.add(dataProvider);

                                }while (cursor.moveToNext()&&cursor1.moveToNext()&&cursor2.moveToNext());

                            }
                            break;
                        case "1/2 Cup":

                            userDbHelper.addinnformation(value2,"- "+"1/2 Cup of "+food,sqLiteDatabase);

                            listDataAdpter=new ListDataAdpter(getApplicationContext(), R.layout.row_layout);
                            listView.setAdapter(listDataAdpter);
                            userDbHelper2=new recipeDbHelper(getApplicationContext());
                            sqLiteDatabase=userDbHelper2.getReadableDatabase();
                            cursor=userDbHelper2.getinformation(sqLiteDatabase);
                            cursor1=userDbHelper2.getinformation2(sqLiteDatabase);
                            cursor2=userDbHelper2.getinformationid(sqLiteDatabase);


                            if(cursor.moveToFirst()&&cursor1.moveToFirst()&&cursor2.moveToFirst())
                            {
                                do {
                                    int id=cursor2.getInt(0);
                                    calory=cursor.getString(0);
                                    Float M=cursor1.getFloat(0);
                                    caloryvalue=M.toString();
                                    sum=userDbHelper2.getTotal(sqLiteDatabase);
                                    Text.setText(String.valueOf(sum));
                                    DataProvider dataProvider=new DataProvider(calory,caloryvalue,id);
                                    listDataAdpter.add(dataProvider);

                                }while (cursor.moveToNext()&&cursor1.moveToNext()&&cursor2.moveToNext());

                            }
                            break;
                        case "3/4 Cup":

                            userDbHelper.addinnformation(value3,"- "+"3/4 Cup of "+food,sqLiteDatabase);

                            listDataAdpter=new ListDataAdpter(getApplicationContext(), R.layout.row_layout);
                            listView.setAdapter(listDataAdpter);

                            userDbHelper2=new recipeDbHelper(getApplicationContext());
                            sqLiteDatabase=userDbHelper2.getReadableDatabase();
                            cursor=userDbHelper2.getinformation(sqLiteDatabase);
                            cursor1=userDbHelper2.getinformation2(sqLiteDatabase);
                            cursor2=userDbHelper2.getinformation2(sqLiteDatabase);


                            if(cursor.moveToFirst()&&cursor1.moveToFirst()&&cursor2.moveToFirst())
                            {
                                do {
                                    int id=cursor2.getInt(0);
                                    calory=cursor.getString(0);
                                    Float M=cursor1.getFloat(0);
                                    caloryvalue=M.toString();
                                    sum=userDbHelper2.getTotal(sqLiteDatabase);
                                    Text.setText(String.valueOf(sum));
                                    DataProvider dataProvider=new DataProvider(calory,   caloryvalue,id);
                                    listDataAdpter.add(dataProvider);

                                }while (cursor.moveToNext()&&cursor1.moveToNext()&&cursor2.moveToNext());

                            }
                            break;


                    }





                } catch (Exception e) {
                    e.printStackTrace();

                }

                setListViewHeightBasedOnItems(listView);

            }


            @Override
            protected String doInBackground(String... params) {
                Text = (TextView) findViewById(R.id.text);
                return params[0];

            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(calory);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipe, menu);
        return true;
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        droptable=new recipeDbHelper(getApplicationContext());
        sqLiteDatabase=droptable.getWritableDatabase();
        //sqLiteDatabase.drop( droptable);
        droptable.drop(sqLiteDatabase);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == SELECT_video)
            {
                if (resultCode == RESULT_OK) {

                    System.out.println("SELECT_video");
                    Uri selectedImageUri = data.getData();
                    selectedPath = getPath(selectedImageUri);
                   // t1.setText("SELECT_video Path :" +selectedPath);
                    Log.d("path","SELECT_video Path :" +selectedPath);
                }
                else if (resultCode == RESULT_CANCELED) {

                    // user cancelled Image capture
                    Toast.makeText(this,
                            "User cancelled video capture", Toast.LENGTH_SHORT)
                            .show();

                } else {
                    // failed to capture image
                    Toast.makeText(this,
                            "Sorry! Failed to capture video", Toast.LENGTH_SHORT)
                            .show();
                }

            }
            // When an Image is picked
            else  if (requestCode == RESULT_LOAD_IMG)
            {
                if(resultCode == RESULT_OK   && null != data && data.getData() != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    // profile_image=selectedImage.toString();
                    //   URL newurl = new URL(selectedImage);
                    // Uri selectedImage = data.getData();
                    //in = new BufferedInputStream(new URL(selectedImage).openStream(), IO_BUFFER_SIZE);
                    //  String     selectedFilePath = FilePath.getPath(this,selectedImage);

                    // Get the cursor
             /*  Cursor cursor = getContentResolver().query(selectedImage,
                     filePathColumn, null, null, null);
           //    Move to first row
            cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
               imgPath = cursor.getString(columnIndex);
               cursor.close();

                Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString( idx);
                */
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    //Bitmap bitmap1=BitmapFactory.decodeFile(picturePath);
                    cursor.close();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                    byte[] byte_arr = stream.toByteArray();
                    image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);


                    //    Bitmap bitmap=BitmapFactory.decodeFile(picturePath);

                    //return cursor.getString(idx);
                    imgVieww = (ImageView) findViewById(R.id.img);
                    // Set the Image in ImageView
                    imgVieww.setImageBitmap(bitmap);

                    //  String fileNameSegments[] = imgPath.split("/");
                    //fileName = fileNameSegments[fileNameSegments.length - 1];
                    // Text.setText( image_str);
                    //  imgVieww.setImageBitmap(BitmapFactory
                    //    .decodeFile(imgPath));
                    Toast.makeText(this, "worked",
                            Toast.LENGTH_LONG).show();
                }
                // Get the Image's file name
                // String fileNameSegments[] = imgPath.split("/");
                //   fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
                //   params.put("filename", fileName);
                else if (resultCode == RESULT_CANCELED)  {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }

            }

        } catch (Exception e) {
            Toast.makeText(this, "some thing went wrong ", Toast.LENGTH_LONG)
                    .show();
        }

    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private class doupload extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AddRecipe.this);
            progressDialog.setMessage("Uploading Video...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            super.onPreExecute();

        }

    /**    @Override
     protected void onProgressUpdate(Integer... progress) {
            // progressBar.setProgress(progress[0]);
            // percentageTextView.setText(String.valueOf(progress[0]) + "%");
            if(isCancelled())
            {
                return ;
            }
            else
            {
                progressDialog.setProgress(progress[0]);
                if(progress[0] == 100)
                {
                    progressDialog.setMessage("Validating video from server...");
                    //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                }
            }
     }
     **/

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            DataInputStream inStream = null;
            String result="";
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary =  "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1*1024*1024;
            String responseFromServer = "";
            String urlString = "http://10.0.2.2/upload2.php";
            try
            {
                //------------------ CLIENT REQUEST
                FileInputStream fileInputStream = new FileInputStream(new File(selectedPath) );
                // open a URL connection to the Servlet
                URL url = new URL(urlString);
                // Open a HTTP connection to the URL
                conn = (HttpURLConnection) url.openConnection();

                // Allow Inputs
                conn.setDoInput(true);
                // Allow Outputs
                conn.setDoOutput(true);
                // Don't use a cached copy.
                conn.setUseCaches(false);
                // Use a post method.
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
                dos = new DataOutputStream( conn.getOutputStream() );
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + selectedPath + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0)
                {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // close streams
                //  Log.e("Debug","File is written");
                fileInputStream.close();
                dos.flush();
                dos.close();
            }
            catch (MalformedURLException ex)
            {

                ex.printStackTrace();
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            //------------------ read the SERVER RESPONSE
            try {
                inStream = new DataInputStream ( conn.getInputStream() );
                String str;

                while (( str = inStream.readLine()) != null)
                {
                    result+=str;
                }

                inStream.close();
                return result;
            }
            catch (IOException ioex){
                ioex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                Log.d("result upload",result);
                videoResult=result;
            }

            super.onPostExecute(result);
        }
    }

    private void Addrecipe(String title , String calory, String secret, String desc, String list, String total, String prep, String cook, final String videoName) {
       /* String urlSuffix = "?name=" + name + "&password=" + password;*/





        class add extends AsyncTask<String, Void, String> {

            addphprecipe ruc = new addphprecipe();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("add result",s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if(s.equalsIgnoreCase("successfully added")){
                    droptable=new recipeDbHelper(getApplicationContext());
                    sqLiteDatabase=droptable.getWritableDatabase();
                    //sqLiteDatabase.drop( droptable);
                    droptable.drop(sqLiteDatabase);
                    droptable.dropdesc(sqLiteDatabase);
                }

            }

            @Override
            protected String doInBackground(String... params) {
              /*  String s = params[0];
              Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(REGISTER_URL+s));
                startActivity(i);*/



                Long tsLong = System.currentTimeMillis() / 1000;
                String timestamp = tsLong.toString();
                String nameimage ="IMG_"+timestamp;
                HashMap<String, String> data = new HashMap<String,String>();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                String user=pref .getString("user_type","");

                /**        if(selectedPath=="empty") {

                 if (user.equals("app")) {
                 String user1 = pref.getString("user_email", "");
                 data.put("title", params[0]);
                 data.put("calory", params[1]);
                 data.put("secret", params[2]);
                 data.put("desc", params[3]);
                 data.put("list", params[4]);
                 data.put("total", params[5]);
                 data.put("prep", params[6]);
                 data.put("cook", params[7]);
                 data.put("image", image_str);
                 data.put("video",selectedPath);
                 data.put("nameimage", nameimage);
                 data.put("type", "app");
                 data.put("user_key", user1);

                 } else {
                 String user1 = pref.getString("face_id", "");
                 data.put("title", params[0]);

                 data.put("calory", params[1]);
                 data.put("secret", params[2]);
                 data.put("desc", params[3]);
                 data.put("list", params[4]);
                 data.put("total", params[5]);
                 data.put("prep", params[6]);
                 data.put("cook", params[7]);
                 data.put("image", image_str);
                 data.put("video",selectedPath);
                 data.put("nameimage", nameimage);
                 data.put("type", "face");
                 data.put("face_id", user1);

                 }


                 //  String fileNameSegments[] = imgPath.split("/");
                 // fileName = fileNameSegments[fileNameSegments.length - 1];
                 //        Bitmap image = ((BitmapDrawable) imgVieww.getDrawable()).getBitmap();
                 }
                 **/

                if (user.equals("app")) {
                    String user1 = pref.getString("user_email", "");
                    data.put("title", params[0]);
                    data.put("calory", params[1]);
                    data.put("secret", params[2]);
                    data.put("desc", params[3]);
                    data.put("list", params[4]);
                    data.put("total", params[5]);
                    data.put("prep", params[6]);
                    data.put("cook", params[7]);
                    data.put("video", params[8]);
                    data.put("image", image_str);
                    data.put("video",videoName);
                    data.put("nameimage", nameimage);
                    data.put("type", "app");
                    data.put("user_key", user1);

                } else {
                    String user1 = pref.getString("face_id", "");
                    data.put("title", params[0]);

                    data.put("calory", params[1]);
                    data.put("secret", params[2]);
                    data.put("desc", params[3]);
                    data.put("list", params[4]);
                    data.put("total", params[5]);
                    data.put("prep", params[6]);
                    data.put("cook", params[7]);
                    data.put("video", videoName);
                    data.put("image", image_str);
                    data.put("video",str);
                    data.put("nameimage", nameimage);
                    data.put("type", "face");
                    data.put("face_id", user1);

                }





                String result = ruc.sendPostRequest(RECIPE_URL, data);


                return result;

               /* BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;
;
                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return "no";
                }*/
            }
        }

        add ru = new add();
        ru.execute(title,calory,secret,desc,list,total,prep,cook,videoName);

       /* ru.execute(urlSuffix);*/
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.done:

                String secret="3CH6knCsenas2va8GrHk4mf3JqmUctCM";
                String title = Title.getText().toString().trim();
                String food = Text.getText().toString().trim();
                String desc = Desc.getText().toString().trim();
                String prep = Prep.getText().toString().trim();
                String videoName="no video";

                try{  String cook = Cook.getText().toString().trim();
                    int prepering=Integer.valueOf(prep);
                    int cooking=Integer.valueOf(cook);
                    int totalall=prepering+cooking;
                    String total=String.valueOf(totalall);
                    //String total = Total.getText().toString().trim();
                    // String prep = Prep.getText().toString().trim();
                    //String cook = Cook.getText().toString().trim();


                    //  String food = autoCom.getText().toString().trim();
                    //String[] stringArray = new String[listVieww.getCount()];
                    userDbHelper3=new recipeDbHelper(getApplicationContext());
                    sqLiteDatabase=userDbHelper3.getWritableDatabase();

                    myDataa = userDbHelper3.SelectAll();
                    list=(Arrays.toString(myDataa));
                    myDataadesc = userDbHelper3.SelectAlldesc();
                    listdesc=(Arrays.toString(myDataadesc));
                    //list =  list.replaceAll(",", "|");
                    if(selectedPath !="") {


                        videoResult = new doupload().execute().get();
                        if (!videoResult.equals("There was an error") && null !=videoResult) {
                            videoName = videoResult;
                            Addrecipe( title , food, secret,listdesc,list,total,prep,cook,videoName);
                            break;
                        }
                        else {
                            Toast.makeText(this, "some thing went wrong while uploading video ", Toast.LENGTH_LONG)
                                    .show();
                            break;
                        }


                    }
                          else {
                        Addrecipe( title , food, secret,listdesc,list,total,prep,cook,videoName);
                        break;
                    }


                }

                catch (Exception e) {
                    Toast.makeText(this, "fill all the field", Toast.LENGTH_LONG)
                            .show();
                }}

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);

    }
}

