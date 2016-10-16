package com.example.taxipooling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Route extends Fragment {

	public Route() {
		// Required empty public constructor
	}

	MapView mMapView;
	private GoogleMap googleMap;
	ArrayList<LatLng> markerPoints;
	LatLng origin,dest;

	private Polyline newPolyline;
	private boolean isTravelingToParis = false;
	private int width, height;
	private LatLngBounds latlngBounds;
	View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		v = inflater.inflate(R.layout.fragment_e, container,false);

		Validating valid = new Validating(getActivity());
		if(!valid.isConnectingToInternet()){
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.VISIBLE);			
		}


		mMapView = (MapView) v.findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);
		getSreenDimensions();
		//setSpinner
		Spinner spinner = (Spinner) v.findViewById(R.id.createdPoolings);
		ArrayList<String> srcdeslist = new ArrayList();
		String srcdes;
		srcdes="Select Route";
		srcdeslist.add(srcdes);
		for(int i=0;i<Pooling.listOfPoolings.size();i++){
			srcdes= Pooling.listOfPoolings.get(i).get(1)+" to "+Pooling.listOfPoolings.get(i).get(2);
			srcdeslist.add(srcdes);
		}
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, srcdeslist);
		//ArrayAdapter.createFromResource(getActivity(), android.R.layout.simple_spinner_item,list);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				/*	double latitude = 0.0;
				double longitude = 0.0;
				if(Pooling.srcLat==0.0){
					latitude = 0.0;
					longitude = 0.0;
				}
				else{
					latitude= Pooling.listOfPoolings.get(position).g;
					longitude=Pooling.srcLng;
				}*/
				if(position!=0)
				{
					Toast.makeText(getActivity(), "hello : "+position, Toast.LENGTH_LONG).show();
					//				Pooling.srcLat= Double.parseDouble(Pooling.listOfPoolings.get(position).get(4));
					//				Pooling.srcLng= Double.parseDouble(Pooling.listOfPoolings.get(position).get(5));
					//				Pooling.desLat=Double.parseDouble(Pooling.listOfPoolings.get(position).get(6));
					//				Pooling.desLng=Double.parseDouble(Pooling.listOfPoolings.get(position).get(7));
					origin=new LatLng(Double.parseDouble(Pooling.listOfPoolings.get(position-1).get(4)), 
							Double.parseDouble(Pooling.listOfPoolings.get(position-1).get(5)));
					dest= new LatLng(Double.parseDouble(Pooling.listOfPoolings.get(position-1).get(6)),
							Double.parseDouble(Pooling.listOfPoolings.get(position-1).get(7)));
					Toast.makeText(getActivity(), origin+" "+dest, Toast.LENGTH_LONG).show();

					// create marker
					MarkerOptions marker1 = new MarkerOptions().position(origin);
					MarkerOptions marker2= new MarkerOptions().position(dest);

					// marker location name
					findDirections( origin.latitude, origin.longitude,dest.latitude, dest.longitude, GMapV2Direction.MODE_DRIVING );
					//handleGetDirectionsResult(User.routePoints);
					// Changing marker icon
					marker1.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
					//*
					// adding marker
					try{
						googleMap.clear();
						googleMap.addMarker(marker1);
						googleMap.addMarker(marker2);
					}
					catch(Exception e){
						Toast.makeText(getActivity(), "Adding markar "+e.toString(), Toast.LENGTH_LONG).show();
					}
					 mMapView.getMapAsync(new OnMapReadyCallback() {
						public void onMapReady(GoogleMap gMap) {
							// .googleMap..
							googleMap = gMap;
							CameraPosition cameraPosition = new CameraPosition.Builder()
									.target(new LatLng(origin.latitude, origin.longitude)).zoom(15).build();
							googleMap.animateCamera(CameraUpdateFactory
									.newCameraPosition(cameraPosition));
						}
					});
					// latitude and longitude

					//enabling location button
					//googleMap.setMyLocationEnabled(true);
					//* Perform any camera updates here

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		mMapView.onResume();// needed to get the map to display immediately

		try {
			MapsInitializer.initialize(getActivity().getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}




		//
		return v;
	}

	@Override
	public void onResume() {
		Validating valid = new Validating(getActivity());
		if(!valid.isConnectingToInternet()){
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.VISIBLE);         
		}else{
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.GONE);  
		}
		//				latlngBounds = createLatLngBoundsObject(origin, dest);
		//				googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 160));
		// adding marker
		super.onResume();
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if(isVisibleToUser){
			
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	@Override
	public void onPause() {
		super.onPause();
		//	mMapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//		mMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		//		mMapView.onLowMemory();
	}




	// to add route on map...........
	public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
		//public void handleGetDirectionsResult() {
		try{
			PolylineOptions rectLine = new PolylineOptions().width(8).color(Color.RED);


			for(int i = 0 ; i < directionPoints.size() ; i++) 
			{          
				rectLine.add(directionPoints.get(i));
				if(i==0 || i==directionPoints.size()-1){
					Circle circle = googleMap.addCircle(new CircleOptions().center(new LatLng(directionPoints.get(i).latitude,directionPoints.get(i).longitude)).radius(10).fillColor(Color.RED).strokeColor(Color.BLACK).strokeWidth(6));
				}
			}
			if (newPolyline != null)
			{
				newPolyline.remove();
			}
			newPolyline = googleMap.addPolyline(rectLine);
			if (isTravelingToParis)
			{
				latlngBounds = createLatLngBoundsObject(origin, dest);
				googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 160));
			}
			else
			{
				latlngBounds = createLatLngBoundsObject(origin, dest);
				googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 160));
			}
		}
		catch(Exception e){
			Toast.makeText(getActivity(), "handle function"+e.toString(), Toast.LENGTH_LONG).show();
		}

	}

	private void getSreenDimensions()
	{
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight(); 
	}

	private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
	{
		if (firstLocation != null && secondLocation != null)
		{
			LatLngBounds.Builder builder = new LatLngBounds.Builder();    
			builder.include(firstLocation).include(secondLocation);

			return builder.build();
		}
		return null;
	}
	//*	
	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(GetDirectionAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
		map.put(GetDirectionAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
		map.put(GetDirectionAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
		map.put(GetDirectionAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
		map.put(GetDirectionAsyncTask.DIRECTIONS_MODE, mode);

		GetDirectionAsyncTask asyncTask = new GetDirectionAsyncTask(this);
		asyncTask.execute(map);	
	}
	//*/
	/*	
	public void setSpinnerContent(View v){
		Spinner spinner = (Spinner) v.findViewById(R.id.createdPoolings);
		ArrayList<String> list = new ArrayList();
		list.add("barakaho to abpara");
		list.add("barakaho to faizabad");
//		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

	}
	 */
	//	private void onbackpress() {
	//		// TODO Auto-generated method stub
	//		Intent i=new Intent(getActivity(), MainActivity.class);
	//		
	//		startActivity(i);
	//
	//	}

}
