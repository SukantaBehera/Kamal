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


import com.example.app.USERLIST.MODEL.Distributordetail;

import com.example.app.USERLIST.UI.ViewDistributorDetails;
import com.example.sukanta.foodie.R;


import java.util.ArrayList;

public class DistributorListAdapter extends RecyclerView.Adapter<DistributorListAdapter.ViewHolder> {

    private ArrayList<Distributordetail> distributordetails;
    Context context;
    public DistributorListAdapter(Context applicationContext,ArrayList<Distributordetail> distributorlist) {
        this.distributordetails = distributorlist;
        this.context = applicationContext;

    }
    public void updateList(ArrayList<Distributordetail> list){
        distributordetails = list;
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
        viewHolder.dist_id.setText(distributordetails.get(position).getDist_id());
        viewHolder.CompanyName.setText(distributordetails.get(position).getCompany_name());
        viewHolder.Unitholdername.setText(distributordetails.get(position).getUnit_holder_name());
        viewHolder.UnitAddress.setText(distributordetails.get(position).getUnit_address());
        viewHolder.permanentaddress.setText(distributordetails.get(position).getPermanent_address());
        viewHolder.MobileNo.setText(distributordetails.get(position).getPhone_no());
        viewHolder.Emailid.setText(distributordetails.get(position).getEmail_id());
        viewHolder.Userid.setText(distributordetails.get(position).getUser_id());
        viewHolder.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewDistributorDetails.class);
                intent.putExtra("dist_id",viewHolder.dist_id.getText().toString());
                intent.putExtra("company_name",viewHolder.CompanyName.getText().toString());
                intent.putExtra("unit_holder_name",viewHolder.Unitholdername.getText().toString());
                intent.putExtra("unit_address",viewHolder.UnitAddress.getText().toString());
                intent.putExtra("permanent_address",viewHolder.permanentaddress.getText().toString());
                intent.putExtra("phone_no",viewHolder.MobileNo.getText().toString());
                intent.putExtra("email_id",viewHolder.Emailid.getText().toString());
                intent.putExtra("user_id",viewHolder.Userid.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.v(DistributorListAdapter.class.getSimpleName(),""+distributordetails.size());
        return distributordetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dist_id;
        TextView CompanyName;
        ImageView Photo;
        TextView Unitholdername;
        TextView UnitAddress;
        TextView permanentaddress;
        TextView MobileNo;
        TextView Emailid;
        TextView Userid;
        TextView viewmore;

        public ViewHolder (View view){
            super(view);
            dist_id = view.findViewById(R.id.dist_id);
            Photo = view.findViewById(R.id.distribitor_photo);
            CompanyName = view.findViewById(R.id.companynamelist);
            Unitholdername = view.findViewById(R.id.unitholder_address);
            UnitAddress = view.findViewById(R.id.unit_address);
            permanentaddress = view.findViewById(R.id.permanent_address);
            MobileNo = view.findViewById(R.id.distributor_mobile_number);
            Emailid = view.findViewById(R.id.dist_email);
            Userid = view.findViewById(R.id.user_id);
            viewmore = view.findViewById(R.id.viewdetails);

        }
    }
}
