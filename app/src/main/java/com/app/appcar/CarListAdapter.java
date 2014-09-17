package com.app.appcar;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by VictorSampaio on 17/09/2014.
 */
public class CarListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Car> list;

    public CarListAdapter(Context context, List<Car> list){

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    public int getCount(){
        return list.size();
    }

    public Object getItem(int position){
        return list.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    public View getView (int position, View convertView, ViewGroup parent){

        //Recover the car in actual position
        Car c = list.get(position);
        View view = inflater.inflate(R.layout.activity_app_car, null);

        // Update the value of the TextView
        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(c.name);
        //Plaque
        TextView plaque = (TextView)view.findViewById(R.id.plaque);
        plaque.setText(c.plaque);
        // Year
        TextView year = (TextView)view.findViewById(R.id.year);
        year.setText(String.valueOf(c.year));

        return view;
    }
}