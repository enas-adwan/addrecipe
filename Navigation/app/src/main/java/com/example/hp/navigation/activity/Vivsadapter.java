package com.example.hp.navigation.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.navigation.drawer.activity.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by mac on 15‏/1‏/2016.
 */
public class Vivsadapter extends RecyclerView.Adapter<Vivsadapter.MasonryView>  {

    ArrayList<Record> list;
    static  Context context;
    recipeDbHelper userDbHelper2;
    SQLiteDatabase sqLiteDatabase;
    Vivsadapter Vivs;
    String title="";
    private final static int FADE_DURATION = 1000;
    private int lastPosition = -1;

    // DatabaseHelper database;
    public Bitmap bmp;
    public Vivsadapter(Context c, ArrayList<Record> input) {
        context = c;
        list = new ArrayList<Record>();
        list = input;

    }
    public Vivsadapter(Vivsadapter Vivs) {

        this.Vivs=Vivs;

    }

    class LayoutHandler{

        ImageView favoriteImg;
        ImageView shareimage;


    }

    public int getCount() {
        return list.size();
    }



    @Override
    public Vivsadapter.MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        MasonryView masonryView = new MasonryView(layoutView);
        return masonryView;
    }

    @Override
    public void onBindViewHolder(final Vivsadapter.MasonryView holder, final int position) {
      /*  holder.imageview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1=   gettitle( position);
                Toast.makeText(context,title1 , Toast.LENGTH_LONG).show();
            }
        });
        holder.rowTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1=   gettitle( position);
                Toast.makeText(context,title1 , Toast.LENGTH_LONG).show();
            }
        });*/

        holder.card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(  ShowRecipe.mRecyclerView.getTag()=="sec") {
                    String title1 = gettitle(position);
                    Toast.makeText(context, title1, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context,Sec.class);
                    i.putExtra("title", title1);
                    i.putExtra("type", "sec");
                    context.startActivity(i);

                }


                else if ( searchactivity.mRecyclerView!=null){
                if(  searchactivity.mRecyclerView.getTag()=="sec") {
                    String title1 = gettitle(position);
                    Toast.makeText(context, title1, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context,Sec.class);
                    i.putExtra("title", title1);
                    i.putExtra("type", "sec");
                    context.startActivity(i);

                }}






                else if ( Showfav.mRecyclerView!=null){
                    if(Showfav.mRecyclerView.getTag()=="fav") {
                        String title1 = gettitle(position);
                        Toast.makeText(context, title1 + "  fav", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(context, Sec.class);
                        i.putExtra("title", title1);
                        i.putExtra("type", "fav");
                        context.startActivity(i);
                    }

                }else if ( ShowRecipe.mRecyclerView.getTag()=="offline"){
                    String title1 = gettitle(position);
                    Toast.makeText(context, title1+"  offline", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, Sec.class);
                    i.putExtra("title", title1);
                    i.putExtra("type", "offline");
                    context.startActivity(i);


                }



            }
        });
        final Record temp = list.get(position);
        // String m=new String(Base64.decode(temp.getImage(), Base64.DEFAULT));
        String[] safe = temp.getImage().split("=");
        byte[] qrimage = Base64.decode(safe[0], Base64.NO_PADDING);

        bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
        holder.imageview.setImageBitmap(bmp);
        holder.rowTitle.setText(temp.getName());
        // holder.rowDescription.setText(String.valueOf(temp.getNoid()));
        if(Float.valueOf(temp.getRating())!=0){
            holder.ratingBar.setRating(Float.valueOf(temp.getRating()));}
        else{
            holder.ratingBar.setRating(0);

        }
        holder.favoriteImg.setFocusable(false);

        // holder.ratingBar.setFocusable(false);
        // holder.ratingBar.setRating(0);
        // holder.shareimage.setFocusable(false);
        userDbHelper2=new recipeDbHelper(context);
        sqLiteDatabase=userDbHelper2.getReadableDatabase();
        title=   gettitle( position);
        userDbHelper2.CheckIsDataAlreadyInDBorNot(title,sqLiteDatabase);
        if(userDbHelper2.CheckIsDataAlreadyInDBorNot(title,sqLiteDatabase)){
            holder.favoriteImg.setImageResource(R.drawable.heart_red);
            holder.favoriteImg.setTag("red");

        }else{

            holder.favoriteImg.setImageResource(R.drawable.heart_grey);
            holder.favoriteImg.setTag("grey");

        }


        holder.favoriteImg.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (  holder.favoriteImg.getTag()!="red") {
                    holder.favoriteImg.setImageResource(R.drawable.heart_red);
                    holder.favoriteImg.setTag("red");
                    String title1=   gettitle( position);
                    SQLiteDatabase sqLiteDatabase;

                    Sec.jsosqlite(title1);

                    Toast.makeText(context, title1, Toast.LENGTH_LONG).show();

















                } else {
                    String title2=   gettitle( position);
                    userDbHelper2.deletefav(sqLiteDatabase,title2);
                    //if(Showfav.listView!=null){
                    //  list.remove(position);

                    //  }
                    if (Showfav.mRecyclerView!=null ){
                        if(Showfav.mRecyclerView.getTag()=="fav") {
                            String title1 = gettitle(position);
                            list.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, title1 + "  remove", Toast.LENGTH_LONG).show();

                        }

                    }
                    //notifyDataSetChanged();
                    Toast.makeText(context, title2+position, Toast.LENGTH_LONG).show();

                    holder.favoriteImg.setImageResource(R.drawable.heart_grey);
                    holder.favoriteImg.setTag("grey");

                }
            }
        });
        if(userDbHelper2.Checkranking(title,sqLiteDatabase)){
            holder.ratingBar.setEnabled(false);
            //holder. ratingBar.setClickable(true);
            LayerDrawable starss = (LayerDrawable)   holder.ratingBar.getProgressDrawable();
            starss.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);


        }else{
            holder.ratingBar.setEnabled(true);


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
        holder.ratingBar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    int count =0;
                    float touchPositionX = event.getX();
                    float width =    holder.ratingBar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    float stars = (float) starsf + 2;

                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    float curRate = Float.valueOf(decimalFormat.format((stars
                            * count + starsf)
                            / ++count));
                    String title1=   gettitle( position);
                    String str=String.valueOf(curRate);
                    Double d=Double.valueOf(str);
                    Double m=Math.ceil(d);
                    String str1=String.valueOf(m);
                    Toast.makeText(context,
                            str1, Toast.LENGTH_SHORT)
                            .show();
                    Float r=Float.valueOf(str1);
                    holder.ratingBar.setRating(r);
                    if(isOnline()){
                        holder.ratingBar.setEnabled(false);
                        LayerDrawable starss = (LayerDrawable)       holder.ratingBar.getProgressDrawable();
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

        setAnimation(holder.card, position);
        //   setFadeAnimation(holder.card);
    }

    @Override
    public long getItemId(int position) {
        //  return list.get(position);
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();

    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    class MasonryView extends RecyclerView.ViewHolder {
        ImageView  favoriteImg;
        TextView textView;
        ListView listView;
        CardView card;
        final RatingBar ratingBar;
        TextView rowTitle;
        TextView rowDescription;
        final ImageView     imageview;
        FrameLayout container;
        public MasonryView(View itemView) {
            super(itemView);


            // Find Views
            //  listView = (ListView)itemView.findViewById(R.id.lv);
            ratingBar= (RatingBar) itemView.findViewById(R.id.ratingBar);
            rowTitle = (TextView) itemView.findViewById(R.id.nam);
            // rowDescription = (TextView) itemView.findViewById(R.id.idd);
            //  ListView lv = (ListView)MainActivity.findViewById(R.id.li);
            imageview = (ImageView) itemView.findViewById(R.id.imageV);
            card=(CardView)itemView.findViewById(R.id.card_view);
            favoriteImg = (ImageView) itemView.findViewById(R.id.imgbtn_favorite);

        }
    }



    public String gettitle(int position) {
        final Record temp = list.get(position);
        return temp.getName();
    }
    public String getimg(int position) {
        final Record temp = list.get(position);
        return temp.getImage();
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        Animation animation = AnimationUtils.loadAnimation(context,   R.anim.slide_down);
        viewToAnimate.startAnimation(animation);
        lastPosition = position;

    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
