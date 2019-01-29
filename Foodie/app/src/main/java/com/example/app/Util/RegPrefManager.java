package com.example.app.Util;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.app.Response.ViewResultCart;
import com.example.app.foodie.SharedPreferenceClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    /*public void setCartItems(ArrayList<ViewResultCart> list){

        Gson gson = new Gson();
        String json = gson.toJson(list);
        mSharedPreferences.edit().putString("CARTLIST", json).apply();
           // This line is IMPORTANT !!!


    }

    public ArrayList<ViewResultCart> getCartItems(String key){

        Gson gson = new Gson();
        String json = mSharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
    public ArrayList<ViewResultCart> getTasksFromSharedPrefs(Context context) {
       // SharedPreferences appSharedPrefs = PreferenceManager .getDefaultSharedPreferemces(context.getApplicationContext());
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("CARTLIST", "");
       ViewResultCart tasks = gson.fromJson(json, new TypeToken<ArrayList<ViewResultCart>>(){}.getType());
        return tasks;
    }
*/



    public void setFlagCart(String flag){
        mSharedPreferences.edit().putString("FLAGCART",flag).apply();
    }

    public String getFlagCart(){
        return mSharedPreferences.getString("FLAGCART",null);
    }

    public void setUpdateStatus(String status){
        mSharedPreferences.edit().putString("ORDERSTATUS",status).apply();


    }
    public String getUpdateStatus(){
        return mSharedPreferences.getString("ORDERSTATUS",null);
    }

    public void setDeliveryDate(String date){
        mSharedPreferences.edit().putString("ORDERDELIVER",date).apply();


    }
    public String getDeliveryDate(){
        return mSharedPreferences.getString("ORDERDELIVER",null);
    }

    public void setDispatchDate(String flag){
        mSharedPreferences.edit().putString("ORDERDISPATCH",flag).apply();


    }
    public String getDispatchDate(){
        return mSharedPreferences.getString("ORDERDISPATCH",null);
    }

    public void setDispatchBy(String flag){
        mSharedPreferences.edit().putString("DISPATCHBY",flag).apply();


    }
    public String getDispatchBy(){
        return mSharedPreferences.getString("DISPATCHBY",null);
    }

    public void setDeliverBy(String flag){
        mSharedPreferences.edit().putString("DELIVERBY",flag).apply();


    }
    public String getDeliverBy(){
        return mSharedPreferences.getString("DELIVERBY",null);
    }

    public void setOrderId(String id){
        mSharedPreferences.edit().putString("ORDERID",id).apply();


    }
    public String getOrderId(){
        return mSharedPreferences.getString("ORDERID",null);
    }

    public void Clear(){
       SharedPreferences.Editor editor = mSharedPreferences.edit();
       editor.clear();
       editor.commit();
   }
    public void ClearCartFlag(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove("FLAGCART");
        editor.commit();
    }

}

