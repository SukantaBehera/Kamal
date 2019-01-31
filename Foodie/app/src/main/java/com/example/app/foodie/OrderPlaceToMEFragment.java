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
import com.example.app.Util.RegPrefManager;
import com.example.app.foodie.adapter.DistributorDashboardAdapter;
import com.example.sukanta.foodie.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;

public class OrderPlaceToMEFragment extends Fragment {
private EditText searchlist;
private RecyclerView recycleview;
private TextView textView2;
private ProgressBar progressBarDil;
    private WebApi webApi;
    Retrofit retrofit;
    private DistributorDashboardAdapter adapterNew;
    private ArrayList<ViewOrderResult> viewOrderResultsArray;
    private ArrayList<ViewOrderResult> viewOrderResultsArray1;
    private String acess_token,userid;



    public OrderPlaceToMEFragment() {
        // Required empty public constructor
    }


    public static OrderPlaceToMEFragment newInstance(String param1, String param2) {
        OrderPlaceToMEFragment fragment = new OrderPlaceToMEFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_order_place_to_me, container, false);
        searchlist=(EditText)v.findViewById(R.id.searchlist);
        recycleview=(RecyclerView)v.findViewById(R.id.item_list);
        textView2=(TextView)v.findViewById(R.id.textView2);
        progressBarDil=(ProgressBar)v.findViewById(R.id.progressBarDil);
        userid= RegPrefManager.getInstance(getContext()).getLoginUserID();
        viewOrderResultsArray=new ArrayList<>();
        viewOrderResultsArray1=new ArrayList<>();
        searchlist.addTextChangedListener(new TextWatcher() {
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
                networkAdmin();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void networkAdmin(){
        viewOrderResultsArray.clear();

        progressBarDil.setVisibility(View.VISIBLE);
        Call<OrderResponse> call=webApi.getOrderAllItem(acess_token);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                progressBarDil.setVisibility(View.GONE);
                int code=response.code();
                switch (code) {
                    case RESPONSE_ERROR:
                        textView2.setVisibility(View.VISIBLE);
                        recycleview.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        break;
                    case RESPONSE_OK:
                        String status=response.body().getStatus();
                        if(status.equals("SUCCESS")){
                            viewOrderResultsArray=response.body().getResult();
                            Log.d("Tag","Size===>"+viewOrderResultsArray.size());
                            if(viewOrderResultsArray.size()>0){
                                for(int i=0;i<viewOrderResultsArray.size();i++){
                                    ViewOrderResult viewOrderResult=viewOrderResultsArray.get(i);
                                    int value=viewOrderResult.getDistributor_ID();
                                    String pendingstatus=viewOrderResult.getOrder_deliv_status();
                                    if(value==0){
                                        Log.d("Tag","Go to Hell");

                                    }else {
                                        if(Integer.parseInt(userid)==value) {
                                            if(pendingstatus==null) {
                                                Log.d("Tag","jddj");
                                            }
                                            else {
                                                if(pendingstatus.equals("PENDING")){
                                                    ViewOrderResult viewOrderResult1 = new ViewOrderResult();
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
                                            }



                                        }
                                        if(viewOrderResultsArray1.size()>0) {
                                            adapterNew = new DistributorDashboardAdapter(getContext(), viewOrderResultsArray1);
                                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                            recycleview.setLayoutManager(mLayoutManager);
                                            recycleview.setItemAnimator(new DefaultItemAnimator());
                                            recycleview.setAdapter(adapterNew);
                                            adapterNew.notifyDataSetChanged();
                                        }else {
                                            textView2.setVisibility(View.VISIBLE);
                                            recycleview.setVisibility(View.GONE);
                                        }

                                    }
                                }

                            }
                        }

                        break;
                }


            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                progressBarDil.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
