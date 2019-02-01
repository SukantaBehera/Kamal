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

import com.example.app.Request.UpdateFranchisorRequest;
import com.example.app.Response.TokenResponse;
import com.example.app.Response.UpdateUserResponse;
import com.example.app.Util.Common.ApiClient;
import com.example.app.Util.Common.Token;
import com.example.app.Util.Common.WebApi;
import com.example.app.foodie.DrawerActivity;
import com.example.app.foodie.LoginActivity;
import com.example.sukanta.foodie.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.app.Util.Common.Constants.RESPONSE_ERROR;
import static com.example.app.Util.Common.Constants.RESPONSE_OK;

public class ViewFranchisorDetails extends AppCompatActivity implements View.OnClickListener {

  private EditText CompanyName,holderEd,UnitAddress,residenceaddress,MobileNo,Emailid;
  private Button updateBtn;
    TextView status;

    private Toolbar toolbar;
    private ImageView updateImg;
    private WebApi webApi;
    Retrofit retrofit;
    String acess_token = "",getdist_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_franchisor_details);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewFranchisorDetails.this, DrawerActivity.class));
                finish();
            }
        });
        retrofit = ApiClient.getRetrofit();

        webApi = retrofit.create(WebApi.class);
        initialize();

    }
    @Override
    public void onResume(){
        super.onResume();

        Bundle bundle=getIntent().getExtras();
        if(bundle != null)
        {
             getdist_id = (String)bundle.get("fran_id");
           // fran_id.setText(getdist_id);
            String getCompanyName = (String)bundle.get("company_name");
            CompanyName.setText(getCompanyName);
            String getUnitholdername = (String)bundle.get("phone_no");
            MobileNo.setText(getUnitholdername);
            String getUnitAddress = (String)bundle.get("email_id");
            Emailid.setText(getUnitAddress);
            String getpermanentaddress = (String)bundle.get("unit_address");
            UnitAddress.setText(getpermanentaddress);

            String getMobileNo = (String)bundle.get("resident_address");
            residenceaddress.setText(getMobileNo);
            String getStatus = (String)bundle.get("is_active");
            status.setText(getStatus);
            /*if (getEmailid.equals("Y")){
                status.setText("Active");
            }else {
                status.setText("Inactive");
            }*/

            String getUserid = (String)bundle.get("user_id");
         //   Userid.setText(getUserid);
        }

    }
    private void initialize(){
       // fran_id = findViewById(R.id.dist_id);
        // Photo = findViewById(R.id.distribitor_photo);
        CompanyName = findViewById(R.id.companynamelist);
        MobileNo = findViewById(R.id.franchisor_mobile_number);
        Emailid = findViewById(R.id.franch_email);
        UnitAddress = findViewById(R.id.unit_address);
        residenceaddress = findViewById(R.id.permanent_address);
        status = findViewById(R.id.status);
        holderEd= findViewById(R.id.holderEd);
        updateBtn=findViewById(R.id.updateBtn);


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
                        Toast.makeText(ViewFranchisorDetails.this, "Please Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private void update(){
        CompanyName.setEnabled(true);
        MobileNo.setEnabled(true);
        Emailid.setEnabled(true);
        holderEd.setEnabled(true);
        UnitAddress.setEnabled(true);
        residenceaddress.setEnabled(true);
        updateBtn.setVisibility(View.VISIBLE);

    }
    private void updateNetwork(){
     /*   Token token = new Token(webApi,retrofit);
       acess_token =  token.accessToken();
       Log.d("Tag","Token=========>"+acess_token);*/

        UpdateFranchisorRequest updaterequest = new UpdateFranchisorRequest();
        updaterequest.setFran_id(getdist_id);
        updaterequest.setCompany_name(CompanyName.getText().toString());
        updaterequest.setUnit_holder_name(holderEd.getText().toString());
        updaterequest.setPhone_no(MobileNo.getText().toString());
        updaterequest.setEmail_id(Emailid.getText().toString());
        updaterequest.setUnit_address(UnitAddress.getText().toString());
        updaterequest.setPermanent_address(residenceaddress.getText().toString());
        updaterequest.setStatus("Y");
        Call<UpdateUserResponse> updateUserResponseCall=webApi.updateFranchisor(acess_token,updaterequest);
        updateUserResponseCall.enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                String status = response.body().getStatus();
                String message ;
                if(status.equals("SUCCESS")){
                    message = response.body().getMessage();
                    Toast.makeText(ViewFranchisorDetails.this, message, Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(ViewFranchisorDetails.this,ViewFranchisor.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                Toast.makeText(ViewFranchisorDetails.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });
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
                Toast.makeText(ViewFranchisorDetails.this, "Invalid Token", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private Boolean validation() {


        if (CompanyName.getText().toString().trim().isEmpty()) {
            CompanyName.requestFocus();
            CompanyName.setError("Please Enter Company Name");

            return false;
        }


        if (MobileNo.getText().toString().trim().isEmpty()) {
            MobileNo.requestFocus();
            MobileNo.setError("Please Enter Mobile Number");

            return false;
        }


        if (Emailid.getText().toString().trim().isEmpty()) {
            Emailid.requestFocus();
            Emailid.setError("Please Enter Email Id");

            return false;
        }

        if (UnitAddress.getText().toString().trim().isEmpty()) {
            UnitAddress.requestFocus();
            UnitAddress.setError("Please Enter UnitAddress ");

            return false;
        }

        if (residenceaddress.getText().toString().trim().isEmpty()) {
            residenceaddress.requestFocus();
            residenceaddress.setError("Please Enter Residence Address ");

            return false;
        }


        return true;
    }
}
