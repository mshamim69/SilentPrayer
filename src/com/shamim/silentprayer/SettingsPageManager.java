package com.shamim.silentprayer;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsPageManager {
	private String country;
	private String city;
	private String calculation_method;
	private String juristic_method;
	private String silentPeriod;
	private String customTime;
	
	private SettingsPageItemLocator settings_item_locator;
	
	public SettingsPageManager() {
		this.settings_item_locator = new SettingsPageItemLocator();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCalculation_method() {
		return calculation_method;
	}

	public void setCalculation_method(String calculation_method) {
		this.calculation_method = calculation_method;
	}

	public String getJuristic_method() {
		return juristic_method;
	}

	public void setJuristic_method(String juristic_method) {
		this.juristic_method = juristic_method;
	}

	public String getSilentPeriod() {
		return silentPeriod;
	}

	public void setSilentPeriod(String silentPeriod) {
		this.silentPeriod = silentPeriod;
	}

	public SettingsPageItemLocator getSettings_item_locator() {
		return settings_item_locator;
	}

	public String getCustomTime() {
		return customTime;
	}

	public void setCustomTime(String customTime) {
		this.customTime = customTime;
	}

	public void setSettings_item_locator(SettingsPageItemLocator settings_item_locator) {
		this.settings_item_locator = settings_item_locator;
	}

	public void setSettingsPageItems(View view) {
		settings_item_locator.setCountryView((TextView) view.findViewById(R.id.countryId));
		settings_item_locator.setCityView((TextView) view.findViewById(R.id.cityId));
		settings_item_locator.setCalculationView((TextView) view.findViewById(R.id.calculationId));
		settings_item_locator.setJuristicView((TextView) view.findViewById(R.id.juristicId));
		settings_item_locator.setSilentPeriodView((TextView) view.findViewById(R.id.silentPeriodId));
		settings_item_locator.setCustomView((EditText) view.findViewById(R.id.customTimeId));
	}

	public void saveSettingsValues(String[] settingsData) {
		settingsData[0] = country = settings_item_locator.getCountryView().getText().toString();
		settingsData[1] = city = settings_item_locator.getCityView().getText().toString();
		settingsData[2] = calculation_method = settings_item_locator.getCalculationView().getText().toString();
		settingsData[3] = juristic_method = settings_item_locator.getJuristicView().getText().toString();
		settingsData[4] = silentPeriod = settings_item_locator.getSilentPeriodView().getText().toString();
		EditText customTimeView = (EditText) settings_item_locator.getCountryView();
		if (customTimeView.isShown()){
			settingsData[5] = customTime = customTimeView.getText().toString();
		}
	}

}
