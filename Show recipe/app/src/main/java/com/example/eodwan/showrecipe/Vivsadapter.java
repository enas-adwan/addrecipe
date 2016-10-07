package com.example.eodwan.showrecipe;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.graphics.drawable.LayerDrawable;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.AdapterView;
import java.text.DecimalFormat;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.graphics.PorterDuff;
import android.widget.TextView;
import android.widget.RatingBar;
import java.util.ArrayList;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.Toast;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import android.widget.AdapterView;
import java.util.HashMap;
import android.widget.AdapterView.OnItemClickListener;
import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by mac on 15‏/1‏/2016.
 */
public class Vivsadapter extends BaseAdapter  {

    ArrayList<Record> list;
    static  Context context;
    recipeDbHelper userDbHelper2;
    SQLiteDatabase sqLiteDatabase;
    String title="";
   // DatabaseHelper database;
   public Bitmap bmp;
    public Vivsadapter(Context c, ArrayList<Record> input) {
        context = c;
        list = new ArrayList<Record>();
        list = input;

    }
    class LayoutHandler{

        ImageView favoriteImg;

    }

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get single_row layout

        final LayoutHandler layoutHandler;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.single_row, parent, false);
        layoutHandler=new LayoutHandler();
        // Find Views
        ListView listView = (ListView)row.findViewById(R.id.lv);
       final RatingBar ratingBar= (RatingBar) row.findViewById(R.id.ratingBar);
        TextView rowTitle = (TextView) row.findViewById(R.id.nam);
        TextView rowDescription = (TextView) row.findViewById(R.id.idd);
      //  ListView lv = (ListView)MainActivity.findViewById(R.id.li);
        final ImageView imageview = (ImageView) row.findViewById(R.id.imageV);
        layoutHandler.favoriteImg = (ImageView) row.findViewById(R.id.imgbtn_favorite);



        // Find Data sourcesapple

        final Record temp = list.get(position);
       // String m=new String(Base64.decode(temp.getImage(), Base64.DEFAULT));
        String[] safe = temp.getImage().split("=");
        byte[] qrimage = Base64.decode(safe[0], Base64.NO_PADDING);

        bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
        imageview.setImageBitmap(bmp);
        rowTitle.setText(temp.getName());
        rowDescription.setText(String.valueOf(temp.getNoid()));
        ratingBar.setRating(Float.valueOf(temp.getRating()));
        layoutHandler.favoriteImg.setFocusable(false);
        ratingBar.setFocusable(false);

        userDbHelper2=new recipeDbHelper(context);
        sqLiteDatabase=userDbHelper2.getReadableDatabase();
      title=   gettitle( position);
       userDbHelper2.CheckIsDataAlreadyInDBorNot(title,sqLiteDatabase);
        if(userDbHelper2.CheckIsDataAlreadyInDBorNot(title,sqLiteDatabase)){
            layoutHandler.favoriteImg.setImageResource(R.drawable.heart_red);
            layoutHandler.favoriteImg.setTag("red");

        }
        layoutHandler.favoriteImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
        if ( layoutHandler.favoriteImg.getTag()!="red") {
            layoutHandler.favoriteImg.setImageResource(R.drawable.heart_red);
            layoutHandler.favoriteImg.setTag("red");
String title1=   gettitle( position);
            SQLiteDatabase sqLiteDatabase;

            Sec.jsosqlite(title1);

            Toast.makeText(context, title1, Toast.LENGTH_LONG).show();

















        } else {
            String title2=   gettitle( position);
            userDbHelper2.deletefav(sqLiteDatabase,title2);
            if(Showfav.listView!=null){
            list.remove(position);
                notifyDataSetChanged();
            }
            Toast.makeText(context, title2+position, Toast.LENGTH_LONG).show();

            layoutHandler.favoriteImg.setImageResource(R.drawable.heart_grey);
            layoutHandler.favoriteImg.setTag("grey");

        }
            }
        });
        if(userDbHelper2.Checkranking(title,sqLiteDatabase)){
            ratingBar.setEnabled(false);
            ratingBar.setClickable(true);
       LayerDrawable starss = (LayerDrawable) ratingBar.getProgressDrawable();
            starss.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);


        }
    /*ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            String title1=   gettitle( position);
            String rate=Float.toString(rating);
            Sec.jsorating(title1,rate);
           // ratingBar.setEnabled(false);
            Toast.makeText(context, "Rating:"+rating, Toast.LENGTH_SHORT).show();

        }});*/
       ratingBar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    int count =0;
                    float touchPositionX = event.getX();
                    float width = ratingBar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int) starsf + 2;

                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                   Float curRate = Float.valueOf(decimalFormat.format((stars
                            * count + starsf)
                            / ++count));
                    String title1=   gettitle( position);
                    String str=String.valueOf(curRate);
                    Double d=Double.valueOf(str);
Double m=Math.ceil(d);
                  String str1=String.valueOf(m);
                    Float r=Float.valueOf(str1);
                    ratingBar.setRating(r);
                   if(isOnline()){
                    ratingBar.setEnabled(false);
                    LayerDrawable starss = (LayerDrawable) ratingBar.getProgressDrawable();
                    starss.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(context,
                            "your vote has been submited ,Thank you " , Toast.LENGTH_SHORT)
                            .show();
                    userDbHelper2.titleranking(sqLiteDatabase,title1);

                    Sec.jsorating(title1,str1);
                }else{
                    Toast.makeText(context,
                            "please vote when you are connected to the wifi so it will be submitted " , Toast.LENGTH_SHORT)
                            .show();

                }}
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }

                return true;}
            });

       /* ratingBar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

              float newRating=ratingBar.getRating();
                Toast.makeText(context, "Rating:"+newRating, Toast.LENGTH_SHORT).show();
              //  processing(newRating);

            }
        });*/
/*      MainActivity.listVieww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(context,Sec.class);
                i.putExtra("title",gettitle(position));
                context.startActivity(i);

            }
        });*/


        return row;
    }
    public String gettitle(int position) {
        final Record temp = list.get(position);
        return temp.getName();
    }
    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
