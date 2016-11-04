package com.example.hp.navigation.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ListDataAdpterdesc extends ArrayAdapter  {
    List list= new ArrayList();
    TextView Text;
    public static ListView listView;
    public ListDataAdpterdesc(Context context, int resource){
        super(context,resource);
    }
    static class LayoutHandler{
        TextView calory;

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
        final DataProviderdesc temp = (DataProviderdesc) list.get(position);
        return temp.getName();
    }
    public int geti(int position) {
        final DataProviderdesc temp = (DataProviderdesc) list.get(position);
        return temp.getId();
    }


    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        // ViewHolder holder;
        listView= (ListView) AddRecipe.listV;

        if(  convertView == null)
        {

            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.row_layout1,parent,false);
            layoutHandler=new LayoutHandler();
            // holder = new ViewHolder();
            layoutHandler.calory=(TextView)convertView.findViewById(R.id.descname);

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
                userDbHelper.deletedesc(sqLiteDatabase,i);

                notifyDataSetChanged();


                if( userDbHelper.istabledescexist(sqLiteDatabase))
                {
                    AddRecipe.Textnum.setText("1-");

                }
                AddRecipe.setListViewHeightBasedOnItems(listView);
                Toast.makeText(getContext(), m+position+i , Toast.LENGTH_LONG).show();
            }
        });

        if(layoutHandler!=null){
            Cursor cursor;
            Cursor cursor1;
            Cursor cursor2;
            int sum = 1;
            ListDataAdpterdesc listDataAdpter;
            SQLiteDatabase sqLiteDatabase;
            recipeDbHelper userDbHelper;
            recipeDbHelper userDbHelper2;

            userDbHelper2 = new recipeDbHelper(getContext());



            sqLiteDatabase = userDbHelper2.getReadableDatabase();
            userDbHelper2.istabledescexist(sqLiteDatabase);

            if(  userDbHelper2.istabledescexist(sqLiteDatabase))
            {
                AddRecipe.Textnum.setText("hello");

            }
           else{ AddRecipe.Textnum.setText(position+2+"-");}
            DataProviderdesc dataProvider=(DataProviderdesc)this.getTtem(position);
            layoutHandler.calory.setText(position+1+"-"+dataProvider.getName());
         }
        return convertView;

    }
}
