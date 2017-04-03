package com.shamim.silentprayer;

import android.widget.EditText;
import android.widget.TextView;

public class SettingsPageItemLocator {
	
	private TextView countryView;
	private TextView cityView;
	private TextView calculationView;
	private TextView juristicView;
	private TextView silentPeriodView;
	private EditText customView;
	
	public TextView getCountryView() {
		return countryView;
	}
	public void setCountryView(TextView countryView) {
		this.countryView = countryView;
	}
	public TextView getCityView() {
		return cityView;
	}
	public void setCityView(TextView cityView) {
		this.cityView = cityView;
	}
	public TextView getCalculationView() {
		return calculationView;
	}
	public void setCalculationView(TextView calculationView) {
		this.calculationView = calculationView;
	}
	public TextView getJuristicView() {
		return juristicView;
	}
	public void setJuristicView(TextView juristicView) {
		this.juristicView = juristicView;
	}
	public TextView getSilentPeriodView() {
		return silentPeriodView;
	}
	public void setSilentPeriodView(TextView silentPeriodView) {
		this.silentPeriodView = silentPeriodView;
	}
	public EditText getCustomView() {
		return customView;
	}
	public void setCustomView(EditText customView) {
		this.customView = customView;
	}
}
