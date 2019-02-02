package com.example.app.QOM.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.app.ITEM.UI.AddItemsActivity;
import com.example.app.ITEM.UTIL.DilogueFRagment;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.RecyclerItemClickListener;
import com.example.app.Util.Common.WebApi;
import com.example.app.QOM.Adapter.QomListAdapter;
import com.example.app.QOM.Model.QomModel;
import com.example.app.Request.UpdateQomRequest;
import com.example.app.Response.GetAllQomReponse;
import com.example.app.Response.ResultQOM;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.UpdateQomResponse;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.sukanta.foodie.R;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QomList extends DilogueFRagment {
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";

    ArrayList<QomModel> itemList = new ArrayList<QomModel>();

    ArrayList<ResultQOM> itemLists = new ArrayList<ResultQOM>();

    private QomListAdapter itemlistAdapter;


    RecyclerView recycleview;
    FloatingActionButton fab;
    SwipeRefreshLayout pullToRefresh;
    View rootView = null;
    EditText search;

    String quantity;
    String price,id;
    private WebApi webApi;
    Retrofit retrofit;

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
        retrofit = ApiClient.getRetrofit();

        webApi = retrofit.create(WebApi.class);
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
                if(isNetworkAvailable()) {
                    fetchAcessToken(rootView);
                }else {
                    Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        recycleview.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recycleview ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                         QomModel selectedObject = itemList.get(position);
                         id = selectedObject.getId();
                     /*   Toast.makeText(getActivity(), selectedObject.getId()+"----Name"+selectedObject.getName(), Toast.LENGTH_SHORT).show();*/



                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());


                        LayoutInflater inflater = getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.custom_dialog_qom, null);

                        dialogBuilder.setView(dialogView);






                        dialogBuilder.setTitle("Enter Quantity");
                        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText quantityEdt = (EditText) dialogView.findViewById(R.id.quantity);
                                EditText priceedt = (EditText) dialogView.findViewById(R.id.price);
                                if(quantityEdt.getText().toString().isEmpty()){

                                    quantityEdt.requestFocus();
                                    quantityEdt.setError("Enter Quanity");


                                }else if (priceedt.getText().toString().isEmpty()){
                                    priceedt.requestFocus();
                                    priceedt.setError("Enter the Updated Price");
                                }

                                else{
                                    //final QomModel selectedObject = itemList.get(position);
                                    quantity = quantityEdt.getText().toString();
                                    price = priceedt.getText().toString();
                                 //   dialog.dismiss();
                                    updateQom();
                                 //   new  UpdateQomAsyncTask().execute(ServerLinks.BASE_URL_NEW+"update_item_and_qom/+"+selectedObject.getId()+"?"+acess_token);


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




        if(isNetworkAvailable()) {
            fetchAcessToken(rootView);
        }else {
            Toast.makeText(getContext(), "Please Check Network Connection", Toast.LENGTH_LONG).show();
        }




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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void  updateQom(){

        final ProgressBar pprogressBar = (ProgressBar)rootView.findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);

        UpdateQomRequest updateQomRequest = new UpdateQomRequest();

        updateQomRequest.setId(Long.valueOf(id));
        updateQomRequest.setPrice(Double.valueOf(price));
        updateQomRequest.setQuantity(Integer.valueOf(quantity));

        Call<UpdateQomResponse> responseCall=webApi.updateQom(acess_token,updateQomRequest);

        responseCall.enqueue(new Callback<UpdateQomResponse>() {
            @Override
            public void onResponse(Call<UpdateQomResponse> call, Response<UpdateQomResponse> response) {
                pprogressBar.setVisibility(View.INVISIBLE);
                String status=response.body().getStatus();
                if(status.equals("SUCCESS")){
                    Toast.makeText(getActivity(), "Item updated Sucessfully...", Toast.LENGTH_SHORT).show();
                    getAllItemList();
                }
                else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateQomResponse> call, Throwable t) {
                pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void fetchAcessToken(final View view) {
        //getting the progressbar


        Call<TokenResponse> call=webApi.accessToken("password","fbApp","fbApp","admin","123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
              //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token=response.body().getValue();
                if (acess_token != null) {
                    getAllItemList();
                } else {
                    Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
           //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });



      /*  //creating a string request to send request to the url
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
        requestQueue.add(stringRequest);*/
    }

    private void getAllItemList() {
        //getting the progressbar

        itemLists.clear();
        itemList.clear();
        final ProgressBar pprogressBar = (ProgressBar) rootView.findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);

        Call<GetAllQomReponse> call=webApi.getAllQom(acess_token);
        call.enqueue(new Callback<GetAllQomReponse>() {
            @Override
            public void onResponse(Call<GetAllQomReponse> call, Response<GetAllQomReponse> response) {
                pprogressBar.setVisibility(View.INVISIBLE);

                Log.d("Tag","value");
                String status=response.body().getStatus();
                if(status.equals("SUCCESS")){

                    itemLists=response.body().getResult();
                    for(int i=0;i<itemLists.size();i++){
                        int id=response.body().getResult().get(i).getId();
                        String name=response.body().getResult().get(i).getName();
                        String description=response.body().getResult().get(i).getDescription();
                        double price=response.body().getResult().get(i).getPrice();
                        String status1=response.body().getResult().get(i).getStatus();
                        int unit_id=response.body().getResult().get(i).getUnit_id();
                        int entered_by=response.body().getResult().get(i).getEntered_by();
                        int quantity=response.body().getResult().get(i).getQuantity();
                        String  qom_status=response.body().getResult().get(i).getQom_status();


                    QomModel qomModel=new QomModel(name,description,price,status1,unit_id,String.valueOf(entered_by),String.valueOf(id),qom_status,quantity);
                        itemList.add(qomModel);


                    }


                    if (!itemList.isEmpty()){
                        itemlistAdapter = new QomListAdapter(itemList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recycleview.setLayoutManager(mLayoutManager);
                        recycleview.setItemAnimator(new DefaultItemAnimator());
                        recycleview.setAdapter(itemlistAdapter);
                        itemlistAdapter.notifyDataSetChanged();
                    }
                }



            }

            @Override
            public void onFailure(Call<GetAllQomReponse> call, Throwable t) {
                pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });




       /* //creating a string request to send request to the url
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
        requestQueue.add(stringRequest);*/
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