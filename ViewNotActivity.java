package com.example.taxipooling;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ViewNotActivity extends Activity {
	
	static ArrayList<String> comments = new ArrayList<String>();
	static ArrayList<String> commentors = new ArrayList<String>();
	static String username="", rating="",userTypeValue="",totalP="";
	static float ratingValue =0f;
	TextView uName, rComment, tPooling;
	RatingBar rBar;
	ProgressDialog progressDialog;
	String _poolerSource, _poolerDestination, _poolingID, _poolerDesription;
	Dialog srcdes;
	
	String user_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_not);
		this.getActionBar().hide();
		user_ID=Not.userID.get(Not.item_position);
		Toast.makeText(getApplicationContext(), "Hello: "+user_ID, Toast.LENGTH_LONG).show();
		Button accept,reject;
		 uName = (TextView) findViewById(R.id.username_view_not);
		 rBar = (RatingBar) findViewById(R.id.ratingBar_view_not);
		 rComment = (TextView) findViewById(R.id.ratingComment_view_not);
		 tPooling = (TextView) findViewById(R.id.poolingCreatedNumber_view_not);
		accept = (Button) findViewById(R.id.acceptButton_view_not);
		reject = (Button) findViewById(R.id.rejectButton_view_not);
		
		new AsyncUserData().execute();
		
		Customlistadapter adap = new Customlistadapter(getApplicationContext(), User.images,comments, commentors);
		 ListView cList = (ListView) findViewById(R.id.commentList_view_not);
		 cList.setAdapter(adap);
		 
		 //SOURCE AND DESTINATION DIALOG DATA...............
			srcdes= new Dialog(ViewNotActivity.this);
			srcdes.setTitle("Source & Destination");
			srcdes.setContentView(R.layout.srcdes_poolers);
			Button sb = (Button) srcdes.findViewById(R.id.submit);
			final EditText s = (EditText) srcdes.findViewById(R.id.source);
			final EditText d = (EditText) srcdes.findViewById(R.id.destination);
			final EditText descrip= (EditText) srcdes.findViewById(R.id.description);
		
		reject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		

		sb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				_poolerSource = s.getText().toString();
				_poolerDestination = d.getText().toString();
				_poolerDesription = descrip.getText().toString();
				
				new AsyncTaskPoolers().execute();
				Toast.makeText(getApplicationContext(), _poolerSource+" "+_poolerDestination+" "+_poolerDesription, Toast.LENGTH_LONG).show();
				
				
			}
		});
		
		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				srcdes.show();
				
			}
		});
		
		
		
		
		
	}
	//ASYNC CLASS FOR GETTING USER DATA ON LIST ITEM CLICK____________________________________#
		class AsyncUserData extends AsyncTask<Object, Void, String> {
			int responseCode;
			String responseBody;
			JSONArray jArray;
			JSONObject jObject;
			private Context context;
			
			
			public AsyncUserData(Context cn) {
				// TODO Auto-generated constructor stub
				this.context= cn;
			}
			
			public AsyncUserData() {
				// TODO Auto-generated constructor stub
			}
			

			@Override
			protected String doInBackground(Object... params) {
				return postData();
			}

			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				progressDialog = new ProgressDialog(ViewNotActivity.this);
				progressDialog.setMessage("Getting user information.....");
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(false);
				progressDialog.show();
				//Toast.makeText(getApplicationContext(), user_ID, Toast.LENGTH_LONG).show();
			}

			@SuppressWarnings("unused")
			protected void onProgressUpdate(Integer... progress) {
				
			}

			public String postData() {
				HttpClient httpclient = new DefaultHttpClient();
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
				HttpConnectionParams.setSoTimeout(httpParameters,5000);
				HttpPost httppost = new HttpPost(Url.SERVER_URL+"getUserData.php");
				try {
					@SuppressWarnings("rawtypes")
					List nameValuePairs = new ArrayList();
				
					nameValuePairs.add(new BasicNameValuePair("userID", user_ID));
					// Execute HTTP Post Request
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					responseCode = response.getStatusLine().getStatusCode();
					responseBody = EntityUtils.toString(response.getEntity());
				} catch (Throwable t) {
					Log.d("Error Time of Login", t + "");
				}
				return responseBody;
			}

			protected void onPostExecute(String responseBody) {
				super.onPostExecute(responseBody);
				//Toast.makeText(LoginActivity.this, responseBody,
				//Toast.LENGTH_LONG).show();
				if(responseBody.matches("fail"))
				{
					Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
				//	error.setText("Invalid Username or Password.");
					//error.setVisibility(View.VISIBLE);
				}
				else
				{
					//Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
					try {
						jArray = new JSONArray(responseBody);
						comments.clear();
						commentors.clear();
						jObject = jArray.getJSONObject(0);
						username=jObject.getString("username");
					//	Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();
						rating=jObject.getString("totalRating");
					//	Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
						totalP=jObject.getString("poolingCreated");
					//	Toast.makeText(getApplicationContext(), totalP, Toast.LENGTH_LONG).show();
						
						for(int i=0; i<jArray.length(); i++){
							jObject= jArray.getJSONObject(i);
							comments.add(jObject.getString("comment"));
							commentors.add(jObject.getString("commentor"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//						
				}
			
				//setting username.............
				try{
					if(username.matches("")){
						uName.setText("hello..");
					}
					else{
						uName.setText(username);
					}
					//setting rating of user...............
					if(rating.matches(""))
					{
						ratingValue=0f;
					}
					else{
						ratingValue=Float.parseFloat(rating);
					}
					
					rBar.setRating(ratingValue);
					if(ratingValue>=4.0){
						//ratingtext.setText("Excellent "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
						rComment.setText(rBar.getRating()+" / 5"+" (Excelent)");
					}
					else if(ratingValue>=3.0){
						//ratingtext.setText("Good "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
						rComment.setText(rBar.getRating()+" / 5"+" (Good)");
					}
					else if(ratingValue>=1.0){
						//ratingtext.setText("Fair "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
						rComment.setText(rBar.getRating()+" / 5"+" (Fair)");
					}
					else{
						//ratingtext.setText("Poor "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
						rComment.setText(rBar.getRating()+" / 5"+" (Poor)");
					}
					//setting usertype here.........
					if(User.userTYPE.matches("p"))
						userTypeValue="passenger";
						else
							userTypeValue="driver";	
					//setting total pooling number.....
					tPooling.setText(totalP);
					//d.show();
					
				}
				catch(Exception e){
					Toast.makeText(getApplicationContext(),  e.toString(), Toast.LENGTH_LONG).show();
				}
				progressDialog.dismiss();
			}
		}
		//ASYNC CLASS FOR SENDING POOLERS DATA_________________________________________________________*
				class AsyncTaskPoolers extends AsyncTask<Object, Void, String> {
					int responseCode;
					String responseBody;
					JSONArray jArray;
					JSONObject jObject;
					private Context context;
					ProgressDialog pd;
					
					public AsyncTaskPoolers(Context cn) {
						// TODO Auto-generated constructor stub
						this.context= cn;
					}
					
					public AsyncTaskPoolers() {
						// TODO Auto-generated constructor stub
					}
					

					@Override
					protected String doInBackground(Object... params) {
						return postData();
					}

					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						pd = new ProgressDialog(ViewNotActivity.this);
						pd.setMessage("Sending...");
						pd.setIndeterminate(true);
						pd.setCancelable(false);
						pd.show();
						Toast.makeText(getApplicationContext(), user_ID, Toast.LENGTH_LONG).show();
					}

					@SuppressWarnings("unused")
					protected void onProgressUpdate(Integer... progress) {
						
					}

					public String postData() {
						HttpClient httpclient = new DefaultHttpClient();
						HttpParams httpParameters = new BasicHttpParams();
						HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
						HttpConnectionParams.setSoTimeout(httpParameters,5000);
						HttpPost httppost = new HttpPost(Url.SERVER_URL+"insertIntoUserInPoolings.php");
						try {
							@SuppressWarnings("rawtypes")
							List nameValuePairs = new ArrayList();
						String op = "iuip";
							nameValuePairs.add(new BasicNameValuePair("poolerSource", _poolerSource));
							nameValuePairs.add(new BasicNameValuePair("poolerDestination", _poolerDestination));
							nameValuePairs.add(new BasicNameValuePair("poolingID", Not.poolingID.get(Not.item_position)));
							nameValuePairs.add(new BasicNameValuePair("poolerID", User.userId));
							nameValuePairs.add(new BasicNameValuePair("poolerDescription", _poolerDesription));
							nameValuePairs.add(new BasicNameValuePair("username", User.userNAME));
							nameValuePairs.add(new BasicNameValuePair("operation", op));
							
							// Execute HTTP Post Request
							httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
							HttpResponse response = httpclient.execute(httppost);
							responseCode = response.getStatusLine().getStatusCode();
							responseBody = EntityUtils.toString(response.getEntity());
						} catch (Throwable t) {
							Log.d("Error Time of Login", t + "");
						}
						return responseBody;
					}

					protected void onPostExecute(String responseBody) {
						super.onPostExecute(responseBody);
						try{
						Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
						if(responseBody.matches("fail"))
						{
							Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
						}
						else{
							Toast.makeText(getApplicationContext(), "You enter into pooling.", Toast.LENGTH_LONG).show();

						}
						
						}
						catch(Exception e){
							Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
						}
						pd.dismiss();
						srcdes.dismiss();
						finish();
					
					}
				}
				//ENDING ASYNC CLASS FOR SENDING POOLERS DATA_________________________________________________________*
	
}
