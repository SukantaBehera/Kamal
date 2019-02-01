package com.example.app.USERLIST.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.USERLIST.ADAPTER.DistributorListAdapter;
import com.example.app.USERLIST.MODEL.Distributordetail;
import com.example.app.Util.RegPrefManager;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.sukanta.foodie.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewDistributor extends DilogueFRagment {

    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token="";
    RecyclerView recycleview;
    FloatingActionButton fab;
    ArrayList<Distributordetail> distributorlist = new ArrayList<Distributordetail>();
    ArrayList<Distributordetail> distributorlist1 = new ArrayList<Distributordetail>();
    private DistributorListAdapter distributorListAdapter;
    Context context;
    EditText search;
    TextView empty_notes_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_distributor_list, container, false);
        context= getContext();
        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        empty_notes_view=rootView.findViewById(R.id.empty_notes_view);
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getActivity(),"clicked fab icon",Toast.LENGTH_LONG).show();
                getActivity().startActivity(new Intent(getActivity(), AddDistributorActivity.class));
            }
        });
        fetchAcessToken(rootView);
        search = (EditText)rootView.findViewById(R.id.searchlist);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (distributorlist != null) {
                    filter(editable.toString());
                }

            }
        });

        return rootView;
    }

    void filter(String text) {
        ArrayList<Distributordetail> temp = new ArrayList();
        for (Distributordetail d : distributorlist) {
            if (d.getCompany_name().toLowerCase().contains(text.toLowerCase()) ) {
                temp.add(d);
            }
        }
        distributorListAdapter.updateList(temp);
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
                        Log.e("Response",response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            acess_token = obj.getString("value");
                            if(acess_token != null){
                                getAllDistributorList(view);
                            }else{
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


    private void getAllDistributorList(View view) {
        //getting the progressbar


        final ProgressBar pprogressBar = (ProgressBar) view.findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.GET_ALLDISTRIBUTOR + acess_token,
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
                                //Toast.makeText(getActivity(), "Total Distributor's present = " + jsonArray.length(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String dist_id = jsonObject.getString("dist_id");
                                    String company_name = jsonObject.getString("company_name");
                                    String unit_holder_name = jsonObject.getString("unit_holder_name");
                                    String unit_address = jsonObject.getString("unit_address");
                                    String permanent_address = jsonObject.getString("permanent_address");
                                    String phone_no = jsonObject.getString("phone_no");
                                    String email_id = jsonObject.getString("email_id");
                                    String status = jsonObject.getString("is_active");
                                    String id = jsonObject.getString("user_id");
                                    distributorlist.add(new Distributordetail(dist_id, company_name, unit_holder_name,unit_address,permanent_address,phone_no, email_id,status, id));


                                }


                                if(distributorlist.size()>0) {
                                    for (int i = 0; i < distributorlist.size(); i++) {
                                        String status1 = distributorlist.get(i).getStatus();
                                        String id1 = distributorlist.get(i).getDist_id();
                                        String loginid = RegPrefManager.getInstance(getContext()).getLoginUserID();
                                        Distributordetail distributordetail1;
                                        if (status1.equals("N")) {
                                            Log.d("Tag", "Go");
                                        } else {



                                                distributordetail1 = new Distributordetail(distributorlist.get(i).getDist_id(), distributorlist.get(i).getCompany_name(), distributorlist.get(i).getUnit_holder_name(), distributorlist.get(i).getUnit_address(), distributorlist.get(i).getPermanent_address(),
                                                        distributorlist.get(i).getPhone_no(), distributorlist.get(i).getEmail_id(), distributorlist.get(i).getStatus(), distributorlist.get(i).getUser_id());

                                                distributorlist1.add(distributordetail1);
                                                // employeelist.clear();



                                        }
                                    }
                                }


                                // cartList = response.getDetail();
                                if(distributorlist1.size()>0) {
                                    distributorListAdapter = new DistributorListAdapter(context, distributorlist1, ViewDistributor.this);
                                    distributorlist.clear();
                                    distributorlist = distributorlist1;
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recycleview.setLayoutManager(mLayoutManager);
                                    recycleview.setItemAnimator(new DefaultItemAnimator());
                                    recycleview.setAdapter(distributorListAdapter);
                                    distributorListAdapter.notifyDataSetChanged();
                                }else {
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

    public void deleteItem(int pos){
        distributorlist.remove(pos);
        distributorListAdapter = new DistributorListAdapter(context,distributorlist, ViewDistributor.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(distributorListAdapter);
        distributorListAdapter.notifyDataSetChanged();
    }
}

