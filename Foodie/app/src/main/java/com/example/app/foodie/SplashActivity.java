package com.example.app.foodie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.sukanta.foodie.R;

public class SplashActivity extends AppCompatActivity {
    ConnectionDetector cd = null;
    SessionManager sessionManager = null;
    LinearLayout splashLogo;
    private static final int TIME = 5 * 1000;// 5 seconds
    private boolean isInternetPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       /* splashLogo = (LinearLayout)findViewById(R.id.splash_logo);*/
        sessionManager = new SessionManager(SplashActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        PermissionUtil.checkPermission(SplashActivity.this);
        cd = new ConnectionDetector(SplashActivity.this);
        isInternetPresent = cd.isConnectingToInternet();
        // check for Internet status
        if (!isInternetPresent) {
            // Internet connection is not present
            // Ask user to connect to Internet
            cd.displayAlert();

        }else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sessionManager.checkLogin();
                    Intent intent = new Intent(SplashActivity.this, UsernameActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }

            }, TIME);

        }
    }
}
