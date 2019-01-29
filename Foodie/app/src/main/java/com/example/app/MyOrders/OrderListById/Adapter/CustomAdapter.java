package com.example.app.MyOrders.OrderListById.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app.Response.EmployeeIDResultResponse;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter  extends ArrayAdapter<String> {
    ArrayList list;
    Context context;
    public Resources res;
    LayoutInflater inflater;

    public CustomAdapter(Context context, int resource, ArrayList  objects) {
        super(context, resource, objects);
        list=objects;
        this.context=context;


        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.customspinnerlayout, parent, false);

        /***** Get each Model object from Arraylist ********/
       // tempValues = null;
        EmployeeIDResultResponse tempValues = (EmployeeIDResultResponse) list.get(position);

        TextView textTv        = (TextView)row.findViewById(R.id.textTv);



            // Set values for spinner each row
        textTv.setText(tempValues.getUserName());


        return row;
    }
}
