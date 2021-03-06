package com.example.app.foodie;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.Response.PendingReportResponse;
import com.example.app.Response.ResultPending;
import com.example.app.Response.TokenResponse;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.Util.DatePickerFragmentFrom;
import com.example.app.foodie.adapter.PendingAdapter;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.app.Util.Common.Constants.RESPONSE_BAD;
import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_NOT_FOUND;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;


public class DailyFragment extends Fragment {
    private int mYear, mMonth, mDay, mHour, mMinute;

    private RecyclerView itemlistRecyclerview;
    private TextView textView2;
    private ProgressBar progressBarDil;
    private EditText searchlist,startEd,endEd;
    private WebApi webApi;
    Retrofit retrofit;
    private String acess_token;
    private PendingAdapter pendingAdapter;
    private ArrayList<ResultPending> resultDatewiseArrayList;
    String startDate,datemain_from="",datemain_end="";
    int flag;
    String endDate;
     Button btn_getData;


     public DailyFragment() {
        // Required empty public constructor
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
    }


    public static DailyFragment newInstance(String param1, String param2) {
        DailyFragment fragment = new DailyFragment();

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
        View rootview= inflater.inflate(R.layout.fragment_daily, container, false);
          startEd=(EditText)rootview.findViewById(R.id.startEd);
          endEd=(EditText)rootview.findViewById(R.id.endEd);
        progressBarDil=rootview.findViewById(R.id.progressBarDil);
        textView2=rootview.findViewById(R.id.textView2);
        itemlistRecyclerview=rootview.findViewById(R.id.item_list);
        searchlist=rootview.findViewById(R.id.searchlist);
        btn_getData = rootview.findViewById(R.id.btn_getData);
        resultDatewiseArrayList=new ArrayList<>();
        if(isNetworkAvailable()) {
            fetchAcessToken();
        }else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }
        startEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                startEd.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                startDate = startEd.getText().toString();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();*/
              flag=1;
              showCurrentDate();
             /* if(!datemain_from.isEmpty()){
                  startEd.setText(datemain_from);
              }else {
                 // showCurrentDate();
              }*/

            }
        });


        endEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                endEd.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                endDate = endEd.getText().toString();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();*/
                flag=2;
                showCurrentDate();
                /*if(!datemain_end.isEmpty()){
                    endEd.setText(datemain_end);
                }else {
                    // showCurrentDate();
                }*/
            }
        });

        btn_getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDateWiseItemList();
                    startEd.getText().clear();
                    endEd.getText().clear();
                    resultDatewiseArrayList.clear();

            }
        });


        return rootview;
    }

    public void showCurrentDate(){
        DatePickerFragmentFrom date = new DatePickerFragmentFrom();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondatefrom);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondatefrom = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            if(flag==1) {
                startDate =String.valueOf(year)+ "/" +  String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth) ;
                startEd.setText(startDate);
            }else {
                endDate = String.valueOf(year)+ "/" +  String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth) ;
                endEd.setText(endDate);
            }
            //startEd.setText(datemain_from);

        }
    };


    private void fetchAcessToken() {
        //getting the progressbar


        Call<TokenResponse> call = webApi.accessToken("password", "fbApp", "fbApp", "admin", "123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token = response.body().getValue();
                Log.d("Tag", "token===>" + acess_token);

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

    private void getDateWiseItemList(){
        progressBarDil.setVisibility(View.VISIBLE);
        Call<PendingReportResponse> call=webApi.getDatewiseReport(acess_token,startDate,endDate);
        call.enqueue(new Callback<PendingReportResponse>() {
            @Override
            public void onResponse(Call<PendingReportResponse> call, Response<PendingReportResponse> response) {
                progressBarDil.setVisibility(View.GONE);
                int code=response.code();
                switch (code) {
                    case RESPONSE_ERROR:
                        Toast.makeText(getContext(), "Check Again", Toast.LENGTH_LONG).show();
                        break;
                    case RESPONSE_OK:
                        String status=response.body().getStatus();
                        if(status.equals("SUCCESS")){
                            resultDatewiseArrayList=response.body().getResult();
                            Log.d("Tag","Size===>"+resultDatewiseArrayList.size());
                            if(resultDatewiseArrayList.size()>0){
                                pendingAdapter = new PendingAdapter(getContext(),resultDatewiseArrayList);
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
                        break;
                    case RESPONSE_BAD:
                        itemlistRecyclerview.setVisibility(View.GONE);
                        textView2.setVisibility(View.VISIBLE);
                        break;
                    case RESPONSE_NOT_FOUND:
                        itemlistRecyclerview.setVisibility(View.GONE);
                        textView2.setVisibility(View.VISIBLE);
                        break;
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
