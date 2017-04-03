package com.shamim.silentprayer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class PrayerTimeManager extends BroadcastReceiver {
	final public static String PRAYER_ALARM = "Prayer Alarm";
	final public static String VOLUME_OFF_ALARM = "volumeOffAlarm";
	final public static String VOLUME_ON_ALARM = "volumeOnAlarm";
	final public static String PRAYER_UPDATE_ALARM = "updateAlarm";
	final public static String NEW_ALARM = "newAlarm";
	
	private AudioManager audioManager;
	private int volumeOffTimeMin;

	public void setVolumeOFFTimeMin(int volumeOffTimeMin) {
		if (volumeOffTimeMin != -1){
			this.volumeOffTimeMin = volumeOffTimeMin;
		}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		 Bundle extras = intent.getExtras();
		 Log.d(MainActivity.DBGTAG, "Extras: " + extras.getInt(PRAYER_ALARM));

		 if (extras != null){
			 
			 if (extras.getInt(PRAYER_ALARM) == 1){
				 Log.d(MainActivity.DBGTAG, "Volume OFF alarm is received!!");

				 this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
				 switchOFFAudio();
				
				 setNextAlarm(context, VOLUME_ON_ALARM);
			 }
			 else if (extras.getInt(PRAYER_ALARM) == 2){
				 Log.d(MainActivity.DBGTAG, "Volume ON alarm is received!!");
				 
				 this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
				 switchONAudio();

				 setNextAlarm(context, NEW_ALARM);
			 }
			 else if (extras.getInt(PRAYER_ALARM) == 3){
				 Log.d(MainActivity.DBGTAG, "Prayer time update alarm is received!!");
				 context.sendBroadcast(new Intent("UPDATE_PRAYER_TIME"));
				 
				 setNextAlarm(context, NEW_ALARM);
			 }
			 else{
				 Log.d(MainActivity.DBGTAG, "Unknown broadcast is received!!");
			 }
		 }
	}

    private void setNextAlarm(Context context, String nextAlarm) {
    	long alarmTime;
    	
    	if (nextAlarm.equals(NEW_ALARM)){
    		alarmTime = getNextAlarmTime();
    		
    		if (alarmTime == -1){
    			setAlarm(context, 3, alarmTime);
    		}
    		else{
    			setAlarm(context, 1, alarmTime);
    		}
    	}
    	else if (nextAlarm.equals(VOLUME_ON_ALARM)){
    		alarmTime = System.currentTimeMillis() + volumeOffTimeMin * 60 * 1000;
    		setAlarm(context, 2, alarmTime);
    	}
    	else{
    		Log.d(MainActivity.DBGTAG, "PrayerTimeManager: Unknown Alarm request!!");
    	}
	}

	private long getNextAlarmTime() {
		return 0;
	}

	public void setAlarm(Context context, int value, long alarmtime){
    	AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    	
        Intent intent = new Intent(context, PrayerTimeManager.class);
        intent.putExtra(PRAYER_ALARM, value);
        
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, alarmtime , pi);
    }
    
	public void switchOFFAudio(){
		Log.d(MainActivity.DBGTAG, "PrayerTimeAudioManager: Switching OFF audio.");
		
	    if (Build.VERSION.SDK_INT >= 23) {
//	        aManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
//	        aManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
//	        aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
//	        aManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
//	        aManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
	    } else {
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	    	audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
	    	audioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
	    	audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
	    	audioManager.setStreamMute(AudioManager.STREAM_RING, true);
	    	audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
	    }
	}

	public void switchONAudio(){
		Log.d(MainActivity.DBGTAG, "PrayerTimeAudioManager: Switching ON audio.");
		
	    if (Build.VERSION.SDK_INT >= 23) {
//	    	aManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
//	    	aManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
//	    	aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE,0);
//	    	aManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
//	    	aManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
	    } else {
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	    	audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
	    	audioManager.setStreamMute(AudioManager.STREAM_ALARM, false);
	    	audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
	    	audioManager.setStreamMute(AudioManager.STREAM_RING, false);
	    	audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
	    }
	}

};