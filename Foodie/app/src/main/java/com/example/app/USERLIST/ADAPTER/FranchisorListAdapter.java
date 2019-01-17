package com.example.app.USERLIST.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.USERLIST.MODEL.Franchisordetail;
import com.example.app.USERLIST.UI.ViewFranchisorDetails;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class FranchisorListAdapter extends RecyclerView.Adapter<FranchisorListAdapter.ViewHolder> {

    private ArrayList<Franchisordetail> franchisordetails;
    Context context;

    public FranchisorListAdapter(ArrayList<Franchisordetail> franchisordetails, Context context) {
        this.franchisordetails = franchisordetails;
        this.context = context;
    }

    public void updateList(ArrayList<Franchisordetail> list){
        franchisordetails = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.distributorlist,viewGroup,false);


        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.fran_id.setText(franchisordetails.get(position).getFran_id());
        viewHolder.company_name.setText(franchisordetails.get(position).getCompany_name());
        viewHolder.phone_no.setText(franchisordetails.get(position).getPhone_no());
        viewHolder.email_id.setText(franchisordetails.get(position).getEmail_id());
        viewHolder.unit_address.setText(franchisordetails.get(position).getUnit_address());
        viewHolder.resident_address.setText(franchisordetails.get(position).getResident_address());
        viewHolder.status.setText(franchisordetails.get(position).getStatus());
        viewHolder.user_id.setText(franchisordetails.get(position).getUser_id());


       viewHolder.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewFranchisorDetails.class);

                intent.putExtra("fran_id",viewHolder.fran_id.getText().toString());
                intent.putExtra("fran_id",viewHolder.company_name.getText().toString());
                intent.putExtra("phone_no",viewHolder.phone_no.getText().toString());
                intent.putExtra("email_id",viewHolder.email_id.getText().toString());
                intent.putExtra("unit_address",viewHolder.unit_address.getText().toString());
                intent.putExtra("resident_address",viewHolder.resident_address.getText().toString());
                intent.putExtra("status",viewHolder.status.getText().toString());
                intent.putExtra("user_id",viewHolder.user_id.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(FranchisorListAdapter.class.getSimpleName(),""+franchisordetails.size());
        return franchisordetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fran_id;
        TextView company_name;
        TextView phone_no;
        TextView email_id;
        ImageView Photo;
        TextView unit_address;
        TextView resident_address;
        TextView status;
        TextView user_id;
        TextView viewmore;

        public ViewHolder (View view){
            super(view);

            fran_id = view.findViewById(R.id.dist_id);
            Photo = view.findViewById(R.id.distribitor_photo);
            company_name = view.findViewById(R.id.companynamelist);
            phone_no = view.findViewById(R.id.unitholder_address);
            email_id = view.findViewById(R.id.unit_address);
            unit_address = view.findViewById(R.id.permanent_address);
            resident_address = view.findViewById(R.id.distributor_mobile_number);
            status = view.findViewById(R.id.dist_email);
            user_id = view.findViewById(R.id.user_id);
            viewmore = view.findViewById(R.id.viewdetails);

        }
    }
}
