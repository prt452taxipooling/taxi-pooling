package com.example.taxipooling;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackAdaper extends ArrayAdapter<String>
{
	ArrayList<String> _poolerList;
	ArrayList<String> _poolerID;
	int imageId;
	int index;
	Context context;
	
	int rate;
	boolean likeFlag=true, dislikeFlag=true, like_dislike=false;
	static int p;
	String _userComment=null;

	public FeedbackAdaper(Context context, int userPicture, ArrayList<String> poolerList,ArrayList<String> poolerid )
	{
		super(context, userPicture, poolerList);
		// TODO Auto-generated constructor stub
		this._poolerList = poolerList;
		this.imageId= userPicture;
		this.context = context;
		this._poolerID= poolerid;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		index=position;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View single_row = inflater.inflate(R.layout.feedback_list_item, null, true);
		TextView tv_poolerName = (TextView) single_row.findViewById(R.id.tv_poolerName);
		ImageView poolerImage = (ImageView) single_row.findViewById(R.id.poolerImage);
		final ImageButton likeButton = (ImageButton) single_row.findViewById(R.id.likeButton);
		final ImageButton dislikeButton = (ImageButton) single_row.findViewById(R.id.dislikeButton);
		final EditText comment = (EditText) single_row.findViewById(R.id.et_comment_feedback);
		//comment.setText("hello mr...");
		final TextView tv_like = (TextView) single_row.findViewById(R.id.tv_Like);
		final TextView tv_dislike= (TextView) single_row.findViewById(R.id.tv_dislike);
		Button send = (Button) single_row.findViewById(R.id.send_feeback);
		
		likeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(p!=position){
					likeFlag=true;
				}
				if(likeFlag){
					p=position;
					likeButton.setImageResource(R.drawable.like_fill);
					tv_like.setText("liked");
					tv_dislike.setText("dislike");
					rate=1;
					likeFlag=false;
					dislikeButton.setImageResource(R.drawable.dislike_empty);
					Toast.makeText(context, rate+"", Toast.LENGTH_LONG).show();
				}
				else
				{
					likeButton.setImageResource(R.drawable.like_empty);	
					likeFlag=true;
					tv_like.setText("like");
				}
				
			}
		});
		
		dislikeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(p!=position){
					dislikeFlag=true;
				}
				if(dislikeFlag){
					p=position;
					dislikeButton.setImageResource(R.drawable.dislike_fill);
					tv_dislike.setText("disliked");
					tv_like.setText("like");
					dislikeFlag=false;
					rate=-1;
					likeButton.setImageResource(R.drawable.like_empty);
					Toast.makeText(context, rate+"", Toast.LENGTH_LONG).show();
				}
				else
				{
					dislikeButton.setImageResource(R.drawable.dislike_empty);	
					dislikeFlag=true;
					tv_dislike.setText("dislike");
				}
				
			}
		});
		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				index=position;
					//new MyAsyncTaskForFeedback().execute();
				_userComment = comment.getText().toString();
				Toast.makeText(context, "Comment: "+rate+" "+_userComment, Toast.LENGTH_LONG).show();
				
				
				
			}
		});
		
		//SETTING VARIABLE VALUES.......................
		tv_poolerName.setText(_poolerList.get(position));
		poolerImage.setImageResource(imageId);
		
		return single_row;
		
	}
	
	//ASYNC TASK FOR FEEDBACK ABOUT POOLERS___________________________________________________________________(*)
			private class MyAsyncTaskForFeedback extends
			AsyncTask<Object, Void, String> {
				int responseCode;
				String responseBody;
				JSONArray jArray;
				JSONObject jObject;
				ProgressDialog	progressDialog = new ProgressDialog(getContext());

				@Override
				protected String doInBackground(Object... params) {
					return postData();
				}

				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
				
				//	progressDialog.setMessage("Getting Poolers");
					progressDialog.setIndeterminate(true);
					progressDialog.setCancelable(true);
					progressDialog.show();
				}

				@SuppressWarnings("unused")
				protected void onProgressUpdate(Integer... progress) {
				}

				public String postData() {
					HttpClient httpclient = new DefaultHttpClient();
					HttpParams httpParameters = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
					HttpConnectionParams.setSoTimeout(httpParameters,5000);
					HttpPost httppost = new HttpPost(Url.SERVER_URL+"feedback.php");
					try {
						@SuppressWarnings("rawtypes")
						List nameValuePairs = new ArrayList();
						nameValuePairs.add(new BasicNameValuePair("fromID",User.userId ));
						nameValuePairs.add(new BasicNameValuePair("toID",_poolerID.get(index) ));
						nameValuePairs.add(new BasicNameValuePair("commentor",User.userNAME ));
						nameValuePairs.add(new BasicNameValuePair("rate",rate+"" ));
						nameValuePairs.add(new BasicNameValuePair("comment",_userComment ));
						nameValuePairs.add(new BasicNameValuePair("image",User.image ));
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
						Toast.makeText(context, "Operation Failed...", Toast.LENGTH_LONG).show();
					}
					else
					{
						JSONArray jArray;
						JSONObject jObject;
						try {
							jArray = new JSONArray(responseBody);
							jObject = jArray.getJSONObject(0);
							User.totalRATING= jObject.getString("totalRating");
							Toast.makeText(context, User.totalRATING, Toast.LENGTH_LONG).show();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				
					progressDialog.dismiss();
					
				}
			}
			//ENDING ASYNC TASK FOR SENDING FEEDBACK ABOUT POOLERS_________________________________________________(*)


}
