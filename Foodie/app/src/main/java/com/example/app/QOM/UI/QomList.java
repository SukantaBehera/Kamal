package com.example.app.QOM.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.app.ITEM.UI.AddItemsActivity;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.MyOrders.Common.RecyclerItemClickListener;
import com.example.app.QOM.Adapter.QomListAdapter;
import com.example.app.QOM.Model.QomModel;
import com.example.app.foodie.ServerLinks;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.sukanta.foodie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class QomList extends DilogueFRagment {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<QomModel> itemList = new ArrayList<QomModel>();
    private QomListAdapter itemlistAdapter;


    RecyclerView recycleview;
    FloatingActionButton fab;
    SwipeRefreshLayout pullToRefresh;
    View rootView = null;
    EditText search;

    String quantity;
    String price;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.qomlistfloating, container, false);

        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
        recycleview = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        pullToRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.pullToRefresh);
        search = (EditText) rootView.findViewById(R.id.searchlist);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_add_white_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(),"clicked fab icon",Toast.LENGTH_LONG).show();
               getActivity().startActivity(new Intent(getActivity(), AddItemsActivity.class));
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAcessToken(rootView);
            }
        });

        recycleview.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recycleview ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        final QomModel selectedObject = itemList.get(position);
                        Toast.makeText(getActivity(), selectedObject.getId()+"----Name"+selectedObject.getName(), Toast.LENGTH_SHORT).show();



                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());


                        LayoutInflater inflater = getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.custom_dialog_qom, null);

                        dialogBuilder.setView(dialogView);

                        final EditText quantityEdt = (EditText) dialogView.findViewById(R.id.quantity);
                        final EditText priceedt = (EditText) dialogView.findViewById(R.id.price);


                        dialogBuilder.setTitle("Enter Quantity");
                        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                if(quantityEdt.getText().toString().isEmpty()){
                                    quantity = "0";

                                }else{

                                    quantity = quantityEdt.getText().toString();
                                    price = priceedt.getText().toString();


                                   // new  UpdateQomAsyncTask().execute(ServerLinks.BASE_URL_NEW+"update_item_and_qom/+"+selectedObject.getId()+"?"+acess_token);


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

                    }
                })
        );




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
        ArrayList<QomModel> temp = new ArrayList();
        for (QomModel d : itemList) {
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.getqomlist + acess_token,
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
                                    String name = jsonObject.getString("name");
                                    String description= jsonObject.getString("description");
                                    double price = jsonObject.getDouble("price");
                                   // String status= jsonObject.getString("status");
                                    Integer unit_id= jsonObject.getInt("unit_id");
                                   // String entered_by =  jsonObject.getString("entered_by");
                                    String id = jsonObject.getString("entered_by");
                                   // String qom_status = jsonObject.getString("qom_status");
                                    Integer quantity_avail = jsonObject.getInt("quantity");
                                    itemList.add(new QomModel(name, description, price, "no status", unit_id, "1", id,"no qom status",quantity_avail));
                                }
                                // cartList = response.getDetail();
                                itemlistAdapter = new QomListAdapter(itemList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(mLayoutManager);
                                recycleview.setItemAnimator(new DefaultItemAnimator());
                                recycleview.setAdapter(itemlistAdapter);
                                itemlistAdapter.notifyDataSetChanged();

                            } else {

                            }


                        } catch (Exception e) {
                            Log.e("Exception",e.toString());
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

    public class UpdateQomAsyncTask extends AsyncTask<String, String, String> {
        final ProgressBar simpleProgressBar = (ProgressBar) getView().findViewById(R.id.progressBarDil);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            simpleProgressBar .setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    Log.e("url",urls[0]);
                    return HttpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //tvResult.setText(result);
            Log.e("response",result);
            JSONObject jsonObject = null;
            try {

                simpleProgressBar.setVisibility(View.INVISIBLE);
                jsonObject = new JSONObject(result);
                if(jsonObject.getString("status").equals("SUCCESS")){
                    Toast.makeText(getActivity(), "Item updated Sucessfully...", Toast.LENGTH_SHORT).show();

                }else{

                }


            } catch (Exception e) {
                e.printStackTrace();
                simpleProgressBar.setVisibility(View.INVISIBLE);
            }


            //

        }
    }


    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);
        Log.e("UrlCopyOrder", myUrl);
        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        Log.e("ResponseCode", conn.getResponseCode() + "");
        Log.e("ResponseMessage", conn.getResponseMessage() + "");
        Log.e("ResponseContent", conn.getContent() + "");
        Log.e("ResponseTimeOut", conn.getReadTimeout() + "");
        Log.e("ResponseEncoding", conn.getContentEncoding() + "");
        Log.e("ResponseEncoding", conn.getInputStream() + "");

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();

        //return conn.getResponseMessage()+"";
        //  return conn.getResponseMessage() + "";



    }
    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("price", Integer.parseInt(price));
        jsonObject.accumulate("quantity",  quantity);

        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(getActivity().toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

}