package com.shamim.silentprayer;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;

public class MainPageItemLocator {
	
	private DigitalClock currentTimeView;
	private TextView currentDateView;
	private Button volumeStatusView;
	private TextView volumeCounterView;
	private TextView currentLocationView;
	private TextView fajrTimeView;
	private TextView dhuhrTimeView;
	private TextView asrTimeView;
	private TextView maghribTimeView;
	private TextView ishaTimeView;
	private WebView browserView;
	private Button updateBtnView;
	private Button settingsBtnView;
	
	public MainPageItemLocator() {
		Log.d(MainActivity.DBGTAG, "MainPageItemLocator: Constructor() is called.");
	}

	public DigitalClock getCurrentTimeView() {
		return currentTimeView;
	}

	public void setCurrentTimeView(DigitalClock currentTimeView) {
		this.currentTimeView = currentTimeView;
	}

	public TextView getCurrentDateView() {
		return currentDateView;
	}

	public void setCurrentDateView(TextView currentDateView) {
		this.currentDateView = currentDateView;
	}

	public Button getVolumeStatusView() {
		return volumeStatusView;
	}

	public void setVolumeStatusView(Button volumeStatusView) {
		this.volumeStatusView = volumeStatusView;
	}

	public TextView getVolumeCounterView() {
		return volumeCounterView;
	}

	public void setVolumeCounterView(TextView volumeCounterView) {
		this.volumeCounterView = volumeCounterView;
	}

	public TextView getCurrentLocationView() {
		return currentLocationView;
	}

	public void setCurrentLocationView(TextView currentLocationView) {
		this.currentLocationView = currentLocationView;
	}

	public TextView getFajrTimeView() {
		return fajrTimeView;
	}

	public void setFajrTimeView(TextView fajrTimeView) {
		this.fajrTimeView = fajrTimeView;
	}

	public TextView getDhuhrTimeView() {
		return dhuhrTimeView;
	}

	public void setDhuhrTimeView(TextView dhuhrTimeView) {
		this.dhuhrTimeView = dhuhrTimeView;
	}

	public TextView getAsrTimeView() {
		return asrTimeView;
	}

	public void setAsrTimeView(TextView asrTimeView) {
		this.asrTimeView = asrTimeView;
	}

	public TextView getMaghribTimeView() {
		return maghribTimeView;
	}

	public void setMaghribTimeView(TextView maghribTimeView) {
		this.maghribTimeView = maghribTimeView;
	}

	public TextView getIshaTimeView() {
		return ishaTimeView;
	}

	public void setIshaTimeView(TextView ishaTimeView) {
		this.ishaTimeView = ishaTimeView;
	}

	public WebView getBrowserView() {
		return browserView;
	}

	public void setBrowserView(WebView browserView) {
		this.browserView = browserView;
	}

	public Button getUpdateBtnView() {
		return updateBtnView;
	}

	public void setUpdateBtnView(Button updateBtnView) {
		this.updateBtnView = updateBtnView;
	}

	public Button getSettingsBtnView() {
		return settingsBtnView;
	}

	public void setSettingsBtnView(Button settingsBtnView) {
		this.settingsBtnView = settingsBtnView;
	}	
}
