package com.example.app.FEEDBACK.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.FEEDBACK.MODEL.FeedBackDetail;

import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {
    /* public final Activity context;
     public final String[] promotername;
     *//*public final Integer[] imgid;*//*
    public final String[] promoterAddress;
    int count = 0;*/
    private ArrayList<FeedBackDetail> listItemDetail;

    public FeedbackListAdapter(ArrayList<FeedBackDetail> listUsers) {
        this.listItemDetail = listUsers;
    }
    public void updateList(ArrayList<FeedBackDetail> list){
        listItemDetail = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedbacklist,viewGroup,false);


        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.ItemName.setText(listItemDetail.get(position).getId()+"");
        viewHolder.itemPrice.setText(listItemDetail.get(position).getItemName()+"");
        viewHolder.itemDescription.setText(listItemDetail.get(position).getFeedBack()+"");

    }

    @Override
    public int getItemCount() {
        Log.v(FeedbackListAdapter.class.getSimpleName(),""+listItemDetail.size());
        return listItemDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ItemName;
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
