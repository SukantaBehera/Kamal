package com.example.app.ITEM.UI;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.ITEM.ADAPTER.ItemListAdapter;
import com.example.app.ITEM.MODEL.ItemDetail;
import com.example.app.MyOrders.Common.BaseActivity;
import com.example.app.foodie.InputValidation;
import com.example.app.foodie.ServerLinks;
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
import java.util.List;

public class AddItemsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    EditText additem, description, quantityavailable ,itemprice;
    Button add;
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    Spinner unit_mearurement;
    int i;
    String acess_token="";
    String Qom;
    TextView tverror;
     ArrayList<ItemDetail> itemList = new ArrayList<ItemDetail>();
     private ItemListAdapter itemlistAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Item");
        setSupportActionBar(toolbar);
        enableActionBar(true);

        String test = "Unit Type";
        additem = findViewById(R.id.additems);
        description = findViewById(R.id.desc);
        //quantityavailable = findViewById(R.id.quanityno);
        itemprice = findViewById(R.id.item_price);
        unit_mearurement = findViewById(R.id.uomeasurement);
        add = findViewById(R.id.add);
        sharedPreferenceClass = new SharedPreferenceClass(getApplicationContext());
        progressDialog = new ProgressDialog(getApplicationContext());
        unit_mearurement.setOnItemSelectedListener(this);
        tverror = findViewById(R.id.tvInvisibleError);
        List<String> unitmeasurent = new ArrayList<String>();
        unitmeasurent.add("Unit Type");
        unitmeasurent.add("Packet");
        unitmeasurent.add("Cartoon");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitmeasurent);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_mearurement.setAdapter(dataAdapter);
        if(test!=null){
            int spinnerposition = dataAdapter.getPosition(test);
            unit_mearurement.setSelection(spinnerposition);
        }
boolean check = true;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String itemname = additem.getText().toString().trim();
                final String desc  = description.getText().toString().trim();
                final String uom  = unit_mearurement.toString().trim();
                final String Itemprice = itemprice.getText().toString();

                if(itemname.length()==0) {
                    additem.requestFocus();
                    additem.setError("Enter ItemName");
                }
               else if(!InputValidation.isEdittextName(additem)){
                    additem.requestFocus();
                    additem.setError("Enter Valid ItemName");
                }
                else if(! InputValidation.isEdittextName(description)){
                    description.requestFocus();
                    description.setError("Enter Description");
                }
                else if(!InputValidation.isSpinnerSelected(unit_mearurement)){
                    tverror.requestFocus();
                    tverror.setError("Select Unit Type");
                } else if(Itemprice.length()==0){
                    itemprice.requestFocus();
                    itemprice.setError("Enter Unit price");

                }

                    else {
                          new HTTPAsyncTaskGetDetail().execute(ServerLinks.ADDITEMS_NEW+acess_token);

                }
            }
        });



        fetchAcessToken();

    }




    private void fetchAcessToken() {
        //getting the progressbar


        final ProgressBar pprogressBar = (ProgressBar)findViewById(R.id.progressBarDil);
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
                                Toast.makeText(getApplicationContext(), "Invalid Token", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Qom = parent.getItemAtPosition(position).toString();
        String spinneritem = unit_mearurement.getSelectedItem().toString();
        if(position>0){

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(getApplicationContext(), "Select Unit Type", Toast.LENGTH_SHORT).show();
    }


    public class HTTPAsyncTaskGetDetail extends AsyncTask<String, String, String> {
        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.progressBarDil);
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
                    Toast.makeText(getApplicationContext(), "Item Added Sucessfully...", Toast.LENGTH_SHORT).show();
                    finish();
                    /*additem.setText("");
                    description.setText("");
                    quantityavailable.setText("");
                    itemprice.setText("");*/

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
        if(Qom =="Packet"){
           int i = 1;
            jsonObject.accumulate("unit_id",  i);
        }else if (Qom =="Cartoon"){
            int i = 2;
            jsonObject.accumulate("unit_id",  i);
        }

        jsonObject.accumulate("entered_by",  1);
        jsonObject.accumulate("quantity",  1);
        jsonObject.accumulate("qom_status",  "active");

        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(getApplicationContext().toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }



}

