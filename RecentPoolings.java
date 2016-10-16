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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RecentPoolings extends Activity {
	
	 int list_item_position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_poolings);
		



		//SHOWING RECENT POOLINGS IN LIST OF FRAGMENT B________________________________ (2)

		//	_listDialog = new Dialog(getActivity());
		//	_listDialog.setTitle("Recent Poolings");
		//	_listDialog.setContentView(R.layout.recent_poolings);
		ListView poolingsList = (ListView) findViewById(R.id.list_recentPoolings);
		
		
		
		//new MyAsyncTaskForGettingPoolings().execute();
		ArrayList<String> srcList = new ArrayList();
		ArrayList<String> destList = new ArrayList();
		ArrayList<String> dateList = new ArrayList();
		for(int i=0;i<Pooling.listOfPoolings.size();i++){
			//srcdes= Pooling.listOfPoolings.get(i).get(1)+" to "+Pooling.listOfPoolings.get(i).get(2);
			srcList.add(Pooling.listOfPoolings.get(i).get(1));
			destList.add(Pooling.listOfPoolings.get(i).get(2));
			dateList.add(Pooling.listOfPoolings.get(i).get(8)+" : "+Pooling.listOfPoolings.get(i).get(9));
			Toast.makeText(getApplicationContext(), dateList.get(i), Toast.LENGTH_LONG).show();
		}
//		PoolingsAdaptor pA = new PoolingsAdaptor(getApplicationContext(), R.drawable.picon, srcList, destList, dateList);
//		poolingsList.setAdapter(pA);
		//	Button complete = (Button) _listDialog.findViewById(R.id.acceptButton_view_not);
		//	Button cancel = (Button) _listDialog.findViewById(R.id.cancel_recentPoolingsItem);
		poolingsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,View view, int position, long id) {
				list_item_position=position;
				Toast.makeText(getApplicationContext(), "hello recentPooling :  "+position, Toast.LENGTH_LONG).show();

			}

		});
	

		
//		poolingsList.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//					int position, long id) {
//				Toast.makeText(getApplicationContext(), "hello :  "+position, Toast.LENGTH_LONG).show();
//				
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		//ENDING SHOWING RECENT POOLINGS IN LIST OF FRAGMENT B________________________________ (2)*/

	}



}
