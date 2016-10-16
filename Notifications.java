package com.example.taxipooling;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link Notifications#newInstance} factory method to create an instance of this
 * fragment.
 * 
 */
public class Notifications extends Fragment {

	View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.fragment_c, container, false);

		Validating valid = new Validating(getActivity());
		if(!valid.isConnectingToInternet()){
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.VISIBLE);			
		}

		final Button cPooling = (Button) v.findViewById(R.id.btnone);
		final Button other = (Button) v.findViewById(R.id.btntwo);
		//CHANGING COLOR OF BUTTONS.....................................
		cPooling.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
		cPooling.setTextColor(Color.BLACK);
		other.setTextColor(Color.BLACK);
		
		Fragment f = new NestedPoolingNotification();
		android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.replace(R.id.NotiFrame, f).commit();

		cPooling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cPooling.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
				cPooling.setTextColor(Color.BLACK);
				other.setBackgroundColor(Color.WHITE);
				other.setTextColor(Color.BLACK);
				Fragment f = new NestedPoolingNotification();
				android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
				transaction.replace(R.id.NotiFrame, f).commit();
				new AsyncNotificatoin().execute();

			}
		});

		other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cPooling.setBackgroundColor(Color.WHITE);
				cPooling.setTextColor(Color.BLACK);
				other.setBackgroundColor(Color.parseColor(MainActivity.actionbarColor));
				other.setTextColor(Color.BLACK);
				Fragment f = new NestedOtherNotification();
				android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
				// transaction.add(R.id.one, f).commit();
				transaction.replace(R.id.NotiFrame, f).commit();

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
		}else{
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.GONE);  
		}
		super.onResume();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if(isVisibleToUser){
			
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	/*
	Integer image_id;
	ListView lv;
	String user_ID;
	Dialog d;
	Dialog srcdes;
	TextView uName, rComment, tPooling;
	ListView cList;
	RatingBar rBar;
	ProgressDialog	progressDialog;
	static ArrayList<String> comments = new ArrayList<String>();
	static ArrayList<String> commentors = new ArrayList<String>();
	static String username="", rating="",userTypeValue="",totalP="";
	static float ratingValue =0f;
	String _poolerSource, _poolerDestination, _poolingID;



	public Notifications() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v= inflater.inflate(R.layout.fragment_c, container, false);
		 lv = (ListView) v.findViewById(R.id.notificationList);
		Validating valid = new Validating(getActivity());
		if(!valid.isConnectingToInternet()){
			TextView ie= (TextView) v.findViewById(R.id.internetError);
			ie.setVisibility(View.VISIBLE);			
			}

		NotificationAdapter adapter;
		//new AsyncNotificatoin(getActivity()).execute();

		//ADDING NOTIFICATION INTO NOTIFICATION LIST_______________________________________________(!)

   		image_id = R.drawable.picon_selected;

   		  adapter = new NotificationAdapter(getActivity(),image_id,Not.senderList, Not.srcList, Not.desList, Not.dateTime, Not.poolingID);

   		 lv.setAdapter(adapter);
   		//ENDING ADDING NOTIFICATION INTO NOTIFICATION LIST________________________________________(!)
//LIST VIEW ONITEMCLICK LISTENER START_____________________________________________________________(&)   		 
   		 lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Not.item_position=position;
				Intent i = new Intent(getActivity(), ViewNotActivity.class);
				startActivity(i);
				user_ID= Not.userID.get(position);
				Not.item_position=position;
				//_poolingID = Not.poolingID.get(position);

				new AsyncUserData().execute();

				 d = new Dialog(getActivity());
				d.setContentView(R.layout.activity_view_not);
				d.setTitle("Profile");


				 uName = (TextView) d.findViewById(R.id.username_view_not);
				 rBar = (RatingBar) d.findViewById(R.id.ratingBar_view_not);
				 rComment = (TextView) d.findViewById(R.id.ratingComment_view_not);
				 tPooling = (TextView) d.findViewById(R.id.poolingCreatedNumber_view_not);
				Button accept = (Button) d.findViewById(R.id.acceptButton_view_not);
				Button reject = (Button) d.findViewById(R.id.rejectButton_view_not);
				//Toast.makeText(getActivity(),username+" : "+ position+" : "+user, Toast.LENGTH_LONG).show();

				Customlistadapter adap = new Customlistadapter(getActivity(), image_id,comments, commentors);
				 ListView cList = (ListView) d.findViewById(R.id.commentList_view_not);
				 cList.setAdapter(adap);

				reject.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						d.dismiss();

					}
				});

				accept.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						d.dismiss();
						srcdes= new Dialog(getActivity());
						srcdes.setTitle("Send your Source and Destination");
						srcdes.setContentView(R.layout.srcdes_poolers);
						Button sb = (Button) srcdes.findViewById(R.id.submit);
						final EditText s = (EditText) srcdes.findViewById(R.id.source);
						final EditText d = (EditText) srcdes.findViewById(R.id.destination);
						sb.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								_poolerSource = s.getText().toString();
								_poolerDestination = d.getText().toString();

								new AsyncTaskPoolers().execute();


							}
						});

						srcdes.show();


					}
				});

			}
		});
//LIST VIEW ONITEMCLICK LISTENER _____________________________________________________________(&)		

		return v;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		try{
		if(isVisibleToUser){
		NotificationAdapter adapter;
		 adapter = new NotificationAdapter(getActivity(),image_id,Not.senderList, Not.srcList, Not.desList, Not.dateTime, Not.poolingID);
		 lv.setAdapter(adapter);
		}
		}catch(Exception e){
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	//ASYNC CLASS FOR GETTING USER DATA ON LIST ITEM CLICK____________________________________#
	class AsyncUserData extends AsyncTask<Object, Void, String> {
		int responseCode;
		String responseBody;
		JSONArray jArray;
		JSONObject jObject;
		private Context context;

		public AsyncUserData(Context cn) {
			// TODO Auto-generated constructor stub
			this.context= cn;
		}

		public AsyncUserData() {
			// TODO Auto-generated constructor stub
		}


		@Override
		protected String doInBackground(Object... params) {
			return postData();
		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Getting user information.....");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
			Toast.makeText(getActivity(), user_ID, Toast.LENGTH_LONG).show();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {

		}

		public String postData() {
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
			HttpConnectionParams.setSoTimeout(httpParameters,5000);
			HttpPost httppost = new HttpPost(Url.SERVER_URL+"getUserData.php");
			try {
				@SuppressWarnings("rawtypes")
				List nameValuePairs = new ArrayList();

				nameValuePairs.add(new BasicNameValuePair("userID", user_ID));
				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());
			} catch (Throwable t) {
				Log.d("Error Time of Login", t + "");
			}
			return responseBody;
		}

		protected void onPostExecute(String responseBody) {
			super.onPostExecute(responseBody);
			//Toast.makeText(LoginActivity.this, responseBody,
			//Toast.LENGTH_LONG).show();
			if(responseBody.matches("fail"))
			{
				Toast.makeText(context, responseBody, Toast.LENGTH_LONG).show();
			//	error.setText("Invalid Username or Password.");
				//error.setVisibility(View.VISIBLE);
			}
			else
			{
				//Toast.makeText(context, responseBody, Toast.LENGTH_LONG).show();
				try {
					jArray = new JSONArray(responseBody);
					comments.clear();
					commentors.clear();
					jObject = jArray.getJSONObject(0);
					username=jObject.getString("username");
					rating=jObject.getString("totalRating");
					totalP=jObject.getString("poolingCreated");

					for(int i=0; i<jArray.length(); i++){
						jObject= jArray.getJSONObject(i);
						comments.add(jObject.getString("comment"));
						commentors.add(jObject.getString("commentor"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//						
			}
			progressDialog.dismiss();
			//setting username.............
			if(username.matches("")){
				uName.setText("hello..");
			}
			else{
				uName.setText(username);
			}
			//setting rating of user...............
			if(rating.matches(""))
			{
				ratingValue=0f;
			}
			else{
				ratingValue=Float.parseFloat(rating);
			}

			rBar.setRating(ratingValue);
			if(ratingValue>=4.0){
				//ratingtext.setText("Excellent "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
				rComment.setText(rBar.getRating()+" / 5"+" (Excelent)");
			}
			else if(ratingValue>=3.0){
				//ratingtext.setText("Good "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
				rComment.setText(rBar.getRating()+" / 5"+" (Good)");
			}
			else if(ratingValue>=1.0){
				//ratingtext.setText("Fair "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
				rComment.setText(rBar.getRating()+" / 5"+" (Fair)");
			}
			else{
				//ratingtext.setText("Poor "+ "( "+2*ratingbar.getRating()+" / "+ratingbar.getNumStars()*4+" )");
				rComment.setText(rBar.getRating()+" / 5"+" (Poor)");
			}
			//setting usertype here.........
			if(User.userTYPE.matches("p"))
				userTypeValue="passenger";
				else
					userTypeValue="driver";	
			//setting total pooling number.....
			tPooling.setText(totalP);
			d.show();
		}
	}

	//ASYNC CLASS FOR SENDING POOLERS DATA_________________________________________________________*
		class AsyncTaskPoolers extends AsyncTask<Object, Void, String> {
			int responseCode;
			String responseBody;
			JSONArray jArray;
			JSONObject jObject;
			private Context context;
			ProgressDialog pd;

			public AsyncTaskPoolers(Context cn) {
				// TODO Auto-generated constructor stub
				this.context= cn;
			}

			public AsyncTaskPoolers() {
				// TODO Auto-generated constructor stub
			}


			@Override
			protected String doInBackground(Object... params) {
				return postData();
			}

			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd = new ProgressDialog(getActivity());
				pd.setMessage("Sending...");
				pd.setIndeterminate(true);
				pd.setCancelable(false);
				pd.show();
				Toast.makeText(getActivity(), user_ID, Toast.LENGTH_LONG).show();
			}

			@SuppressWarnings("unused")
			protected void onProgressUpdate(Integer... progress) {

			}

			public String postData() {
				HttpClient httpclient = new DefaultHttpClient();
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
				HttpConnectionParams.setSoTimeout(httpParameters,5000);
				HttpPost httppost = new HttpPost(Url.SERVER_URL+"insertIntoUserInPoolings.php");
				try {
					@SuppressWarnings("rawtypes")
					List nameValuePairs = new ArrayList();
				String op = "iuip";
					nameValuePairs.add(new BasicNameValuePair("poolerSource", _poolerSource));
					nameValuePairs.add(new BasicNameValuePair("poolerDestination", _poolerDestination));
					//nameValuePairs.add(new BasicNameValuePair("poolingID", Not.poolingID.get(item_position)));
					nameValuePairs.add(new BasicNameValuePair("poolerID", User.userId));
					nameValuePairs.add(new BasicNameValuePair("username", User.userNAME));
					nameValuePairs.add(new BasicNameValuePair("operation", op));
					Toast.makeText(getActivity(), _poolerSource, Toast.LENGTH_LONG).show();
					Toast.makeText(getActivity(), _poolerDestination, Toast.LENGTH_LONG).show();
					Toast.makeText(getActivity(), User.userId, Toast.LENGTH_LONG).show();
					Toast.makeText(getActivity(), User.userNAME, Toast.LENGTH_LONG).show();
					Toast.makeText(getActivity(), op, Toast.LENGTH_LONG).show();

					// Execute HTTP Post Request
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					responseCode = response.getStatusLine().getStatusCode();
					responseBody = EntityUtils.toString(response.getEntity());
				} catch (Throwable t) {
					Log.d("Error Time of Login", t + "");
				}
				return responseBody;
			}

			protected void onPostExecute(String responseBody) {
				super.onPostExecute(responseBody);
				//Toast.makeText(getActivity(), responseBody, Toast.LENGTH_LONG).show();
				if(responseBody.matches("fail"))
				{
					Toast.makeText(getActivity(), responseBody, Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(getActivity(), "You enter into pooling.", Toast.LENGTH_LONG).show();

				}
				pd.dismiss();
				srcdes.dismiss();
			}
		}
		//ENDING ASYNC CLASS FOR SENDING POOLERS DATA_________________________________________________________*

	 */
}
