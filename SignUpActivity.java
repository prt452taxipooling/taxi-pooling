package com.example.taxipooling;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity {
	
	GPSTracker gps;
	private Context mContext;
	
	double longitude=0.0, latitude=0.0;
	public static JSONArray jArray;
	public static JSONObject jObject;
	EditText un,pw,loc,email,taxino;
	boolean cbox=false;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup); 
		//to hide the action bar..		
		this.getActionBar().hide();
		
		//accessing the items by their id's..
		un = (EditText) findViewById(R.id.signup_usernameET);
		pw = (EditText) findViewById(R.id.signup_passwordET);
		loc = (EditText) findViewById(R.id.signup_locationET);
		email = (EditText) findViewById(R.id.signup_emailET);
		taxino = (EditText) findViewById(R.id.signup_taxinoET);
		Button back = (Button) findViewById(R.id.signup_cancelBTN);
		CheckBox chk = (CheckBox) findViewById(R.id.signup_driverCB);
		Button sup = (Button) findViewById(R.id.signup_signupBTN);

		gps = new GPSTracker(SignUpActivity.this);
		
	//setting signup button click listener..		
		sup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				longitude=gps.getLongitude();
				latitude=gps.getLatitude();
				Toast.makeText(getApplicationContext(), latitude+" "+longitude, Toast.LENGTH_LONG).show();
				new MyAsyncTaskForAllEnquire().execute();
				//startActivity(new Intent(SignUpActivity.this, MainActivity.class));
				//finish();				
			}
		});
		//setting back button click listener...		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
				finish();				
			}
		});
		//setting checkbox button listener to show or hide taxi no field..		
		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					taxino.setVisibility(View.VISIBLE);
					cbox=true;
				}
				else{
					taxino.setVisibility(View.INVISIBLE);
					cbox=false;
				}
				
			}			
		});			
	}
	//__________Asynchronous task connectivity__________________________________________________________________________________________
	private class MyAsyncTaskForAllEnquire extends
	AsyncTask<Object, Void, String> {

		int responseCode;
		String responseBody;
		ProgressDialog progressDialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(SignUpActivity.this);
			progressDialog.setMessage("inserting data..");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			return postData();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {

		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public String postData() {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Url.SERVER_URL + "index.php");
			try {
				// Data that I am sending
				List nameValuePairs = new ArrayList();
				nameValuePairs.add(new BasicNameValuePair("username", un.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("password", pw.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("userID", email.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("location", loc.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("lng", Double.toString(longitude)));
				nameValuePairs.add(new BasicNameValuePair("lat", Double.toString(latitude)));
				if(cbox){
					nameValuePairs.add(new BasicNameValuePair("taxiNo", taxino.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("userType", "d"));
				}
				if(!cbox)
				{
					nameValuePairs.add(new BasicNameValuePair("userType", "p"));
				}
													
				
				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
				HttpResponse response = httpclient.execute(httppost);

				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());

			} catch (Throwable t) {

				Log.d("Error Time of Login", t + "");
			}
			return responseBody;
		}

		protected void onPostExecute(String response) {
			super.onPostExecute(response);
			try {
				
				Toast.makeText(getApplicationContext(), "SU OPEXE "+responseCode, 5000000).show();
				if (responseCode == 200) {
					try {
						jArray = new JSONArray(responseBody);
						jObject = jArray.getJSONObject(0);
						User.userNAME= jObject.getString("username");
						User.userTYPE= jObject.getString("userType");
						User.totalRATING= jObject.getString("totalRating");
						User.poolingCREATED= Integer.parseInt(jObject.getString("poolingCreated"));
						User.taxiNO= jObject.getString("taxiNo");
						User.LOC= jObject.getString("location");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent i = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(i);
				} else {

					Toast.makeText(getApplicationContext(), responseBody, 900000).show();
				}
				// progressDialog.dismiss();
			}

			catch (NullPointerException e) {
				e.printStackTrace();
				Log.d("error", "internet not avaliable");
				Toast.makeText(getApplicationContext(), "catch"+responseBody, 900000).show();
			}
			progressDialog.dismiss();

		}
	}


}
