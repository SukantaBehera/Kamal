package com.example.app.ITEM.UI;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.ITEM.ADAPTER.ItemListAdapter;
import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.app.ITEM.UTIL.DilogueFRagment;
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
import java.util.HashMap;
import java.util.Map;

public class AddItems extends DilogueFRagment {
    EditText additem, description, quantityavailable ,itemprice;
    Button add;
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token="";

     ArrayList<ItemDetail> itemList = new ArrayList<ItemDetail>();
    private ItemListAdapter itemlistAdapter;
     RecyclerView recycleview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_add_items, container, false);
        additem = rootView.findViewById(R.id.additems);
        description = rootView.findViewById(R.id.desc);
        quantityavailable = rootView.findViewById(R.id.quanityno);
        itemprice = rootView.findViewById(R.id.item_price);
        add = rootView.findViewById(R.id.add);
        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        progressDialog = new ProgressDialog(getContext());
          recycleview = (RecyclerView) rootView.findViewById(R.id.item_list);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (additem.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Item name", Toast.LENGTH_SHORT).show();
                } else if (description.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Description", Toast.LENGTH_SHORT).show();
                } else if (quantityavailable.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Quantity", Toast.LENGTH_SHORT).show();
                } else if (itemprice.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Per Price", Toast.LENGTH_SHORT).show();
                } else {
                     // additem();


                    new HTTPAsyncTaskGetDetail().execute(ServerLinks.ADDITEMS_NEW+acess_token);
                }
            }
        });

/*
        Spinner spinner = rootView.findViewById(R.id.additems);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Sandwitch");
        categories.add("Burger");
        categories.add("Paties");
        categories.add("Masroom Roll");
        categories.add("Samosa");
        categories.add("Momos");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>( getContext(),android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter)*/;

        fetchAcessToken(rootView);

        return rootView;
    }

/*
 private  void additem(){
     progressDialog.show();
     progressDialog.setMessage("Loading...");
     progressDialog.setCancelable(false);
     StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.ADDITEMS, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
             progressDialog.dismiss();
             try {
                 Log.d("AddItem response is ", response);

                 JSONObject jsonObject = new JSONObject(response);
                 if (jsonObject.getString("status").equals("SUCCESS") ) {

                     */
/* String regId = jsonObject.getString("regId");
                        sharedPreferenceClass.setValue_string("user_id",regId);*//*

                        Toast.makeText(getActivity(), "Data Added Successfully", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_SHORT).show();
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }

         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
             Toast.makeText(getActivity(), "Adding Item Failed", Toast.LENGTH_SHORT).show();
         }
     }){
         @Override
         protected Map<String, String> getParams() throws AuthFailureError {
             Map<String, String> params = new HashMap<>();
             params.put("name", additem.getText().toString());
             params.put("description", description.getText().toString());
             params.put("price", itemprice.getText().toString());
             params.put("qom", quantityavailable.getText().toString());
             return params;
         }
     };
     RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
     requestQueue.add(stringRequest);
}
*/

    private void additem () {
        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.ADDITEMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    Log.d("register response is ", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("SUCCESS")) {

                        Toast.makeText(getActivity(), "Items Added Successfully", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                        additem();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("name", additem.getText().toString());
                params.put("description", description.getText().toString());
                params.put("price", itemprice.getText().toString());
                params.put("quantity", quantityavailable.getText().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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
                                //getAllItemList(view);
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

  /*  private void getAllItemList(View view) {
        //getting the progressbar


        final ProgressBar pprogressBar = (ProgressBar) view.findViewById(R.id.progressBarDil);
        //making the progressbar visible
        pprogressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerLinks.getAllItems+acess_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        pprogressBar.setVisibility(View.INVISIBLE);
                        Log.e("Response",response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("status").equals("SUCCESS")){

                                JSONArray jsonArray = obj.getJSONArray("result");
                               // Toast.makeText(getActivity(), "SizeArrary"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                                for(int i = 0; i <jsonArray.length();i++){
                                    JSONObject jsonObject  = jsonArray.getJSONObject(i);

                                    String itemId = jsonObject.getString("item_id");
                                    String name= jsonObject.getString("name");
                                    String description= jsonObject.getString("description");
                                    String price= jsonObject.getString("price");
                                    String status= jsonObject.getString("status");
                                    String franchisorflag =jsonObject.getString("Y");
                                    String unit_id= jsonObject.getString("unit_id");
                                    String entered_by= jsonObject.getString("entered_by");
                                    String id= jsonObject.getString("id");
                                    itemList.add(new ItemDetail(itemId,name,description,price,status,franchisorflag,unit_id,entered_by,id,"NO"));



                                }





                               // cartList = response.getDetail();
                                itemlistAdapter = new ItemListAdapter(itemList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(mLayoutManager);
                                recycleview.setItemAnimator(new DefaultItemAnimator());
                                recycleview.setAdapter(itemlistAdapter);
                                itemlistAdapter.notifyDataSetChanged();

                            }else{

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
*/





    public class HTTPAsyncTaskGetDetail extends AsyncTask<String, String, String> {
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
                    Toast.makeText(getActivity(), "Item Added Sucessfully...", Toast.LENGTH_SHORT).show();
                    additem.setText("");
                    description.setText("");
                    quantityavailable.setText("");
                    itemprice.setText("");
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

        jsonObject.accumulate("name",  additem.getText().toString().trim());
        jsonObject.accumulate("description",  description.getText().toString().trim());
        jsonObject.accumulate("price",  itemprice.getText().toString().trim());
        jsonObject.accumulate("status",  "Y");
        jsonObject.accumulate("unit_id",  1);
        jsonObject.accumulate("entered_by",  1);
        jsonObject.accumulate("quantity",  Integer.parseInt(quantityavailable.getText().toString().trim()));
        jsonObject.accumulate("qom_status",  "active");

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

