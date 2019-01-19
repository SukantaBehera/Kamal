package com.example.app.MyOrders.AllItem.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.MyOrders.AllItem.adapter.OrderItemListAdapter;
import com.example.app.MyOrders.AllItem.datamodels.OrderPlacedResponse;
import com.example.app.MyOrders.AllItem.mvp.ItemsPresenterImpl;
import com.example.app.MyOrders.AllItem.mvp.ItemsView;
import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.MyOrders.AllItem.datamodels.OrderDetails;
import com.example.app.MyOrders.AllItem.datamodels.OrderItem;
import com.example.app.MyOrders.AllItem.datamodels.PaymentDetails;
import com.example.app.Util.Common.RecyclerItemClickListener;
import com.example.sukanta.foodie.R;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ViewItems extends DilogueFRagment implements ItemsView {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<ItemDetail> itemList = new ArrayList<ItemDetail>();
    ArrayList<ItemDetail> itemListcart = new ArrayList<ItemDetail>();
    ArrayList<OrderItem> cartlist = new ArrayList<OrderItem>();
    private OrderItemListAdapter itemlistAdapter;
    RecyclerView recycleview;

    FloatingActionButton fab;
    TextView cartCount;
    String userId;
    Double totalAmount = 0.0;


    ItemsPresenterImpl itemsPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_item_list, container, false);

        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.item_list);
        cartCount = (TextView) rootView.findViewById(R.id.cartCount);
        LinearLayout cartLayout = (LinearLayout) rootView.findViewById(R.id.cartLayout);
        cartLayout.setVisibility(View.VISIBLE);
        cartCount.setText("Total Count: 0  Total Price : 0.00 ");

        itemsPresenter = new ItemsPresenterImpl(this);

        userId=  SharedPreferenceClass.readString(getActivity(), "USERID","");

        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cartlist.size() > 0){
                    OrderDetails orderDetail = new OrderDetails(Integer.parseInt(userId),"EXECUTED","2018-12-2");
                    PaymentDetails paymentDetails = new PaymentDetails(1,5,totalAmount,"SUCCESS","2019-12-2");

                    Intent in1 = new Intent(getActivity(),CartItem.class);
                    in1.putExtra("itemArray",cartlist);
                    in1.putExtra("paymentdetail",paymentDetails);
                    in1.putExtra("orderDetail",orderDetail);
                    in1.putExtra("totalPrice",totalAmount+"");
                    in1.putExtra("acesstoken",acess_token);
                    startActivity(in1);
                    //itemsPresenter.createOrder(orderDetail,paymentDetails,cartlist);
                }else {
                    Toast.makeText(getActivity(), "Please add atleast one item to cart...", Toast.LENGTH_SHORT).show();

                }



            }
        });


        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"clicked fab icon",Toast.LENGTH_LONG).show();
               // getActivity().startActivity(new Intent(getActivity(),AddItems.class));
            }
        });


        recycleview.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recycleview ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        final ItemDetail selectedObject = itemList.get(position);
                        Toast.makeText(getActivity(), selectedObject.getId()+"----Name"+selectedObject.getName(), Toast.LENGTH_SHORT).show();



                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());


                        LayoutInflater inflater = getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);

                        dialogBuilder.setView(dialogView);

                        final EditText quantityEdt = (EditText) dialogView.findViewById(R.id.quantity);


                        dialogBuilder.setTitle("Enter Quantity");
                        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                String quantity  = quantityEdt.getText().toString();
                                if(quantityEdt.getText().toString().isEmpty()){
                                    quantity = "0";

                                }else if(quantityEdt.getText().toString().equals("0")){

                                    Toast.makeText(getActivity(), "Give value more than 0", Toast.LENGTH_SHORT).show();
                                }else{

                                    double totalPrice = 0.0;
                                    double itemPrice = Double.parseDouble(selectedObject.getPrice());
                                    totalPrice  = Integer.parseInt(quantity) * itemPrice;

                                    OrderItem newItemAdded  =   new OrderItem(Integer.parseInt(selectedObject.getItemId()),Integer.parseInt(quantityEdt.getText().toString()),totalPrice,"2018-8-5","Active",selectedObject.getName());



                                    boolean flag = true;
                                    int k = 0;

                                    if(cartlist.size()> 0){
                                        for(int i = 0; i<cartlist.size();i++){
                                            OrderItem pocketDetailone = cartlist.get(i);
                                            if(pocketDetailone.getItem_id().equals(newItemAdded.getItem_id())){
                                                flag = false;
                                                k = i;
                                            }
                                        }

                                        if(flag == false){
                                            cartlist.set(k,newItemAdded);

                                        }else{
                                            cartlist.add(newItemAdded);
                                        }
                                    }else{
                                        cartlist.add(newItemAdded);
                                    }




                                    double sumprice = 0.0;
                                    for(int i = 0; i<cartlist.size(); i++){
                                        OrderItem pocketDetailOne = cartlist.get(i);
                                        sumprice = sumprice + pocketDetailOne.getTotal_price();

                                    }
                                    cartCount.setText("Total Items added ="+cartlist.size()+"   Total Price="+new DecimalFormat("#.##").format(sumprice));

                                    totalAmount = sumprice;

                                    selectedObject.setBuyStatus(quantity);
                                    itemlistAdapter = new OrderItemListAdapter(itemList);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recycleview.setLayoutManager(mLayoutManager);
                                    recycleview.setItemAnimator(new DefaultItemAnimator());
                                    recycleview.setAdapter(itemlistAdapter);
                                    itemlistAdapter.notifyDataSetChanged();



                                }


                                //cartList.remove(selectedObject);
                            }
                        });
                        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                //pass
                            }
                        });
                        AlertDialog b = dialogBuilder.create();
                        b.show();



                    }

                    @Override public void onLongItemClick(View view, int position) {
                        final ItemDetail selectedObject = itemList.get(position);
                        String status = selectedObject.getBuyStatus();
                        boolean flag = true;
                        OrderItem pocketDetailone = null;

                        if(status.equals("NOT")){

                        }else{
                            if(cartlist.size()> 0){
                                for(int i = 0; i<cartlist.size();i++){
                                     pocketDetailone = cartlist.get(i);
                                    if(pocketDetailone.getItem_id().equals(selectedObject.getItemId())){
                                        flag = false;
                                    }
                                }

                                if(flag == false){
                                    cartlist.remove(pocketDetailone);

                                }else{

                                }
                            }
                            double sumprice = 0.0;
                            for(int i = 0; i<cartlist.size(); i++){
                                OrderItem pocketDetailOne = cartlist.get(i);
                                sumprice = sumprice + pocketDetailOne.getTotal_price();

                            }
                            cartCount.setText("Total Items added ="+cartlist.size()+"   Total Price="+new DecimalFormat("#.##").format(sumprice));

                            totalAmount = sumprice;


                        }
                    }
                })
        );

        fetchAcessToken(rootView);

        return rootView;
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
                                Toast.makeText(getActivity(), "Total Items present = " + jsonArray.length(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                                    String itemId = jsonObject.getString("item_id");
                                    String name = jsonObject.getString("name");
                                    String description = jsonObject.getString("description");
                                    String price = jsonObject.getString("price");
                                    String status = jsonObject.getString("status");
                                    String unit_id = jsonObject.getString("unit_id");
                                    String entered_by = jsonObject.getString("entered_by");
                                    String id = jsonObject.getString("id");
                                    itemList.add(new ItemDetail(itemId, name, description, price, status, unit_id, entered_by, id,"NOT"));
                                }
                                // cartList = response.getDetail();
                                itemlistAdapter = new OrderItemListAdapter(itemList);
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


    @Override
    public void onValidationFail(int type) {

    }

    @Override
    public void onLoad(OrderPlacedResponse response) {

        if(response.getStatus().equals("SUCCESS")){
            Toast.makeText(getActivity(), "Order Placed Sucessfully...", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showError(String message) {

    }
}