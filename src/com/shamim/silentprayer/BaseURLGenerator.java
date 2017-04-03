package com.shamim.silentprayer;

import android.content.Context;

public class BaseURLGenerator {
	/*
	 *  http://www.islamicfinder.org/prayer-times?latitude=60.4167&longitude=22.1831
	 *  &timeZone=GMT%2B2&placeName=Turku, FI&calculationMethod=5&juristicMethod=1
	 *  &hijriDateAdjustment=0&fajrAngle=15.0&ishaAngle=15.0
	 *  &autoLocationSettings=false&dhuharTimeAfterZawal=1
	 *  &maghribTimeAfterSunset=1&dayLightAdjustment=0&startDate=&endDate=&language=en"
	 */
	private static final String ISLAMIC_FINDER_BASIC_URL = 
			"http://www.islamicfinder.org/prayer-times?"
			+ "latitude=%s&"
			+ "longitude=%s&"
			+ "timeZone=%s&"
			+ "placeName=%s&"
			+ "calculationMethod=%s&"
			+ "juristicMethod=%s&"
			+ "hijriDateAdjustment=%s&"
			+ "fajrAngle=%s&"
			+ "ishaAngle=%s&"
			+ "autoLocationSettings=%s&"
			+ "dhuharTimeAfterZawal=%s&"
			+ "maghribTimeAfterSunset=%s&"
			+ "dayLightAdjustment=%s&"
			+ "startDate=&endDate=&"
			+ "language=en";
	
	private String baseURL;
	private JSONParserFromAsset jsonParser;
	private CityLocator cityLocator;
	private CalculationSetting calculationSetting;

	public BaseURLGenerator(Context ctx) {
		this.jsonParser = new JSONParserFromAsset(ctx);
	}
	
	public String getBaseURL() {
		return baseURL;
	}

	public void setCustomURL(String baseURL) {
		this.baseURL = baseURL;
	}
	
	public CityLocator getCityLocator() {
		return cityLocator;
	}

	public void setCityLocator(CityLocator cityLocator) {
		this.cityLocator = cityLocator;
	}

	public CalculationSetting getCalculationSetting() {
		return calculationSetting;
	}

	public void setCalculationSetting(CalculationSetting calculationSetting) {
		this.calculationSetting = calculationSetting;
	}
	
	public void islamicFinderUpdateURL(String country, String city, String calc_method, String juris_method, String daylight){
		this.cityLocator = new CityLocator(jsonParser, country, city);
		this.calculationSetting = new CalculationSetting(jsonParser, calc_method, juris_method, daylight);
		this.baseURL = generateBaseURL();
	}

	private String generateBaseURL() {
		String latitude = cityLocator.getLatitude();
		String longitude = cityLocator.getLongitude();
		String timeZone = cityLocator.getTimeZone();
		String placeName = cityLocator.getPlace();
		String calculationMethod = calculationSetting.getCalculationMethod();
		String juristicMethod = calculationSetting.getJuristicMethod();
		String hijriDateAdjustment = calculationSetting.getHijridateadjustment();
		String fajrAngle = calculationSetting.getFajrAngle();
		String ishaAngle = calculationSetting.getIshaAngle();
		String autoLocationSettings = calculationSetting.getAutolocationsettings();
		String dhuharTimeAfterZawal = calculationSetting.getDhuhartimeafterzawal();
		String maghribTimeAfterSunset = calculationSetting.getMaghribtimeaftersunset();
		String dayLightAdjustment = calculationSetting.getDayLightAdjustment();
		String startDate = calculationSetting.getStartdate();
		String endDate = calculationSetting.getEnddate();
		String language = calculationSetting.getLanguage();
		
		String url = 
				String.format(ISLAMIC_FINDER_BASIC_URL, 
						latitude, longitude, timeZone, placeName, calculationMethod,
						juristicMethod, hijriDateAdjustment, fajrAngle, ishaAngle,
						autoLocationSettings, dhuharTimeAfterZawal, maghribTimeAfterSunset,
						dayLightAdjustment, startDate, endDate, language);
		return url;
	}
}
