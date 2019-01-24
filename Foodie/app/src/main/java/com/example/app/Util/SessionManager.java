package com.example.app.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.app.Response.ViewResultCart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@SuppressWarnings("ALL")
public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	//private static final String PREF_NAME = "AndroidHivePref";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";


	//for normal login
	public static final String KEY_USERTOKEN = "token";
	public static final String KEY_USEREMAIL = "emaill";
	public static final String KEY_NICENAME = "uesrname";
	public static final String KEY_DISPLAYNAME = "displayname";


	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";
	private static final String PREF_NAME = "datacenter";
	public static final String FLAG_CHANGE="FLAG_CHANGE";
	public static final String CURRENT_ADDRESS="CURRENT_ADDRESS";
	public static final String CURRENT_CHANGE_LATITUDE="CURRENT_CHANGE_LATITUDE";
	public static final String CURRENT_CHANGE_LANGITUDE="CURRENT_CHANGE_LANGITUDE";
	public static final String CHECK_SAVE_NUMBER="CHECK_SAVE_NUMBER";
	public static final String SAVE_NUMBER="SAVE_NUMBER";



	//for cart data
	public static final String KEY_CART_DATA = "cart_dat";
	public static final String FLAG_DATA = "falg_data";
	public static final String KEY_QTY = "key_qty";
	public static final int KEY_CART_SIZE= 0;
	ArrayList<ViewResultCart> lstArrayList ;

	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}





	//for cart data

	public void SetCart_ProductList(ArrayList<ViewResultCart> cart_array) {

		/*for(int i=0;i<cart_array.size();i++){


			editor.putString(KEY_CART_DATA, cart_array.get(i).toString());
		}

		editor.putInt("KEY_CART_SIZE",cart_array.size());
		editor.commit();*/

		Gson gson = new Gson();
		String json = gson.toJson(cart_array);
		/*editor = shref.edit();
		editor.remove(key).commit();*/
		editor.putString(KEY_CART_DATA, json);
		editor.commit();



	}

	public ArrayList<ViewResultCart> getCart_ProductList() {

		/*ArrayList<CartListDto>cart_array = new ArrayList<>();
		int size =pref.getInt("KEY_CART_SIZE",0);

		HashMap<String, String> countValue = new HashMap<String, String>();

		countValue.put(KEY_CART_DATA, pref.getString(KEY_CART_DATA, null));


		for(int j=0;j<size;j++){

			cart_array.add(pref.getString(KEY_CART_DATA,j));
		}*/

		Gson gson = new Gson();
		String response=pref.getString(KEY_CART_DATA , "");
		 lstArrayList = gson.fromJson(response, new TypeToken<List<ViewResultCart>>(){}.getType());

		return lstArrayList;
	}

	//for notification
	public void setCartItemQunantityValue(String qtyvalue) {
		//editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_QTY, qtyvalue);
		editor.commit();
	}


	public void ClearArraylist(){

		editor.remove(KEY_CART_DATA);
		editor.commit();
	}



}
