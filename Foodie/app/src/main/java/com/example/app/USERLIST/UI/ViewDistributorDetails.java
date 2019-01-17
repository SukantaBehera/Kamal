package com.example.app.USERLIST.UI;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sukanta.foodie.R;

public class ViewDistributorDetails extends AppCompatActivity {
    TextView dist_id;
    TextView CompanyName;
    ImageView Photo;
    TextView Unitholdername;
    TextView UnitAddress;
    TextView permanentaddress;
    TextView MobileNo;
    TextView Emailid;
    TextView Userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_distributor_details);
        dist_id = findViewById(R.id.dist_id);
        Photo = findViewById(R.id.distribitor_photo);
        CompanyName = findViewById(R.id.companynamelist);
        Unitholdername = findViewById(R.id.unitholder_address);
        UnitAddress = findViewById(R.id.unit_address);
        permanentaddress = findViewById(R.id.permanent_address);
        MobileNo = findViewById(R.id.distributor_mobile_number);
        Emailid = findViewById(R.id.dist_email);
        Userid = findViewById(R.id.user_id);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (android.os.Build.VERSION.SDK_INT >= 21){
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.logintextColor));

        }


        Bundle bundle=getIntent().getExtras();
        if(bundle != null)
        {
            String getdist_id = (String)bundle.get("dist_id");
            dist_id.setText(getdist_id);
            String getCompanyName = (String)bundle.get("company_name");
            CompanyName.setText(getCompanyName);
            String getUnitholdername = (String)bundle.get("unit_holder_name");
            Unitholdername.setText(getUnitholdername);
            String getUnitAddress = (String)bundle.get("unit_address");
            UnitAddress.setText(getUnitAddress);
            String getpermanentaddress = (String)bundle.get("permanent_address");
            permanentaddress.setText(getpermanentaddress);
            String getMobileNo = (String)bundle.get("phone_no");
            MobileNo.setText(getMobileNo);
            String getEmailid = (String)bundle.get("email_id");
            Emailid.setText(getEmailid);
            String getUserid = (String)bundle.get("user_id");
            Userid.setText(getUserid);
        }

    }
}
