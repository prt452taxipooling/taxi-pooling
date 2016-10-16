package com.example.taxipooling;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationAdapter extends ArrayAdapter<String> {
	ArrayList<String> s,src,des,dt,idp;
	ArrayList<String>  image_id;
	Context context;
	
	public NotificationAdapter(Context context,ArrayList<String> image_id, ArrayList<String> s, ArrayList<String> src, ArrayList<String> des, ArrayList<String> datetime, ArrayList<String> idp){
		super(context, R.layout.fragment_c_list_items,  s);
		// TODO Auto-generated constructor stub
		this.src = src;
		this.s = s;
		this.image_id = image_id;
		this.context = context;
		this.des = des;
		this.dt = datetime;
		this.idp = idp;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View single_row = inflater.inflate(R.layout.fragment_c_list_items, null, true);
		TextView tv_sender = (TextView) single_row.findViewById(R.id.senderName);
		TextView tv_src = (TextView) single_row.findViewById(R.id.src);
		TextView tv_des = (TextView) single_row.findViewById(R.id.des);
		TextView tv_dateTime = (TextView) single_row.findViewById(R.id.dateTime);
		ImageView imageView = (ImageView) single_row.findViewById(R.id.senderImage);
		tv_des.setText(des.get(position));
		tv_sender.setText(s.get(position));
		tv_src.setText(src.get(position));
		tv_dateTime.setText(dt.get(position));
		try{
			String photo=null;
			photo=User.userImages.get(image_id.get(position));
			if(photo==null || photo.matches("")){
				//Toast.makeText(context, "nichay imageValue: "+photo, Toast.LENGTH_LONG).show();
				imageView.setImageResource(R.drawable.picon_selected);
			}
			else{
				//Toast.makeText(context, "oper imageValue: "+photo, Toast.LENGTH_LONG).show();
				imageView.setImageBitmap(User.stob(photo));

			}
			notifyDataSetChanged();
		}
		catch(Exception e){
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
		
		
		return single_row; 
	}
}
