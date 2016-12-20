package com.example.hp.navigation.activity;

import android.annotation.TargetApi;
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
import android.icu.text.DecimalFormat;
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
            @TargetApi(24)
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


                Double call=Double.valueOf(calorysum);
                double calorydouble = Math.round(call*100.0)/100.0;
                Double vitc=Double.valueOf(vitcsum);
                double vitcdouble = Math.round(vitc*100.0)/100.0;
                Double vitb12=Double.valueOf(vitb12sum);

                double vitb12double = Math.round(vitb12*100.0)/100.0;
                Double vite=Double.valueOf(vitesum);

                double vitedouble = Math.round(vite*100.0)/100.0;
                Double vitb6=Double.valueOf(vit6sum);

                double vitb6double = Math.round(vitb6*100.0)/100.0;
                Double pros=Double.valueOf(prosum);
                double prodouble = Math.round(pros*100.0)/100.0;
                Double iron=Double.valueOf(ironsum);
                double irondouble = Math.round(iron*100.0)/100.0;
                Double calc=Double.valueOf(calcsum);
                double calcdouble = Math.round(calc*100.0)/100.0;
                AddRecipe.Text.setText(String.valueOf(calorydouble));
               AddRecipe.Textvitc.setText(String.valueOf(vitcdouble ));
               AddRecipe.Textpro.setText(String.valueOf(prodouble));
                AddRecipe.Textvitb6.setText(String.valueOf(vitb6double));
                AddRecipe.Textvite.setText(String.valueOf(vitedouble));
                AddRecipe.Textvitb12.setText(String.valueOf(vitb12double));
                AddRecipe.Textiron.setText(String.valueOf(irondouble));
                AddRecipe.Textcalc.setText(String.valueOf(calcdouble));


                AddRecipe.Text.setText(String.valueOf(calorydouble));

                AddRecipe.setListViewHeightBasedOnItems(listView);
                Toast.makeText(getContext(), m+"has been removed", Toast.LENGTH_LONG).show();
            }
        });

        if(layoutHandler!=null){
            DataProvider dataProvider=(DataProvider)this.getTtem(position);
            layoutHandler.calory.setText(position+1+dataProvider.getName());
            layoutHandler.num.setText(dataProvider.getCalory()+"Kcal");}
        return convertView;

    }
}
