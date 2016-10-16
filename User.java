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

 private static User singleUser = new User( );

   /* A private Constructor prevents any other
    * class from instantiating.
    */
   private User() { }

   /* Static 'instance' method */
   public static User getInstance( ) {
      return singleUser;
   }

	private String userId="";
	private  String userNAME="";
	private  String userTYPE="";
	private  String taxiNO="";
	private  String LOC="";
	private  String totalRATING="";
	private  int  poolingCREATED=0;
	private  String password="";
	private String locLat="";
	private String locLng="";
	private String image=null;
	private ArrayList<LatLng> routePoints = new ArrayList<LatLng>();
	private ArrayList<String> comments = new ArrayList<String>();
	private ArrayList<String> fromId = new ArrayList<String>();
	private ArrayList<String> commentors = new ArrayList<String>();
	private ArrayList<String> images = new ArrayList<String>();
	private Map<String, String> userImages = new HashMap<String, String>();


	public static User getSingleUser() {
		return singleUser;
	}

	public static void setSingleUser(User singleUser) {
		User.singleUser = singleUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNAME() {
		return userNAME;
	}

	public void setUserNAME(String userNAME) {
		this.userNAME = userNAME;
	}

	public String getUserTYPE() {
		return userTYPE;
	}

	public void setUserTYPE(String userTYPE) {
		this.userTYPE = userTYPE;
	}

	public String getTaxiNO() {
		return taxiNO;
	}

	public void setTaxiNO(String taxiNO) {
		this.taxiNO = taxiNO;
	}

	public String getLOC() {
		return LOC;
	}

	public void setLOC(String LOC) {
		this.LOC = LOC;
	}

	public String getTotalRATING() {
		return totalRATING;
	}

	public void setTotalRATING(String totalRATING) {
		this.totalRATING = totalRATING;
	}

	public int getPoolingCREATED() {
		return poolingCREATED;
	}

	public void setPoolingCREATED(int poolingCREATED) {
		this.poolingCREATED = poolingCREATED;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocLat() {
		return locLat;
	}

	public void setLocLat(String locLat) {
		this.locLat = locLat;
	}

	public String getLocLng() {
		return locLng;
	}

	public void setLocLng(String locLng) {
		this.locLng = locLng;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ArrayList<LatLng> getRoutePoints() {
		return routePoints;
	}

	public void setRoutePoints(ArrayList<LatLng> routePoints) {
		this.routePoints = routePoints;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	public ArrayList<String> getFromId() {
		return fromId;
	}

	public void setFromId(ArrayList<String> fromId) {
		this.fromId = fromId;
	}

	public ArrayList<String> getCommentors() {
		return commentors;
	}

	public void setCommentors(ArrayList<String> commentors) {
		this.commentors = commentors;
	}

	public ArrayList<String> getImages() {
		return images;
	}

	public void setImages(ArrayList<String> images) {
		this.images = images;
	}

	public Map<String, String> getUserImages() {
		return userImages;
	}

	public void setUserImages(Map<String, String> userImages) {
		this.userImages = userImages;
	}

	public Bitmap stob(String encodedString) {
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
