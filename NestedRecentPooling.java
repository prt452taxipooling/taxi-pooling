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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

//import com.example.taxipooling.Poolings.DownloadTask;
//import com.example.taxipooling.Poolings.ParserTask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class NestedRecentPooling extends Fragment {

	static PoolingsAdaptor pA;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.recent_poolings, container, false);
		
		ListView poolingsList = (ListView) v.findViewById(R.id.list_recentPoolings);
		
		ArrayList<String> srcList = new ArrayList();
		ArrayList<String> destList = new ArrayList();
		ArrayList<String> dateList = new ArrayList();
		for(int i=0;i<Pooling.listOfPoolings.size();i++){
			//srcdes= Pooling.listOfPoolings.get(i).get(1)+" to "+Pooling.listOfPoolings.get(i).get(2);
			srcList.add(Pooling.listOfPoolings.get(i).get(1));
			destList.add(Pooling.listOfPoolings.get(i).get(2));
			dateList.add(Pooling.listOfPoolings.get(i).get(8)+" / "+Pooling.listOfPoolings.get(i).get(9));
			Toast.makeText(getActivity(), dateList.get(i), Toast.LENGTH_LONG).show();
		}
		pA = new PoolingsAdaptor(getActivity(), R.drawable.picon, srcList, destList, dateList);
		poolingsList.setAdapter(pA);
		
		return v;
	}

}
