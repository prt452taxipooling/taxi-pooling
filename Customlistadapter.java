package com.example.taxipooling;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Customlistadapter extends ArrayAdapter<String>{
	ArrayList<String> c,ct,img;
	Context context;
	public Customlistadapter(Context context,ArrayList<String> img, ArrayList<String> c, ArrayList<String> ct){
		super(context, R.layout.commentlist_row,  ct);
		// TODO Auto-generated constructor stub
		this.ct = ct;
		this.c = c;
		this.img = img;
		this.context = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View single_row = inflater.inflate(R.layout.commentlist_row, null,
				true);
		TextView textView = (TextView) single_row.findViewById(R.id.comment);
		TextView tvCt = (TextView) single_row.findViewById(R.id.commentor);
		ImageView imageView = (ImageView) single_row.findViewById(R.id.commentorImage);
		textView.setText(c.get(position));
		tvCt.setText(ct.get(position));
		try{
			String photo=null;
			photo=User.userImages.get(img.get(position));
			if(photo==null || photo.matches("")){
				//Toast.makeText(context, "nichay imageValue: "+photo, Toast.LENGTH_LONG).show();
				imageView.setImageResource(R.drawable.picon_selected);
			}
			else{
				//Toast.makeText(context, "oper imageValue: "+photo, Toast.LENGTH_LONG).show();
				imageView.setImageBitmap(User.stob(photo));

			}
			Profile.adapter.notifyDataSetChanged();
		}
		catch(Exception e){
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
		return single_row; 
	}
}
