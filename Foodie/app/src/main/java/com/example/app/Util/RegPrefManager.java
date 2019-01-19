package com.example.app.Util;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.app.foodie.SharedPreferenceClass;

import java.util.HashMap;


/**
 * Created by Raju Satheesh on 10/18/2016.
 */
public class RegPrefManager {

    private SharedPreferences mSharedPreferences;
    private static RegPrefManager mPrefManager;



    public Context mContext;


    private RegPrefManager(Context context) {
        this.mContext=context;
        mSharedPreferences = context.
                getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
    }

    public static RegPrefManager getInstance(Context context) {
        if (mPrefManager == null) {
            mPrefManager = new RegPrefManager(context);
        }
        return mPrefManager;
    }



   public void setLoginDetails(String ROLEID,String USERID,String NAME,String EMAILID){
       mSharedPreferences.edit().putString("ROLEID",ROLEID).apply();
       mSharedPreferences.edit().putString("USERIDValue",USERID).apply();
       mSharedPreferences.edit().putString("NAME",NAME).apply();
       mSharedPreferences.edit().putString("EMAILID",EMAILID).apply();

   }
   public String getLoginUserID(){
       return mSharedPreferences.getString("USERIDValue",null);
   }

    public String getLoginRoleId(){
        return mSharedPreferences.getString("ROLEID",null);
    }
    public String getLoginName(){
        return mSharedPreferences.getString("NAME",null);
    }
    public String getLoginEmailId(){
        return mSharedPreferences.getString("EMAILID",null);
    }


   public void Clear(){
       SharedPreferences.Editor editor = mSharedPreferences.edit();
       editor.clear();
       editor.commit();
   }

}

