package com.shamim.silentprayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

/* An instance of this class will be registered as a JavaScript interface */
public class JavaScriptHtmlParser {

	private String[] prayerTimes;
	private Object prayerTimeLock;

	public JavaScriptHtmlParser(String[] prayerTimes, Object prayerTimeLock) {
		this.prayerTimes = prayerTimes;
		this.prayerTimeLock = prayerTimeLock;
	}

	public void processHTML(String html) {
		synchronized (prayerTimeLock) {
			Log.d(MainActivity.DBGTAG, "processHtml() is called.");

			Document doc = Jsoup.parse(html);
			Elements elems = doc.select("span.todayPrayerTime");

			int i = 0;
			int count = 0;
			for (Element elem : elems) {

				if ((i == 1) || (i == 6)) {
					i++;
					continue;
				} else {
					prayerTimes[count++] = elem.text();
					i++;
				}
			}
			
			prayerTimeLock.notify();
		}
	}

}
