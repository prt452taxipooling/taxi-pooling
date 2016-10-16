package com.example.taxipooling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

	class AsyncNotificatoin extends AsyncTask<Object, Void, String> {
		int responseCode;
		String responseBody;
		JSONArray jArray;
		JSONObject jObject;
		private Context context;
		
		public AsyncNotificatoin(Context cn) {
			// TODO Auto-generated constructor stub
			this.context= cn;
		}
		
		public AsyncNotificatoin() {
			// TODO Auto-generated constructor stub
		}
		

		@Override
		protected String doInBackground(Object... params) {
			return postData();
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//progressDialog = new ProgressDialog(LoginActivity.this);
			//progressDialog.setMessage("Login checking..");
			//progressDialog.setIndeterminate(true);
			//progressDialog.setCancelable(true);
			//progressDialog.show();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {
		}

		public String postData() {
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
			HttpConnectionParams.setSoTimeout(httpParameters,5000);
			HttpPost httppost = new HttpPost(Url.SERVER_URL+"getAllNot.php");
			try {
				@SuppressWarnings("rawtypes")
				List nameValuePairs = new ArrayList();
				nameValuePairs.add(new BasicNameValuePair("toID", User.userId));
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
				Toast.makeText(context, responseBody, Toast.LENGTH_LONG).show();
			//	error.setText("Invalid Username or Password.");
				//error.setVisibility(View.VISIBLE);
			}
			else
			{
				
				try {
					//Toast.makeText(context, responseBody, Toast.LENGTH_LONG).show();
					jArray = new JSONArray(responseBody);
					Not.senderList.clear();
					Not.srcList.clear();
					Not.desList.clear();
					Not.userID.clear();
					Not.poolingID.clear();
					Not.notID.clear();
					Not.readBit.clear();
					Not.dateTime.clear();
					Not.type.clear();
					Not.notUserData.clear();
					for(int i=0; i<jArray.length(); i++){
						jObject= jArray.getJSONObject(i);
						Map<String, String> temp = new HashMap<String, String>();
						temp.put("fromName", jObject.getString("fromName"));
						temp.put("source", jObject.getString("source"));
						temp.put("destination", jObject.getString("destination"));
						temp.put("fromID", jObject.getString("fromID"));
						temp.put("readBit", jObject.getString("readBit"));
						temp.put("poolingID", jObject.getString("poolingID"));
						temp.put("notID", jObject.getString("notificationID"));
						temp.put("dateTime", jObject.getString("dateTime"));
						temp.put("type", jObject.getString("type"));
						Not.notUserData.add(temp);
					}
					
					for (int i = 0; i < Not.notUserData.size(); i++) {
						Map<String, String> hm = new HashMap<String, String>();
						hm=Not.notUserData.get(i);
						Not.senderList.add(hm.get("fromName"));
						Not.srcList.add(hm.get("source"));
						Not.desList.add(hm.get("destination"));
						Not.dateTime.add(hm.get("dateTime"));
						Not.userID.add(hm.get("fromID"));
						Not.readBit.add(hm.get("readBit"));
						Not.notID.add(hm.get("notificationID"));
						Not.poolingID.add(hm.get("poolingID"));
						Not.type.add(hm.get("type"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//						
			}

		//	progressDialog.dismiss();
		}
	}


