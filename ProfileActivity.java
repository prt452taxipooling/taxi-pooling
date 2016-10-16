package com.example.taxipooling;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	public static String KEY_IMAGE = "key";
	JSONArray jArray;
	JSONObject jObject;
	Button save;
	EditText oldPass, newPass, phoneNo, newName;
	TextView err;
	String name, id, pass;
	boolean oldPassFlag = true, newPassFlag = true, phoneNoFlag = true,
			noCB = false;
	Bitmap photo;
	private static final int CAMERA_REQUEST = 1888;
	private static int RESULT_LOAD_IMAGE = 1;
	 final int PIC_CROP = 2;
	 Uri photoUri;
	ImageView iView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		save = (Button) findViewById(R.id.saveProfileB);
		oldPass = (EditText) findViewById(R.id.oldPassET);
		newPass = (EditText) findViewById(R.id.newPassET);
		err = (TextView) findViewById(R.id.errorMsg);
		phoneNo = (EditText) findViewById(R.id.numberET);
		newName = (EditText) findViewById(R.id.newNameET);
		CheckBox nochk = (CheckBox) findViewById(R.id.loginNoCHK);
		iView = (ImageView) findViewById(R.id.userImage);

		iView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
						Intent i = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

						startActivityForResult(i, RESULT_LOAD_IMAGE);
					

			}
		});

		// newEmail = (EditText) findViewById(R.id.newEmailET);

		// SETTING CHECKBOX CHECKED CHNAGE
		// LISTENER-------------------------------------------------------------
		nochk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					phoneNo.setVisibility(View.VISIBLE);
					noCB = true;
				} else {
					phoneNo.setVisibility(View.INVISIBLE);
					noCB = false;
				}

			}
		});

		// SETTING SAVE ON CLICK
		// LISTENER------------------------------------------------------------------------------
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// err.setText("Please enter your email address in the format someone@example.com.");

				// EMAIL PATTERN VALIDATOIN...................................
				/*
				 * String em=""; em=newEmail.getText().toString();
				 * if(!newEmail.getText().toString().equals("")){
				 * if(checkEmail(em)){ emailFlag=true;
				 * 
				 * } else{ emailFlag=false; err.setVisibility(View.VISIBLE);
				 * err.setText(
				 * "Please enter your email address in the format someone@example.com."
				 * ); } }
				 */
				// OLD PASSWORD AND NEW PASSWORD VALIDATION..................
				if (!oldPass.getText().toString().equals("")) {
					if (oldPass.getText().toString().equals(User.password)) {
						if (!newPass.getText().toString().equals("")) {
							if (newPass.getText().toString().length() < 6) {
								err.setText("New Password is very short..");
								newPassFlag = false;
							}
						} else {
							err.setText("New Password is required..");
							newPassFlag = false;
						}
					} else {
						err.setText("Incorrect Old Password..");
						err.setVisibility(View.VISIBLE);
						// Toast.makeText(getApplicationContext(),
						// "Incorrect Old Password", Toast.LENGTH_LONG).show();
						oldPassFlag = false;
					}
				}
				if (!newPass.getText().toString().equals("")) {
					if (newPass.getText().toString().length() < 6) {
						err.setText("Password must have more than 5 characters.");
					}
					if (oldPass.getText().toString().equals(User.password)) {
						newPassFlag = true;
						oldPassFlag = true;
					}

				}

				// phone number validation..........
				if (noCB) {
					if (!phoneNo.getText().toString().matches("")) {
						if (!(phoneNo.getText().toString().length() == 11)) {
							err.setText("Phone number must have 11 digits.");
							phoneNoFlag = false;
						} else {
							phoneNoFlag = true;
						}
					} else {
						err.setText("Phone number is required..");
						phoneNoFlag = false;
					}
				}
				name = newName.getText().toString();
				pass = newPass.getText().toString();

				if (oldPassFlag == true && newPassFlag == true
						&& phoneNoFlag == true) {

					// id=User.userId;
					// Toast.makeText(getApplicationContext(),
					// name+" "+pass+" "+id, Toast.LENGTH_LONG).show();
					// finish();
					new MyAsyncTaskForAllEnquire().execute();
				}

			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

			 photoUri = data.getData();
			performCrop();
			try {
				photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// imageView.setImageBitmap(photo);

		}
		 else if (requestCode == PIC_CROP) 
		   {
		    // get the returned data
		    Bundle extras = data.getExtras();
		    // get the cropped bitmap
		    photo = extras.getParcelable("data");
		   }

		if (photo != null) {
			//photo=getRoundedShape(photo);
			photo = getCircleBitmap(photo, 15);
			iView.setImageBitmap(photo);
			User.image=btos(photo);
		}

	}

	/****************************************** PHOTO TO STRING AND VISEVIRSA FUNCTION ****************/

	public Bitmap stob(String encodedString) {
		try {

			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			photo = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return photo;

		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public String btos(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	// ==========================================================================================

	// __________Asynchronous task
	// connectivity__________________________________________________________________________________________
	private class MyAsyncTaskForAllEnquire extends
	AsyncTask<Object, Void, String> {

		int responseCode;
		String responseBody;
		ProgressDialog progressDialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(ProfileActivity.this);
			progressDialog.setMessage("inserting data..");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			return postData();
		}

		@SuppressWarnings("unused")
		protected void onProgressUpdate(Integer... progress) {

		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public String postData() {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Url.SERVER_URL + "updateUser.php");
			try {
				// Data that I am sending
				List nameValuePairs = new ArrayList();
				nameValuePairs.add(new BasicNameValuePair("image",User.image));
				nameValuePairs.add(new BasicNameValuePair("newName", newName
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("newPassword",
						newPass.getText().toString()));
				nameValuePairs
				.add(new BasicNameValuePair("userID", User.userId));
				// nameValuePairs.add(new BasicNameValuePair("location",
				// loc.getText().toString()));
				if (noCB) {
					nameValuePairs.add(new BasicNameValuePair("phoneBit", "1"));
					nameValuePairs.add(new BasicNameValuePair("phoneNo",
							phoneNo.getText().toString()));
				} else {
					nameValuePairs.add(new BasicNameValuePair("phoneBit", "0"));
					nameValuePairs.add(new BasicNameValuePair("phoneNo", "0"));
				}

				// Execute HTTP Post Request
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
						"utf-8"));
				HttpResponse response = httpclient.execute(httppost);

				responseCode = response.getStatusLine().getStatusCode();
				responseBody = EntityUtils.toString(response.getEntity());

			} catch (Throwable t) {

				Log.d("Error Time of Login", t + "");
			}
			return responseBody;
		}

		protected void onPostExecute(String response) {
			super.onPostExecute(response);
			try {

				Toast.makeText(getApplicationContext(),"O Post EXE " + responseBody + ".." + responseCode,
						Toast.LENGTH_LONG).show();
				if (responseBody != null) {
					try {
						jArray = new JSONArray(responseBody);
						jObject = jArray.getJSONObject(0);
						User.userNAME = jObject.getString("username");
						User.userTYPE = jObject.getString("userType");
						User.totalRATING = jObject.getString("totalRating");
						User.poolingCREATED = Integer.parseInt(jObject
								.getString("poolingCreated"));
						User.image = jObject.getString("image");
						User.taxiNO = jObject.getString("taxiNo");
						User.LOC = jObject.getString("location");
						iView.setImageBitmap(stob(User.image));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finish();

				} else {

					Toast.makeText(getApplicationContext(), "Problem in updating data..",
							Toast.LENGTH_LONG).show();
				}
				// progressDialog.dismiss();
			}

			catch (NullPointerException e) {
				e.printStackTrace();
				Log.d("error", "internet not avaliable");
				Toast.makeText(getApplicationContext(), "catch" + responseBody,	900000).show();
			}
			progressDialog.dismiss();

		}
	}

	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
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
	
	public Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
		  Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		  Canvas canvas = new Canvas(output);

		  final int color = 0xffff0000;
		  final Paint paint = new Paint();
		  final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		  final RectF rectF = new RectF(rect);

		  paint.setAntiAlias(true);
		  paint.setDither(true);
		  paint.setFilterBitmap(true);
		  canvas.drawARGB(0, 0, 0, 0);
		  paint.setColor(color);
		  canvas.drawOval(rectF, paint);

		  paint.setColor(Color.BLUE);
		  paint.setStyle(Paint.Style.STROKE);
		  paint.setStrokeWidth((float) 4);
		  paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		  canvas.drawBitmap(bitmap, rect, rect, paint);

		  return output;
		 }

	
	
	public void performCrop(){
		// take care of exceptions
		try {
			// call the standard crop action intent (the user device may not
			// support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(photoUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast
					.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}

	}

}