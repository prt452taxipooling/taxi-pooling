package com.example.taxipooling;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	TextView theme, selectedTheme, themeColor, radius;
	ImageView iButton;
	boolean themeF=false;
	android.app.ActionBar actionbar;
	String theme_Color="";
	View v1, v2;
	static boolean themeFlag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		//SETTING THEME FROM PREFERENCES.......

		actionbar = getActionBar();
		actionbar.setTitle("Settings");
		if (SharedPreferencesManager.getStringPreferences("theme", this,
				"light").equals("light")) {
			setTheme(R.style.lightTheme);
		} else {
			setTheme(R.style.blackTheme);
		}
		if (SharedPreferencesManager.getStringPreferences("theme", this,"light").equals("light") && 
				!(SharedPreferencesManager.getStringPreferences("theme", this,"light").equals(null))) {
			theme_Color = SharedPreferencesManager.getStringPreferences("theme_color", this, "yellow");
			actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(theme_Color)));
		}
		if(SharedPreferencesManager.getStringPreferences("theme", this,"dark").equals("dark") && 
				!(SharedPreferencesManager.getStringPreferences("theme", this,"light").equals(null))){
			theme_Color = SharedPreferencesManager.getStringPreferences("theme_color", this, "yellow");
			actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(theme_Color)));
		}
		//		View v = this.getWindow().getDecorView();
		//		v.setBackgroundColor(color);


		theme = (TextView) findViewById(R.id.themes);
		selectedTheme = (TextView) findViewById(R.id.selectedTheme);
		themeColor = (TextView) findViewById(R.id.themeColor);
		iButton = (ImageView) findViewById(R.id.colorPic);
		v1 = findViewById(R.id.viewOne);
		v2 = findViewById(R.id.viewTwo);

		selectedTheme.setText(MainActivity.selectedTheme);
		
		//CHANGING COLOR OF LINES
				v1.setBackgroundColor(Color.parseColor(theme_Color));
				v2.setBackgroundColor(Color.parseColor(theme_Color));
		iButton.setColorFilter(Color.parseColor(theme_Color), Mode.SRC_ATOP);

		theme.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final Dialog _startDialog;
				_startDialog =  new Dialog(SettingsActivity.this);
				_startDialog.setTitle("Select Theme");
				_startDialog.setContentView(R.layout.select_theme);
				final CheckBox light = (CheckBox) _startDialog.findViewById(R.id.lightTheme);
				final CheckBox dark = (CheckBox) _startDialog.findViewById(R.id.darkTheme);
				light.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							dark.setChecked(false);
							light.setChecked(true);
							selectedTheme.setText(light.getText().toString());
							SharedPreferencesManager.savePreferences("theme", "light", SettingsActivity.this);
							MainActivity.themeFlag=true;
							themeFlag=true;
							themeF=true;
						}
						_startDialog.dismiss();
					}
				});
				dark.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							light.setChecked(false);
							dark.setChecked(true);
							selectedTheme.setText(dark.getText().toString());
							SharedPreferencesManager.savePreferences("theme", "dark", SettingsActivity.this);
							MainActivity.themeFlag=true;
							themeFlag=true;
							themeF=true;
						}
						_startDialog.dismiss();

					}
				});
				_startDialog.show();



			}
		});

		iButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAlertDialog();

			}
		});

		themeColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAlertDialog();

			}
		});


	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Toast.makeText(getApplicationContext(), "setting value"+themeFlag, Toast.LENGTH_LONG).show();
		if (themeFlag==true) {
			Toast.makeText(getApplicationContext(), "setting resume..", Toast.LENGTH_LONG).show();
			Intent intent = getIntent();
			overridePendingTransition(0, 0);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			finish();
			overridePendingTransition(0, 0);
			startActivity(intent);
			
			themeFlag=false;
		}
		super.onWindowFocusChanged(hasFocus);
	}
	@Override
	protected void onResume() {
		
		/*Toast.makeText(getApplicationContext(), "setting value"+themeFlag, Toast.LENGTH_LONG).show();
		if (themeFlag==true) {
			Toast.makeText(getApplicationContext(), "setting resume..", Toast.LENGTH_LONG).show();
			Intent intent = getIntent();
			overridePendingTransition(0, 0);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			finish();
			overridePendingTransition(0, 0);
			startActivity(intent);
			
			themeFlag=false;
		}*/
/*		if (SharedPreferencesManager.getStringPreferences("theme", this,"light").equals("light") && 
				!(SharedPreferencesManager.getStringPreferences("theme", this,"light").equals(null))) {
			theme_Color = SharedPreferencesManager.getStringPreferences("theme_color", this, "yellow");
			actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(theme_Color)));
		}
		if(SharedPreferencesManager.getStringPreferences("theme", this,"dark").equals("dark") && 
				!(SharedPreferencesManager.getStringPreferences("theme", this,"light").equals(null))){
			theme_Color = SharedPreferencesManager.getStringPreferences("theme_color", this, "yellow");
			actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(theme_Color)));
		}*/
		//CHANGING COLOR OF LINES
		/*v1.setBackgroundColor(Color.parseColor(theme_Color));
		v2.setBackgroundColor(Color.parseColor(theme_Color));
iButton.setColorFilter(Color.parseColor(theme_Color), Mode.SRC_ATOP);*/


		super.onResume();
	}

	private void showAlertDialog() {
		// Prepare grid view
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		GridView gridView = new GridView(this);

		final ColorAdapter ca = new ColorAdapter(getApplication());

		gridView.setAdapter(ca);
		gridView.setNumColumns(4);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				//dialog.dismiss();
				//				updateColor(colorAdapter.getColor(position));
				SharedPreferencesManager.savePreferences("theme_color", ca.getColor(position), SettingsActivity.this);
				//				MainActivity.actionbar.getTabAt(0).getIcon().setColorFilter(Color.parseColor(ca.getColor(position)),Mode.SRC_ATOP);
				//				MainActivity.actionbar.getTabAt(1).getIcon().setColorFilter(Color.parseColor(ca.getColor(position)),Mode.SRC_ATOP);
				//				MainActivity.actionbar.getTabAt(2).getIcon().setColorFilter(Color.parseColor(ca.getColor(position)),Mode.SRC_ATOP);
				//				MainActivity.actionbar.getTabAt(3).getIcon().setColorFilter(Color.parseColor(ca.getColor(position)),Mode.SRC_ATOP);
				//MainActivity.actionbar.getSelectedTab().getIcon().setColorFilter(Color.parseColor(ca.getColor(position)),Mode.SRC_ATOP);
				LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
				//View v= inflater.inflate(R.layout.fragment_a, viewpager);
				//ImageButton b = (ImageButton) v.findViewById(R.id.editProfile);
				//b.setColorFilter(Color.parseColor(ca.getColor(position)), Mode.SRC_ATOP);
				MainActivity.actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(ca.getColor(position))));
				MainActivity.themeColorFlag=true;
				MainActivity.themeFlag=true;
				themeFlag=true;
				themeF=true;

				//builder.create().dismiss();
				

			}
		});
		builder.setView(gridView);
		builder.setTitle("Select Colors");
		// Set grid view to alertDialog

		builder.show();
	}

	private GridView getColorView() {
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		final ColorAdapter colorAdapter = new ColorAdapter(this);
		GridView	gridView = new GridView(this);
		gridView.setAdapter(colorAdapter);
		gridView.setNumColumns(4);
		gridView.setColumnWidth(100);
		ViewGroup.LayoutParams layoutParams = new LayoutParams((6 * width) / 9,
				(2 * height) / 7);
		gridView.setGravity(Gravity.CENTER_HORIZONTAL);
		gridView.setOverScrollMode(GridView.OVER_SCROLL_NEVER);
		gridView.setFadingEdgeLength(0);
		gridView.setHorizontalScrollBarEnabled(false);
		gridView.setVerticalScrollBarEnabled(true);
		gridView.setLayoutParams(layoutParams);

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				//dialog.dismiss();
				//				updateColor(colorAdapter.getColor(position));

				SharedPreferencesManager.savePreferences("theme_color", colorAdapter.getColor(position), SettingsActivity.this);

			}
		});
		return gridView;
	}


}
