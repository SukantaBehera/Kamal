package com.example.app.USERLIST.UI;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sukanta.foodie.R;

public class ViewFranchisorDetails extends AppCompatActivity {
    TextView fran_id;
    TextView CompanyName;
   /* ImageView Photo;*/
    TextView MobileNo;
    TextView Emailid;
    TextView UnitAddress;
    TextView residenceaddress;
    TextView status;
    TextView Userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_franchisor_details);
        fran_id = findViewById(R.id.dist_id);
       // Photo = findViewById(R.id.distribitor_photo);
        CompanyName = findViewById(R.id.companynamelist);
        MobileNo = findViewById(R.id.franchisor_mobile_number);
        Emailid = findViewById(R.id.franch_email);
        UnitAddress = findViewById(R.id.unitholder_address);
        residenceaddress = findViewById(R.id.permanent_address);
        status = findViewById(R.id.status);
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
            String getdist_id = (String)bundle.get("fran_id");
            fran_id.setText(getdist_id);
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
            String getEmailid = (String)bundle.get("status");
            if (getEmailid.equals("Y")){
                status.setText("Active");
            }else {
                status.setText("Inactive");
            }

            String getUserid = (String)bundle.get("user_id");
            Userid.setText(getUserid);
        }

    }
}
