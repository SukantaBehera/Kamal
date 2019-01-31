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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.Response.OrderResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.ViewOrderResult;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.foodie.adapter.FransAdapterNew;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;


public class MyOrdersFragment extends Fragment {
    private WebApi webApi;
    Retrofit retrofit;
    RecyclerView recycleview;
    EditText search;
    ProgressBar progressBarDil;
    TextView textView2;
    private FransAdapterNew adapterNew;
    private ArrayList<ViewOrderResult> viewOrderResultsArray;
    private String acess_token;
    String userId;
    SharedPreferenceClass sharedPreferenceClass;

    public MyOrdersFragment() {
        // Required empty public constructor
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
    }


    public static MyOrdersFragment newInstance(String param1, String param2) {
        MyOrdersFragment fragment = new MyOrdersFragment();

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
        View v= inflater.inflate(R.layout.fragment_my_orders, container, false);
        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        userId=  SharedPreferenceClass.readString(getActivity(), "USERID","");

        recycleview = (RecyclerView) v.findViewById(R.id.item_list);
        search = (EditText) v.findViewById(R.id.searchlist);
        textView2=(TextView)v.findViewById(R.id.textView2);
        progressBarDil=(ProgressBar)v.findViewById(R.id.progressBarDil);
        viewOrderResultsArray=new ArrayList<>();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (viewOrderResultsArray != null) {
                    filter(editable.toString());
                }

            }
        });

        if(isNetworkAvailable()) {
            fetchAcessToken();
        }else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return v;

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                netwOrderById(userId);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void filter(String text) {
        ArrayList<ViewOrderResult> temp = new ArrayList();
        for(int i=0;i<viewOrderResultsArray.size();i++){
            ViewOrderResult result=viewOrderResultsArray.get(i);
            String  id=String.valueOf(result.getOrder_id());
            if(id.contains(text.toLowerCase())){
                temp.add(result);
            }
        }

        adapterNew.updateList(temp);
    }

    private void netwOrderById(String id){
        viewOrderResultsArray.clear();

        progressBarDil.setVisibility(View.VISIBLE);
        Call<OrderResponse> call=webApi.getOrderById(acess_token,id);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                progressBarDil.setVisibility(View.GONE);
                int code=response.code();
                switch (code){
                    case RESPONSE_ERROR:
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                        break;
                    case RESPONSE_OK:
                        String status=response.body().getStatus();
                        if(status.equals("SUCCESS")){
                            viewOrderResultsArray=response.body().getResult();
                            Log.d("Tag","Size===>"+viewOrderResultsArray.size());
                            if(viewOrderResultsArray.size()>0){

                                adapterNew = new FransAdapterNew(getContext(),viewOrderResultsArray);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(mLayoutManager);
                                recycleview.setItemAnimator(new DefaultItemAnimator());
                                recycleview.setAdapter(adapterNew);
                                adapterNew.notifyDataSetChanged();
                            }
                            else {
                                textView2.setVisibility(View.VISIBLE);
                                recycleview.setVisibility(View.GONE);
                            }
                        }
                        break;
                }
                //       pprogressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                progressBarDil.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
