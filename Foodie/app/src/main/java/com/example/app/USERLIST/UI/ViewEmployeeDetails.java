package com.example.app.USERLIST.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.app.foodie.DrawerActivity;
import com.example.sukanta.foodie.R;

public class ViewEmployeeDetails extends AppCompatActivity implements View.OnClickListener {
    TextView emp_id;
    TextView name;
    ImageView Photo;
    TextView designation;
    TextView phone_no;
    TextView address;
    TextView email_id;
    TextView status;
    TextView user_id;
    ImageView updateemp ;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee_details);


        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewEmployeeDetails.this, DrawerActivity.class));
                finish();
            }
        });

        emp_id = findViewById(R.id.dist_id);
        Photo = findViewById(R.id.distribitor_photo);
        name = findViewById(R.id.companynamelist);
        designation = findViewById(R.id.unitholder_address);
        phone_no = findViewById(R.id.unit_address);
        address = findViewById(R.id.permanent_address);
        email_id = findViewById(R.id.distributor_mobile_number);
        status = findViewById(R.id.dist_email);
        user_id = findViewById(R.id.user_id);
        updateemp = findViewById(R.id.updateemp);
        updateemp.setOnClickListener(this);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (android.os.Build.VERSION.SDK_INT >= 21){
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.logintextColor));

        }


        Bundle bundle=getIntent().getExtras();
        if(bundle != null)
        {
            String getdist_id = (String)bundle.get("emp_id");
            emp_id.setText(getdist_id);
            String getCompanyName = (String)bundle.get("employee_name");
            name.setText(getCompanyName);
            String getUnitholdername = (String)bundle.get("designation");
            designation.setText(getUnitholdername);
            String getUnitAddress = (String)bundle.get("phone_no");
            phone_no.setText(getUnitAddress);
            String getpermanentaddress = (String)bundle.get("address");
            address.setText(getpermanentaddress);
            String getMobileNo = (String)bundle.get("email_id");
            email_id.setText(getMobileNo);
            String getEmailid = (String)bundle.get("status");
            status.setText(getEmailid);
            String getUserid = (String)bundle.get("user_id");
            user_id.setText(getUserid);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateemp:

                break;
        }
    }
}
