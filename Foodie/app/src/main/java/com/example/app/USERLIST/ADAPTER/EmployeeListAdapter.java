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
import android.widget.Toast;

import com.example.app.Response.DeleteUserResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.USERLIST.MODEL.Distributordetail;
import com.example.app.USERLIST.MODEL.Employeedetail;
import com.example.app.USERLIST.UI.ViewDistributor;
import com.example.app.USERLIST.UI.ViewEmployee;
import com.example.app.USERLIST.UI.ViewEmployeeDetails;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    private ArrayList<Employeedetail> empployeelist;
    Context context;
    String acess_token="";
    private WebApi webApi;
    Retrofit retrofit;
    String UserId;
    ViewEmployee frag;
    public EmployeeListAdapter(ArrayList<Employeedetail> empployeelist, Context context, ViewEmployee frga) {
        this.empployeelist = empployeelist;
        this.context = context;
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        frag=frga;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final int position) {

        viewHolder.emp_id.setText(empployeelist.get(position).getEmp_id());
        viewHolder.name.setText(empployeelist.get(position).getName());
        viewHolder.designation.setText(empployeelist.get(position).getDesignation());
        viewHolder.phone_no.setText(empployeelist.get(position).getPhone_no());
        viewHolder.address.setText(empployeelist.get(position).getAddress());
        viewHolder.email_id.setText(empployeelist.get(position).getEmail_id());
        viewHolder.status.setText(empployeelist.get(position).getPhone_no());
        viewHolder.user_id.setText(empployeelist.get(position).getUser_id());

        viewHolder.deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAcessToken(position);


            }
        });

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

    private void fetchAcessToken(final int position) {
        //getting the progressbar

        Call<TokenResponse> call = webApi.accessToken("password", "fbApp", "fbApp", "admin", "123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token = response.body().getValue();
                Log.d("Tag", "token===>" + acess_token);
                deleteUser(position);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private  void deleteUser(final int position){
        Employeedetail employeedetail = empployeelist.get(position);
        //DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        String userId = employeedetail.getUser_id();
        // deleteUserRequest.setUser_id(UserId);
        Call<DeleteUserResponse> call = webApi.deleteemployee(acess_token,userId);
        call.enqueue(new Callback<DeleteUserResponse>() {
            @Override
            public void onResponse(Call<DeleteUserResponse> call, Response<DeleteUserResponse> response) {
                String status = response.body().getStatus();
                if(status.equals("SUCCESS")){
                    Toast.makeText(context, "Distributor Deleted", Toast.LENGTH_SHORT).show();
                    frag.deleteItem(position);
                }
            }

            @Override
            public void onFailure(Call<DeleteUserResponse> call, Throwable t) {
                Toast.makeText(context, "Check your Connection", Toast.LENGTH_SHORT).show();
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
        ImageView deleteuser;

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
            deleteuser = view.findViewById(R.id.deleteuser);

        }
    }
}
