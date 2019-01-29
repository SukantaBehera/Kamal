package com.example.app.foodie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MyOrders.OrderListById.Adapter.MyOrderAdapterNew;
import com.example.app.MyOrders.OrderListById.UI.ViewOrderItems;
import com.example.app.Response.PendingReportResponse;
import com.example.app.Response.ResultPending;
import com.example.app.Response.TokenResponse;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.foodie.adapter.PendingAdapter;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PendingFragment extends Fragment {
    private RecyclerView itemlistRecyclerview;
    private TextView textView2;
    private ProgressBar progressBarDil;
    private EditText searchlist;

    private WebApi webApi;
    Retrofit retrofit;
    private String acess_token;
    private PendingAdapter pendingAdapter;
    private ArrayList<ResultPending> resultPendingArrayList;


    public PendingFragment() {
        // Required empty public constructor
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
    }


    public static PendingFragment newInstance(String param1, String param2) {
        PendingFragment fragment = new PendingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_pending, container, false);
        progressBarDil=v.findViewById(R.id.progressBarDil);
        textView2=v.findViewById(R.id.textView2);
        itemlistRecyclerview=v.findViewById(R.id.item_list);
        searchlist=v.findViewById(R.id.searchlist);
        resultPendingArrayList=new ArrayList<>();
        if(isNetworkAvailable()) {
            fetchAcessToken();
        }else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }

        return v;
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
                getAllItemList();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getAllItemList(){
        progressBarDil.setVisibility(View.VISIBLE);
        Call<PendingReportResponse> call=webApi.getPendingReport(acess_token);
        call.enqueue(new Callback<PendingReportResponse>() {
            @Override
            public void onResponse(Call<PendingReportResponse> call, Response<PendingReportResponse> response) {
                progressBarDil.setVisibility(View.GONE);
                String status=response.body().getStatus();
                if(status.equals("SUCCESS")){
                    resultPendingArrayList=response.body().getResult();
                    Log.d("Tag","Size===>"+resultPendingArrayList.size());
                    if(resultPendingArrayList.size()>0){
                        pendingAdapter = new PendingAdapter(getContext(),resultPendingArrayList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        itemlistRecyclerview.setLayoutManager(mLayoutManager);
                        itemlistRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        itemlistRecyclerview.setAdapter(pendingAdapter);
                        pendingAdapter.notifyDataSetChanged();
                    }
                    else{
                        itemlistRecyclerview.setVisibility(View.GONE);
                        textView2.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingReportResponse> call, Throwable t) {
                progressBarDil.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                itemlistRecyclerview.setVisibility(View.GONE);
                textView2.setVisibility(View.VISIBLE);
            }
        });
    }
}
