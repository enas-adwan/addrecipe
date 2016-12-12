package com.example.hp.navigation.activity;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.navigation.drawer.activity.R;

public class SQLiteListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> userID;
    ArrayList<String> RecipeName;
    ArrayList<String> Recipe_calory;
    ArrayList<String> Recipe_date ;


    public SQLiteListAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> recipe,
            ArrayList<String> calory,
            ArrayList<String> date
    )
    {

        this.context = context2;
        this.userID = id;
        this.RecipeName = recipe;
        this.Recipe_calory = calory;
        this.Recipe_date = date ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return userID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.colmn_row, null);

            holder = new Holder();


            holder.textviewrecipename = (TextView) child.findViewById(R.id.textViewRecipeNAME);
            holder.textviewcalory = (TextView) child.findViewById(R.id.textViewcalory);
            holder.textviewdate = (TextView) child.findViewById(R.id.textViewdate);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.textviewrecipename.setText(RecipeName.get(position));
        holder.textviewcalory.setText(Recipe_calory.get(position));
        holder.textviewdate.setText(Recipe_date.get(position));

        return child;
    }

    public class Holder {
        TextView textviewrecipename;
        TextView textviewcalory;
        TextView textviewdate;
    }

}