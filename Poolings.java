package com.example.taxipooling;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Poolings extends Fragment {

	View v;

	public Poolings() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.fragment_b, container, false);

		Validating valid = new Validating(getActivity());
		if(!valid.isConnectingToInternet()){
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.VISIBLE);			
		}

		final Button create = (Button) v.findViewById(R.id.bone);
		final Button recent = (Button) v.findViewById(R.id.btwo);
		//CHANGING COLOR OF BUTTONS......................................
		create.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
		create.setTextColor(Color.BLACK);
		recent.setTextColor(Color.BLACK);
		
		Fragment f = new NestedPoolingFragment();
		android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.replace(R.id.one, f).commit();

		create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//create.setBackgroundResource(Color.parseColor(MainActivity.actionbarColor));
				create.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
				create.setTextColor(Color.BLACK);
				recent.setBackgroundColor(Color.WHITE);
				recent.setTextColor(Color.BLACK);
				Fragment f = new NestedPoolingFragment();
				android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
				transaction.replace(R.id.one, f).commit();

			}
		});

		recent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				create.setBackgroundColor(Color.WHITE);
				create.setTextColor(Color.BLACK);
				recent.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
				recent.setTextColor(Color.BLACK);
				Fragment f = new NestedRecentPooling();
				android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
				// transaction.add(R.id.one, f).commit();
				transaction.replace(R.id.one, f).commit();

			}
		});


		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Validating valid = new Validating(getActivity());
		if(!valid.isConnectingToInternet()){
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.VISIBLE);         
		}
		else{
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.GONE);  
		}

		super.onResume();

	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if(isVisibleToUser){
			Validating valid = new Validating(getActivity());
			if(!valid.isConnectingToInternet()){
				TextView ie= (TextView) v.findViewById(R.id.internetError);
				ie.setVisibility(View.VISIBLE);         
			}else{
				TextView ie= (TextView) v.findViewById(R.id.internetError);
				ie.setVisibility(View.GONE);  
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}



	/*	static final int TIME_DIALOG_ID = 1111;
	private int hour,minute;


	JSONArray jArray;
	JSONObject jObject;
	Dialog _listDialog;
	int _listItemNumber;


	boolean srcFlag = false, destFlag = false;
	String srcLoc, desLoc;
	double srcLat, srcLng, desLat, desLng;
	EditText src, dest, date, time;
	Button send,recent;
	ImageButton srcButton, destButton;
	Validating valid;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_b, container, false);


		src = (EditText) v.findViewById(R.id.source);
		dest = (EditText) v.findViewById(R.id.destination);
		date = (EditText) v.findViewById(R.id.date);
		time = (EditText) v.findViewById(R.id.time);
		send = (Button) v.findViewById(R.id.send);
		srcButton = (ImageButton) v.findViewById(R.id.sourceSearch);
		destButton = (ImageButton) v.findViewById(R.id.destsearch);
		recent = (Button) v.findViewById(R.id.btn_recentPooling);



		rpSpinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new MyAsyncTaskForGettingPoolings().execute();

			}
		});

		Button b= (Button) v.findViewById(R.id.bTemp);
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



		//STARTING date edittext onclick listener____
		final Calendar c=Calendar.getInstance();
		 int	day=c.get(Calendar.DAY_OF_MONTH);
		 int	month=c.get(Calendar.MONTH);
		int	year=c.get(Calendar.YEAR);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		int hour = c.get(Calendar.HOUR);
		String currentTime=new SimpleDateFormat("hh-mm-ss").format(c.getTime());
		String currentDate=new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
		date.setText(currentDate);
		time.setText(currentTime);

		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			final Calendar c=Calendar.getInstance();
		 int	day=c.get(Calendar.DAY_OF_MONTH);
		 int	month=c.get(Calendar.MONTH);
		int	year=c.get(Calendar.YEAR);
		DatePickerDialog dpd=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int cyear, int monthOfYear,
					int dayOfMonth) {
					c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					c.set(Calendar.MONTH, monthOfYear);
					c.set(Calendar.YEAR, cyear);
					String currentDate=new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
					date.setText(currentDate);
			}
		}, year, month, day);
		dpd.show();


			}
		});

		//ENDING date edittext onclick listener________________________________________________________________________(=)

		valid = new Validating(getActivity());

		//temporary...............
		src.setText("barakaho");
		dest.setText("aabpara islamabad");


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
		recent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(getActivity(), RecentPoolings.class);
//				startActivity(i);
				Dialog _recentPoolings = new Dialog(getActivity());
				_recentPoolings.setContentView(R.layout.recent_poolings);
				_recentPoolings.setTitle("Recent Poolings");
				ListView poolingsList = (ListView) _recentPoolings.findViewById(R.id.list_recentPoolings);

				ArrayList<String> srcList = new ArrayList();
				ArrayList<String> destList = new ArrayList();
				for(int i=0;i<Pooling.listOfPoolings.size();i++){
					//srcdes= Pooling.listOfPoolings.get(i).get(1)+" to "+Pooling.listOfPoolings.get(i).get(2);
					Toast.makeText(getActivity(), Pooling.listOfPoolings.get(i).get(1)+"  "+Pooling.listOfPoolings.get(i).get(1),	Toast.LENGTH_LONG).show();
					srcList.add(Pooling.listOfPoolings.get(i).get(1));
					destList.add(Pooling.listOfPoolings.get(i).get(2));
				}
				PoolingsAdaptor pA = new PoolingsAdaptor(getActivity(), R.drawable.picon, srcList, destList, "temp Date/time");
				poolingsList.setAdapter(pA);
				_recentPoolings.show();

			}
		});
		//ENDING RECENT POOLING BUTTON LISTENER____________________________________________________!



		// SEND BUTTON ON CLICK LISTENER______________________________________________________________(&)
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


	 * if(valid.isConnectingToInternet()){ new
	 * MyAsyncTaskForAllEnquire().execute(); } else{
	 * Toast.makeText(getActivity(),
	 * "Problem in network connectivity..",
	 * Toast.LENGTH_LONG).show(); }

				// getting routepoints function call
				findDirections(srcLat, srcLng, desLat, desLng,
						GMapV2Direction.MODE_DRIVING);

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

				// Getting the parsed data as a an ArrayList //
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
	}


	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }



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
	// /
//	private void onbackpress() {
//		// TODO Auto-generated method stub
//		Intent i=new Intent(getActivity(), MainActivity.class);
//		
//		startActivity(i);
//
//	}

	 */
}
