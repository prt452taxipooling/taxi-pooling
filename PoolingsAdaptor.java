package com.example.taxipooling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification.Builder;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.taxipooling.RecentPoolings;

public class PoolingsAdaptor extends ArrayAdapter<String>
{
	ArrayList<String> source,destination;
	Dialog _feedbackDialog,_editDialog;
	AlertDialog.Builder _startDialog, _cancelDialog;
	Integer image_id;
	Context context;
	ArrayList<String> date_time;
	String _poolingID;

	static	List<List<String>> _poolersList = new ArrayList<List<String>>();
	ListView _lv;
	FeedbackAdaper fA;
	Date compare=null,current=null;
	ImageView delete_btn,edit_btn,start_btn,cancel_btn,complete_btn;

	public PoolingsAdaptor(Context context, int resource, ArrayList<String> src, ArrayList<String> dest, ArrayList<String> date) {
		super(context, resource,src);
		// TODO Auto-generated constructor stub
		this.source=src;
		this.destination=dest;
		this.context=context;
		this.date_time=date;
	}
	/*	ArrayList<String> s,sd;
	Integer image_id;
	Context context;
	public void PoolingsAdapter(Context context,Integer image_id, ArrayList<String> s, ArrayList<String> sd){
		super(context, R.layout.fragment_c_list_items,  s);
		// TODO Auto-generated constructor stub
		this.sd = sd;
		this.s = s;
		this.image_id = image_id;
		this.context = context;
	}*/
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub


		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View single_row = inflater.inflate(R.layout.recent_poolings_list_items, parent, false);
		TextView tv_source = (TextView) single_row.findViewById(R.id.tv_source);
		TextView tv_dest = (TextView) single_row.findViewById(R.id.tv_destination);
		TextView dt = (TextView) single_row.findViewById(R.id.tv_dateTime);

		  edit_btn = (ImageView) single_row.findViewById(R.id.editPooling);
		  start_btn = (ImageView) single_row.findViewById(R.id.startPooling);
		  cancel_btn = (ImageView) single_row.findViewById(R.id.cancelPooling);
		  complete_btn = (ImageView) single_row.findViewById(R.id.complete);
		  delete_btn = (ImageView) single_row.findViewById(R.id.deletePooling);

		//CHANGING EDIT BUTTON COLOR
			edit_btn.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
			start_btn.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
			cancel_btn.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
			complete_btn.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
			delete_btn.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
			
		//ImageView imageView = (ImageView) single_row.findViewById(R.id.senderImage);
		tv_source.setText(source.get(position));
		tv_dest.setText(destination.get(position));
		dt.setText(date_time.get(position));
		//imageView.setImageResource(image_id);

		//SETTING RECENT POOLING BUTTONS DISABLE AND ENABLE........................................................
		if(Pooling.listOfPoolings.get(position).get(0).matches(User.userId)){
			delete_btn.setVisibility(View.VISIBLE);
			cancel_btn.setVisibility(View.GONE);
		}
		else{
			delete_btn.setVisibility(View.GONE);
			start_btn.setVisibility(View.GONE);
			edit_btn.setVisibility(View.GONE);
			cancel_btn.setVisibility(View.VISIBLE);
		}
		if(Pooling.listOfPoolings.get(position).get(10).matches("start")){
			start_btn.setEnabled(false);
			edit_btn.setEnabled(false);
			cancel_btn.setEnabled(false);
		}
		else if(Pooling.listOfPoolings.get(position).get(10).matches("pending")){
			complete_btn.setEnabled(false);
		}


		//COMPLETE POOLING CLICK LISTENER STARTING..................................................................
		complete_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(context, "hello mr. "+position, Toast.LENGTH_LONG).show();
				Toast.makeText(context, Pooling.listOfPoolings.get(position).get(1)+" "+Pooling.listOfPoolings.get(position).get(3), Toast.LENGTH_LONG).show();
				new MyAsyncTaskForPoolers().execute();
				_poolingID = Pooling.listOfPoolings.get(position).get(3);
				_feedbackDialog = new Dialog(context );
				_feedbackDialog.setTitle("Feedback");
				_feedbackDialog.setContentView(R.layout.feedback);

				_lv = (ListView) _feedbackDialog.findViewById(R.id.feedback_list);





