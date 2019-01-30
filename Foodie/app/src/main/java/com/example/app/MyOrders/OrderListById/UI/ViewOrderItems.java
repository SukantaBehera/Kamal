package com.example.app.MyOrders.OrderListById.UI;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.ITEM.ADAPTER.ItemListAdapter;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.MyOrders.OrderListById.Adapter.MyOrderAdapterNew;
import com.example.app.Request.MyOrderUpdateDeliveryRequest;
import com.example.app.Request.MyOrderUpdateRequest;
import com.example.app.Response.EmployeeIDResultResponse;
import com.example.app.Response.EmployeeIdResponse;
import com.example.app.Response.MyOrderUpdateResponse;
import com.example.app.Response.OrderResponse;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.ViewOrderResult;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.RecyclerItemClickListener;
import com.example.app.MyOrders.OrderListById.Model.OrderItemDetail;
import com.example.app.Util.Common.WebApi;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.OrderListById.Adapter.MyOrderAdapter;
import com.example.sukanta.foodie.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.app.Util.Common.Constants.RESPONSE_BAD;
import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;

public class ViewOrderItems extends DilogueFRagment {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<OrderItem> itemDetail = null;
    ArrayList<OrderItemDetail> itemList = new ArrayList<OrderItemDetail>();
    ArrayList<OrderItemDetail> itemList1 = new ArrayList<OrderItemDetail>();
    private MyOrderAdapter itemlistAdapter;
    private MyOrderAdapterNew adapterNew;
    RecyclerView recycleview;
    private ArrayList<ViewOrderResult> viewOrderResultsArray;

    FloatingActionButton fab;
    String userId;
    String role,spinselect1,by_name,by_date,by_orderid;
    EditText search;
    private WebApi webApi;
    Retrofit retrofit;

