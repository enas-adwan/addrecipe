package com.example.hp.navigation.activity;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.AlphabeticIndex.Record;

import com.navigation.drawer.activity.R;

import java.util.ArrayList;
import java.util.List;

public class ListDataAdpter extends ArrayAdapter  {
    List list= new ArrayList();
    TextView Text;
    public static ListView listView;
    public ListDataAdpter(Context context, int resource){
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
    public View getView( final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        // ViewHolder holder;
        listView= (ListView)AddRecipe.listView;

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
                userDbHelper=new  recipeDbHelper(getContext());

                sqLiteDatabase=userDbHelper.getWritableDatabase();
                userDbHelper.delete(sqLiteDatabase,i);

                notifyDataSetChanged();

                ArrayList<Float> sums=userDbHelper.getTotal(sqLiteDatabase);
                Float calorysum=sums.get(0);
                Float vitcsum=sums.get(1);
                Float prosum=sums.get(2);
                Float ironsum=sums.get(3);
                Float calcsum=sums.get(4);
                Float vit6sum=sums.get(5);
                Float vitb12sum=sums.get(6);
                Float vitesum=sums.get(7);
                AddRecipe.Text.setText(String.valueOf(calorysum)+"Kcal");
                AddRecipe.Textvitc.setText(String.valueOf(vitcsum));
                AddRecipe.Textpro.setText(String.valueOf(prosum));
                AddRecipe.Textvitb6.setText(String.valueOf(vit6sum));
                AddRecipe.Textvite.setText(String.valueOf(vitesum));
                AddRecipe.Textvitb12.setText(String.valueOf(vitb12sum));
                AddRecipe.Textiron.setText(String.valueOf(ironsum));
                AddRecipe.Textcalc.setText(String.valueOf(calcsum));
                AddRecipe.setListViewHeightBasedOnItems(listView);
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
