package com.example.taxipooling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends FragmentActivity implements TabListener {

	ViewPager viewpager;
	static android.app.ActionBar actionbar;
	android.app.ActionBar.Tab tab1, tab2, tab3, tab4;
	GoogleCloudMessaging gcm;
	static boolean themeFlag=false;
	static boolean themeColorFlag=false;
	static String actionbarColor="";
	static String selectedTheme="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		actionbar = getActionBar();
		try{
			if (SharedPreferencesManager.getStringPreferences("theme", this,
					"light").equals("light")) {
				setTheme(R.style.lightTheme);
				selectedTheme="Light";
			} else {
				setTheme(R.style.blackTheme);
				selectedTheme="Dark";
			}

			if (SharedPreferencesManager.getStringPreferences("theme", this,"light").equals("light") && 
					!(SharedPreferencesManager.getStringPreferences("theme", this,"light").equals(null))) {
				actionbarColor = SharedPreferencesManager.getStringPreferences("theme_color", this, "yellow");
				Toast.makeText(getApplicationContext(), actionbarColor, Toast.LENGTH_LONG).show();
				actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionbarColor)));
			}
			if(SharedPreferencesManager.getStringPreferences("theme", this,"dark").equals("dark") && 
					!(SharedPreferencesManager.getStringPreferences("theme", this,"light").equals(null))){
				actionbarColor = SharedPreferencesManager.getStringPreferences("theme_color", this, "yellow");
				Toast.makeText(getApplicationContext(), actionbarColor, Toast.LENGTH_LONG).show();
				actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionbarColor)));
			}
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(), "Theme not found..", Toast.LENGTH_LONG).show();
		}

		setContentView(R.layout.activity_main);
		viewpager = (ViewPager) findViewById(R.id.pager);
		viewpager.setAdapter(new MyAdaptor(getSupportFragmentManager()));
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionbar.setSelectedNavigationItem(arg0);
				if (arg0 == 0) {
					actionbar.setTitle("Profile");
				} else if (arg0 == 1) {
					actionbar.setTitle("Pooling");
				} else if (arg0 == 2) {
					actionbar.setTitle("Alerts");
				} else if (arg0 == 3) {
					actionbar.setTitle("Maps");
				}
				Fragment fragment = ((MyAdaptor) viewpager.getAdapter()).getFragment(arg0);
				if(arg0==0 && fragment!=null){
					fragment.onResume();
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		
		//		actionbar.setBackgroundDrawable(getResources().getDrawable(
		//				R.drawable.tabback));
		// actionbar.setBackgroundDrawable(new
		// ColorDrawable(getResources().getColor(R.drawable.tabback)));

		actionbar.setNavigationMode(actionbar.NAVIGATION_MODE_TABS);

		tab1 = actionbar.newTab();
		tab1.setTabListener((TabListener) this);
		tab1.setIcon(R.drawable.picon);

		tab2 = actionbar.newTab();
		tab2.setIcon(R.drawable.poolingicon);
		tab2.setTabListener((TabListener) this);

		tab3 = actionbar.newTab();
		tab3.setIcon(R.drawable.noticon);
		tab3.setTabListener((TabListener) this);

		// tab4 = actionbar.newTab();
		// tab4.setIcon(R.drawable.msgicon);
		// tab4.setTabListener((TabListener) this);

		tab4 = actionbar.newTab();
		tab4.setIcon(R.drawable.mapicon);
		tab4.setTabListener((TabListener) this);

		actionbar.addTab(tab1);
		actionbar.addTab(tab2);
		actionbar.addTab(tab3);
		// actionbar.addTab(tab4);
		actionbar.addTab(tab4);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (themeFlag) {
			Intent intent = getIntent();
			overridePendingTransition(0, 0);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			finish();
			overridePendingTransition(0, 0);
			startActivity(intent);	
			themeFlag=false;
		}
	/*	if (SharedPreferencesManager.getStringPreferences("theme", this,"light").equals("light") && 
				!(SharedPreferencesManager.getStringPreferences("theme", this,"light").equals(null))) {
			actionbarColor = SharedPreferencesManager.getStringPreferences("theme_color", this, "yellow");
			Toast.makeText(getApplicationContext(), actionbarColor, Toast.LENGTH_LONG).show();
			MainActivity.actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionbarColor)));
		}
		if(SharedPreferencesManager.getStringPreferences("theme", this,"dark").equals("dark") && 
				!(SharedPreferencesManager.getStringPreferences("theme", this,"light").equals(null))){
			actionbarColor = SharedPreferencesManager.getStringPreferences("theme_color", this, "yellow");
			Toast.makeText(getApplicationContext(), actionbarColor, Toast.LENGTH_LONG).show();
			MainActivity.actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionbarColor)));
		}*/
		/*		if(themeColorFlag){
			Toast.makeText(getApplicationContext(), "hello this main....", Toast.LENGTH_LONG).show();
			Intent intent = getIntent();
			overridePendingTransition(0, 0);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			finish();
			overridePendingTransition(0, 0);
			startActivity(intent);	
			themeColorFlag=false;
		}*/

		super.onResume();

	}

	@Override
	public void onTabReselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// Log.d("khan",
		// "on reselected at"+" position "+tab.getPosition()+" name "+tab.getText());

	}

	@Override
	public void onTabSelected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		viewpager.setCurrentItem(tab.getPosition());
		if (tab.getPosition() == 0) {
			actionbar.setTitle("Profile");
			tab1.setIcon(R.drawable.picon_selected);
			tab1.getIcon().setColorFilter(Color.parseColor(actionbarColor),Mode.SRC_ATOP);
		} else if (tab.getPosition() == 1) {
			actionbar.setTitle("Pooling");
			tab2.setIcon(R.drawable.poolingicon_selected);
			tab2.getIcon().setColorFilter(Color.parseColor(actionbarColor),Mode.SRC_ATOP);
		} else if (tab.getPosition() == 2) {
			actionbar.setTitle("Alerts");
			tab3.setIcon(R.drawable.noticon_selected);
			tab3.getIcon().setColorFilter(Color.parseColor(actionbarColor),Mode.SRC_ATOP);
		} else if (tab.getPosition() == 3) {
			actionbar.setTitle("Maps");
			tab4.setIcon(R.drawable.mapicon_selected);
			tab4.getIcon().setColorFilter(Color.parseColor(actionbarColor),Mode.SRC_ATOP);
		}
		// if(tab.getPosition()==3){
		// actionbar.setTitle("Msgs");
		// tab4.setIcon(R.drawable.msgicon_selected);
		// }

		// TODO Auto-generated method stub
		Log.d("khan", "on selected at" + " position " + tab.getPosition()
				+ " name " + tab.getText());

	}

	@Override
	public void onTabUnselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// Log.d("khan",
		// "on unselected at"+" position "+tab.getPosition()+" name "+tab.getText());
		tab1.setIcon(R.drawable.picon);
		tab2.setIcon(R.drawable.poolingicon);
		tab3.setIcon(R.drawable.noticon);
		// tab4.setIcon(R.drawable.msgicon);
		tab4.setIcon(R.drawable.mapicon);
		// tab5.setIcon(R.drawable.checking);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout:
			Log.i("intert", "create");
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);

			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging
							.getInstance(getApplicationContext());
				}
				gcm.unregister();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("after", "unregister");

			Log.i("start", "activity");
			startActivity(i);
			return true;

		case R.id.action_settings:
			// Intent intent = new Intent(this, SettingsActivity.class);
			// startActivity(intent);
			//			setTheme(R.style.myDark);

			startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

class MyAdaptor extends FragmentPagerAdapter {
	private Map<Integer, String> fragmentTags;
	private FragmentManager fragmentManager;
	public MyAdaptor(FragmentManager fm) {
		super(fm);
		fragmentManager = fm;

		fragmentTags = new HashMap<Integer, String>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		Log.d("khan", " Argument: " + arg0);
		if (arg0 == 0) {
			fragment = new Profile();
		} else if (arg0 == 1) {
			fragment = new Poolings();
		} else if (arg0 == 2) {
			fragment = new Notifications();
		} else if (arg0 == 3) {
			fragment = new Route();
		}
		// if(arg0==3)
		// {
		// fragment = new FragmentD();
		// }

		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		Object obj = super.instantiateItem(container, position);
		if(obj instanceof Fragment){
			Fragment f = (Fragment) obj;
			String tag = f.getTag();
			fragmentTags.put(position, tag);
		}


		return obj;
	}

	public Fragment getFragment(int position)
	{
		String tag= fragmentTags.get(position);
		if(tag==null)
			return null;

		return fragmentManager.findFragmentByTag(tag); 
	}





}