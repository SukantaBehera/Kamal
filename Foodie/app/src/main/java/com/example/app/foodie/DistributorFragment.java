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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MyOrders.OrderListById.Adapter.CustomAdapter;
import com.example.app.Response.EmployeeIDResultResponse;
import com.example.app.Response.EmployeeIdResponse;
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

import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_NOT_FOUND;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;

public class DistributorFragment extends Fragment {
    private String[] statusArray={"Pending","Dispatched","Delivered"};
    private ArrayList<EmployeeIDResultResponse> emplist;
    private CustomAdapter adapter;
    private WebApi webApi;
    private String acess_token;
    Retrofit retrofit;
    Spinner spinner;
    Button btn_getData;
    private RecyclerView itemlistRecyclerview;
    private TextView textView2;
    private ProgressBar progressBarDil;
    private EditText searchlist;
    private ArrayList<ResultPending> resultDistributorwiseArrayList;
    private PendingAdapter distributorwiseAdapter;
    int  by_format_value;
    public DistributorFragment() {
        // Required empty public constructor
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        emplist = new ArrayList<>();
    }


    public static DistributorFragment newInstance(String param1, String param2) {
        DistributorFragment fragment = new DistributorFragment();

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
        View v = inflater.inflate(R.layout.fragment_distributor, container, false);
        spinner = (Spinner)v.findViewById(R.id.spinner);
        progressBarDil=v.findViewById(R.id.progressBarDil);
        textView2=v.findViewById(R.id.textView2);
        itemlistRecyclerview=v.findViewById(R.id.item_list);
        searchlist=v.findViewById(R.id.searchlist);
        btn_getData = v.findViewById(R.id.btn_getData);
        if(isNetworkAvailable()) {
            fetchAcessToken(v);
        }else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              by_format_value=emplist.get(position).getUserID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
              by_format_value=emplist.get(0).getUserID();
            }
        });

        btn_getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDistributorWiseItemList(by_format_value);
                //resultDistributorwiseArrayList.clear();

            }
        });
        return v;
    }
    private void fetchAcessToken(final View rootView) {
        //getting the progressbar

        Call<TokenResponse> call = webApi.accessToken("password", "fbApp", "fbApp", "admin", "123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token = response.body().getValue();
                Log.d("Tag", "token===>" + acess_token);
                getDistributorList(rootView);
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
    private void getDistributorList(View v){
        final ProgressBar pprogressBar = (ProgressBar) v.findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);
        Call<EmployeeIdResponse> call=webApi.getDistID(2,acess_token);
        call.enqueue(new Callback<EmployeeIdResponse>() {
            @Override
            public void onResponse(Call<EmployeeIdResponse> call, Response<EmployeeIdResponse> response) {
                pprogressBar.setVisibility(View.GONE);
                String status=response.body().getStatus();
                if(status.equals("SUCCESS")){
                    emplist=response.body().getResult();
                    if (emplist.size()>0){
// Create custom adapter object ( see below CustomAdapter.java )
                        adapter = new CustomAdapter(getContext(), R.layout.customspinnerlayout, emplist);

                        // Set adapter to spinner
                        spinner.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<EmployeeIdResponse> call, Throwable t) {
                pprogressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDistributorWiseItemList(int dist_id){

        //making the progressbar visible
        progressBarDil.setVisibility(View.VISIBLE);
        Call<PendingReportResponse> call=webApi.getDistReport(dist_id,acess_token);
        call.enqueue(new Callback<PendingReportResponse>() {
            @Override
            public void onResponse(Call<PendingReportResponse> call, Response<PendingReportResponse> response) {
                progressBarDil.setVisibility(View.GONE);
                int code=response.code();
                switch (code) {
                    case RESPONSE_NOT_FOUND:
                        Toast.makeText(getContext(), "NO REORDS FOUND", Toast.LENGTH_SHORT).show();
                       // String message = response.body().getMessage();

                        break;
                    case RESPONSE_OK:
                String status =response.body().getStatus();
                if(status.equals("SUCCESS")){
                    resultDistributorwiseArrayList =response.body().getResult();
                    if(resultDistributorwiseArrayList.size()>0){
                        distributorwiseAdapter = new PendingAdapter(getContext(),resultDistributorwiseArrayList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        itemlistRecyclerview.setLayoutManager(mLayoutManager);
                        itemlistRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        itemlistRecyclerview.setAdapter(distributorwiseAdapter);
                        distributorwiseAdapter.notifyDataSetChanged();


                    }
                    else{
                        itemlistRecyclerview.setVisibility(View.GONE);
                        textView2.setVisibility(View.VISIBLE);
                    }
                }
                }
            }

            @Override
            public void onFailure(Call<PendingReportResponse> call, Throwable t) {

            }
        });

    }
}
