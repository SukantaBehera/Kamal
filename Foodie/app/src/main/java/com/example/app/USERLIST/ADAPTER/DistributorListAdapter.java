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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Request.DeleteUserRequest;
import com.example.app.Response.DeleteUserResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.USERLIST.MODEL.Distributordetail;

import com.example.app.USERLIST.UI.ViewDistributor;
import com.example.app.USERLIST.UI.ViewDistributorDetails;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.foodie.ServerLinks;
import com.example.sukanta.foodie.R;


import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DistributorListAdapter extends RecyclerView.Adapter<DistributorListAdapter.ViewHolder> {

    private ArrayList<Distributordetail> distributordetails;
    DistributorListAdapter distributorListAdapter;
    Context context;
    String acess_token="";
    private WebApi webApi;
    Retrofit retrofit;
    String UserId;
    ViewDistributor frag;

    public DistributorListAdapter() {
    }

    public DistributorListAdapter(Context applicationContext, ArrayList<Distributordetail> distributorlist,ViewDistributor frga) {
        this.distributordetails = distributorlist;
        this.context = applicationContext;
        retrofit = ApiClient.getRetrofit();

        webApi = retrofit.create(WebApi.class);
        frag=frga;

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.dist_id.setText(distributordetails.get(position).getDist_id());
        viewHolder.CompanyName.setText(distributordetails.get(position).getCompany_name());
        viewHolder.Unitholdername.setText(distributordetails.get(position).getUnit_holder_name());
        viewHolder.UnitAddress.setText(distributordetails.get(position).getUnit_address());
        viewHolder.permanentaddress.setText(distributordetails.get(position).getPermanent_address());
        viewHolder.MobileNo.setText(distributordetails.get(position).getPhone_no());
        viewHolder.Emailid.setText(distributordetails.get(position).getEmail_id());

        viewHolder.Userid.setText(distributordetails.get(position).getUser_id());
        UserId = distributordetails.get(position).getUser_id();
        viewHolder.deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              fetchAcessToken(position);


            }
        });




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
    private  void deleteUser(final int position){
        Distributordetail distributordetail = distributordetails.get(position);
        //DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        String userId = distributordetail.getUser_id();
       // deleteUserRequest.setUser_id(UserId);
        Call<DeleteUserResponse> call = webApi.deletedistributor(acess_token,userId);
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
        //TextView Status;
        TextView Userid;
        TextView viewmore;
        ImageView deleteuser;
        LinearLayout linearLayout ;

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
            deleteuser = view.findViewById(R.id.deleteuser);
            viewmore = view.findViewById(R.id.viewdetails);
            linearLayout = view.findViewById(R.id.userLayout);

        }
    }
}
