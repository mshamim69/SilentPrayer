package com.shamim.silentprayer;

import org.json.JSONException;
import org.json.JSONObject;

public class CalculationSetting {

	private final String hijriDateAdjustment = "0";
	private final String autoLocationSettings="false";
	private final String dhuharTimeAfterZawal="1";
	private final String maghribTimeAfterSunset="1";
	private final String startDate="";
	private final String endDate="";
	private final String language="en";
	
	private String juristicMethod = "1";
	private String calculationMethod = "5";
	private String fajrAngle = "18.0";
	private String ishaAngle = "18.0";
	private String dayLightAdjustment = "0";

	public CalculationSetting(JSONParserFromAsset parser, String calc, String juristic, String daylight) {
		String input = parser.loadJSONFromAsset("calculation_setting.json");
		
		try {
			JSONObject reader = new JSONObject(input);
			
			JSONObject calcObj = reader.getJSONObject("calculationMethod").getJSONObject(calc);
			this.calculationMethod = calcObj.getString("method_code");
			this.fajrAngle = calcObj.getString("fajr_angle");
			this.ishaAngle = calcObj.getString("isha_angle");
			
			JSONObject juristicObj = reader.getJSONObject("juristicMethod");
			this.juristicMethod = juristicObj.getString(juristic);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.dayLightAdjustment = daylight;
	}

	public String getHijridateadjustment() {
		return hijriDateAdjustment;
	}

	public String getFajrAngle() {
		return fajrAngle;
	}

	public void setFajrAngle(String fajrAngle) {
		this.fajrAngle = fajrAngle;
	}

	public String getIshaAngle() {
		return ishaAngle;
	}
	
	public void setIshaAngle(String ishaAngle) {
		this.ishaAngle = ishaAngle;
	}

	public String getAutolocationsettings() {
		return autoLocationSettings;
	}

	public String getDhuhartimeafterzawal() {
		return dhuharTimeAfterZawal;
	}

	public String getMaghribtimeaftersunset() {
		return maghribTimeAfterSunset;
	}

	public String getStartdate() {
		return startDate;
	}

	public String getEnddate() {
		return endDate;
	}

	public String getLanguage() {
		return language;
	}

	public String getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

	public String getJuristicMethod() {
		return juristicMethod;
	}

	public void setJuristicMethod(String juristicMethod) {
		this.juristicMethod = juristicMethod;
	}

	public String getDayLightAdjustment() {
		return dayLightAdjustment;
	}

	public void setDayLightAdjustment(String dayLightAdjustment) {
		this.dayLightAdjustment = dayLightAdjustment;
	}

}
