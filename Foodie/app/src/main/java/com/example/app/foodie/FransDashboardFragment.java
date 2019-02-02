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

import com.example.app.MyOrders.OrderListById.Adapter.MyOrderAdapterNew;
import com.example.app.MyOrders.OrderListById.UI.ViewOrderItems;
import com.example.app.Response.OrderResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.ViewOrderResult;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.foodie.adapter.FransAdapterNew;
import com.example.app.foodie.adapter.FransAdapterNew1;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;


public class FransDashboardFragment extends Fragment {
    private WebApi webApi;
    Retrofit retrofit;
    RecyclerView recycleview;
    EditText search;
    ProgressBar progressBarDil;
    TextView textView2;
    private FransAdapterNew1 adapterNew;
    private ArrayList<ViewOrderResult> viewOrderResultsArray;
    private ArrayList<ViewOrderResult> viewOrderResultsArray1;

    private String acess_token;
    String userId;
    SharedPreferenceClass sharedPreferenceClass;
    public FransDashboardFragment() {
        // Required empty public constructor
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
    }


    public static FransDashboardFragment newInstance(String param1, String param2) {
        FransDashboardFragment fragment = new FransDashboardFragment();

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
        View v= inflater.inflate(R.layout.fragment_frans_dashboard, container, false);
        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        userId=  SharedPreferenceClass.readString(getActivity(), "USERID","");

        recycleview = (RecyclerView) v.findViewById(R.id.item_list);
        search = (EditText) v.findViewById(R.id.searchlist);
        textView2=(TextView)v.findViewById(R.id.textView2);
        progressBarDil=(ProgressBar)v.findViewById(R.id.progressBarDil);
        viewOrderResultsArray=new ArrayList<>();
        viewOrderResultsArray1=new ArrayList<>();
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
                                for(int i=0;i<viewOrderResultsArray.size();i++){
                                    ViewOrderResult viewOrderResult=viewOrderResultsArray.get(i);
                                    String value=viewOrderResult.getOrder_deliv_status();
                                    if(value==null) {
                                    }
                                     else {

                                        if(value.equals("PENDING")){
                                            ViewOrderResult viewOrderResult1=new ViewOrderResult();
                                            viewOrderResult1.setDelivered_by_empId(viewOrderResult.getDelivered_by_empId());
                                            viewOrderResult1.setDispatched_by_empName(viewOrderResult.getDelivered_by_empName());
                                            viewOrderResult1.setDelivery_date(viewOrderResult.getDelivery_date());
                                            viewOrderResult1.setDispatched_by_empName(viewOrderResult.getDispatched_by_empName());
                                            viewOrderResult1.setDispatched_by_empId(viewOrderResult.getDispatched_by_empId());
                                            viewOrderResult1.setDispatch_date(viewOrderResult.getDispatch_date());
                                            viewOrderResult1.setOrderby_custId(viewOrderResult.getOrderby_custId());
                                            viewOrderResult1.setOrderDate(viewOrderResult.getOrderDate());
                                            viewOrderResult1.setOrder_deliv_status(viewOrderResult.getOrder_deliv_status());
                                            viewOrderResult1.setOrder_id(viewOrderResult.getOrder_id());
                                            viewOrderResult1.setTotal_price(viewOrderResult.getTotal_price());
                                            viewOrderResult1.setUserName(viewOrderResult.getUserName());
                                            viewOrderResult1.setUserRoleCode(viewOrderResult.getUserRoleCode());
                                            viewOrderResult1.setUser_active_status(viewOrderResult.getUser_active_status());
                                            viewOrderResult1.setItemQOmEmbed(viewOrderResult.getItemQOmEmbed());
                                            viewOrderResultsArray1.add(viewOrderResult1);
                                        }
                                        adapterNew = new FransAdapterNew1(getContext(),viewOrderResultsArray1);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                        recycleview.setLayoutManager(mLayoutManager);
                                        recycleview.setItemAnimator(new DefaultItemAnimator());
                                        recycleview.setAdapter(adapterNew);
                                        adapterNew.notifyDataSetChanged();
                                    }


                                }

                            }
                            else {
                                recycleview.setVisibility(View.GONE);
                                textView2.setVisibility(View.VISIBLE);
                                search.setVisibility(View.GONE);
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
