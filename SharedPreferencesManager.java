package com.example.taxipooling;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {
	public static void savePreferences(String key, boolean value,
			Context context) {
		
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static void savePreferences(String key, int value, Context context) {
		
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		
		editor.commit();
		
	}
	
	public static void savePreferences(String key, String value, Context context) {
		
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static int getIntPreferences(String key, Context context) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getInt(key,-1);
		
	}
	
	public static String getStringPreferences(String key, Context context,String defaultValue) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(key, defaultValue);
	}
	public static boolean getBooleanPreferences(String key, Context context,boolean bol) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(key, bol);
	}
}
