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
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.Request.DeleteUserRequest;
import com.example.app.Response.DeleteUserResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.USERLIST.MODEL.Distributordetail;
import com.example.app.USERLIST.MODEL.Franchisordetail;
import com.example.app.USERLIST.UI.ViewEmployee;
import com.example.app.USERLIST.UI.ViewFranchisor;
import com.example.app.USERLIST.UI.ViewFranchisorDetails;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FranchisorListAdapter extends RecyclerView.Adapter<FranchisorListAdapter.ViewHolder> {

    private ArrayList<Franchisordetail> franchisordetails;
    Context context;
    String acess_token="";
    private WebApi webApi;
    Retrofit retrofit;
    String UserId;
    ViewFranchisor  frag;



    public FranchisorListAdapter(ArrayList<Franchisordetail> franchisordetails, Context context, ViewFranchisor frga) {
        this.franchisordetails = franchisordetails;
        this.context = context;
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        frag=frga;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final int position) {
        viewHolder.fran_id.setText(franchisordetails.get(position).getFran_id());
        viewHolder.company_name.setText(franchisordetails.get(position).getCompany_name());
        viewHolder.phone_no.setText(franchisordetails.get(position).getPhone_no());
        viewHolder.email_id.setText(franchisordetails.get(position).getEmail_id());
        viewHolder.unit_address.setText(franchisordetails.get(position).getUnit_address());
        viewHolder.resident_address.setText(franchisordetails.get(position).getResident_address());
        viewHolder.is_active.setText(franchisordetails.get(position).getIs_active());
        viewHolder.user_id.setText(franchisordetails.get(position).getUser_id());
        viewHolder.deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   fetchAcessToken(position);
            }
        });


        viewHolder.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewFranchisorDetails.class);

                intent.putExtra("fran_id",viewHolder.fran_id.getText().toString());
                intent.putExtra("company_name",viewHolder.company_name.getText().toString());
                intent.putExtra("phone_no",viewHolder.phone_no.getText().toString());
                intent.putExtra("email_id",viewHolder.email_id.getText().toString());
                intent.putExtra("unit_address",viewHolder.unit_address.getText().toString());
                intent.putExtra("resident_address",viewHolder.resident_address.getText().toString());
                intent.putExtra("is_active",viewHolder.is_active.getText().toString());
                //intent.putExtra("status",viewHolder.status.getText().toString());
                intent.putExtra("user_id",viewHolder.user_id.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    private  void deleteUser(final int position){
        Franchisordetail franchisordetail = franchisordetails.get(position);
        //DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        String userId = franchisordetail.getUser_id();
        //deleteUserRequest.setUser_id(UserId);
        Call<DeleteUserResponse> call = webApi.deletefranchisor(acess_token,userId);
        call.enqueue(new Callback<DeleteUserResponse>() {
            @Override
            public void onResponse(Call<DeleteUserResponse> call, Response<DeleteUserResponse> response) {
                String status = response.body().getStatus();
                if(status.equals("SUCCESS")){
                    Toast.makeText(context, "Franchisee Deleted", Toast.LENGTH_SHORT).show();
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
        TextView is_active;
        TextView user_id;
        TextView viewmore;
        ImageView deleteuser;
        LinearLayout linearLayout ;

        public ViewHolder (View view){
            super(view);

            fran_id = view.findViewById(R.id.dist_id);
            Photo = view.findViewById(R.id.distribitor_photo);
            company_name = view.findViewById(R.id.companynamelist);
            phone_no = view.findViewById(R.id.unitholder_address);
            email_id = view.findViewById(R.id.dist_email);
            unit_address = view.findViewById(R.id.permanent_address);
            resident_address = view.findViewById(R.id.distributor_mobile_number);
            is_active = view.findViewById(R.id.userStatus);
            user_id = view.findViewById(R.id.user_id);
            viewmore = view.findViewById(R.id.viewdetails);
            deleteuser = view.findViewById(R.id.deleteuser);
            linearLayout = view.findViewById(R.id.userLayout);

        }
    }
}
