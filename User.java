package com.example.taxipooling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;

import com.google.android.gms.maps.model.LatLng;

public class User
{
	static String userId="";	
	static  String userNAME="";
	static  String userTYPE="";
	static  String taxiNO="";
	static  String LOC="";
	static  String totalRATING="";
	static  int  poolingCREATED=0;
	static  String password="";
	static String locLat="";
	static String locLng="";
	static String image=null;
	static ArrayList<LatLng> routePoints = new ArrayList<LatLng>();
	static ArrayList<String> comments = new ArrayList<String>();
	static ArrayList<String> fromId = new ArrayList<String>();
	static ArrayList<String> commentors = new ArrayList<String>();
	static ArrayList<String> images = new ArrayList<String>();
	static Map<String, String> userImages = new HashMap<String, String>();


	public static Bitmap stob(String encodedString) {
		try {
			Bitmap photo;
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			photo = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return photo;

		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
}
