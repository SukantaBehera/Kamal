package com.example.app.foodie;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class PermissionUtil {
    static String[] PERMISSIONS = {Manifest.permission.ACCESS_NETWORK_STATE};
    static int PERMISSION_ALL = 1;
    public static void checkPermission(Activity context){
        if(!hasPermissions(context, PERMISSIONS)){
            ActivityCompat.requestPermissions(context, PERMISSIONS, PERMISSION_ALL);
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
