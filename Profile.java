package com.example.taxipooling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link Profile#newInstance} factory method to create an instance of this
 * fragment.
 * 
 */
public class Profile extends Fragment{
	// TODO: Rename parameter arguments, choose names that match


	public Profile() {
		// Required empty public constructor
	}

	float ratingValue;
	int poolingValue;
	String userTypeValue;

	RatingBar ratingbar;
	TextView ratingtext, createdPooling, userType, uNAME,uLOC;
	ImageView editProfile;
	ImageView image;
	View v, Vone, Vtwo;
	static Customlistadapter adapter;
	ListView lv;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		v= inflater.inflate(R.layout.fragment_a, container, false);

		Validating valid = new Validating(getActivity());
		if(!valid.isConnectingToInternet()){
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.VISIBLE);         
		}

		adapter = new Customlistadapter(getActivity(), User.fromId,User.comments, User.commentors);
		lv = (ListView) v.findViewById(R.id.commentList);
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		ratingtext = (TextView)v.findViewById(R.id.ratingComment);
		ratingbar = (RatingBar)v.findViewById(R.id.ratingBar);
		createdPooling = (TextView) v.findViewById(R.id.poolingCreatedNumber);
		userType = (TextView) v.findViewById(R.id.userType);
		uNAME= (TextView) v.findViewById(R.id.userName);
		uLOC= (TextView) v.findViewById(R.id.ulocation);
		editProfile = (ImageView) v.findViewById(R.id.editProfile);
		image= (ImageView) v.findViewById(R.id.userImage);
		Vtwo = v.findViewById(R.id.viewTwo);
		Vone = v.findViewById(R.id.viewOne);


		editProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ProfileActivity.class);
				startActivity(intent);

			}
		});

		//CHANGING RATING BAR STARS COLORS
		LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
		//CHANGING EDIT BUTTON COLOR
		editProfile.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
		//CHANGING COLOR OF LINES
		Vtwo.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
		Vone.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));

		uLOC.setText(User.LOC);
		uNAME.setText(User.userNAME);
		//ratingValue=Float.parseFloat(User.totalRATING);
		poolingValue=User.poolingCREATED;
		if(User.userTYPE.matches("p"))
			userTypeValue="passenger";
		else
			userTypeValue="driver"; 

		//setRating(ratingValue);
		setPooling(poolingValue);
		setUserType(userTypeValue);
		if(User.totalRATING.matches("")){
			ratingValue=0f;
		}
		else{
			ratingValue=Float.parseFloat(User.totalRATING);
		}
		sRating(ratingValue);

		//comment list view
		//  for(int i=0; i<User.comments.size();i++){
		//      Toast.makeText(getActivity(), User.comments.get(i), Toast.LENGTH_LONG).show();
		//  }
		//   String color_names[] = {"red", "green", "blue", "yellow", "pink", "brown"};
		Integer image_id = R.drawable.picon_selected;

		


		return v;

	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		try {
			adapter.notifyDataSetChanged();
		} catch (Exception e) {
			
		}
		
		
		super.setUserVisibleHint(isVisibleToUser);
	}
	//set rating function
	public void sRating(float rating){
		float value=(float) 0.0;
		//value=(rating/20)*10;
		ratingbar.setRating(rating);
		//ratingbar.setStepSize(0.25f);

		if(rating>=4.0){
			//ratingtext.setText("Excellent "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
			ratingtext.setText(ratingbar.getRating()+" excelent");
		}
		else if(rating>=3.0){
			//ratingtext.setText("Good "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
			ratingtext.setText(ratingbar.getRating()+" good");
		}
		else if(rating>=1.0){
			//ratingtext.setText("Fair "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
			ratingtext.setText(ratingbar.getRating()+" fair");
		}
		else{
			//ratingtext.setText("Poor "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
			ratingtext.setText(ratingbar.getRating()+" poor");
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Validating valid = new Validating(getActivity());
		if(!valid.isConnectingToInternet()){
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.VISIBLE);         
		}else{
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.GONE);  
		}

		if(User.image!=null || User.image!=""){
			image.setImageBitmap(User.stob(User.image));
		}
		//CHANGING RATING BAR STARS COLORS
		LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
		//CHANGING EDIT BUTTON COLOR
		editProfile.setColorFilter(Color.parseColor(MainActivity.actionbarColor), Mode.SRC_ATOP);
		//CHANGING COLOR OF LINES
		Vtwo.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
		Vone.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
adapter.notifyDataSetChanged();
		super.onResume();
	}
	//set pooling funtion
	public void setPooling(int value){
		createdPooling.setText(value+"");
	}

	//set userType function
	public void setUserType(String s){
		userType.setText(s);
	}

	public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		int targetWidth = 125;
		int targetHeight = 125;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
				targetHeight,Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), 
						((float) targetHeight)) / 2),
						Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, 
				new Rect(0, 0, sourceBitmap.getWidth(),
						sourceBitmap.getHeight()), 
						new Rect(0, 0, targetWidth,
								targetHeight), null);
		return targetBitmap;
	}
}