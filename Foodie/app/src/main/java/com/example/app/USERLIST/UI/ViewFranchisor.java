package com.example.app.USERLIST.UI;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.USERLIST.ADAPTER.FranchisorListAdapter;
import com.example.app.USERLIST.MODEL.Distributordetail;
import com.example.app.USERLIST.MODEL.Franchisordetail;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.sukanta.foodie.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewFranchisor extends DilogueFRagment {

    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token="";
    RecyclerView recycleview;
    ArrayList<Franchisordetail> franchisorlist = new ArrayList<Franchisordetail>();
    ArrayList<Franchisordetail> franchisorlist1 = new ArrayList<Franchisordetail>();
    private FranchisorListAdapter franchisorListAdapter;
    FloatingActionButton fab;
    EditText search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_franchisor_list_activiry, container, false);

        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getActivity(),"clicked fab icon",Toast.LENGTH_LONG).show();
                getActivity().startActivity(new Intent(getActivity(), AddFranchisorActivity.class));
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
                if (franchisorlist != null) {
                    filter(editable.toString());
                }

            }
        });

        return rootView;
    }

    void filter(String text) {
        ArrayList<Franchisordetail> temp = new ArrayList();
        for (Franchisordetail d : franchisorlist) {
            if (d.getCompany_name().toLowerCase().contains(text.toLowerCase()) ) {
                temp.add(d);
            }
        }
        franchisorListAdapter.updateList(temp);
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
                                getAllFranchisorList(view);
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


    private void getAllFranchisorList(View view) {
        //getting the progressbar


        final ProgressBar pprogressBar = (ProgressBar) view.findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.GET_ALLFRANCHISOR + acess_token,
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
                              //  Toast.makeText(getActivity(), "Total Franchisor's present = " + jsonArray.length(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String fran_id = jsonObject.getString("fran_id");
                                    String company_name = jsonObject.getString("company_name");
                                    String phone_no = jsonObject.getString("phone_no");
                                    String email_id = jsonObject.getString("email_id");
                                    String unit_address = jsonObject.getString("unit_address");
                                    if(unit_address.equals("null"))
                                    {
                                        unit_address = "her";
                                    }
                                    String resident_address = jsonObject.getString("resident_address");
                                    if(resident_address.equals("null"))
                                    {
                                        resident_address = "her";
                                    }
                                    String is_active = jsonObject.getString("is_active");
                                    if(is_active.equals("Y"))
                                    {
                                        is_active = "Active";
                                    }
                                    else {
                                        is_active = "In Active";
                                    }

                                    String id = jsonObject.getString("user_id");
                                    if(id.equals(""))
                                    {
                                        id = "null";
                                    }
                                    franchisorlist.add(new Franchisordetail(fran_id, company_name, phone_no, email_id, unit_address, resident_address, is_active, id));


                                }

                                if(franchisorlist.size()>0) {
                                    for (int i = 0; i < franchisorlist.size(); i++) {
                                        String status1 = franchisorlist.get(i).getIs_active();
                                       // String id1 = franchisorlist.get(i).getDist_id();
                                     //   String loginid = RegPrefManager.getInstance(getContext()).getLoginUserID();
                                        Distributordetail distributordetail1;
                                        if (status1.equals("In Active")) {
                                            Log.d("Tag", "Go");
                                        } else {

                                       Franchisordetail f=     new Franchisordetail(franchisorlist.get(i).getFran_id(),franchisorlist.get(i).getCompany_name(),franchisorlist.get(i).getPhone_no(),franchisorlist.get(i).getEmail_id(), franchisorlist.get(i).getUnit_address(),franchisorlist.get(i).getResident_address(),franchisorlist.get(i).getIs_active(), franchisorlist.get(i).getUser_id());
                                            franchisorlist1.add( f  );


                                            // employeelist.clear();



                                        }
                                    }
                                }

                                // cartList = response.getDetail();
                                franchisorListAdapter = new FranchisorListAdapter(franchisorlist1,getContext(), ViewFranchisor.this);
                                franchisorlist.clear();
                                franchisorlist=franchisorlist1;
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(mLayoutManager);
                                recycleview.setItemAnimator(new DefaultItemAnimator());
                                recycleview.setAdapter(franchisorListAdapter);
                                franchisorListAdapter.notifyDataSetChanged();

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
        franchisorlist.remove(pos);
        franchisorListAdapter = new FranchisorListAdapter(franchisorlist,getContext(), ViewFranchisor.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(franchisorListAdapter);
        franchisorListAdapter.notifyDataSetChanged();
    }
}

