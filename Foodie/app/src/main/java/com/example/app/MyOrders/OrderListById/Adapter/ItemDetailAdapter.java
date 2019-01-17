package com.example.app.MyOrders.OrderListById.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ViewHolder> {
   /* public final Activity context;
    public final String[] promotername;
    *//*public final Integer[] imgid;*//*
    public final String[] promoterAddress;
    int count = 0;*/
    private ArrayList<OrderItem> listItemDetail;

    public ItemDetailAdapter(ArrayList<OrderItem> listUsers) {
        this.listItemDetail = listUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cartlist,viewGroup,false);


        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.ItemName.setText("Id:"+listItemDetail.get(position).getItem_name());
        viewHolder.itemPrice.setText("Quantity: "+listItemDetail.get(position).getItemCount()+"   Total Price"+listItemDetail.get(position).getPrice());
        viewHolder.itemDescription.setText("Status: "+listItemDetail.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        Log.v(ItemDetailAdapter.class.getSimpleName(),""+listItemDetail.size());
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
