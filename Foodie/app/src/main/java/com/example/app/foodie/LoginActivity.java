package com.example.app.foodie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MyOrders.Common.ApiClient;
import com.example.app.MyOrders.Common.WebApi;
import com.example.app.Request.LoginRequest;
import com.example.app.Response.LoginResponse;
import com.example.app.Util.RegPrefManager;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    LinearLayout layoutuser_name;
    LinearLayout layoutpassword;
    EditText userName;
    EditText password;
    Button login;
    TextView register;
    //SessionManager sessionManager = null;
    ProgressDialog progressDialog;
    SharedPreferenceClass sharedPreferenceClass;
    private int i = 0;
    private WebApi webApi;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        layoutuser_name = findViewById(R.id.layoutuser_name);
        layoutpassword = findViewById(R.id.layoutpassword);
        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        sharedPreferenceClass = new SharedPreferenceClass(LoginActivity.this);
        progressDialog = new ProgressDialog(LoginActivity.this);
         retrofit = ApiClient.getRetrofit();

        webApi = retrofit.create(WebApi.class);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("")){
                    userName.setError("Enter User name");
                   // Toast.makeText(LoginActivity.this, "Enter user name", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("")){
                    password.setError("Enter password");
                    //Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else {

                 //   new loginasynctask().execute(ServerLinks.LOGIN_USER);
                    if(isNetworkAvailable()) {
                        loginResponse();
                    }else {
                        Toast.makeText(LoginActivity.this, "Please Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }


        @Override
        protected void onStart () {
            super.onStart();
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


        private void loginResponse(){
            final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.progressBarDil);
            simpleProgressBar .setVisibility(View.VISIBLE);
            LoginRequest loginRequest=new LoginRequest();
            loginRequest.setUser_id(userName.getText().toString().trim());
            loginRequest.setPassword(password.getText().toString().trim());
            Call<LoginResponse> call = webApi.loginAdmin(loginRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    simpleProgressBar.setVisibility(View.INVISIBLE);
                    int code=response.code();
                    switch (code){
                        case 417:
                            Toast.makeText(LoginActivity.this,"Please Enter Correct Login Credential",Toast.LENGTH_LONG).show();
                            break;
                        case 200:
                            String status=response.body().getStatus();
                            if(status.equals("SUCCESS")){
                                String role=response.body().getResult().getRole();
                                int userId=response.body().getResult().getUserId();
                                String name=response.body().getResult().getName();
                                String email=response.body().getResult().getEmailid();
                                RegPrefManager.getInstance(LoginActivity.this).setLoginDetails(role,String.valueOf(userId),name,email);
                                SharedPreferenceClass.writeString(LoginActivity.this, "ROLEID", role);
                                SharedPreferenceClass.writeString(LoginActivity.this, "USERID", String.valueOf(userId));
                                SharedPreferenceClass.writeString(LoginActivity.this, "NAME", name);
                                SharedPreferenceClass.writeString(LoginActivity.this, "EMAILID", email);
                                startActivity(new Intent(LoginActivity.this, DrawerActivity.class));
                                finish();
                            }
                            else if(status.equals("ERROR")){

                                String message = response.body().getMessage();
                                password.requestFocus();
                                password.setError(message);


                            }
                            else {

                                Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_LONG).show();
                            }
                            break;
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    simpleProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this,"Try again",Toast.LENGTH_LONG).show();
                }
            });

        }

    public class loginasynctask extends AsyncTask<String, String, String> {
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
                    Toast.makeText(getApplicationContext(), "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                    JSONObject jobj2 = jsonObject.getJSONObject("result");

                    String role = jobj2.getString("role");
                    String userId = jobj2.getString("userId");
                    String name = jobj2.getString("name");
                    String email = jobj2.getString("emailid");
                    SharedPreferenceClass.writeString(LoginActivity.this, "ROLEID", role);
                    SharedPreferenceClass.writeString(LoginActivity.this, "USERID", userId);
                    SharedPreferenceClass.writeString(LoginActivity.this, "NAME", name);
                    SharedPreferenceClass.writeString(LoginActivity.this, "EMAILID", email);

                    startActivity(new Intent(LoginActivity.this, DrawerActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Credential...", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Invalid Credential", Toast.LENGTH_SHORT).show();
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

        jsonObject.accumulate("user_id",  userName.getText().toString().trim());
        jsonObject.accumulate("password",  password.getText().toString().trim());

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




