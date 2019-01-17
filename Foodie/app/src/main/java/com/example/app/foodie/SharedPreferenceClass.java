package com.example.app.foodie;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass {

	private static final String USER_PREFS = "SeekingDaddie";
	private SharedPreferences appSharedPrefs;
	private SharedPreferences.Editor prefsEditor;

	// private String user_name = "user_name_prefs";
	// String user_id = "user_id_prefs";

	public SharedPreferenceClass(Context context) {
		this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	//get value
	public int getValue_int(String intKeyValue) {
		return appSharedPrefs.getInt(intKeyValue, 0);
	}

	public String getValue_string(String stringKeyValue) {
		return appSharedPrefs.getString(stringKeyValue, "");
	}

	public Boolean getValue_boolean(String stringKeyValue) {
		return appSharedPrefs.getBoolean(stringKeyValue, false);
	}

	//setvalue
	
	public void setValue_int(String intKeyValue, int _intValue) {

		prefsEditor.putInt(intKeyValue, _intValue).commit();
	}

	public void setValue_string(String stringKeyValue, String _stringValue) {

		prefsEditor.putString(stringKeyValue, _stringValue).commit();

	}
	
	public void setValue_boolean(String stringKeyValue, Boolean _bool) {

		prefsEditor.putBoolean(stringKeyValue, _bool).commit();

	}

	public void setValue_int(String intKeyValue) {

		prefsEditor.putInt(intKeyValue, 0).commit();
	}

	public void clearData() {
		prefsEditor.clear().commit();

	}




	public static final String PREF_NAME = "KAMAL_SharedPreferences";
	public static final int MODE = Context.MODE_PRIVATE;

	/**
	 * to write integer into prefernce connector
	 *
	 * @param context context
	 * @param key     key
	 * @param value   value
	 */
	public static void writeBoolean(Context context, String key, boolean value) {
		getEditor(context).putBoolean(key, value).commit();
	}

	/**
	 * to read integer into prefernce connector
	 *
	 * @param context  context
	 * @param key      key
	 * @param defValue defValue
	 */
	public static boolean readBoolean(Context context, String key, boolean defValue) {
		return getPreferences(context).getBoolean(key, defValue);
	}

	/**
	 * to write string into prefernce connector
	 *
	 * @param context context
	 * @param key     key
	 * @param value   value
	 */
	public static void writeString(Context context, String key, String value) {
		getEditor(context).putString(key, value).commit();
	}

	/**
	 * to read integer into prefernce connector
	 *
	 * @param context  context
	 * @param key      key
	 * @param defValue defValue
	 */
	public static String readString(Context context, String key, String defValue) {
		return getPreferences(context).getString(key, defValue);
	}

	/**
	 * to write integer into prefernce connector
	 *
	 * @param context context
	 * @param key     key
	 * @param value   value
	 */
	public static void writeInteger(Context context, String key, int value) {
		getEditor(context).putInt(key, value).commit();
	}

	/**
	 * to read integer into prefernce connector
	 *
	 * @param context  context
	 * @param key      key
	 * @param defValue defValue
	 */
	public static int readInteger(Context context, String key, int defValue) {
		return getPreferences(context).getInt(key, defValue);
	}

	/**
	 * to get shared preferences
	 *
	 * @param context context
	 * @return name and mode of shared preferences
	 */
	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, MODE);
	}

	/**
	 * to get edited value
	 *
	 * @param context context
	 * @return edited shared preference
	 */
	public static SharedPreferences.Editor getEditor(Context context) {
		return getPreferences(context).edit();
	}

	/**
	 * to cleasr data in SharedPreferences
	 *
	 * @param context context
	 */
	public static void clearData(Context context) {
		getEditor(context).clear().commit();
	}
}

