package com.example.app.MyOrders.AllItem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.Response.ViewResult;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;



/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class ViewListAdapter extends RecyclerView.Adapter<ViewListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ViewResult> viewlist;

    public ViewListAdapter(Context context, ArrayList<ViewResult> viewlist) {
        this.viewlist = viewlist;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderitemlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        ViewResult viewResult=viewlist.get(position);
        holder.itemdescription.setText(viewResult.getDescription());
        holder.itemname.setText(viewResult.getName());
        holder.itemprice.setText(""+viewResult.getPrice());


     //   viewHolder.pPhoto.setText(listItemDetail.get(position).getBuyStatus());

    }

    @Override
    public int getItemCount() {
        return viewlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemname,itemprice,itemdescription;
        LinearLayout orderlayout;
        ViewHolder(View itemView) {
            super(itemView);

            itemdescription = (TextView) itemView.findViewById(R.id.itemdescription);
            itemname=(TextView)itemView.findViewById(R.id.itemname);
            itemprice=itemView.findViewById(R.id.itemprice);

            orderlayout=itemView.findViewById(R.id.orderlayout);

        }
    }




}
