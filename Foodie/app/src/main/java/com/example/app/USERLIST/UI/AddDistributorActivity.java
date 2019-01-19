package com.example.app.USERLIST.UI;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Util.Common.BaseActivity;
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

public class AddDistributorActivity extends BaseActivity {

    private TextInputLayout textInputLayoutCompanyname;
    private TextInputLayout textInputLayoutUnitHolderrname;
    private TextInputLayout textInputLayoutUnitAddress;
    private TextInputLayout textInputLayoutDistributorAddress;
    private TextInputLayout textInputLayoutMobileNumber;
    private TextInputLayout textInputLayoutEmailId;
    private TextInputLayout textInputLayoutUserlId;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutReEnterPassword;
    private NestedScrollView nestedScrollView;


    private TextInputEditText company_name;
    private TextInputEditText unitholder_name;
    private TextInputEditText unitaddress;
    private TextInputEditText distributoraddress;
    private TextInputEditText input_mobile;
    private TextInputEditText userid;
    private TextInputEditText emailid;
    private TextInputEditText input_password;
    private TextInputEditText input_reEnterPassword;

    private AppCompatButton btn_upload;
    private AppCompatButton btn_signup;
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token="";
    String distributorrole = "2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Add Distributor ");
        setSupportActionBar(toolbar);
        enableActionBar(true);


        sharedPreferenceClass = new SharedPreferenceClass(getApplicationContext());
        progressDialog = new ProgressDialog(getApplicationContext());
        company_name = findViewById(R.id.company_name);
        unitholder_name = findViewById(R.id.unitholder_name);
        unitaddress = findViewById(R.id.unitaddress);
        distributoraddress = findViewById(R.id.distributoraddress);
        input_mobile = findViewById(R.id.input_mobile);
        emailid =findViewById(R.id.emailid);
        userid = findViewById(R.id.userid);
        input_password = findViewById(R.id.input_password);
        input_reEnterPassword = findViewById(R.id.input_reEnterPassword);
        btn_signup = findViewById(R.id.btn_signup);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String cname = company_name.getText().toString().trim();
                final String unitname  = unitholder_name.getText().toString().trim();
                final String unitadress  = unitaddress.getText().toString().trim();
                final String distaddress = distributoraddress.getText().toString().trim();
                final String mob  = input_mobile.getText().toString().trim();
                final String eid  = emailid.getText().toString().trim();
                final String uid  = userid.getText().toString().trim();
                final String pwd  = input_password.getText().toString().trim();
                final String rwd  = input_reEnterPassword.getText().toString().trim();

                String MobilePattern = "[0-9]{10}";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(cname.length()==0){
                    company_name.requestFocus();
                    company_name.setError("Enter Company Name");

                }else if(unitname.length()==0){
                    unitholder_name.requestFocus();
                    unitholder_name.setError("Enter Unit Name");

                } else if(unitadress.length()==0){
                    unitaddress.requestFocus();
                    unitaddress.setError("Enter Unit Address");

                }
                else if(distaddress.length()==0){
                    distributoraddress.requestFocus();
                    distributoraddress.setError("Enter Distributor Address");
                }

                else if(!mob.matches(MobilePattern)){
                input_mobile.requestFocus();
                input_mobile.setError("Enter Valid Phone Number");

                }

                else if(eid.length()==0){
                    emailid.requestFocus();
                    emailid.setError("Enter EmailId");

                } else if(!eid.matches(emailPattern)){
                    emailid.requestFocus();
                    emailid.setError("Enter Valid EmailId");

                }
                else if(uid.length()==0){
                    userid.requestFocus();
                    userid.setError("Enter User Id");

                }
                else if(pwd.length()==0){
                    input_password.requestFocus();
                    input_password.setError("Enter Password");

                }
                else if(rwd.length()==0){
                    input_reEnterPassword.requestFocus();
                    input_reEnterPassword.setError("Confirm Password");

                }
                else if(!rwd.equals(pwd)){
                    input_reEnterPassword.requestFocus();
                    input_reEnterPassword.setError("Confirm Password Doesn't Match");

                }
                else {
                    new HTTPAsyncTaskGetDetail().execute(ServerLinks.ADD_DISTRIBUTOR + acess_token);
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

    public class HTTPAsyncTaskGetDetail extends AsyncTask<String, String, String> {
        final ProgressBar simpleProgressBar = (ProgressBar)findViewById(R.id.progressBarDil);
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
                    Toast.makeText(getApplicationContext(), "Distributor Added Sucessfully...", Toast.LENGTH_SHORT).show();
                    finish();

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
        JSONObject distributorRegd = new JSONObject();
        JSONObject distributor = new JSONObject();

        distributor.accumulate("company_name",  company_name.getText().toString().trim());
        distributor.accumulate("unit_holder_name",  unitholder_name.getText().toString().trim());
        distributor.accumulate("unit_address",  unitaddress.getText().toString().trim());
        distributor.accumulate("permanent_address",  distributoraddress.getText().toString().trim());
        distributor.accumulate("phone_no",  input_mobile.getText().toString().trim());
        distributor.accumulate("email_id",  emailid.getText().toString().trim());
        distributor.accumulate("status",  "Y");


        JSONObject user = new JSONObject();

        user.accumulate("user_id",userid.getText().toString().trim());
        user.accumulate("isActive","Y");
        user.accumulate("password",input_password.getText().toString());
        JSONObject roles = new JSONObject();
        roles.accumulate("id",distributorrole);

        //JsonArray jsonArray = new JsonArray();
        //jsonArray.accumulate("roles",distributorrole);
        //Role is missing in UI please addd in UI

       // JSONObject rolesObj = new JSONObject();
        JSONArray roleArray = new JSONArray();
        JSONObject role= new JSONObject();
        role.put("id", "2");
        roleArray.put(0,role);
        //rolesObj.put("roles", roleArray);

        user.accumulate("roles",roleArray);
        distributorRegd.accumulate("user",user);
        distributorRegd.accumulate("distributor",distributor);
        Log.e("PostJson",distributorRegd.toString());

        return distributorRegd;
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

