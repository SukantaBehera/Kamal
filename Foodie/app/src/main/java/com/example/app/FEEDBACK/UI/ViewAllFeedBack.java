package com.example.app.FEEDBACK.UI;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.FEEDBACK.ADAPTER.FeedbackListAdapter;
import com.example.app.FEEDBACK.MODEL.FeedBackDetail;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.sukanta.foodie.R;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAllFeedBack extends DilogueFRagment {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<FeedBackDetail> feedBAckList = new ArrayList<FeedBackDetail>();
    private FeedbackListAdapter feedBAckListAdapter;


    RecyclerView recycleview;
    FloatingActionButton fab;
    SwipeRefreshLayout pullToRefresh;
    View rootView = null;

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
       /* fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(),"clicked fab icon",Toast.LENGTH_LONG).show();
             //  getContext().startActivity(new Intent(getActivity(), Feedback.class));
            }
        });*/

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
                if (feedBAckList != null) {
                    filter(editable.toString());
                }

            }
        });

        return rootView;
    }

    void filter(String text) {
        ArrayList<FeedBackDetail> temp = new ArrayList();
        for (FeedBackDetail d : feedBAckList) {
            if (d.getItemName().toLowerCase().contains(text.toLowerCase()) ) {
                temp.add(d);
            }
        }
        feedBAckListAdapter.updateList(temp);
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.getAllIFeedBack + acess_token,
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
                                Toast.makeText(getActivity(), "Total Items present = " + jsonArray.length(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    JSONObject jsonObject = jsonObject1.getJSONObject("userFeedback");




                                    Integer id = jsonObject.getInt("id");
                                    Integer added_by = jsonObject.getInt("added_by");
                                    String name = jsonObject.getString("item_name");
                                    String feedback = jsonObject.getString("feedback");

                                    feedBAckList.add(new FeedBackDetail(name,feedback,id,added_by));
                                }
                                // cartList = response.getDetail();
                                feedBAckListAdapter = new FeedbackListAdapter(feedBAckList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(mLayoutManager);
                                recycleview.setItemAnimator(new DefaultItemAnimator());
                                recycleview.setAdapter(feedBAckListAdapter);
                                feedBAckListAdapter.notifyDataSetChanged();

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


}