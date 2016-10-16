package com.example.taxipooling;

import java.util.ArrayList;
import java.util.Map;
import org.w3c.dom.Document;
import com.google.android.gms.maps.model.LatLng;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class GetDirectionAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList<LatLng>>
{
	public static final String USER_CURRENT_LAT = "user_current_lat";
	public static final String USER_CURRENT_LONG = "user_current_long";
	public static final String DESTINATION_LAT = "destination_lat";
	public static final String DESTINATION_LONG = "destination_long";
	public static final String DIRECTIONS_MODE = "directions_mode";
	private final Route mContext;
	private Exception exception;
	private ProgressDialog progressDialog;

	public GetDirectionAsyncTask(Route context)
	{
		super();
		this.mContext = context;
	}

	public void onPreExecute()
	{
		progressDialog = new ProgressDialog(mContext.getActivity());
		progressDialog.setMessage("Finding Route..");
		progressDialog.show();
	}

	@Override
	public void onPostExecute(ArrayList result)
	{
		progressDialog.dismiss();
		if (exception == null)
		{
			Toast.makeText(mContext.getActivity(),"EEEE"+User.routePoints.size()+":"+result.size(), Toast.LENGTH_LONG).show();
			 mContext.handleGetDirectionsResult(result);    		    
			//User.routePoints.add(new LatLng(result.get(i));
		}
		else
		{
			if (exception != null)
			processException();
		}
	}

	@Override
	protected ArrayList<LatLng> doInBackground(Map<String, String>... params)
	{
		Map<String, String> paramMap = params[0];
		try
		{
			LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
			LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
			GMapV2Direction md = new GMapV2Direction();
			Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
			ArrayList<LatLng> directionPoints = md.getDirection(doc);
			return directionPoints;
		}
		catch (Exception e)
		{
			exception = e;
			return null;
		}
	}

	private void processException()
	{
		Toast.makeText(mContext.getActivity(), "Error retriving data", Toast.LENGTH_LONG).show();
	}


}