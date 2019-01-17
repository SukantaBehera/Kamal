package com.example.app.QOM.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.QOM.Model.QomModel;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class QomListAdapter extends RecyclerView.Adapter<QomListAdapter.ViewHolder> {

    private ArrayList<QomModel> listItemDetail;

    public QomListAdapter(ArrayList<QomModel> listUsers) {
        this.listItemDetail = listUsers;
    }
    public void updateList(ArrayList<QomModel> list){
        listItemDetail = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlistqom,viewGroup,false);
        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.ItemName.setText("Name:           "+listItemDetail.get(position).getName());
        viewHolder.itemPrice.setText("Price:         "+listItemDetail.get(position).getPrice()+"");
        viewHolder.itemDescription.setText("Description:     "+listItemDetail.get(position).getDescription());
        viewHolder.quantityavail.setText("Quantity Available: "+listItemDetail.get(position).getQuantity_avail()+"");

    }

    @Override
    public int getItemCount() {
        Log.v(QomListAdapter.class.getSimpleName(),""+listItemDetail.size());
        return listItemDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ItemName;
        TextView itemPrice;
        TextView itemDescription;
        TextView quantityavail;

        public ViewHolder (View view){
            super(view);
            ItemName = view.findViewById(R.id.itemname);
            itemPrice = view.findViewById(R.id.itemprice);
            itemDescription = view.findViewById(R.id.itemdescription);
            quantityavail = view.findViewById(R.id.quantityavail);

        }
    }
}