    private ArrayList<EmployeeIDResultResponse> emplist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_item_order_list, container, false);

        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.item_list);
        search = (EditText) rootView.findViewById(R.id.searchlist);
        userId=  SharedPreferenceClass.readString(getActivity(), "USERID","");
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        viewOrderResultsArray=new ArrayList<>();
        role=  SharedPreferenceClass.readString(getActivity(), "ROLEID","");
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emplist=new ArrayList<>();
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"clicked fab icon",Toast.LENGTH_LONG).show();
               // getActivity().startActivity(new Intent(getActivity(),AddItems.class));
            }
        });

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
            fetchAcessToken(rootView);
        }else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }



       /* recycleview.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recycleview ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        final OrderItemDetail selectedObject = itemList.get(position);

                        itemDetail = selectedObject.getArrayListItem();
                        for(int i = 0; i<itemDetail.size();i++){
                         Log.e("TEST",itemDetail.get(i).getPrice()+"--"+itemDetail.get(i).getItem_name());
                        }
                        Toast.makeText(getActivity(), selectedObject.getOrder_id() + "----Name" + selectedObject.getName()+itemDetail.size(), Toast.LENGTH_SHORT).show();
                        Intent in1 = new Intent(getActivity(),ItemDetail.class);
                        in1.putExtra("itemArray",itemDetail);
                        in1.putExtra("totalPrice",selectedObject.getTotal_price());

                        startActivity(in1);

                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );*/



        return rootView;
    }
   /* void filter(String text) {
        ArrayList<ViewOrderResult> temp = new ArrayList();
        for (ViewOrderResult d : viewOrderResultsArray) {
            if (d.getOrder_id().contains(text) ) {
                temp.add(d);
            }
        }
        adapterNew.updateList(temp);
    }*/

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
    private void fetchAcessToken(final View rootView) {
        //getting the progressbar


        Call<TokenResponse> call = webApi.accessToken("password", "fbApp", "fbApp", "admin", "123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token = response.body().getValue();
                Log.d("Tag", "token===>" + acess_token);
                getAllItemList(rootView);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });

    }

        private void getAllItemList(View view){
            //getting the progressbar

            getEmpList();
            String orderUrl = "";
            //creating a string request to send request to the url
            if (role.equals("ROLE_ADMIN")) {
               networkAdmin(view);
            } else if (role.equals("ROLE_KML_EMP")) {
                networkAdmin(view);
            } else {
                netwOrderById(view,userId);

            }


        }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


         private void networkAdmin(View view){
             viewOrderResultsArray.clear();
             final ProgressBar pprogressBar = (ProgressBar) view.findViewById(R.id.progressBarDil);
             //making the progressbar visible
             pprogressBar.setVisibility(View.VISIBLE);
        Call<OrderResponse> call=webApi.getOrderAllItem(acess_token);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                pprogressBar.setVisibility(View.GONE);
               String status=response.body().getStatus();
               if(status.equals("SUCCESS")){
                   viewOrderResultsArray=response.body().getResult();
                   Log.d("Tag","Size===>"+viewOrderResultsArray.size());
                   if(viewOrderResultsArray.size()>0){
                       adapterNew = new MyOrderAdapterNew(getContext(),viewOrderResultsArray,ViewOrderItems.this,emplist);
                       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                       recycleview.setLayoutManager(mLayoutManager);
                       recycleview.setItemAnimator(new DefaultItemAnimator());
                       recycleview.setAdapter(adapterNew);
                       adapterNew.notifyDataSetChanged();
                   }
               }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
               pprogressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });
        }

        private void netwOrderById(View view,String id){
            viewOrderResultsArray.clear();
            final ProgressBar pprogressBar = (ProgressBar) view.findViewById(R.id.progressBarDil);
            //making the progressbar visible
            pprogressBar.setVisibility(View.VISIBLE);
            Call<OrderResponse> call=webApi.getOrderById(acess_token,id);
            call.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    pprogressBar.setVisibility(View.GONE);
                    String status=response.body().getStatus();
                    if(status.equals("SUCCESS")){
                        viewOrderResultsArray=response.body().getResult();
                        Log.d("Tag","Size===>"+viewOrderResultsArray.size());
                        if(viewOrderResultsArray.size()>0){
                            adapterNew = new MyOrderAdapterNew(getContext(),viewOrderResultsArray,ViewOrderItems.this,emplist);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recycleview.setLayoutManager(mLayoutManager);
                            recycleview.setItemAnimator(new DefaultItemAnimator());
                            recycleview.setAdapter(adapterNew);
                            adapterNew.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    pprogressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                }
            });

        }


    public void updateStatus(final String spinselect, int  by_format_value, String date_format_value, final String orderid1){

        if(spinselect.equals("Dispatched")){

              /*  JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("order_id","38");
                jsonObject.addProperty("dispatch_date","2019-01-28");
                jsonObject.addProperty("dispatched_by","yue");
                jsonObject.addProperty("status","DISPATCHED");*/

            MyOrderUpdateRequest updateRequest=new MyOrderUpdateRequest(
            Integer.valueOf(orderid1),date_format_value,by_format_value,"DISPATCHED");

            Call<MyOrderUpdateResponse> call=webApi.getUpdateDispatchResponse(acess_token,updateRequest);
            call.enqueue(new Callback<MyOrderUpdateResponse>() {
                @Override
                public void onResponse(Call<MyOrderUpdateResponse> call, Response<MyOrderUpdateResponse> response) {
                    int code=response.code();
                    Log.d("Tag",""+code);
                    switch (code){
                        case RESPONSE_OK:
                            String status=response.body().getStatus();
                            if(status.equals("SUCCESS")){
                                String msg=response.body().getMessage();
                                Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                                //getAllItemList();
                                for(int i=0;i<viewOrderResultsArray.size();i++){
                                    ViewOrderResult value=viewOrderResultsArray.get(i);
                                    if(value.getOrder_id()==Integer.valueOf(orderid1)){
                                        ViewOrderResult value1=new ViewOrderResult();
                                        value1.setOrder_id(Integer.valueOf(orderid1));
                                        value1.setDelivered_by_empId(value.getDelivered_by_empId());
                                        value1.setDelivered_by_empName(value.getDelivered_by_empName());
                                        value1.setDelivery_date(value.getDelivery_date());
                                        value1.setDispatched_by_empId(value.getDispatched_by_empId());
                                        value1.setDispatched_by_empName(value.getDispatched_by_empName());
                                        value1.setDispatch_date(value.getDispatch_date());
                                        value1.setOrderby_custId(value.getOrderby_custId());
                                        value1.setOrderDate(value.getOrderDate());
                                        value1.setOrder_deliv_status(spinselect);
                                        value1.setTotal_price(value.getTotal_price());
                                        value1.setUserName(value.getUserName());
                                        value1.setUserRoleCode(value.getUserRoleCode());
                                        value1.setUser_active_status(value.getUser_active_status());
                                        viewOrderResultsArray.set(i,value1);

                                    }
                                }

                                if(viewOrderResultsArray.size()>0){
                                    adapterNew = new MyOrderAdapterNew(getContext(),viewOrderResultsArray,ViewOrderItems.this,emplist);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recycleview.setLayoutManager(mLayoutManager);
                                    recycleview.setItemAnimator(new DefaultItemAnimator());
                                    recycleview.setAdapter(adapterNew);
                                    adapterNew.notifyDataSetChanged();
                                }
                            }

                            break;
                        case RESPONSE_ERROR:
                            Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                            break;
                        case RESPONSE_BAD:

                            Log.v("ERRor",response.errorBody().toString());
                            Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MyOrderUpdateResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                }
            });






        }
        else {

          /*      JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("order_id",Integer.valueOf(orderid));
                jsonObject.addProperty("delivery_date",date_format_value);
                jsonObject.addProperty("delivered_by",by_format_value);
                jsonObject.addProperty("status","DELIVERED");*/

            MyOrderUpdateDeliveryRequest deliveryRequest = new MyOrderUpdateDeliveryRequest(Integer.valueOf(orderid1), date_format_value, by_format_value, "DELIVERED");

            Call<MyOrderUpdateResponse> call = webApi.getUpdateDeliveryResponse(acess_token, deliveryRequest);
            call.enqueue(new Callback<MyOrderUpdateResponse>() {
                @Override
                public void onResponse(Call<MyOrderUpdateResponse> call, Response<MyOrderUpdateResponse> response) {
                    int code = response.code();
                    Log.d("Tag", "" + code);
                    switch (code) {
                        case RESPONSE_OK:
                            String status = response.body().getStatus();
                            if (status.equals("SUCCESS")) {
                                String msg = response.body().getMessage();
                                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                //getAllItemList();
                                for (int i = 0; i < viewOrderResultsArray.size(); i++) {
                                    ViewOrderResult value = viewOrderResultsArray.get(i);
                                    if (value.getOrder_id() == Integer.valueOf(orderid1)) {
                                        ViewOrderResult value1 = new ViewOrderResult();
                                        value1.setOrder_id(Integer.valueOf(orderid1));
                                        value1.setDelivered_by_empId(value.getDelivered_by_empId());
                                        value1.setDelivered_by_empName(value.getDelivered_by_empName());
                                        value1.setDelivery_date(value.getDelivery_date());
                                        value1.setDispatched_by_empId(value.getDispatched_by_empId());
                                        value1.setDispatched_by_empName(value.getDispatched_by_empName());
                                        value1.setDispatch_date(value.getDispatch_date());
                                        value1.setOrderby_custId(value.getOrderby_custId());
                                        value1.setOrderDate(value.getOrderDate());
                                        value1.setOrder_deliv_status(spinselect);
                                        value1.setTotal_price(value.getTotal_price());
                                        value1.setUserName(value.getUserName());
                                        value1.setUserRoleCode(value.getUserRoleCode());
                                        value1.setUser_active_status(value.getUser_active_status());
                                        viewOrderResultsArray.set(i, value1);

                                    }
                                }

                                if (viewOrderResultsArray.size() > 0) {
                                    adapterNew = new MyOrderAdapterNew(getContext(), viewOrderResultsArray, ViewOrderItems.this,emplist);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recycleview.setLayoutManager(mLayoutManager);
                                    recycleview.setItemAnimator(new DefaultItemAnimator());
                                    recycleview.setAdapter(adapterNew);
                                    adapterNew.notifyDataSetChanged();
                                }
                            }

                            break;
                        case RESPONSE_ERROR:
                            Log.v("ERRor", response.errorBody().toString());
                            Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MyOrderUpdateResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }


    private void getEmpList(){
        Call<EmployeeIdResponse> call=webApi.getEmpID(acess_token);
        call.enqueue(new Callback<EmployeeIdResponse>() {
            @Override
            public void onResponse(Call<EmployeeIdResponse> call, Response<EmployeeIdResponse> response) {
                String status=response.body().getStatus();
                if(status.equals("SUCCESS")){
                    emplist=response.body().getResult();

                }
            }

            @Override
            public void onFailure(Call<EmployeeIdResponse> call, Throwable t) {

            }
        });
    }



}