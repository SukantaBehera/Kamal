package com.example.app.HOME;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.app.MyOrders.Common.BaseActivity;
import com.example.app.foodie.ConnectionDetector;
import com.example.app.foodie.PermissionUtil;
import com.example.app.foodie.SharedPreferenceClass;
import com.example.app.foodie.UsernameActivity;
import com.example.app.foodie.WelcomeActivity;
import com.example.sukanta.foodie.R;


public class SplashActivity extends BaseActivity {
    ConnectionDetector cd = null;
    private boolean isInternetPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        enableActionBar(false);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        String userid =  SharedPreferenceClass.readString(getApplicationContext(), "USERID","");

        PermissionUtil.checkPermission(SplashActivity.this);
        cd = new ConnectionDetector(SplashActivity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if(!isInternetPresent)
        {
            cd.displayAlert();
        }
        else if (userid.isEmpty())
        {
            Login();
        }
        else{


            HomeRun();
        }
    }

    public void Login()
    {
         /*
        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(1*3000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(), UsernameActivity.class);
                    startActivity(i);
                    //Remove activity
                    finish();

                } catch (Exception e) {

                }


            }
        };

        // start thread
        background.start();
    }


    public void HomeRun()
    {
         /*
        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(1*3000);

                    // After 5 seconds redirect to another intent
                    if(!isInternetPresent)
                    {
                        cd.displayAlert();
                    }
                    Intent i=new Intent(getBaseContext(), WelcomeActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }
}
