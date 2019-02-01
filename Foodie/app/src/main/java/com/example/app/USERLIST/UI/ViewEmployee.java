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
import com.example.app.USERLIST.ADAPTER.EmployeeListAdapter;
import com.example.app.USERLIST.MODEL.Employeedetail;
import com.example.app.Util.RegPrefManager;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.sukanta.foodie.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewEmployee extends DilogueFRagment {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token="";
    RecyclerView recycleview;
    FloatingActionButton fab;
    ArrayList<Employeedetail> employeelist = new ArrayList<Employeedetail>();
    ArrayList<Employeedetail> employeelist1= new ArrayList<Employeedetail>();
    private EmployeeListAdapter employeeListAdapter;
    private TextView empty_notes_view;
    Context context;
    EditText search;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_employee_list, container, false);
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
                getActivity().startActivity(new Intent(getActivity(),AddEmplyoeeActivity.class));
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
                if (employeelist != null) {
                    filter(editable.toString());
                }

            }
        });

        return rootView;
    }

    void filter(String text) {
        ArrayList<Employeedetail> temp = new ArrayList();
        for (Employeedetail d : employeelist) {
            if (d.getName().toLowerCase().contains(text.toLowerCase()) ) {
                temp.add(d);
            }
        }
        employeeListAdapter.updateList(temp);
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.GET_ALLEMPLYOEE + acess_token,
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
                                String emp_id="",first_name="",last_name="",name="",designation="",address="",
                                        adhharcardno="",phone_no="",email_id="",status="",id="";
                              //  Toast.makeText(getActivity(), "Total Employee's present = " + jsonArray.length(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                     emp_id = jsonObject.getString("emp_id");
                                     first_name = jsonObject.getString("first_name");
                                     last_name = jsonObject.getString("last_name");
                                     name = first_name +" "+last_name ;
                                     designation = jsonObject.getString("designation");
                                     address = jsonObject.getString("address");
                                     adhharcardno = jsonObject.getString("aadhar_card_no");
                                     phone_no = jsonObject.getString("phone_no");
                                     email_id = jsonObject.getString("email_id");
                                     status = jsonObject.getString("is_active");
                                     id = jsonObject.getString("user_id");
                                    employeelist.add(new Employeedetail(emp_id,first_name,last_name,name,designation,address,adhharcardno,phone_no, email_id,status, id));


                                }

                                if(employeelist.size()>0) {
                                    for (int i = 0; i < employeelist.size(); i++) {
                                        String status1= employeelist.get(i).getStatus();
                                        String id1=employeelist.get(i).getEmp_id();
                                        String loginid= RegPrefManager.getInstance(getContext()).getLoginUserID();
                                        Employeedetail employeedetail1;
                                        if (status1.equals("N")) {
                                            Log.d("Tag","Go");
                                        } else {


                                                if(id1.equals("1")) {
                                                    /*empty_notes_view.setVisibility(View.VISIBLE);
                                                    recycleview.setVisibility(View.GONE);*/
                                                }
                                                else{
                                                    employeedetail1 = new Employeedetail(employeelist.get(i).getEmp_id(),employeelist.get(i).getFitstname(),employeelist.get(i).getLastname(), employeelist.get(i).getName(), employeelist.get(i).getDesignation(),
                                                            employeelist.get(i).getAddress(), employeelist.get(i).getAdhharcardno(), employeelist.get(i).getPhone_no(), employeelist.get(i).getEmail_id(),
                                                            employeelist.get(i).getStatus(), employeelist.get(i).getUser_id());
                                                            employeelist1.add(employeedetail1);
                                                             // employeelist.clear();
                                                }



                                        }
                                    }
                               //     employeelist.clear();
                                 //   employeelist.add(employeedetail1);
                                    employeeListAdapter = new EmployeeListAdapter(employeelist1, context, ViewEmployee.this);
                                    employeelist.clear();
                                    employeelist=employeelist1;
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recycleview.setLayoutManager(mLayoutManager);
                                    recycleview.setItemAnimator(new DefaultItemAnimator());
                                    recycleview.setAdapter(employeeListAdapter);
                                    employeeListAdapter.notifyDataSetChanged();
                                }
                                // cartList = response.getDetail();


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
        employeelist.remove(pos);
        employeeListAdapter = new EmployeeListAdapter(employeelist,context,ViewEmployee.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(employeeListAdapter);
        employeeListAdapter.notifyDataSetChanged();
    }
}

