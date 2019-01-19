package com.example.app.MyOrders.OrderListById.UI;

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
import com.example.app.MyOrders.Common.RecyclerItemClickListener;
import com.example.app.MyOrders.OrderListById.Model.OrderItemDetail;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.OrderListById.Adapter.MyOrderAdapter;
import com.example.sukanta.foodie.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewOrderItems extends DilogueFRagment {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<OrderItem> itemDetail = null;
    ArrayList<OrderItemDetail> itemList = new ArrayList<OrderItemDetail>();
    ArrayList<OrderItemDetail> itemList1 = new ArrayList<OrderItemDetail>();
    private MyOrderAdapter itemlistAdapter;
    private MyOrderAdapter itemlistAdapter2;
    RecyclerView recycleview;

    FloatingActionButton fab;
    String userId;
    String role;
    EditText search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_item_order_list, container, false);

        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.item_list);
        search = (EditText) rootView.findViewById(R.id.searchlist);
        userId=  SharedPreferenceClass.readString(getActivity(), "USERID","");

        role=  SharedPreferenceClass.readString(getActivity(), "ROLEID","");
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
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
                if (itemList != null) {
                    filter(editable.toString());
                }

            }
        });

        fetchAcessToken(rootView);


        recycleview.addOnItemTouchListener(
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
        );



        return rootView;
    }
    void filter(String text) {
        ArrayList<OrderItemDetail> temp = new ArrayList();
        for (OrderItemDetail d : itemList) {
            if (d.getOrder_id().contains(text) ) {
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

        String orderUrl = "";
        //creating a string request to send request to the url
        if(role.equals("ROLE_ADMIN")){
            orderUrl = ServerLinks.allorders+acess_token;
        }else if(role.equals("ROLE_KML_EMP")){
            orderUrl = ServerLinks.allorders+acess_token;
        }else{
           //orderUrl = ServerLinks.allorders+acess_token;
           //orderUrl  = ServerLinks.ordersById+userId+"?access_token="+acess_token;
            Log.e("URLALLORDERBYID",orderUrl);
            orderUrl  = ServerLinks.ordersById+"?access_token="+acess_token+"&custId="+userId;

        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, orderUrl,
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
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String order_id = jsonObject.getString("order_id");
                                    Double total_price = jsonObject.getDouble("total_price");
                                    String cust_id = jsonObject.getString("orderby_custId");

                                    String orderdate = jsonObject.getString("orderDate");
                                    String status = jsonObject.getString("status");
                                    String st = "";
                                    if (status.equals("EXECUTED")) {
                                        st = "PENDING";
                                    }
                                    String uname = jsonObject.getString("userName");
                                    JSONArray jsonArray1 = jsonObject.getJSONArray("itemQOmEmbed");
                                    itemDetail = new ArrayList<OrderItem>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject data = jsonArray1.getJSONObject(j);
                                        String name = data.getString("name");
                                        String description = data.getString("description");
                                        Double price = data.getDouble("price");
                                        String item_count = data.getString("item_count");
                                        String statusorder = data.getString("status");

                                                itemList1.add(new OrderItemDetail(name, description, price, item_count));
                                                itemDetail.add(new OrderItem(name, description, price, item_count,statusorder));



                                    }
                             itemList.add(new OrderItemDetail(total_price, order_id, cust_id, orderdate, st, uname, itemList1,itemDetail));





                                }


                                itemlistAdapter = new MyOrderAdapter(itemList,getContext());
                                itemlistAdapter2 = new MyOrderAdapter(itemList1,getContext());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(mLayoutManager);
                                recycleview.setItemAnimator(new DefaultItemAnimator());
                                recycleview.setAdapter(itemlistAdapter);
                                itemlistAdapter.notifyDataSetChanged();

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