				//		ArrayList<String> destList = new ArrayList();
			}
		});
		//COMPLETE POOLING CLICK LISTENER ENDIND....................................................................

		//EDIT POOLING CLICK LISTENER STARTING......................................................................
		edit_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edit_btn.setImageResource(R.drawable.edit_black);
				_poolingID = Pooling.listOfPoolings.get(position).get(3);
				_editDialog = new Dialog(context);
				_editDialog.setTitle("Edit Pooling");
				_editDialog.setContentView(R.layout.edit_pooling);
				
				_editDialog.show();
				edit_btn.setImageResource(R.drawable.edit);
			}
		});
		//EDIT POOLING CLICK LISTENER ENDIND..........................................................................

		//START POOLING CLICK LISTENER STARTING......................................................................
		start_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String _timeRemaining;
				_timeRemaining=formatDateTime(position);
				try
				{
					if(current.before(compare))//Pooling Starting date/time and current date/time checking. 
					{
						_startDialog = new AlertDialog.Builder(context);
						_startDialog.setTitle("Taxi Pooling.");
						_startDialog.setMessage(_timeRemaining+" remaining to start this pooling.");
						_startDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								dialog.dismiss();
							}
						});
						_startDialog.show();

						//.....
					}
					else
					{
						//ON LOCATION CHANGE LISTENER WHICH CHECK THE USER LOCATION TO SHOW POOLING COMPLETE NOTIFICAION
						_poolingID = Pooling.listOfPoolings.get(position).get(3);
						Toast.makeText(context, _poolingID, Toast.LENGTH_LONG).show();
						new MyAsyncTaskForStartPooling().execute();

						_startDialog = new AlertDialog.Builder(context);
						_startDialog.setTitle("Pooling Started.");
						_startDialog.setMessage("You have started this pooling now.");
						_startDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								dialog.dismiss();
							}
						});
					}
				}
				catch(Exception e)
				{
					Toast.makeText(context,e.toString(), Toast.LENGTH_LONG).show();

				}
			}
		});
		//START POOLING CLICK LISTENER ENDING......................................................................

		//CANCEL POOLING CLICK LISTENER STARTING......................................................................
		cancel_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Warning");
				alert.setMessage("Are you sure you want to cancel this pooling?");
				alert.setIcon(R.drawable.warning30);
				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						new MyAsyncTaskForCancelPooling().execute();
						source.remove(position);
						destination.remove(position);
						date_time.remove(position);
						Pooling.listOfPoolings.remove(position);
						NestedRecentPooling.pA.notifyDataSetChanged();
					}
				});
				alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
				alert.show();
			}
		});
		//CANCEL POOLING CLICK LISTENER ENDING......................................................................

		//DELETE POOLING CLICK LISTENER ............................................................................
		delete_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				_poolingID = Pooling.listOfPoolings.get(position).get(3);
				
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Warning");
				alert.setMessage("Are you sure you want to delete this pooling?");
				alert.setIcon(R.drawable.warning30);
				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						new MyAsyncTaskForDeletePooling().execute();
						source.remove(position);
						destination.remove(position);
						date_time.remove(position);
						Pooling.listOfPoolings.remove(position);
						NestedRecentPooling.pA.notifyDataSetChanged();
					}
				});
				alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
				alert.show();

			}
		});
		//DELETE POOLING CLICK LISTENER ENDING......................................................................


		return single_row; 
	}

	//ASYNC TASK FOR GETTING USERS OF POOLING___________________________________________________________________(*)_
	private class MyAsyncTaskForPoolers extends
	AsyncTask<Object, Void, String> {
		int responseCode;
		String responseBody;
		JSONArray jArray;
		JSONObject jObject;
		ProgressDialog	progressDialog = new ProgressDialog(getContext());

		@Override
		protected String doInBackground(Object... params) {
			return postData();
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog.setMessage("Getting Poolers");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
		}

		public String postData() {
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
			HttpConnectionParams.setSoTimeout(httpParameters,5000);
			HttpPost httppost = new HttpPost(Url.SERVER_URL+"pooling.php");
			try {
				@SuppressWarnings("rawtypes")
				List nameValuePairs = new ArrayList();
				nameValuePairs.add(new BasicNameValuePair("poolingID",_poolingID ));
				nameValuePairs.add(new BasicNameValuePair("operation","uip" ));
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

			}
			else
			{
				//Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
				try {
					_poolersList.clear();
					jArray = new JSONArray(responseBody);
					for(int i=0; i<jArray.length(); i++){
						jObject = jArray.getJSONObject(i);
						List<String> tempList = new ArrayList<String>();
						Toast.makeText(getContext(),jObject.getString("username")+" :"+ jObject.getString("poolingID"), Toast.LENGTH_LONG).show();
						tempList.add(jObject.getString("poolingID"));
						tempList.add(jObject.getString("username"));
						tempList.add(jObject.getString("userID"));
						_poolersList.add(tempList);
					}
					ArrayList<String> poolers = new ArrayList();
					ArrayList<String> poolers_ID = new ArrayList();
					for(int i=0;i<_poolersList.size();i++){
						//srcdes= Pooling.listOfPoolings.get(i).get(1)+" to "+Pooling.listOfPoolings.get(i).get(2);
						poolers.add(_poolersList.get(i).get(1));
						poolers_ID.add(_poolersList.get(i).get(2));
					}
					//PoolingsAdaptor pA = new PoolingsAdaptor(context, R.drawable.picon, poolers, null, "temp Date/time");
					fA = new FeedbackAdaper(context, R.drawable.picon_selected, poolers, poolers_ID);
					_lv.setAdapter(fA);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			progressDialog.dismiss();
			_feedbackDialog.show();
		}
	}
	//ENDING ASYNC TASK FOR GETTING USERS OF POOLING__________________________________________________________(*)

	//ASYNC TASK FOR START POOLING___________________________________________________________________(*)_
	private class MyAsyncTaskForStartPooling extends
	AsyncTask<Object, Void, String> {
		int responseCode;
		String responseBody;
		JSONArray jArray;
		JSONObject jObject;
		ProgressDialog	_startPoolingProgress = new ProgressDialog(getContext());

		@Override
		protected String doInBackground(Object... params) {
			return postData();
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			_startPoolingProgress.setMessage("Starting pooling....");
			_startPoolingProgress.setIndeterminate(true);
			_startPoolingProgress.setCancelable(false);
			_startPoolingProgress.show();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
		}

		public String postData() {
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
			HttpConnectionParams.setSoTimeout(httpParameters,5000);
			HttpPost httppost = new HttpPost(Url.SERVER_URL+"pooling.php");
			try {
				@SuppressWarnings("rawtypes")
				List nameValuePairs = new ArrayList();
				nameValuePairs.add(new BasicNameValuePair("username",User.userNAME ));
				nameValuePairs.add(new BasicNameValuePair("userID",User.userId ));
				nameValuePairs.add(new BasicNameValuePair("poolingID",_poolingID ));
				nameValuePairs.add(new BasicNameValuePair("status","start" ));
				nameValuePairs.add(new BasicNameValuePair("operation","sp" ));
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
				Toast.makeText(context, "if wali : "+responseBody, Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(context, "else wali : "+responseBody, Toast.LENGTH_LONG).show();
				/*try {
					_poolersList.clear();
					jArray = new JSONArray(responseBody);
					for(int i=0; i<jArray.length(); i++){
						jObject = jArray.getJSONObject(i);
						List<String> tempList = new ArrayList<String>();
						Toast.makeText(getContext(),jObject.getString("username")+" :"+ jObject.getString("poolingID"), Toast.LENGTH_LONG).show();
						tempList.add(jObject.getString("poolingID"));
						tempList.add(jObject.getString("username"));
						tempList.add(jObject.getString("userID"));
						_poolersList.add(tempList);
					}
					ArrayList<String> poolers = new ArrayList();
					ArrayList<String> poolers_ID = new ArrayList();
					for(int i=0;i<_poolersList.size();i++){
						//srcdes= Pooling.listOfPoolings.get(i).get(1)+" to "+Pooling.listOfPoolings.get(i).get(2);
						poolers.add(_poolersList.get(i).get(1));
						poolers_ID.add(_poolersList.get(i).get(2));
					}
					//PoolingsAdaptor pA = new PoolingsAdaptor(context, R.drawable.picon, poolers, null, "temp Date/time");
					fA = new FeedbackAdaper(context, R.drawable.picon_selected, poolers, poolers_ID);
					_lv.setAdapter(fA);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

			}

			_startPoolingProgress.dismiss();
			_startDialog.show();
		}
	}
	//ENDING ASYNC TASK FOR START POOLING__________________________________________________________(*)
	
	//ASYNC TASK FOR DELETE POOLING___________________________________________________________________(X)_
		private class MyAsyncTaskForDeletePooling extends
		AsyncTask<Object, Void, String> {
			int responseCode;
			String responseBody;
			JSONArray jArray;
			JSONObject jObject;
			ProgressDialog	progressDialog = new ProgressDialog(getContext());

			@Override
			protected String doInBackground(Object... params) {
				return postData();
			}

			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();

				progressDialog.setMessage("Deleting....");
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(false);
				progressDialog.show();
			}

			@SuppressWarnings("unused")
			protected void onProgressUpdate(Integer... progress) {
			}

			public String postData() {
				HttpClient httpclient = new DefaultHttpClient();
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
				HttpConnectionParams.setSoTimeout(httpParameters,5000);
				HttpPost httppost = new HttpPost(Url.SERVER_URL+"pooling.php");
				try {
					@SuppressWarnings("rawtypes")
					List nameValuePairs = new ArrayList();
					nameValuePairs.add(new BasicNameValuePair("poolingID",_poolingID ));
					nameValuePairs.add(new BasicNameValuePair("operation","dp" ));
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
				
					Toast.makeText(context, "Pooling deleted successfully..", Toast.LENGTH_LONG).show();

				progressDialog.dismiss();
				
			}
		}
		//ENDING ASYNC TASK FOR DELETE POOLING__________________________________________________________(X)
		
		//ASYNC TASK FOR CANCEL POOLING___________________________________________________________________(X)_
				private class MyAsyncTaskForCancelPooling extends
				AsyncTask<Object, Void, String> {
					int responseCode;
					String responseBody;
					JSONArray jArray;
					JSONObject jObject;
					ProgressDialog	progressDialog = new ProgressDialog(getContext());

					@Override
					protected String doInBackground(Object... params) {
						return postData();
					}

					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();

						progressDialog.setMessage("Cancelling....");
						progressDialog.setIndeterminate(true);
						progressDialog.setCancelable(false);
						progressDialog.show();
					}

					@SuppressWarnings("unused")
					protected void onProgressUpdate(Integer... progress) {
					}

					public String postData() {
						HttpClient httpclient = new DefaultHttpClient();
						HttpParams httpParameters = new BasicHttpParams();
						HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
						HttpConnectionParams.setSoTimeout(httpParameters,5000);
						HttpPost httppost = new HttpPost(Url.SERVER_URL+"pooling.php");
						try {
							@SuppressWarnings("rawtypes")
							List nameValuePairs = new ArrayList();
							nameValuePairs.add(new BasicNameValuePair("poolingID",_poolingID ));
							nameValuePairs.add(new BasicNameValuePair("userID",User.userId ));
							nameValuePairs.add(new BasicNameValuePair("operation","cp" ));
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
						
							Toast.makeText(context, "Pooling Cancelled successfully..", Toast.LENGTH_LONG).show();

						progressDialog.dismiss();
						
					}
				}
				//ENDING ASYNC TASK FOR CANCEL POOLING__________________________________________________________(X)


	
	//FUNCTION FORMAT DATE
	public String formatDateTime(int position){
		String _timeRemaining="";
		String _tempDate, _tempTime;
		try {
			_tempDate= Pooling.listOfPoolings.get(position).get(8);
			_tempTime= Pooling.listOfPoolings.get(position).get(9);
			String currentTime=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
			String compareDate=_tempDate+" "+_tempTime+":00";

			compare=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(compareDate);
			current=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(currentTime);

			//GETTING REMAINING TIME FOR POOLING

			int hours=(int) ((compare.getTime()-current.getTime())/(1000*60));
			hours/=60;
			int days=hours/24;
			hours=hours%24;
			int minutes=   (int) (((compare.getTime()-current.getTime())/(1000*60))%60);
			_timeRemaining= days+" days,"+hours+" hours, "+minutes+" mins.";
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
		finally{
			return _timeRemaining;
		}
	}

}
