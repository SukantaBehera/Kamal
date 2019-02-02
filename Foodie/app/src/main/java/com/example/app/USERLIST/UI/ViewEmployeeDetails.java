package com.example.app.USERLIST.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.app.Request.UpdateEmployeeRequest;
import com.example.app.Request.UpdateFranchisorRequest;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.UpdateUserResponse;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.WebApi;
import com.example.app.foodie.DrawerActivity;
import com.example.sukanta.foodie.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewEmployeeDetails extends AppCompatActivity implements View.OnClickListener {
    private EditText CompanyName,holderEd,designTv,perAddTv,mobileTv,emailTv,lastnamelist;
    private Button updateBtn;
    TextView status;

    private Toolbar toolbar;
    private ImageView updateImg;
    private WebApi webApi;
    Retrofit retrofit;
    String acess_token = "",getdist_id,getFistname,getLastname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee_details);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(ViewEmployeeDetails.this, DrawerActivity.class));*/
                finish();
            }
        });
        retrofit = ApiClient.getRetrofit();

        webApi = retrofit.create(WebApi.class);
        initialize();
        Bundle bundle=getIntent().getExtras();
        if(bundle != null)
        {
            getdist_id=(String)bundle.get("emp_id");
            String getCompanyName = (String)bundle.get("employee_name");

            String getUnitholdername = (String)bundle.get("designation");
            designTv.setText(getUnitholdername);
            String getUnitAddress = (String)bundle.get("phone_no");
            mobileTv.setText(getUnitAddress);
            String getpermanentaddress = (String)bundle.get("address");
            perAddTv.setText(getpermanentaddress);
            String getMobileNo = (String)bundle.get("email_id");
            emailTv.setText(getMobileNo);

             getFistname = (String)bundle.get("firstname");
            CompanyName.setText(getFistname);
            getLastname = (String)bundle.get("lastname");
            lastnamelist.setText(getLastname);


        }

    }
    private void initialize(){
        // fran_id = findViewById(R.id.dist_id);
        // Photo = findViewById(R.id.distribitor_photo);
        CompanyName = findViewById(R.id.companynamelist);
        mobileTv = findViewById(R.id.mobileTv);
        emailTv = findViewById(R.id.emailTv);
        designTv = findViewById(R.id.designTv);
        perAddTv = findViewById(R.id.perAddTv);
        status = findViewById(R.id.status);
        holderEd= findViewById(R.id.holderEd);
        updateBtn=findViewById(R.id.updateBtn);
        lastnamelist=findViewById(R.id.lastnamelist);

        updateBtn.setOnClickListener(this);
        //    Userid = findViewById(R.id.user_id);
        updateImg = findViewById(R.id.updateImg);
        updateImg.setOnClickListener(this);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (android.os.Build.VERSION.SDK_INT >= 21){
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.logintextColor));

        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void update(){
        CompanyName.setEnabled(true);
        designTv.setEnabled(true);
        mobileTv.setEnabled(true);
        perAddTv.setEnabled(true);
        emailTv.setEnabled(true);
        lastnamelist.setEnabled(true);
        //statusTv.setEnabled(true);
        updateBtn.setVisibility(View.VISIBLE);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateImg:
                update();
                break;
            case R.id.updateBtn:
                //status.setEnabled(true);
                if (validation()) {
                    if(isNetworkAvailable()) {
                        fetchAcessToken();

                    }else {
                        Toast.makeText(ViewEmployeeDetails.this, "Please Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    private void fetchAcessToken() {
        //getting the progressbar


        Call<TokenResponse> call=webApi.accessToken("password","fbApp","fbApp","admin","123");

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, retrofit2.Response<TokenResponse> response) {
                //  pprogressBar.setVisibility(View.INVISIBLE);
                acess_token=response.body().getValue();
                Log.d("Tag","token===>"+acess_token);
                updateNetwork();

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                //     pprogressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ViewEmployeeDetails.this, "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });




    }
    private void updateNetwork(){
     /*   Token token = new Token(webApi,retrofit);
       acess_token =  token.accessToken();
       Log.d("Tag","Token=========>"+acess_token);*/

        UpdateEmployeeRequest updaterequest = new UpdateEmployeeRequest();
        updaterequest.setEmp_id(getdist_id);
        updaterequest.setLast_name(lastnamelist.getText().toString());
        updaterequest.setFirst_name(CompanyName.getText().toString());
        //updaterequest.setUnit_holder_name(holderEd.getText().toString());
        updaterequest.setPhone_no(mobileTv.getText().toString());
        updaterequest.setEmail_id(emailTv.getText().toString());
       // updaterequest.setUnit_address(UnitAddress.getText().toString());
        updaterequest.setAddress(perAddTv.getText().toString());
        updaterequest.setDesignation(designTv.getText().toString());
        updaterequest.setStatus("Y");
        Call<UpdateUserResponse> updateUserResponseCall=webApi.updateEmployee(acess_token,updaterequest);
        updateUserResponseCall.enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                String status = response.body().getStatus();
                String message ;
                if(status.equals("SUCCESS")){
                    message = response.body().getMessage();
                    Toast.makeText(ViewEmployeeDetails.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ViewEmployeeDetails.this,ViewEmployee.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                Toast.makeText(ViewEmployeeDetails.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Boolean validation() {


        if (CompanyName.getText().toString().trim().isEmpty()) {
            CompanyName.requestFocus();
            CompanyName.setError("Please Enter First Name");

            return false;
        }


        if (mobileTv.getText().toString().trim().isEmpty()) {
            mobileTv.requestFocus();
            mobileTv.setError("Please Enter Mobile Number");

            return false;
        }


        if (emailTv.getText().toString().trim().isEmpty()) {
            emailTv.requestFocus();
            emailTv.setError("Please Enter Email Id");

            return false;
        }

        if (perAddTv.getText().toString().trim().isEmpty()) {
            perAddTv.requestFocus();
            perAddTv.setError("Please Enter Address ");

            return false;
        }

        if (designTv.getText().toString().trim().isEmpty()) {
            designTv.requestFocus();
            designTv.setError("Please Enter Designation ");

            return false;
        }

        if (lastnamelist.getText().toString().trim().isEmpty()) {
            lastnamelist.requestFocus();
            lastnamelist.setError("Please Enter Last Name ");

            return false;
        }

        return true;
    }
}
