package com.example.taxipooling;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class NestedOtherNotification extends Fragment {
	static ArrayList<String> sender = new ArrayList<String>();
	static ArrayList<String> source = new ArrayList<String>();
	static ArrayList<String> dest = new ArrayList<String>();
	static ArrayList<String> date_Time = new ArrayList<String>();
	static ArrayList<String> pId = new ArrayList<String>();
	
	ListView lv;
	int image_id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v= inflater.inflate(R.layout.notification_other, container, false);
		lv = (ListView) v.findViewById(R.id.other_notificationList);

		NotificationAdapter adapter;
		//new AsyncNotificatoin(getActivity()).execute();

		//ADDING NOTIFICATION INTO NOTIFICATION LIST_______________________________________________(!)

		image_id = R.drawable.picon_selected;
		adapter = new NotificationAdapter(getActivity(),Not.userID,sender, source, dest, date_Time, pId);
		lv.setAdapter(adapter);
		
		
		return v;

	}
}
