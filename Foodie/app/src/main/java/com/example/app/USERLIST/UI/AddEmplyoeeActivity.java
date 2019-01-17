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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.app.MyOrders.Common.BaseActivity;
import com.example.app.foodie.InputValidation;
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
import java.util.List;

public class AddEmplyoeeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout textInputLayoutFirstname;
    private TextInputLayout textInputLayoutLastrname;
    private TextInputLayout textInputLayoutDesignation;
    private TextInputLayout textInputLayoutMobileNumber;
    private TextInputLayout textInputLayoutPromoterAddress;
    private TextInputLayout textInputLayoutEmailId;
    private TextInputLayout textInputLayoutUserlId;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutReEnterPassword;
    private NestedScrollView nestedScrollView;


    private TextInputEditText first_name;
    private TextInputEditText last_name;
   // private TextInputEditText designation;
    private TextInputEditText input_mobile;
    private TextInputEditText employeeraddress;
    private TextInputEditText emailid;
    private TextInputEditText userid;
    private TextInputEditText input_password;
    private TextInputEditText input_reEnterPassword;
    Spinner desn;
    private AppCompatButton empregd;
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    int i;
    String acess_token = "";
    String employee_role = "4";
    String item;
    TextView tverror;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Add Employee ");
        setSupportActionBar(toolbar);
        enableActionBar(true);

        sharedPreferenceClass = new SharedPreferenceClass(getApplicationContext());
        progressDialog = new ProgressDialog(getApplicationContext());
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        tverror = findViewById(R.id.tvInvisibleError);
        input_mobile = findViewById(R.id.input_mobile);
        employeeraddress = findViewById(R.id.employeeraddress);
        emailid = findViewById(R.id.emailid);
        userid = findViewById(R.id.userid);
        input_password = findViewById(R.id.input_password);
        input_reEnterPassword = findViewById(R.id.input_reEnterPassword);
        desn = (Spinner) findViewById(R.id.designation);
        int position = desn.getSelectedItemPosition();
       /* if(position!=0){
            desn =
        }*/
        desn.setOnItemSelectedListener(this);
        String test = "Select";
        List<String>spinnerdesn = new ArrayList<String>();
        spinnerdesn.add("Select");
        spinnerdesn.add("Manager");
        spinnerdesn.add("Sales Manager");
        spinnerdesn.add("Delivery Boy");
        empregd = findViewById(R.id.empRegd);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerdesn);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desn.setAdapter(dataAdapter);
        if(test!=null){
            int spinnerposition = dataAdapter.getPosition(test);
            desn.setSelection(spinnerposition);
        }
        empregd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final String fname = first_name.getText().toString().trim();
                    final String lname  = last_name.getText().toString().trim();
                    final String desgn  =   desn.toString();
                    final String mob  = input_mobile.getText().toString().trim();
                    final String eaddress = employeeraddress.getText().toString().trim();
                    final String eid  = emailid.getText().toString().trim();
                    final String uid  = userid.getText().toString().trim();
                    final String pwd  = input_password.getText().toString().trim();
                    final String rwd  = input_reEnterPassword.getText().toString().trim();

                String MobilePattern = "[0-9]{10}";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if(fname.length()==0) {
                    first_name.requestFocus();
                    first_name.setError("Enter UserName");
                }
                else if(!InputValidation.isEdittextName(first_name)){
                    first_name.requestFocus();
                    first_name.setError("Enter Valid ItemName");
                }
                else if(! InputValidation.isEdittextName(last_name)){
                    last_name.requestFocus();
                    last_name.setError("Enter Last Name");
                }
                else if(!InputValidation.isSpinnerSelected(desn)){
                    tverror.requestFocus();
                    tverror.setError("Select Designation");
                } else if(!InputValidation.isEdittextMobile(input_mobile)){
                    input_mobile.requestFocus();
                    input_mobile.setError("Enter Valid Phone Number");
                }

                else if(employeeraddress.length()==0){
                    employeeraddress.requestFocus();
                    employeeraddress.setError("Enter Unit Address");

                }

               /* else if(!isEdittextName(employeeraddress)){
                    employeeraddress.requestFocus();
                    employeeraddress.setError("Enter Employee Address");
                }
*/
                else if(!InputValidation.isEditTextContainEmail(emailid)){
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
                else if(!InputValidation.isPasswordMatches(input_password,input_reEnterPassword)){
                    input_reEnterPassword.requestFocus();
                    input_reEnterPassword.setError("Confirm Password doesn's  match");
                }
                    else if(rwd.length()==0){
                        input_reEnterPassword.requestFocus();
                        input_reEnterPassword.setError("Confirm Password");
                    }
                else{
                        new HTTPAsyncTaskGetDetail().execute(ServerLinks.ADD_EMPLYOEE + acess_token);

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
        item = parent.getItemAtPosition(position).toString();
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "Please Select the Designation: " + item, Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getApplicationContext(), "Emplyoee Added Sucessfully...", Toast.LENGTH_SHORT).show();
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
        JSONObject emplyoeeRegd = new JSONObject();
        JSONObject emplyoee = new JSONObject();

        emplyoee.accumulate("first_name",first_name.getText().toString().trim());
        emplyoee.accumulate("last_name",last_name.getText().toString().trim());
        emplyoee.accumulate("designation",item.toString().trim());
        emplyoee.accumulate("phone_no",input_mobile.getText().toString().trim());
        emplyoee.accumulate("address",employeeraddress.getText().toString().trim() );
        emplyoee.accumulate("email_id",emailid.getText().toString().trim());
        emplyoee.accumulate("aadhar_card_no",  "AQWER6576787");
        emplyoee.accumulate("status",  "Y");


        JSONObject user = new JSONObject();

        user.accumulate("user_id",userid.getText().toString().trim());
        user.accumulate("isActive","Y");
        user.accumulate("password",input_password.getText().toString().trim());

        JSONArray roleArray = new JSONArray();
        JSONObject role= new JSONObject();
        role.put("id", "4");
        roleArray.put(0,role);
        user.accumulate("roles",roleArray);
        //Role is missing in UI please addd in UI

        emplyoeeRegd.accumulate("user",user);
        emplyoeeRegd.accumulate("employee",emplyoee);
        Log.e("PostJson",emplyoeeRegd.toString());

        return emplyoeeRegd;
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


   /* private boolean employeeValidate() {
        if (fname.equals("")) {
            first_name.setError("Enter First Name");
        } else if (lname.equals("")) {
            last_name.setError("Enter Last Name");

        } else if (desn.equals("")) {
            designation.setError("Enter Designation");

        } else if (mob.equals("")) {
            input_mobile.setError("Enter Mobile Number");

        } else if (eaddress.equals("")) {
            employeeraddress.setError("Enter Employee Address");

        } else if (eid.equals("")) {
            emailid.setError("Enter Email Id");

        } else if (uid.equals("")) {
            userid.setError("Enter User Id");

        } else if (pwd.equals("")) {
            input_password.setError("Enter Password");

        } else if (rwd.equals("")) {
            input_reEnterPassword.setError("Confirm Password");

        } else if (!pwd.equals(rwd)) {
            input_reEnterPassword.setError("Confirm Password Doesn't Match");

        }


    }*/
}

