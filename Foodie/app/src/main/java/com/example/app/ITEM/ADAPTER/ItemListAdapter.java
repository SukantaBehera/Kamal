package com.example.app.ITEM.ADAPTER;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.app.ITEM.UI.ViewItems;
import com.example.app.Response.DeleteUserResponse;
import com.example.app.Response.GetAllItemResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.USERLIST.MODEL.Employeedetail;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    /* public final Activity context;
     public final String[] promotername;
     *//*public final Integer[] imgid;*//*
    public final String[] promoterAddress;
    int count = 0;*/
    String acess_token="";
    Context context;
    private WebApi webApi;
    Retrofit retrofit;
    ViewItems items;
    private ArrayList<ItemDetail> listItemDetail;

    public ItemListAdapter(ArrayList<ItemDetail> listItemDetail, Context context, ViewItems item ) {
        this.context = context;
        this.listItemDetail = listItemDetail;
        items =item;


    }


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
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);

        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.ItemName.setText(listItemDetail.get(position).getName());
        viewHolder.itemPrice.setText(listItemDetail.get(position).getPrice());
        viewHolder.itemDescription.setText(listItemDetail.get(position).getDescription());
        /*viewHolder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAcessToken(position);
            }
        });*/
    }
   /* private void fetchAcessToken(final int position) {
        //getting the progressbar

        Call<TokenResponse> call = webApi.accessToken("password", "fbApp", "fbApp", "admin", "123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token = response.body().getValue();
                Log.d("Tag", "token===>" + acess_token);
               // deleteItem(position);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    private  void deleteItem(final int position){
        ItemDetail itemDetail = listItemDetail.get(position);
        //DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        String itemID = itemDetail.getItemId();
        // deleteUserRequest.setUser_id(UserId);
        Call<DeleteUserResponse> call = webApi.deleteItem(acess_token,itemID);
        call.enqueue(new Callback<DeleteUserResponse>() {
            @Override
            public void onResponse(Call<DeleteUserResponse> call, Response<DeleteUserResponse> response) {
                String status = response.body().getStatus();
                if(status.equals("SUCCESS")){
                    Log.d("Tag",status);
                    items.deleteItems(position);
                }
            }

            @Override
            public void onFailure(Call<DeleteUserResponse> call, Throwable t) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


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
        ImageView deleteitem;

        public ViewHolder (View view){
            super(view);
            ItemName = view.findViewById(R.id.itemname);
            itemPrice = view.findViewById(R.id.itemprice);
            itemDescription = view.findViewById(R.id.itemdescription);
            deleteitem = view.findViewById(R.id.deleteitem);

        }
    }
}
