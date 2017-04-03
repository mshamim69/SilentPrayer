package com.shamim.silentprayer;

import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class MainPageManager{
	
	private PrayerTimeCollector prayerTimeCollector;
	private MainPageItemLocator mainPageItemLocator;
	private Animation textAnim;
	String volumeStatus;
	
	public MainPageManager(Context context) {		
		Log.d(MainActivity.DBGTAG, "MainPageManager: Constructor() is called.");
		
		this.prayerTimeCollector = new PrayerTimeCollector(context);
		this.mainPageItemLocator = new MainPageItemLocator();
		this.volumeStatus = "ON";
		
		this.textAnim = new AlphaAnimation(0.0f, 1.0f);
		textAnim.setDuration(1000); //You can manage the time of the blink with this parameter
		textAnim.setStartOffset(20);
		textAnim.setRepeatMode(Animation.REVERSE);
		textAnim.setRepeatCount(Animation.INFINITE);
	}
	
	public PrayerTimeCollector getPrayerTimeCollector() {
		return prayerTimeCollector;
	}
	
	public MainPageItemLocator getMainPageItemLocator() {
		return mainPageItemLocator;
	}

	public void setMainPageItems(View view) {
		mainPageItemLocator.setCurrentTimeView((DigitalClock) view.findViewById(R.id.currentTimeId));
		mainPageItemLocator.setCurrentDateView((TextView) view.findViewById(R.id.currentDateId));
		mainPageItemLocator.setVolumeStatusView((Button) view.findViewById(R.id.volStatusId));
		mainPageItemLocator.setVolumeCounterView((TextView) view.findViewById(R.id.volCounterId));
		mainPageItemLocator.setCurrentLocationView((TextView) view.findViewById(R.id.locationId));
		mainPageItemLocator.setFajrTimeView((TextView) view.findViewById(R.id.fajrId));
		mainPageItemLocator.setDhuhrTimeView((TextView) view.findViewById(R.id.dhuhrId));
		mainPageItemLocator.setAsrTimeView((TextView) view.findViewById(R.id.asrId));
		mainPageItemLocator.setMaghribTimeView((TextView) view.findViewById(R.id.maghribId));
		mainPageItemLocator.setIshaTimeView((TextView) view.findViewById(R.id.ishaId));
		mainPageItemLocator.setUpdateBtnView((Button) view.findViewById(R.id.updateBtnId));
		mainPageItemLocator.setSettingsBtnView((Button) view.findViewById(R.id.settingsBtnId));		
		mainPageItemLocator.setBrowserView((WebView) view.findViewById(R.id.browserId));
		
		// Configure the prayer time collector's web view
		WebView browser = mainPageItemLocator.getBrowserView();
		browser.setBackgroundColor(Color.TRANSPARENT);
		browser.setVisibility(View.INVISIBLE);
	}

	public void displayBasicInfo(){
		Date date = new Date();
		mainPageItemLocator.getCurrentDateView().setText(prayerTimeCollector.getDateFormat().format(date));
		
		// Volume status
		mainPageItemLocator.getVolumeStatusView().setText(volumeStatus);
		mainPageItemLocator.getVolumeStatusView().setTextColor(Color.GREEN);
		
		// Volume timer
		String textTimer = "<font color=\"#FFFFFF\">00:00</font> <font color=\"#808000\"> minutes left...</font>";
		mainPageItemLocator.getVolumeCounterView().setText(Html.fromHtml(textTimer), BufferType.SPANNABLE);
		
		mainPageItemLocator.getCurrentLocationView().setText("My Location");		
	}
	
	public void displayUpdatedInfo(){
		displayUpdatedPrayerTime();
		displayUpdatedLocation();
		displayNextPrayerTime();
	}
	
	public void displayUpdatedPrayerTime() {
		Log.d(MainActivity.DBGTAG, "Displaying updated prayer time.");
		
		mainPageItemLocator.getFajrTimeView().setText(prayerTimeCollector.getFajrTime());
		mainPageItemLocator.getDhuhrTimeView().setText(prayerTimeCollector.getDhuhrTime());
		mainPageItemLocator.getAsrTimeView().setText(prayerTimeCollector.getAsrTime());
		mainPageItemLocator.getMaghribTimeView().setText(prayerTimeCollector.getMaghribTime());
		mainPageItemLocator.getIshaTimeView().setText(prayerTimeCollector.getIshaTime());

		// Update time
		Date updateDate = new Date();
		prayerTimeCollector.setPrayerTimeDate(prayerTimeCollector.getDateFormat().format(updateDate));
	}
	
	public void displayUpdatedLocation(){
		mainPageItemLocator.getCurrentLocationView().setText(prayerTimeCollector.getUrlGenerator().getCityLocator().getPlace());
	}
	
	public void displayNextPrayerTime(){
		String curDate = mainPageItemLocator.getCurrentDateView().getText().toString();
		String curTime = mainPageItemLocator.getCurrentTimeView().getText().toString();
		prayerTimeCollector.getNextPrayerTime(curDate, curTime);
		
		updateNextPrayerView(prayerTimeCollector.getNextPrayerTimeIndex());
	}
	
	private void updateNextPrayerView(int index){
		TextView textView = null;
		
		switch(index){
		case 0:
			textView = mainPageItemLocator.getFajrTimeView();
			break;
		case 1:
			textView = mainPageItemLocator.getDhuhrTimeView();
			break;
		case 2:
			textView = mainPageItemLocator.getAsrTimeView();
			break;
		case 3:
			textView = mainPageItemLocator.getMaghribTimeView();
			break;
		case 4:
			textView = mainPageItemLocator.getIshaTimeView();
			break;
		case -1:
			return;
		}

		textView.setTextColor(Color.WHITE);
	    textView.startAnimation(textAnim); 
	}

	public void startCounter(int duration_in_sec){
		final TextView timerV = mainPageItemLocator.getVolumeCounterView();
		
		new CountDownTimer(duration_in_sec*60000, 1000) {
		
			public void onTick(long millisUntilFinished) {
				String time = prayerTimeCollector.getCounterFormat().format(new Date(millisUntilFinished)).toString();
				String textTimer = "<font color=\"#FFFFFF\"> "+ time + " </font> <font color=\"#808000\"> minutes left...</font>";
				timerV.setText(Html.fromHtml(textTimer), BufferType.SPANNABLE);
			}

			public void onFinish() {
				timerV.setText("Done!");
			}
		}.start();
	}
}
