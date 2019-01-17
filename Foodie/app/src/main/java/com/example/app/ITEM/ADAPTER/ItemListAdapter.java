package com.example.app.ITEM.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    /* public final Activity context;
     public final String[] promotername;
     *//*public final Integer[] imgid;*//*
    public final String[] promoterAddress;
    int count = 0;*/
    private ArrayList<ItemDetail> listItemDetail;

    public ItemListAdapter(ArrayList<ItemDetail> listUsers) {
        this.listItemDetail = listUsers;
    }
    public void updateList(ArrayList<ItemDetail> list){
        listItemDetail = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlist,viewGroup,false);


        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.ItemName.setText(listItemDetail.get(position).getName());
        viewHolder.itemPrice.setText(listItemDetail.get(position).getPrice());
        viewHolder.itemDescription.setText(listItemDetail.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        Log.v(ItemListAdapter.class.getSimpleName(),""+listItemDetail.size());
        return listItemDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ItemName;
        ImageView pPhoto;
        TextView itemPrice;
        TextView itemDescription;

        public ViewHolder (View view){
            super(view);
            ItemName = view.findViewById(R.id.itemname);
            itemPrice = view.findViewById(R.id.itemprice);
            itemDescription = view.findViewById(R.id.itemdescription);

        }
    }
}
