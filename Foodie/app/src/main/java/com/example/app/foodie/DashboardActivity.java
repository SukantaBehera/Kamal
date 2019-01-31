package com.example.app.foodie;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.app.Response.CountResponse;
import com.example.app.Response.DashboardResponse;
import com.example.app.Response.DashboardResult;
import com.example.app.Response.TokenResponse;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.Util.RegPrefManager;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.app.Util.Common.Constants.RESPONSE_BAD;
import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;
import static java.lang.Integer.bitCount;
import static java.lang.Integer.parseInt;

public class DashboardActivity extends Fragment {
    private WebApi webApi;
    Retrofit retrofit;
    private String acess_token;
    TableLayout tl;
    TextView textView2;
    ProgressBar progressBarDil;
    ArrayList<DashboardResult> arrayList;
    ArrayList<String> sLList;
    ArrayList<String> nameList;
    ArrayList<String> idList;
    ArrayList<String> totalCount;
    private RelativeLayout card_view;
    private TextView francCountTv,distCountTv,empCountTv;

    String role;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_about, container, false);
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        tl =rootView.findViewById(R.id.table);
        textView2=rootView.findViewById(R.id.textView2);
        progressBarDil=rootView.findViewById(R.id.progressBarDil);
        card_view=rootView.findViewById(R.id.card_view);
        francCountTv=rootView.findViewById(R.id.francCountTv);
        distCountTv=rootView.findViewById(R.id.distCountTv);
        empCountTv=rootView.findViewById(R.id.empCountTv);
        role= RegPrefManager.getInstance(getContext()).getLoginRoleId();
        arrayList=new ArrayList<>();
        sLList=new ArrayList<>();
        nameList=new ArrayList<>();
        idList=new ArrayList<>();
        totalCount=new ArrayList<>();
        if(isNetworkAvailable()) {
            fetchAcessToken(rootView);
        }else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }


        return rootView;
    }

    //network
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                empList();
                dashBoardList();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void empList(){
        Call<CountResponse> call=webApi.getCount(acess_token);
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                int code=response.code();
                switch (code) {
                    case RESPONSE_ERROR:
                        card_view.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                        break;
                    case RESPONSE_OK:
                        card_view.setVisibility(View.VISIBLE);
                        String status=response.body().getStatus();
                        if(status.equals("SUCCESS"))
                        {
                            int frnc=response.body().getResult().getFranchisor();
                            int dist=response.body().getResult().getDistributor();
                            int emp=response.body().getResult().getEmployee();

                            if(role.equals("ROLE_ADMIN")) {

                                francCountTv.setText("Franchisor Count: " + frnc + "");
                                distCountTv.setText("Distributor Count: " + dist + "");
                                empCountTv.setText("Employee Count: " + emp + "");
                            }else if(role.equals("ROLE_KML_EMP")){
                                empCountTv.setVisibility(View.GONE);
                                francCountTv.setText("Franchisor Count: " + frnc + "");
                                distCountTv.setText("Distributor Count: " + dist + "");
                            }
                        }
                        else {
                            card_view.setVisibility(View.GONE);
                        }
                        break;
                    case RESPONSE_BAD:
                        card_view.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void dashBoardList(){
        progressBarDil.setVisibility(View.VISIBLE);

        Call<DashboardResponse> call=webApi.getDashBoardTable(acess_token);
        call.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                progressBarDil.setVisibility(View.GONE);
                int code=response.code();
                switch (code){
                    case RESPONSE_ERROR:
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                        break;
                    case RESPONSE_OK:
                        String status=response.body().getStatus();
                        if(status.equals("SUCCESS")){
                            arrayList=response.body().getResult();
                            if(arrayList.size()>0){
                            for(int i=0;i<arrayList.size();i++){
                                int slno=i+1;
                                int id=arrayList.get(i).getId();
                                String name=arrayList.get(i).getName();
                                int count=arrayList.get(i).getTotalCount();

                                sLList.add(String.valueOf(slno));
                                idList.add(String.valueOf(id));
                                nameList.add(name);
                                totalCount.add(String.valueOf(count));
                            }

                                addHeaders();
                                addData();
                            }
                            else {
                                tl.setVisibility(View.GONE);
                                textView2.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case RESPONSE_BAD:
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                progressBarDil.setVisibility(View.GONE);
                tl.setVisibility(View.GONE);
                textView2.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //table layout

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(getContext());
        tv.setId(id);
        tv.setText(title.toString());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());

        return tv;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 1, 1, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }


    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {

        TableRow tr = new TableRow(getContext());
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "Sl No.", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getContext(), R.color.colorAccent)));
        tr.addView(getTextView(0, "Name", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getContext(), R.color.colorAccent)));
        tr.addView(getTextView(0, "Id", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getContext(), R.color.colorAccent)));
        tr.addView(getTextView(0, "Count", Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getContext(), R.color.colorAccent)));
        tl.addView(tr, getTblLayoutParams());
    }

    /**
     * This function add the data to the table
     **/
    public void addData() {
        int numCompanies = sLList.size();

        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, sLList.get(i), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(getContext(), R.color.grey)));
            tr.addView(getTextView(i + numCompanies, nameList.get(i), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(getContext(), R.color.grey)));
            tr.addView(getTextView(i + numCompanies, idList.get(i), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(getContext(), R.color.grey)));
            tr.addView(getTextView(i + numCompanies, totalCount.get(i), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(getContext(), R.color.grey)));
            tl.addView(tr, getTblLayoutParams());
        }
    }


}
