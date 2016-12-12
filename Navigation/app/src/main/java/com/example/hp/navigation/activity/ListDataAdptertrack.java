package com.example.hp.navigation.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.navigation.drawer.activity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eodwan on 2‏/12‏/2016.
 */
public class ListDataAdptertrack extends ArrayAdapter {
    List list= new ArrayList();
    TextView Text;
    public static ListView listView;
    public ListDataAdptertrack(Context context, int resource){
        super(context,resource);
    }
    static class LayoutHandler{
        TextView calory;
        TextView num;
        ImageButton deleteBtn;

    }
    public void add(Object object){
        super.add(object);
        list.add(object);

    }

    public int getCount(){
        return list.size();
    }
    public Object getTtem(int position){
        return list.get(position);
    }
    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }



    @Override
    public long getItemId(int position) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }
    public String getn(int position) {
        final DataProvider temp = (DataProvider) list.get(position);
        return temp.getName();
    }
    public int geti(int position) {
        final DataProvider temp = (DataProvider) list.get(position);
        return temp.getId();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        // ViewHolder holder;
        listView= (ListView) trackingdailycalory.listView;

        if(  convertView == null)
        {

            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler=new LayoutHandler();
            // holder = new ViewHolder();
            layoutHandler.calory=(TextView)convertView.findViewById(R.id.calory_number);
            layoutHandler.num=(TextView)convertView.findViewById(R.id.number1);
            layoutHandler.deleteBtn = (ImageButton)convertView.findViewById(R.id.trash);
            layoutHandler.deleteBtn = (ImageButton)convertView.findViewById(R.id.trash);
            convertView.setTag(layoutHandler);
            // convertView .setTag(position);
            // convertView.setTag();



        }

        else{


            layoutHandler=(LayoutHandler)convertView.getTag();
        }

        layoutHandler.deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Integer pos = (Integer)v.getTag();
                //  layoutHandler=(LayoutHandler)convertView.getTag();
                //  Integer pos = (Integer) v.getTag();
                SQLiteDatabase sqLiteDatabase;
                //  DataProvider dataProvider=(DataProvider)this.getTtem(position);
                // layoutHandler.calory.setText(dataProvider.getName());
                //return list.get(position);
                //do something
                //AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();

                // list.remove(toRemove); //or some other task
                int i =geti(position);
                String m =getn( position);
                list.remove(position);
                recipeDbHelper userDbHelper;
                userDbHelper=new recipeDbHelper(getContext());

                sqLiteDatabase=userDbHelper.getWritableDatabase();
                userDbHelper.deletetrack(sqLiteDatabase,i);

                notifyDataSetChanged();

                ArrayList<Float> sums=userDbHelper.getTotaltrack(sqLiteDatabase);
                Float calorysum=sums.get(0);

               trackingdailycalory.textcalory.setText(String.valueOf(calorysum));

                String c=trackingdailycalory.textcalory.getText().toString();
                String ca=trackingdailycalory.Text.getText().toString();
                Float c1 =Float.valueOf(c);
                Float ca1 =Float.valueOf(ca);
                Float re=ca1-c1;
                String remain=re.toString();
                if    (re>0){

                   trackingdailycalory.textR.setTextColor(Color.BLACK);
                } else{
                    trackingdailycalory.textR.setTextColor(Color.RED);
                }
                trackingdailycalory.textR.setText(remain);
                trackingdailycalory.setListViewHeightBasedOnItems(listView);
                Toast.makeText(getContext(), m+position+i , Toast.LENGTH_LONG).show();
            }
        });

        if(layoutHandler!=null){
            DataProvider dataProvider=(DataProvider)this.getTtem(position);
            layoutHandler.calory.setText(position+1+dataProvider.getName());
            layoutHandler.num.setText(dataProvider.getCalory()+"Kcal");}
        return convertView;

    }
}
