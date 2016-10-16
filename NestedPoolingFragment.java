package com.example.taxipooling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
//import com.example.taxipooling.Poolings.DownloadTask;
//import com.example.taxipooling.Poolings.ParserTask;

public class NestedPoolingFragment extends Fragment {

	static final int TIME_DIALOG_ID = 1111;
	private int hour,minute;


	JSONArray jArray;
	JSONObject jObject;
	Dialog _listDialog;
	int _listItemNumber;


	boolean srcFlag = false, destFlag = false;
	String srcLoc, desLoc;
	double srcLat, srcLng, desLat, desLng;
	EditText src, dest, totalSeats;
	TextView date, time;
	Button recent;
	Spinner vehicleSpinner;
	ImageView srcButton, destButton,send;
	Validating valid;
	String Veh=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.create_poolings, container, false);

		src = (EditText) v.findViewById(R.id.source);
		dest = (EditText) v.findViewById(R.id.destination);
		date = (TextView) v.findViewById(R.id.date);
		time = (TextView) v.findViewById(R.id.time);
		send = (ImageView) v.findViewById(R.id.send);
		srcButton = (ImageView) v.findViewById(R.id.sourceSearch);
		destButton = (ImageView) v.findViewById(R.id.destsearch);
		vehicleSpinner = (Spinner) v.findViewById(R.id.spVehicles);
		totalSeats = (EditText) v.findViewById(R.id.etTotalSeats);

		//CHANGING COLOR OF SOURCE AND DESTINATION BUTTON
		send.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
		srcButton.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
		destButton.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
		
		//		recent = (Button) v.findViewById(R.id.btn_recentPooling);
		final ArrayList<String> vehicleList = new ArrayList<String>();
		vehicleList.add("Select Vehicle");
		vehicleList.add("Motorcycle");
		vehicleList.add("Car");
		vehicleList.add("Rickshaw");
		vehicleList.add("Wagon");
		ArrayAdapter ad = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, vehicleList);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vehicleSpinner.setAdapter(ad);

		vehicleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position==0){
					Toast.makeText(getActivity(), "Please select a valid value.", Toast.LENGTH_LONG).show();
				}
				else
				{
					Veh = vehicleList.get(position);
				}


			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		/*		rpSpinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new MyAsyncTaskForGettingPoolings().execute();

			}
		});*/

		/*		Button b= (Button) v.findViewById(R.id.bTemp);
		final EditText et = (EditText) v.findViewById(R.id.etTemp);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int image_id = R.drawable.picon_selected;
//				Not.senderList.add(1, et.getText().toString());
//				Not.srcDesList.add(1, "Aabpara to barakahoo");
//				NotificationAdapter ad = new NotificationAdapter(getActivity(), image_id, Not.senderList, Not.srcDesList);
//				ad.notifyDataSetChanged();

			}
		});
		 */	


		//DISABLING POP UP KEYBOARD
		//date.setInputType(InputType.TYPE_NULL);      
		/*	if (android.os.Build.VERSION.SDK_INT >= 11)   
		{  
		    date.setRawInputType(InputType.TYPE_CLASS_TEXT);  
		    date.setTextIsSelectable(true);  
		}*/
		//DISABLING POP UP KEYBOARD
		//time.setInputType(InputType.TYPE_NULL);
		/*if (android.os.Build.VERSION.SDK_INT >= 11)   
		{  
		    time.setRawInputType(InputType.TYPE_CLASS_TEXT);  
		    time.setTextIsSelectable(true);  
		}*/
		//TIME ON CLICK LISTENER STARTING HERE.......................................................
		time.setOnClickListener(new OnClickListener() {
			final Calendar c=Calendar.getInstance();
			final int min = c.get(Calendar.MINUTE);
			final int hour = c.get(Calendar.HOUR);


			@Override
			public void onClick(View v) {
				TimePickerDialog tpd = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
					final Calendar c = Calendar.getInstance();

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

						c.set(Calendar.HOUR, hourOfDay);
						c.set(Calendar.MINUTE, minute);
						String currentTime = new SimpleDateFormat("HH:mm:00").format(c.getTime());
						time.setText(currentTime);
					}
				}, hour, min, true);
				tpd.show();

			}
		});//TIME ON CLICK LISTENER ENDING HERE......................................................

		//DATE ON CLICK LISTENER STARTING HERE.......................................................
		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {   


				final Calendar c=Calendar.getInstance();
				int	day=c.get(Calendar.DAY_OF_MONTH);
				int	month=c.get(Calendar.MONTH);
				int	year=c.get(Calendar.YEAR);
				final String currentDate1=new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
				DatePickerDialog dpd=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int cyear, int monthOfYear,
							int dayOfMonth) {
						try{
							view.setMinDate(new Date().getTime());
						}catch(Exception e){
							Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
						}
						c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						c.set(Calendar.MONTH, monthOfYear);
						c.set(Calendar.YEAR, cyear);
						String currentDate2=new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
						date.setText(currentDate2);
					}
				}, year, month, day);
				dpd.show();


			}
		});


		//DATE ON CLICK LISTENER ENDING HERE.......................................................


		valid = new Validating(getActivity());

		//temporary...............
		//		src.setText("barakaho");
		//		dest.setText("aabpara islamabad");
		src.setText("faizabad bus stop islamabad");
		dest.setText("arid agriculture university rawalpindi");


		// source search listener_____________________________________________________________($)
		srcButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String location = src.getText().toString();
				// GETTING LOCATION FOR SOURCE....
				srcFlag = true;
				destFlag = false;
				if (location == null || location.equals("")) {
					Toast.makeText(getActivity(), "No Place is entered",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String url = "https://maps.googleapis.com/maps/api/geocode/json?";
				try {
					// encoding special characters like space in the user input
					// place
					location = URLEncoder.encode(location, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Toast.makeText(getActivity(), "source location" + location,
						Toast.LENGTH_LONG).show();
				String address = "address=" + location;
				String sensor = "sensor=false";

				// url , from where the geocoding data is fetched
				url = url + address + "&" + sensor;

				// Instantiating DownloadTask to get places from Google
				// Geocoding service
				// in a non-ui thread
				if (valid.isConnectingToInternet()) {
					DownloadTask downloadTask = new DownloadTask();
					// Start downloading the geocoding places
					downloadTask.execute(url);
				} else {
					Toast.makeText(getActivity(),"Problem in network connectivity..",
							Toast.LENGTH_LONG).show();
				}

			}
		});//ENDING SOURCE BUTTON CLICK LISTENER________________________________________________($)

		// DESTINATION SEARCH LISTENER____________________________________________________________(%)
		destButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// GETTING LOCATION FOR DESTINATION
				String location = src.getText().toString();
				destFlag = true;
				srcFlag = false;
				location = dest.getText().toString();
				if (location == null || location.equals("")) {
					Toast.makeText(getActivity(), "No Place is entered",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String url = "https://maps.googleapis.com/maps/api/geocode/json?";
				try {
					// encoding special characters like space in the user input
					// place
					location = URLEncoder.encode(location, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Toast.makeText(getActivity(),
						"destination location" + location, Toast.LENGTH_LONG)
						.show();
				String address = "address=" + location;
				String sensor = "sensor=false";

				// url , from where the geocoding data is fetched
				url = url + address + "&" + sensor;

				// Instantiating DownloadTask to get places from Google
				// Geocoding service
				// in a non-ui thread
				if (valid.isConnectingToInternet()) {
					DownloadTask downloadTask2 = new DownloadTask();
					// Start downloading the geocoding places
					downloadTask2.execute(url);
				} else {
					Toast.makeText(getActivity(),
							"Problem in network connectivity..",
							Toast.LENGTH_LONG).show();
				}

			}
		});//ENDING DESTINATION SEARCH LISTENER______________________________________________________(%)

		//STARTING RECENT POOLING BUTTON LISTENER____________________________________________________!
		//		recent.setOnClickListener(new OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		////				Intent i = new Intent(getActivity(), RecentPoolings.class);
		////				startActivity(i);
		//				Dialog _recentPoolings = new Dialog(getActivity());
		//				_recentPoolings.setContentView(R.layout.recent_poolings);
		//				_recentPoolings.setTitle("Recent Poolings");
		//				ListView poolingsList = (ListView) _recentPoolings.findViewById(R.id.list_recentPoolings);
		//				
		//				ArrayList<String> srcList = new ArrayList();
		//				ArrayList<String> destList = new ArrayList();
		//				for(int i=0;i<Pooling.listOfPoolings.size();i++){
		//					//srcdes= Pooling.listOfPoolings.get(i).get(1)+" to "+Pooling.listOfPoolings.get(i).get(2);
		//					Toast.makeText(getActivity(), Pooling.listOfPoolings.get(i).get(1)+"  "+Pooling.listOfPoolings.get(i).get(1),	Toast.LENGTH_LONG).show();
		//					srcList.add(Pooling.listOfPoolings.get(i).get(1));
		//					destList.add(Pooling.listOfPoolings.get(i).get(2));
		//				}
		//				PoolingsAdaptor pA = new PoolingsAdaptor(getActivity(), R.drawable.picon, srcList, destList, "temp Date/time");
		//				poolingsList.setAdapter(pA);
		//				_recentPoolings.show();
		//				
		//			}
		//		});
		//ENDING RECENT POOLING BUTTON LISTENER____________________________________________________!



		// SEND BUTTON ON CLICK LISTENER______________________________________________________________(&)
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());


				if(totalSeats.getText().toString().matches("0") || totalSeats.getText().toString().matches("") )
				{
					alert.setMessage("Please enter the valid value for seats.");
					alert.setTitle("Warning");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();

						}
					});
					alert.show();
				}
				else if(Veh==null)
				{
					alert.setMessage("Please select the valid value for vehicle.");
					alert.setTitle("Warning");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();

						}
					});
					alert.show();
				}
				else
				{
					Pooling.seats=totalSeats.getText().toString();
					Pooling.vehicle= Veh;
					// getting routepoints function call
					if(date.getText().toString().matches("") && !(time.getText().toString().matches(""))){
						Toast.makeText(getActivity(), "Please mention Date also...", Toast.LENGTH_LONG).show();
					}
					if(!(date.getText().toString().matches("")) && time.getText().toString().matches("")){
						Toast.makeText(getActivity(), "Please mention Time also...", Toast.LENGTH_LONG).show();
					}
					Pooling.date= date.getText().toString();
					Pooling.time= time.getText().toString();

					if(Pooling.date.matches("") && Pooling.time.matches(""))
					{
						Pooling.status = "start";
					}
					else
					{
						Pooling.status= "pending";
						/*Date compare=null;

					String alarmDate=Pooling.date+" "+Pooling.time;
					try {
					 compare=new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(alarmDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
					}
					Alarm_Manager alarm = new Alarm_Manager();
					Calendar c = Calendar.getInstance();
					c.setTime(compare);
					//alarm.setAlarm(getApplicationContext(), cal, 2, "1", "Pooling time ho gi ohy");
					alarm.setAlarm(getActivity(), c, 1, "1", "You have one Pooling right now..");*/
					}
					Toast.makeText(getActivity(), "status: "+Pooling.status, Toast.LENGTH_LONG).show();
					findDirections(srcLat, srcLng, desLat, desLng,
							GMapV2Direction.MODE_DRIVING);
				}

			}

		});
		// ENDING SEND BUTTON ON CLICK LISTENER___________________________________________________________(&)

		return v;
	}



	// DOWNLOAD URL FUNCTION TO CONNECT AND READ DATA FROM URL...
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));
			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();
			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;
	}

	// A class, to download Places from Geocoding webservice
	private class DownloadTask extends AsyncTask<String, Integer, String> {

		String data = null;
		ProgressDialog progressDialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Getting location...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result) {

			// Instantiating ParserTask which parses the json data from
			// Geocoding webservice
			// in a non-ui thread
			ParserTask parserTask = new ParserTask();

			// Start parsing the places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);

			progressDialog.dismiss();
		}
	}

	// A class to parse the Geocoding Places in non-ui thread
	class ParserTask extends
	AsyncTask<String, Integer, List<HashMap<String, String>>> {

		JSONObject jObject;

		// Invoked by execute() method of this object
		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {

			List<HashMap<String, String>> places = null;
			GeocodeJSONParser parser = new GeocodeJSONParser();

			try {

				jObject = new JSONObject(jsonData[0]);

				/** Getting the parsed data as a an ArrayList */
				places = parser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<HashMap<String, String>> list) {

			// Clears all the existing markers
			// mMap.clear();
			try{
				for (int i = 0; i < list.size(); i++) {

					// Creating a marker
					// MarkerOptions markerOptions = new MarkerOptions();

					// Getting a place from the places list
					HashMap<String, String> hmPlace = list.get(i);

					if (srcFlag) {
						// Getting latitude of the place
						srcLat = Double.parseDouble(hmPlace.get("lat"));
						Pooling.srcLat = srcLat;
						// Getting longitude of the place
						srcLng = Double.parseDouble(hmPlace.get("lng"));
						Pooling.srcLng = srcLng;
						// Getting name
						srcLoc = hmPlace.get("formatted_address");
						Toast.makeText(getActivity(), srcLoc + " source walaaaa ",
								Toast.LENGTH_LONG).show();
						Pooling.Source = srcLoc;
						src.setText(srcLoc);
						srcFlag = false;
					}
					if (destFlag) {
						// Getting latitude of the place
						desLat = Double.parseDouble(hmPlace.get("lat"));

						// Getting longitude of the place
						desLng = Double.parseDouble(hmPlace.get("lng"));

						// Getting name
						desLoc = hmPlace.get("formatted_address");
						Toast.makeText(getActivity(),
								desLoc + " destinatino walaaa ", Toast.LENGTH_LONG)
								.show();
						Pooling.desLat = desLat;
						Pooling.desLng = desLng;
						Pooling.Destination = desLoc;
						dest.setText(desLoc);
						destFlag = false;
						break;
					}

					// LatLng latLng = new LatLng(lat, lng);

					// Setting the position for the marker
					// markerOptions.position(latLng);

					// Setting the title for the marker
					// markerOptions.title(name);

					// Placing a marker on the touched position
					// mMap.addMarker(markerOptions);

					// Locate the first location
					// if(i==0)
					// mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
				}
			}
			catch(Exception e){
				
			}
		}
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 */


	// setting date and time function________________________________________________(@)

	//ENDING SETTING DATE FUNCTION ___________________________________________________(@)

	// Used to convert 24hr format to 12hr format with AM/PM values_________________________(!)
	// ENDING UPDATE TIME FUNCTION_____________________________________________________ (!)

	// *to get routepoints from getdirectionAsyncTask class......
	public void findDirections(double fromPositionDoubleLat,
			double fromPositionDoubleLong, double toPositionDoubleLat,
			double toPositionDoubleLong, String mode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(GetDirectionAsyncTask.USER_CURRENT_LAT,
				String.valueOf(fromPositionDoubleLat));
		map.put(GetDirectionAsyncTask.USER_CURRENT_LONG,
				String.valueOf(fromPositionDoubleLong));
		map.put(GetDirectionAsyncTask.DESTINATION_LAT,
				String.valueOf(toPositionDoubleLat));
		map.put(GetDirectionAsyncTask.DESTINATION_LONG,
				String.valueOf(toPositionDoubleLong));
		map.put(GetDirectionAsyncTask.DIRECTIONS_MODE, mode);

		if (valid.isConnectingToInternet()) {
			GetPointsAsyncTask asyncTask = new GetPointsAsyncTask(this);
			asyncTask.execute(map);
		} else {
			Toast.makeText(getActivity(), "Problem in network connectivity..",
					Toast.LENGTH_LONG).show();
		}
	}
	// */
	//	private void onbackpress() {
	//		// TODO Auto-generated method stub
	//		Intent i=new Intent(getActivity(), MainActivity.class);
	//		
	//		startActivity(i);
	//
	//	}
}
