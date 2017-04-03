package com.shamim.silentprayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PrayerTimeCollector {

	// Supported Date Formats
	private DateFormat dateFormat;
	private DateFormat timeFormat;
	private DateFormat counterFormat;

	private BaseURLGenerator urlGenerator;
	private JavaScriptHtmlParser htmlParser;
	private NextPrayerTimeCalculator nextPrayerTimeCalculator;
	private String[] prayerTimes;
	private Object prayerTimeLock;
	private PrayerTimeUpdateListener listener;
	private int nextPrayerTimeIndex;

	private int FAJR = 0;
	private int DHUHR = 1;
	private int ASR = 2;
	private int MAGHRIB = 3;
	private int ISHA = 4;
	private int PRAYER_TIME_DATE = 5;

	@SuppressLint("SimpleDateFormat")
	public PrayerTimeCollector(Context context) {
		Log.d(MainActivity.DBGTAG, "PrayerTimeCollector: Constructor() is called.");

		this.dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy"); // "Monday,
																		// 01
																		// January
																		// 1970"
		this.timeFormat = new SimpleDateFormat("hh:mm a"); // "04:00 AM"
		this.counterFormat = new SimpleDateFormat("mm:ss"); // 15:00 minutes
															// Counter
		this.urlGenerator = new BaseURLGenerator(context);
		this.prayerTimes = new String[6];
		this.prayerTimeLock = new Object();
		this.htmlParser = new JavaScriptHtmlParser(prayerTimes, prayerTimeLock);
		this.nextPrayerTimeCalculator = new NextPrayerTimeCalculator(prayerTimes, timeFormat);
		this.listener = null;
		this.nextPrayerTimeIndex = -1;
	};

	public JavaScriptHtmlParser getHtmlParser() {
		return htmlParser;
	}

	public void setListener(PrayerTimeUpdateListener listener) {
		Log.d(MainActivity.DBGTAG, "PrayerTimeCollector: Listener is set.");

		this.listener = listener;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public DateFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(DateFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public DateFormat getCounterFormat() {
		return counterFormat;
	}

	public void setCounterFormat(DateFormat counterFormat) {
		this.counterFormat = counterFormat;
	}

	public int getNextPrayerTimeIndex() {
		return nextPrayerTimeIndex;
	}

	public void setNextPrayerTimeIndex(int nextPrayerTimeIndex) {
		this.nextPrayerTimeIndex = nextPrayerTimeIndex;
	}

	public String getFajrTime() {
		return prayerTimes[FAJR];
	}

	public void setFajrTime(String fajrTime) {
		prayerTimes[FAJR] = fajrTime;
	}

	public String getDhuhrTime() {
		return prayerTimes[DHUHR];
	}

	public void setDhuhrTime(String dhuhrTime) {
		prayerTimes[DHUHR] = dhuhrTime;
	}

	public String getAsrTime() {
		return prayerTimes[ASR];
	}

	public void setAsrTime(String asrTime) {
		prayerTimes[ASR] = asrTime;
	}

	public String getMaghribTime() {
		return prayerTimes[MAGHRIB];
	}

	public void setMaghribTime(String maghribTime) {
		prayerTimes[MAGHRIB] = maghribTime;
	}

	public String getIshaTime() {
		return prayerTimes[ISHA];
	}

	public void setIshaTime(String ishaTime) {
		prayerTimes[ISHA] = ishaTime;
	}

	public String getPrayerTimeDate() {
		return prayerTimes[PRAYER_TIME_DATE];
	}

	public void setPrayerTimeDate(String prayerTimeDate) {
		prayerTimes[PRAYER_TIME_DATE] = prayerTimeDate;
	}

	public NextPrayerTimeCalculator getNextPrayerTimeCalculator() {
		return nextPrayerTimeCalculator;
	}

	public String getPrayerTimeFromIndex(int index) {
		return prayerTimes[index];
	}

	public BaseURLGenerator getUrlGenerator() {
		return urlGenerator;
	}

	public void getPrayerTimesFromMemory() {
		setFajrTime("05:45 AM");
		setDhuhrTime("12:40 PM");
		setAsrTime("03:55 PM");
		setMaghribTime("05:45 PM");
		setIshaTime("08:50 PM");

		Date updateDate = new Date();
		prayerTimes[PRAYER_TIME_DATE] = dateFormat.format(updateDate);
	}

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	public void getTodayPrayerTime(String country, String city, String calc_method, String juris_method,
			String daylight, WebView webView) {
		urlGenerator.islamicFinderUpdateURL(country, city, calc_method, juris_method, daylight);

		final WebView browser = webView;

		/* JavaScript must be enabled if you want it to work, obviously */
		browser.getSettings().setJavaScriptEnabled(true);

		/* Register a new JavaScript interface called HTMLOUT */
		JavaScriptHtmlParser javaInt = htmlParser;

		browser.addJavascriptInterface(javaInt, "HTMLOUT");

		/* WebViewClient must be set BEFORE calling loadUrl! */
		browser.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				synchronized (prayerTimeLock) {
					browser.loadUrl(
							"javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
					try {
						prayerTimeLock.wait();

						if (listener != null) {
							listener.OnTaskCompletion();
						} else {
							Log.d(MainActivity.DBGTAG, "Listener Null pointer.");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		/* load a web page */
		browser.loadUrl(urlGenerator.getBaseURL());
	}

	public String getNextPrayerTime(String curDate, String curTime) {
		nextPrayerTimeIndex = nextPrayerTimeCalculator.calculateNextPrayerTime(curDate, curTime);

		if (nextPrayerTimeIndex != -1){
			return getPrayerTimeFromIndex(nextPrayerTimeIndex);
		}
		else{
			return "Invalid Index";
		}
	}

	public void setSilentOption(int id, boolean checked) {
		
	}

}
