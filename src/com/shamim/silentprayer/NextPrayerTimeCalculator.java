package com.shamim.silentprayer;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import android.util.Log;

public class NextPrayerTimeCalculator {
	DateFormat timeFormat;
	String[] todayPrayerTimes;

	public NextPrayerTimeCalculator(String[] prayerTimes, DateFormat timeFormat) {
		Log.d(MainActivity.DBGTAG, "NextPrayerTimeCalculator: Constructor is called.");

		this.todayPrayerTimes = prayerTimes;
		this.timeFormat = timeFormat;
	}

	public int calculateNextPrayerTime(String curDate, String curTime) {
		Log.d(MainActivity.DBGTAG, "NextPrayerTimeCalculator: calculateNextPrayerTime()");

		String currentTime;
		if (curTime == null) {
			currentTime = timeFormat.format(new Date());
		} else {
			int len = curTime.length();
			currentTime = curTime.substring(0, len - 6) + curTime.substring(len - 3, len);

			if (len == 10) {
				currentTime = "0" + currentTime;
			}
		}

		// Check validity of date
		if (!curDate.equals(todayPrayerTimes[5])) {
			Log.d(MainActivity.DBGTAG,
					"NextPrayerTimeCalculator: " + "PrayerDate: " + todayPrayerTimes[5] + ", TodayDate: " + curDate);
			return -1;
		}

		for (int i = 0; i < 5; i++) {
			try {
				long timeDiff = (timeFormat.parse(currentTime)).getTime()
						- (timeFormat.parse(todayPrayerTimes[i])).getTime();

				if (timeDiff <= 0) {
					return i;
				}
			} catch (ParseException e) {
				Log.d(MainActivity.DBGTAG, "NextPrayerTimeCalculator: prayerTimes StringToDate conversion Failed.");
				e.printStackTrace();
			}
		}

		return -1;
	}
}
