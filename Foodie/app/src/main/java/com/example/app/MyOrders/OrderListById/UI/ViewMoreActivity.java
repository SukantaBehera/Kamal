package com.example.app.MyOrders.OrderListById.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MyOrders.OrderListById.Adapter.ViewAdapterNew;
import com.example.app.Response.OrderViewResponse;
import com.example.app.Response.ResultOrderView;
import com.example.app.Response.TokenResponse;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.Util.RegPrefManager;
import com.example.sukanta.foodie.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewMoreActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView itemListRecyclerview;
    TextView noOrderTv;
    private WebApi webApi;
    Retrofit retrofit;
    private ArrayList<ResultOrderView> ResultOrderViewArray;
    private ViewAdapterNew adapterNew;
    private String acess_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);
        toolbar=findViewById(R.id.toolbar);
        TextView text=toolbar.findViewById(R.id.text);
        text.setText("View More");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(View.this,RemiterDetailsActivity.class));
                finish();
            }
        });
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        intialize();

    }
    private void intialize(){
        itemListRecyclerview=findViewById(R.id.itemListRecyclerview);
        noOrderTv=findViewById(R.id.noOrderTv);

        ResultOrderViewArray=new ArrayList<>();
        if(isNetworkAvailable()) {
            fetchAcessToken();
        }else {
            Toast.makeText(ViewMoreActivity.this, "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void fetchAcessToken() {
        //getting the progressbar


        Call<TokenResponse> call = webApi.accessToken("password", "fbApp", "fbApp", "admin", "123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token = response.body().getValue();
                Log.d("Tag", "token===>" + acess_token);
                viewMoreNetwork();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ViewMoreActivity.this, "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewMoreNetwork(){
        final ProgressBar pprogressBar = (ProgressBar)findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);
        String orderid= RegPrefManager.getInstance(ViewMoreActivity.this).getOrderId();

        Call<OrderViewResponse> call=webApi.getOrderViews(Integer.parseInt(orderid),acess_token);
        call.enqueue(new Callback<OrderViewResponse>() {
            @Override
            public void onResponse(Call<OrderViewResponse> call, Response<OrderViewResponse> response) {
                pprogressBar.setVisibility(View.GONE);
                String status=response.body().getStatus();
                if(status.equals("SUCCESS")){
                    ResultOrderViewArray=response.body().getResult();
                    if(ResultOrderViewArray.size()>0){
                        adapterNew = new ViewAdapterNew(ViewMoreActivity.this,ResultOrderViewArray);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewMoreActivity.this);
                        itemListRecyclerview.setLayoutManager(mLayoutManager);
                        itemListRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        itemListRecyclerview.setAdapter(adapterNew);
                        adapterNew.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderViewResponse> call, Throwable t) {
                pprogressBar.setVisibility(View.GONE);
                Toast.makeText(ViewMoreActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
