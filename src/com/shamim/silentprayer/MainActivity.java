package com.shamim.silentprayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String DBGTAG = "Ahsan Shamim";
	public static final int SETTINGS_ACTIVITY = 100;
	
	private boolean isLocationUpdated = false;
    private Drawable checkBoxOff;
    private Drawable checkBoxOn;
	private View mainActivityView;
	private MainPageManager mainPageManager;
	private PrayerTimeManager prayerTimeManager;
	private String[] settingsData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(MainActivity.DBGTAG, "MainActivity: onCreate() is called.");
		
		// Disable the detection of any violations
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    
	    // Set view in main activity
	    setContentView(R.layout.main_activity);

		this.checkBoxOff = getResources().getDrawable(R.drawable.checkbox_off);
		this.checkBoxOn = getResources().getDrawable(R.drawable.checkbox_on);
		
	    this.mainActivityView = findViewById(R.id.mainActivityId);
	    
		this.mainPageManager = new MainPageManager(MainActivity.this);
		mainPageManager.setMainPageItems(mainActivityView);
		
		this.prayerTimeManager = new PrayerTimeManager();
		IntentFilter intentFilter = new IntentFilter("android.intent.action.ANY_ACTION");
	    registerReceiver(prayerTimeManager, intentFilter);
		
		// PrayerTime settings is not updated or not available
		if (!isLocationUpdated) {
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

			// 'OK' button
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int id) {
					mainPageManager.displayBasicInfo();
					
					// Display stored prayer times from previous settings
					mainPageManager.getPrayerTimeCollector().getPrayerTimesFromMemory();
					mainPageManager.displayUpdatedPrayerTime();
					mainPageManager.displayNextPrayerTime();
					
					onetimeTimer(MainActivity.this);
				}
			});

			// 'Cancel' button
			builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {
					mainPageManager.displayBasicInfo();
				}
			});

			// Set other dialog properties
			builder.setMessage("Do you want to configure the Settings now?");

			// Create the AlertDialog
			AlertDialog dialog = builder.create();
			dialog.show();
		}

		// Add "Update" and "Settings" button listener
		addButtonListener();
		
		registerReceiver(broadcastReceiver, new IntentFilter("UPDATE_PRAYER_TIME"));
	}
	
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        Log.d(MainActivity.DBGTAG, "Intent: doPrayerTimeCalculation() received");
	        doPrayerTimeUpdate();
	    }
	};

	private void addButtonListener() {
		Button updateBtn = mainPageManager.getMainPageItemLocator().getUpdateBtnView();
		Button settingsBtn = mainPageManager.getMainPageItemLocator().getSettingsBtnView();

		updateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(MainActivity.this, "Prayer time is being updated...", Toast.LENGTH_LONG).show();
				
				doPrayerTimeUpdate();
			}
		});

		settingsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {			
				Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
				startActivityForResult(intent, SETTINGS_ACTIVITY);
			}
		});
	}
	
	public void doPrayerTimeUpdate() {
		String country = settingsData[0];
		String city = settingsData[1];
		String calculation = settingsData[2];
		String juristic = settingsData[3];
		WebView browser = mainPageManager.getMainPageItemLocator().getBrowserView();
		
		addPrayerTimeUpdateListener();
		mainPageManager.getPrayerTimeCollector().getTodayPrayerTime(country, city, calculation, juristic, "0", browser);

		mainPageManager.startCounter(10);
	}
	
	private void addPrayerTimeUpdateListener() {
		mainPageManager.getPrayerTimeCollector().setListener(new PrayerTimeUpdateListener() {
			
			@Override
			public void OnTaskCompletion() {
				mainPageManager.displayUpdatedInfo();
				Toast.makeText(MainActivity.this, "Prayer time update is OK.", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(MainActivity.DBGTAG, "MainActivity: " + requestCode + ", " + resultCode);
		
		this.settingsData = data.getExtras().getStringArray("SETTINGS_DATA");
		doPrayerTimeUpdate();
		
		int timePeriod;
		if (settingsData[4].equals("10_min")){
			timePeriod = 10;
		}
		else if (settingsData[4].equals("10_min")){
			timePeriod = 20;
		}
		else if (settingsData[4].equals("Custom")){
			timePeriod = Integer.parseInt(settingsData[5]);
		}
		else{
			timePeriod = -1;
		}
		
		prayerTimeManager.setVolumeOFFTimeMin(timePeriod);
	}
	
	public void onCheckboxClicked(View view) {
	    boolean checked = ((CheckBox) view).isChecked();
	    
	    if (checked){
	    	((CheckBox)view).setButtonDrawable(checkBoxOff);
	    }
	    else{
	    	((CheckBox)view).setButtonDrawable(checkBoxOn);
	    }
	    
	    mainPageManager.getPrayerTimeCollector().setSilentOption(view.getId(), checked);
	}
	
	public void onetimeTimer(Context context) {
		if (prayerTimeManager != null) {
			prayerTimeManager.setAlarm(context, 1 , System.currentTimeMillis() + 5000);
		}
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    unregisterReceiver(broadcastReceiver);
	}
	    	
//	@Override 
//	protected void attachBaseContext(Context base) {
//	    super.attachBaseContext(AudioServiceActivityLeak.preventLeakOf(base));
//	}
	
}
