package com.example.taxipooling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.w3c.dom.Document;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class GetPointsAsyncTask extends
AsyncTask<Map<String, String>, Object, ArrayList<LatLng>> {
	public static final String USER_CURRENT_LAT = "user_current_lat";
	public static final String USER_CURRENT_LONG = "user_current_long";
	public static final String DESTINATION_LAT = "destination_lat";
	public static final String DESTINATION_LONG = "destination_long";
	public static final String DIRECTIONS_MODE = "directions_mode";
	private final NestedPoolingFragment mContext;
	private Exception exception;
	private ProgressDialog progressDialog;
	String list_lat, list_lng;

	public GetPointsAsyncTask(NestedPoolingFragment nestedPoolingFragment) {
		super();
		this.mContext = nestedPoolingFragment;
	}

	public void onPreExecute() {
		progressDialog = new ProgressDialog(mContext.getActivity());
		progressDialog.setMessage("Finding Route..");
		progressDialog.show();
	}

	@Override
	public void onPostExecute(ArrayList result) {
		// progressDialog.dismiss();
		if (exception == null) {
			Toast.makeText(mContext.getActivity(),
					User.routePoints.size() + ":" + result.size(),
					Toast.LENGTH_LONG).show();
			// mContext.handleGetDirectionsResult(result);
			// User.routePoints.add(new LatLng(result.get(i));
			for (int i = 0; i < User.routePoints.size(); i = i + 30) {
				// Toast.makeText(mContext.getActivity(),
				// User.routePoints.get(i).latitude+" "+User.routePoints.get(i).longitude,
				// Toast.LENGTH_LONG).show();
				Log.i("Point :", User.routePoints.get(i).latitude + " "
						+ User.routePoints.get(i).longitude);
			}

		} else {
			processException();
		}
		new MyAsyncTaskForAllEnquire().execute();

	}

	@Override
	protected ArrayList<LatLng> doInBackground(Map<String, String>... params) {
		Map<String, String> paramMap = params[0];
		try {
			LatLng fromPosition = new LatLng(Double.valueOf(paramMap
					.get(USER_CURRENT_LAT)), Double.valueOf(paramMap
							.get(USER_CURRENT_LONG)));
			LatLng toPosition = new LatLng(Double.valueOf(paramMap
					.get(DESTINATION_LAT)), Double.valueOf(paramMap
							.get(DESTINATION_LONG)));
			GMapV2Direction md = new GMapV2Direction();
			Document doc = md.getDocument(fromPosition, toPosition,
					paramMap.get(DIRECTIONS_MODE));
			ArrayList<LatLng> directionPoints = md.getDirection(doc);
			return directionPoints;

		} catch (Exception e) {
			exception = e;
			return null;
		}
	}

	private void processException() {
		Toast.makeText(mContext.getActivity(), "Error retriving data",
				Toast.LENGTH_LONG).show();
	}

	// ASYNCHRONEOUS TAST
	// CONNECTIVITY FOR INSERTING CREATED POOLING DATA AND SENDING NOTIFICATION---------------------------------------------------------------------------------
	private class MyAsyncTaskForAllEnquire extends
	AsyncTask<Object, Void, String> {

		int responseCode;
		String responseBody;

		// ProgressDialog progressDialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// progressDialog = new ProgressDialog();
			// progressDialog.setMessage("Creating Pooling....");
			// progressDialog.setIndeterminate(true);
			// progressDialog.setCancelable(false);
			// progressDialog.show();
			for (int i = 0; i < User.routePoints.size(); i++) {
				if (i == User.routePoints.size() - 1) {
					list_lat = list_lat + User.routePoints.get(i).latitude + "";
					list_lng = list_lng + User.routePoints.get(i).longitude	+ "";
				} else {
					list_lat = list_lat + User.routePoints.get(i).latitude + ",";
					list_lng = list_lng + User.routePoints.get(i).longitude	+ ",";

				}

			}
			Log.i("Lat :", list_lat+"");
			Log.i("Long :", list_lng+"");
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
			HttpPost httppost = new HttpPost(Url.SERVER_URL + "pooling.php");
			try {
				// Data that I am sending
				List nameValuePairs = new ArrayList();
				nameValuePairs.add(new BasicNameValuePair("fromId", User.userId));
				nameValuePairs.add(new BasicNameValuePair("username", User.userNAME));
				nameValuePairs.add(new BasicNameValuePair("source",	Pooling.Source));
				nameValuePairs.add(new BasicNameValuePair("destination",Pooling.Destination));
				nameValuePairs.add(new BasicNameValuePair("srcLat", Double.toString(Pooling.srcLat)));
				nameValuePairs.add(new BasicNameValuePair("desLat", Double.toString(Pooling.desLat)));
				nameValuePairs.add(new BasicNameValuePair("srcLng", Double.toString(Pooling.srcLng)));
				nameValuePairs.add(new BasicNameValuePair("desLng", Double.toString(Pooling.desLng)));
				nameValuePairs.add(new BasicNameValuePair("arr_lat", list_lat));
				nameValuePairs.add(new BasicNameValuePair("arr_lng", list_lng));
				nameValuePairs.add(new BasicNameValuePair("date", Pooling.date));
				nameValuePairs.add(new BasicNameValuePair("time", Pooling.time));
				nameValuePairs.add(new BasicNameValuePair("status", Pooling.status));
				nameValuePairs.add(new BasicNameValuePair("seats", Pooling.seats));
				nameValuePairs.add(new BasicNameValuePair("vehicle", Pooling.vehicle));
				
				// int i = 0;
				// for (i = 0; i < User.routePoints.size(); i++) {
				// nameValuePairs.add(new BasicNameValuePair("arrlat[]",
				// User.routePoints.get(i).latitude + ""));
				// nameValuePairs.add(new BasicNameValuePair("arrlng[]",
				// User.routePoints.get(i).longitude + ""));
				// Log.i("Lat :", User.routePoints.get(i).latitude + "");
				// Log.i("Long :", User.routePoints.get(i).longitude + "");
				// }				

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
						"utf-8"));
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
//				Log.i("Response :", responseBody);
//				Toast.makeText(mContext.getActivity(),
//						responseBody + responseCode, Toast.LENGTH_LONG).show();
				if (responseCode == 200) {
					try {
						JSONArray jArray;
						JSONObject jObject;
						Pooling.listOfPoolings.clear();
						jArray = new JSONArray(responseBody);
						for (int i = 0; i < jArray.length(); i++) {
							jObject = jArray.getJSONObject(i);
							List<String> p = new ArrayList<String>();
							p.add(jObject.getString("createrID"));
							p.add(jObject.getString("source"));
							p.add(jObject.getString("destination"));
							p.add(jObject.getString("poolingID"));
							p.add(jObject.getString("srcLat"));
							p.add(jObject.getString("srcLng"));
							p.add(jObject.getString("destLat"));
							p.add(jObject.getString("destLng"));
							p.add(jObject.getString("date"));
							p.add(jObject.getString("time"));
							p.add(jObject.getString("status"));
							Pooling.listOfPoolings.add(p);
						}
						//ADDING RECENT POOLINGS IN RPSPINNER OF FRAGMENT B________________________________ (2)
						List<String> lables = new ArrayList<String>();
						for(int i=0 ; i<Pooling.listOfPoolings.size(); i++){
							lables.add(Pooling.listOfPoolings.get(i).get(1)+" to "+Pooling.listOfPoolings.get(i).get(2));
						}
					    
						//ENDING______________________________________________________________________________ (2)*/

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// showing alert msg
					// AlertDialog.Builder alert = new AlertDialog.Builder(
					// getActivity());
					// alert.setTitle("Pooling Created successfully");
					// alert.setMessage("Do you want to see the route on map??");
					// alert.setIcon(R.drawable.poolingicon_selected);
					// alert.setPositiveButton("Yes",
					// new DialogInterface.OnClickListener() {
					//
					// @Override
					// public void onClick(DialogInterface dialog,
					// int which) {
					// // TODO Auto-generated method stub
					//
					// }
					// });
					//
					// alert.setNegativeButton("No",
					// new DialogInterface.OnClickListener() {
					//
					// @Override
					// public void onClick(DialogInterface dialog,
					// int which) {
					// // TODO Auto-generated method stub
					// dialog.cancel();
					//
					// }
					// });
					// alert.show();
					//				} else {
					//
					//					// Toast.makeText(getActivity(), responseBody,
					//					// 900000).show();
					//				}
					// progressDialog.dismiss();
				}
			}
			catch (NullPointerException e) {
				e.printStackTrace();
				Log.d("error", "internet not avaliable");
				// Toast.makeText(getActivity(), "catch" + responseBody, 900000)
				// .show();
			}
			progressDialog.dismiss();

		}
	}

}