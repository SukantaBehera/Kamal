package com.example.app.USERLIST.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.USERLIST.MODEL.Employeedetail;
import com.example.app.USERLIST.UI.ViewEmployeeDetails;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    private ArrayList<Employeedetail> empployeelist;
    Context context;

    public EmployeeListAdapter(ArrayList<Employeedetail> empployeelist, Context context) {
        this.empployeelist = empployeelist;
        this.context = context;
    }

    public void updateList(ArrayList<Employeedetail> list){
        empployeelist = list;
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

        viewHolder.emp_id.setText(empployeelist.get(position).getEmp_id());
        viewHolder.name.setText(empployeelist.get(position).getName());
        viewHolder.designation.setText(empployeelist.get(position).getDesignation());
        viewHolder.phone_no.setText(empployeelist.get(position).getPhone_no());
        viewHolder.address.setText(empployeelist.get(position).getAddress());
        viewHolder.email_id.setText(empployeelist.get(position).getEmail_id());
        viewHolder.status.setText(empployeelist.get(position).getPhone_no());
        viewHolder.user_id.setText(empployeelist.get(position).getUser_id());
        viewHolder.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewEmployeeDetails.class);
                intent.putExtra("emp_id",viewHolder.emp_id.getText().toString());
                intent.putExtra("employee_name",viewHolder.name.getText().toString());
                intent.putExtra("designation",viewHolder.designation.getText().toString());
                intent.putExtra("phone_no",viewHolder.phone_no.getText().toString());
                intent.putExtra("address",viewHolder.address.getText().toString());
                intent.putExtra("email_id",viewHolder.email_id.getText().toString());
                intent.putExtra("status",viewHolder.status.getText().toString());
                intent.putExtra("user_id",viewHolder.user_id.getText().toString());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.v(EmployeeListAdapter.class.getSimpleName(),""+empployeelist.size());
        return empployeelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView emp_id;
        TextView name;
        TextView designation;
        TextView phone_no;
        // ImageView Photo;
        TextView address;
        TextView email_id;
        TextView aadhar_card_no;
        TextView status;
        TextView user_id;
        TextView viewmore;

        public ViewHolder (View view){
            super(view);


            emp_id = view.findViewById(R.id.dist_id);
            //Photo = view.findViewById(R.id.distribitor_photo);
            name = view.findViewById(R.id.companynamelist);
            designation = view.findViewById(R.id.unitholder_address);
            phone_no = view.findViewById(R.id.unit_address);
            address = view.findViewById(R.id.permanent_address);
            email_id = view.findViewById(R.id.distributor_mobile_number);
            status = view.findViewById(R.id.dist_email);
            user_id = view.findViewById(R.id.user_id);
            viewmore = view.findViewById(R.id.viewdetails);

           /* EmployeeName = view.findViewById(R.id.employeename);
            EmployeeDesignation = view.findViewById(R.id.employee_designation);
            MobileNo = view.findViewById(R.id.employee_mobile_number);
            Photo = view.findViewById(R.id.employee_photo);
*/
        }
    }
}
