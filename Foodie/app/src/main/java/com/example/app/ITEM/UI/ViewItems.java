package com.example.app.ITEM.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.TextView;
import android.widget.Toast;


import com.example.app.ITEM.ADAPTER.ItemAdapter;
import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.Response.GetAllItemsResponse;
import com.example.app.Response.AllItemResult;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.ViewOrderResult;
import com.example.app.Response.ViewResult;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.foodie.LoginActivity;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.sukanta.foodie.R;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;

public class ViewItems extends DilogueFRagment {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<ItemDetail> itemList = new ArrayList<ItemDetail>();
    ArrayList<ItemDetail> itemList1 = new ArrayList<ItemDetail>();
    private ArrayList<AllItemResult> viewResultsArray;
    private ArrayList<AllItemResult> filterResultsArray;
    private ArrayList<AllItemResult> filterResultsArray1;
    private ItemAdapter itemlistAdapter;
    private WebApi webApi;
    Retrofit retrofit;
    RecyclerView recycleview;
    FloatingActionButton fab;
    SwipeRefreshLayout pullToRefresh;
    View rootView = null;
    TextView empty_notes_view;
    EditText search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.itemlistfloating, container, false);
        retrofit = ApiClient.getRetrofit();
        webApi = retrofit.create(WebApi.class);
        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        pullToRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.pullToRefresh);
        search = (EditText) rootView.findViewById(R.id.searchlist);
        filterResultsArray=new ArrayList<>();
        viewResultsArray=new ArrayList<>();
        filterResultsArray1=new ArrayList<>();
        empty_notes_view = rootView.findViewById(R.id.empty_notes_view);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fabadditem);
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(),"clicked fab icon",Toast.LENGTH_LONG).show();
               getActivity().startActivity(new Intent(getActivity(),AddItemsActivity.class));
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
                if (filterResultsArray != null) {
                    filter(editable.toString());
                }

            }
        });
        if (isNetworkAvailable()) {
            fetchAcessToken();
        } else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    void filter(String text) {

        ArrayList<AllItemResult> temp = new ArrayList();
        for(int i=0;i<filterResultsArray.size();i++){
            AllItemResult result=filterResultsArray.get(i);
            String  id=String.valueOf(result.getId());
            if(id.contains(text.toLowerCase())){
                temp.add(result);
            }
        }

        itemlistAdapter.updateList(temp);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

  /*  private void fetchAcessToken(final View view) {
        //getting the progressbar


        final ProgressBar pprogressBar = (ProgressBar) view.findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.getToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        pprogressBar.setVisibility(View.INVISIBLE);
                        Log.e("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            acess_token = obj.getString("value");
                            if (acess_token != null) {
                                getAllItemList(view);
                            } else {
                                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }*/
    private void fetchAcessToken() {
        //getting the progressbar


        Call<TokenResponse> call=webApi.accessToken("password","fbApp","fbApp","admin","123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token=response.body().getValue();
                Log.d("Tag","token===>"+acess_token);
                networkAllItems1();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void networkAllItems1(){
        Call<GetAllItemsResponse> call=webApi.getAllItemList(acess_token);
        call.enqueue(new Callback<GetAllItemsResponse>() {
            @Override
            public void onResponse(Call<GetAllItemsResponse> call, Response<GetAllItemsResponse> response) {
                int code=response.code();
                switch (code) {
                    case RESPONSE_ERROR:
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
                        break;
                    case RESPONSE_OK:
                        String status = response.body().getStatus();
                        if(status.equals("SUCCESS")) {
                            viewResultsArray = response.body().getResult();
                            for (int i = 0; i < viewResultsArray.size(); i++) {
                                String flag = viewResultsArray.get(i).getIs_active();
                                if (!flag.equals("N")) {
                                    AllItemResult allItemResult = new AllItemResult();
                                    allItemResult.setDescription(viewResultsArray.get(i).getDescription());
                                    allItemResult.setEntered_by(viewResultsArray.get(i).getEntered_by());
                                    allItemResult.setId(viewResultsArray.get(i).getId());
                                    allItemResult.setIs_active(viewResultsArray.get(i).getIs_active());
                                    allItemResult.setUnit_id(viewResultsArray.get(i).getUnit_id());
                                    allItemResult.setFranch_view_flag(viewResultsArray.get(i).getFranch_view_flag());
                                    allItemResult.setName(viewResultsArray.get(i).getName());
                                    allItemResult.setPrice(viewResultsArray.get(i).getPrice());
                                    allItemResult.setQom_status(viewResultsArray.get(i).getQom_status());
                                    allItemResult.setQuantity(viewResultsArray.get(i).getQuantity());

                                    filterResultsArray.add(allItemResult);

                                }
                            }

                            if (filterResultsArray.size() > 0) {
                                itemlistAdapter = new ItemAdapter(filterResultsArray, getContext(), ViewItems.this);
                                //itemList.clear();
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(mLayoutManager);
                                recycleview.setItemAnimator(new DefaultItemAnimator());
                                recycleview.setAdapter(itemlistAdapter);
                                itemlistAdapter.notifyDataSetChanged();
                            } else {
                                empty_notes_view.setVisibility(View.VISIBLE);
                                recycleview.setVisibility(View.GONE);
                            }
                        }
                        break;
                }


            }

            @Override
            public void onFailure(Call<GetAllItemsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }




    /* private void getAllItemList(View view) {
        //getting the progressbar


        final ProgressBar pprogressBar = (ProgressBar) view.findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.getAllItems + acess_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        pprogressBar.setVisibility(View.INVISIBLE);
                        Log.e("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("status").equals("SUCCESS")) {
                                JSONArray jsonArray = obj.getJSONArray("result");
                           //     Toast.makeText(getActivity(), "Total Items present = " + jsonArray.length(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String itemId = jsonObject.getString("item_id");
                                    String name = jsonObject.getString("name");
                                    String description = jsonObject.getString("description");
                                    String price = jsonObject.getString("price");
                                    String status = jsonObject.getString("is_active");
                                    String unit_id = jsonObject.getString("unit_id");
                                    String franchisorflag = jsonObject.getString("franch_view_flag");
                                    String entered_by = jsonObject.getString("entered_by");
                                    String
                                    String id = jsonObject.getString("id");
                                    itemList.add(new ItemDetail(itemId, name, description, price, status,franchisorflag, unit_id, entered_by, id,"NO"));
                                }
                                // cartList = response.getDetail();

                                if(itemList.size()>0){
                                    for (int i = 0; i < itemList.size(); i++) {
                                        String status1 = itemList.get(i).getStatus();
                                        ItemDetail itemDetail ;
                                        if (status1.equals("N")) {
                                            Log.d("Tag","Go");
                                        } else {
                                            itemDetail = new ItemDetail(itemList.get(i).getItemId(),itemList.get(i).getName(),itemList.get(i).getDescription(),
                                                    itemList.get(i).getPrice(),itemList.get(i).getStatus(),itemList.get(i).getFranchisorflag(),itemList.get(i).getUnit_id(),
                                                    itemList.get(i).getEntered_by(),itemList.get(i).getId(),itemList.get(i).getBuyStatus());
                                            itemList1.add(itemDetail);

                                        }
                                    }
                                    if(itemList1.size()>0){
                                        itemlistAdapter = new ItemAdapter(itemList1,getContext(),ViewItems.this);
                                        itemList.clear();
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                        recycleview.setLayoutManager(mLayoutManager);
                                        recycleview.setItemAnimator(new DefaultItemAnimator());
                                        recycleview.setAdapter(itemlistAdapter);
                                        itemlistAdapter.notifyDataSetChanged();
                                    }else{
                                        empty_notes_view.setVisibility(View.VISIBLE);
                                        recycleview.setVisibility(View.GONE);
                                    }

                                }
                            } else {
                               *//* empty_notes_view.setVisibility(View.VISIBLE);
                                recycleview.setVisibility(View.GONE);
*//*
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }*/
    public void deleteItems(int pos){
        filterResultsArray.remove(pos);
        Log.d("Tag",""+itemList.size());
       if( filterResultsArray.size()>0){

        itemlistAdapter = new ItemAdapter(filterResultsArray,getContext(), ViewItems.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(itemlistAdapter);
        itemlistAdapter.notifyDataSetChanged();
    }
    else{

       }
    }

}