package com.example.taxipooling;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

@SuppressLint("NewApi")
public class LoginActivity extends Activity {
    EditText un, pw;
    public String name, pwd;
    TextView error;
    Button ok, signup;
    //latitude and longitude
    double longitude = 0.0, latitude = 0.0;
    public static JSONArray jArray;
    public static JSONObject jObject;
    private String resp;
    private String errorMsg;
    public String em;
    boolean emailflag = false;//to show incorrect email error
    static boolean flagnet;
    GPSTracker gps;
    private Context mContext;
    ProgressDialog progressDialog;
    GoogleCloudMessaging gcm;
    public static String regId;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //to hide the action bar...
        this.getActionBar().hide();
        //getting user location
        gps = new GPSTracker(LoginActivity.this);

        final Validating valid = new Validating(getApplicationContext());
        if (!valid.isConnectingToInternet()) {
            TextView ie = (TextView) findViewById(R.id.internetError);
            ie.setVisibility(View.VISIBLE);
        } else {
            TextView ie = (TextView) findViewById(R.id.internetError);
            ie.setVisibility(View.GONE);
        }

        // accessing the items by id's..
        un = (EditText) findViewById(R.id.un_edittext);
        pw = (EditText) findViewById(R.id.pass_edittext);
        ok = (Button) findViewById(R.id.login);
        error = (TextView) findViewById(R.id.status);
        signup = (Button) findViewById(R.id.register);
        final EditText serveraddress = (EditText) findViewById(R.id.serveraddress);
        final Button saveaddress = (Button) findViewById(R.id.saveaddress);
        saveaddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Url.SERVER_URL = "http://" + serveraddress.getText().toString() + "/taxipolling/";
            }
        });

        un.setText("");
        pw.setText("");


        //__________setting signup button click listener__________________________________________________________________________________________
        signup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();

            }
        });

        //__________ON CLICK LISTENER FOR LOGIN __________________________________________________________________________________________
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * According with the new StrictGuard policy, running long tasks
                 * on the Main UI thread is not possible So creating new thread
                 * to create and execute http operations
                 */
                if (un.getText().toString().matches("")) {
                    error.setText("Please fill username!");
                    error.setVisibility(View.VISIBLE);
                } else if (pw.getText().toString().matches("")) {
                    error.setText("Please fill passeword!");
                    error.setVisibility(View.VISIBLE);
                } else {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Toast.makeText(getApplicationContext(), latitude + " " + longitude + " " + gps.getLocation(), Toast.LENGTH_LONG).show();


                    if (valid.isConnectingToInternet()) {
                        //new MyAsyncTaskForAllEnquire().execute();
                        getRegId();
                    } else {
                        Toast.makeText(getApplicationContext(), "Problem in internet connectivity", Toast.LENGTH_LONG).show();
                    }
                }
                /*else{
					Toast.makeText(getApplicationContext(), "my email"+em, Toast.LENGTH_LONG).show();
					error.setText("Please enter your email address in the format someone@example.com.");
					error.setVisibility(View.VISIBLE);
				}*/


            }
        });
        //__________TEXT CHANGED LISTENER FOR EMAIL________________________________________________________________________________
		/*	un.setOnFocusChangeListener(new OnFocusChangeListener() {


			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				em=un.getText().toString();
				if(!(checkEmail(em))){

					Toast.makeText(getApplicationContext(), "my email"+em, Toast.LENGTH_LONG).show();
					error.setText("Please enter your email address in the format someone@example.com.");
					error.setVisibility(View.VISIBLE);
					//un.setText("");
					//un.requestFocus();
					//pw.clearFocus();

				}
				else{
					error.setVisibility(View.GONE);
				}
			}
		});*/

    }//ONCREATE FUNCTION END

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    //__________Asynchronous task connectivity______________________________________________________________________(*)____________________
    private class MyAsyncTaskForAllEnquire extends
            AsyncTask<Object, Void, String> {
        int responseCode;
        String responseBody;


        @Override
        protected String doInBackground(Object... params) {
            return postData();
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Login checking..");
            //progressDialog.setIndeterminate(true);
            //progressDialog.setCancelable(true);
            //progressDialog.show();
        }

        @SuppressWarnings("unused")
        protected void onProgressUpdate(Integer... progress) {
        }

        public String postData() {
            HttpClient httpclient = new DefaultHttpClient();
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);
            HttpPost httppost = new HttpPost(Url.SERVER_URL + "login.php");
            try {
                @SuppressWarnings("rawtypes")
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("username", un.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", pw.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("lng", Double.toString(longitude)));
                nameValuePairs.add(new BasicNameValuePair("lat", Double.toString(latitude)));
                nameValuePairs.add(new BasicNameValuePair("id", regId));
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
            try {
                if (responseBody.matches("fail")) {
                    //Toast.makeText(getApplicationContext(), responseBody, 2000).show();
                    error.setText("Invalid Username or Password.");
                    error.setVisibility(View.VISIBLE);
                } else {
                    //Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
                    try {
                        jArray = new JSONArray(responseBody);
                        jObject = jArray.getJSONObject(0);
                        User.userNAME = jObject.getString("username");
                        User.userTYPE = jObject.getString("userType");
                        User.totalRATING = jObject.getString("totalRating");
                        User.poolingCREATED = Integer.parseInt(jObject.getString("poolingCreated"));
                        User.LOC = jObject.getString("location");
                        User.password = jObject.getString("password");
                        User.userId = jObject.getString("userID");
                        User.locLat = jObject.getString("locLat");
                        User.locLng = jObject.getString("locLng");
                        User.image = jObject.getString("image");
                        User.comments.clear();
                        User.commentors.clear();
                        for (int i = 0; i < jArray.length(); i++) {
                            jObject = jArray.getJSONObject(i);
                            User.comments.add(jObject.getString("comment"));
                            User.commentors.add(jObject.getString("commentor"));
                            User.fromId.add(jObject.getString("fromID"));
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
                new AsyncNotificatoin(getApplicationContext()).execute();
                new MyAsyncTaskForGettingPoolings().execute();
                new MyAsyncTaskForImages().execute();

                progressDialog.dismiss();

            } catch (NullPointerException e) {
                Toast.makeText(LoginActivity.this, "Server Not Found", Toast.LENGTH_LONG).show();
            } finally {
                progressDialog.dismiss();
            }
        }
    }
    //ENDING Asynchronous task connectivity__________________________________________________________________(*)____________________

    //__________ASYNCHRONOUS TASK CONNECTIVITY FOR GETTING ALL POOLINGS ON RECENT BUTTON CLICK__________________(*)____________________
    private class MyAsyncTaskForGettingPoolings extends
            AsyncTask<Object, Void, String> {
        int responseCode;
        String responseBody;
        //	ProgressDialog	pDialog = new ProgressDialog(getApplicationContext());

        @Override
        protected String doInBackground(Object... params) {
            return postData();
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //	pDialog.setMessage("Loading Poolings..");
            //progressDialog.setIndeterminate(true);
            //progressDialog.setCancelable(true);
            //	pDialog.show();
        }

        @SuppressWarnings("unused")
        protected void onProgressUpdate(Integer... progress) {
        }

        public String postData() {
            HttpClient httpclient = new DefaultHttpClient();
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);
            HttpPost httppost = new HttpPost(Url.SERVER_URL + "getPoolings.php");
            try {
                @SuppressWarnings("rawtypes")
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("userID", User.userId));
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
            if (responseBody.matches("fail")) {
                Toast.makeText(getApplicationContext(), responseBody + " Error in Loading poolings", Toast.LENGTH_LONG).show();

            } else {
                //Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
                try {
                    JSONArray jArray;
                    JSONObject jObject;


                    Pooling.listOfPoolings.clear();
                    jArray = new JSONArray(responseBody);
                    for (int i = 0; i < jArray.length(); i++) {
                        jObject = jArray.getJSONObject(i);
                        List<String> p = new ArrayList<String>();
                        p.add(jObject.getString("createrID"));
                        p.add(jObject.getString("source"));
                        p.add(jObject.getString("destination"));
                        p.add(jObject.getString("poolingID"));
                        p.add(jObject.getString("srcLat"));
                        p.add(jObject.getString("srcLng"));
                        p.add(jObject.getString("destLat"));
                        p.add(jObject.getString("destLng"));
                        p.add(jObject.getString("date"));
                        p.add(jObject.getString("time"));
                        p.add(jObject.getString("status"));
                        Pooling.listOfPoolings.add(p);
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            //	pDialog.dismiss();
            //_listDialog.show();
        }
    }
    //ENDING ASYNCHRONOUS TASK CONNECTIVITY FOR GETTING ALL POOLINGS ON SPINNER CLICK_____________________(*)____________________


    //ASYNC CLASS FOR GETTING IMAGES___________________________________________________________________*
    private class MyAsyncTaskForImages extends
            AsyncTask<Object, Void, String> {
        int responseCode;
        String responseBody;
        //	ProgressDialog	pDialog = new ProgressDialog(getApplicationContext());

        @Override
        protected String doInBackground(Object... params) {
            return postData();
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //	pDialog.setMessage("Loading Poolings..");
            //progressDialog.setIndeterminate(true);
            //progressDialog.setCancelable(true);
            //	pDialog.show();
        }

        @SuppressWarnings("unused")
        protected void onProgressUpdate(Integer... progress) {
        }

        public String postData() {
            HttpClient httpclient = new DefaultHttpClient();
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);
            HttpPost httppost = new HttpPost(Url.SERVER_URL + "getImages.php");
            try {
                @SuppressWarnings("rawtypes")
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("operation", "images"));
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
            if (responseBody.matches("fail")) {
                Toast.makeText(getApplicationContext(), responseBody + " Error in Loading Images..", Toast.LENGTH_LONG).show();

            } else {
                //Toast.makeText(getApplicationContext(), responseBody, Toast.LENGTH_LONG).show();
                try {
                    JSONArray jArray;
                    JSONObject jObject;


                    jArray = new JSONArray(responseBody);
                    for (int i = 0; i < jArray.length(); i++) {
                        jObject = jArray.getJSONObject(i);
                        User.userImages.put(jObject.getString("userID"), jObject.getString("image"));
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            //	pDialog.dismiss();
            //_listDialog.show();
        }
    }
    //ENDING ASYNC CLASS FOR GETTING IMAGES_________________________________________________________*


    //__ EMAIL VALIDATION FUNCTION_______________________________________________________________________(@)___________
    public static boolean checkEmail(String e) {
        final Pattern rfc2822 = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

        return ((rfc2822.matcher(e).matches())
                ? true : false);
    }//__ EMAIL VALIDATION FUNCTION__________________________________________________________________(@)___________

    //__ CHECKING NETWORK FUNCTION_______________________________________________________________________(#)___________
    public static void isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    flagnet = true;
                } else {
                    flagnet = false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                flagnet = false;
            }
        }
        flagnet = false;

    }//ENDING CHECKING NETWORK FUNCTION_______________________________________________________________________(#)___________

    //GET REGISTRATION ID_________________________________________________________________________(!)
    public void getRegId() {
        new AsyncTask<Void, Void, String>() {
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Registring With GCM");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging
                                .getInstance(getApplicationContext());
                    }
                    regId = gcm.register(CommonUtilities.SENDER_ID);
                    //gcm.unregister();
                    msg = "Device registered, registration ID=" + regId;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    // Toast.makeText(customer.this, "unable",
                    // Toast.LENGTH_LONG).show();
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                // etRegId.setText(msg + "\n");
                Toast.makeText(LoginActivity.this, regId + " $ " + msg, Toast.LENGTH_LONG).show();
                new MyAsyncTaskForAllEnquire().execute();
                //new AsyncTaskForImages().execute();
            }
        }.execute(null, null, null);
    }//ENDING GET REGISTRATION ID_________________________________________________________________________(!)


    // onback press button
    // ===================================================================================

    @Override
    public void onBackPressed() {
		/*Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);*/
    }


}