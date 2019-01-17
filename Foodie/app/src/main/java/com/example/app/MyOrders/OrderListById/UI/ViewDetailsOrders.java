package com.example.app.MyOrders.OrderListById.UI;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.MyOrders.Common.BaseActivity;
import com.example.sukanta.foodie.R;

public class ViewDetailsOrders extends BaseActivity implements AdapterView.OnItemSelectedListener {
    TextView name;
    TextView Description;
    ImageView price;
    TextView itemcount;
    TextView starus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_allorder_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" View Order Details ");
        setSupportActionBar(toolbar);
        enableActionBar(true);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (android.os.Build.VERSION.SDK_INT >= 21){
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.logintextColor));

        }


        Bundle bundle=getIntent().getExtras();
        if(bundle != null)
        {
           /* String getdist_id = (String)bundle.get("dist_id");
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
            Userid.setText(getUserid);*/
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
