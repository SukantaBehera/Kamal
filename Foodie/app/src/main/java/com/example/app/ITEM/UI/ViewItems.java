package com.example.app.ITEM.UI;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.ITEM.ADAPTER.ItemAdapter;
import com.example.app.ITEM.ADAPTER.ItemListAdapter;
import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.USERLIST.ADAPTER.EmployeeListAdapter;
import com.example.app.USERLIST.UI.ViewEmployee;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.sukanta.foodie.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewItems extends DilogueFRagment {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<ItemDetail> itemList = new ArrayList<ItemDetail>();
    private ItemAdapter itemlistAdapter;


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

        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        pullToRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.pullToRefresh);
        search = (EditText) rootView.findViewById(R.id.searchlist);
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

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAcessToken(rootView);
            }
        });
        fetchAcessToken(rootView);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (itemList != null) {
                    filter(editable.toString());
                }

            }
        });

        return rootView;
    }

    void filter(String text) {
        ArrayList<ItemDetail> temp = new ArrayList();
        for (ItemDetail d : itemList) {
            if (d.getName().toLowerCase().contains(text.toLowerCase()) ) {
                temp.add(d);
            }
        }
        itemlistAdapter.updateList(temp);
    }



    private void fetchAcessToken(final View view) {
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
    }

    private void getAllItemList(View view) {
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
                                    String id = jsonObject.getString("id");
                                    itemList.add(new ItemDetail(itemId, name, description, price, status,franchisorflag, unit_id, entered_by, id,"NO"));
                                }
                                // cartList = response.getDetail();
                                if(itemList.size()>0){


                                itemlistAdapter = new ItemAdapter(itemList,getContext(),ViewItems.this);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(mLayoutManager);
                                recycleview.setItemAnimator(new DefaultItemAnimator());
                                recycleview.setAdapter(itemlistAdapter);
                                itemlistAdapter.notifyDataSetChanged();
                                }else{
                                    empty_notes_view.setVisibility(View.VISIBLE);
                                    recycleview.setVisibility(View.GONE);
                                }
                            } else {


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
    }
    public void deleteItems(int pos){
        itemList.remove(pos);
        Log.d("Tag",""+itemList.size());
       if( itemList.size()>0){

        itemlistAdapter = new ItemAdapter(itemList,getContext(), ViewItems.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(itemlistAdapter);
        itemlistAdapter.notifyDataSetChanged();
    }
    else{
           empty_notes_view.setVisibility(View.VISIBLE);
           recycleview.setVisibility(View.GONE);
       }
    }

}