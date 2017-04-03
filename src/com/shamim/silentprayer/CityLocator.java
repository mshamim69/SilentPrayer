package com.shamim.silentprayer;

import org.json.JSONException;
import org.json.JSONObject;

public class CityLocator {
	
	private String place;
	private String latitude;
	private String longitude;
	private String timezone;
	
	public CityLocator(JSONParserFromAsset parser, String country, String city) {
		String input = parser.loadJSONFromAsset("city_locator.json");
		
		try {
			JSONObject reader = new JSONObject(input);
			JSONObject cityObj = reader.getJSONObject(country).getJSONObject(city);
			
			this.place = cityObj.getString("place_code");
			this.latitude = cityObj.getString("latitude");
			this.longitude = cityObj.getString("longitude");
			this.timezone = cityObj.getString("timezone");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getPlace() {
		return place;
	}

	public void setCityCountry(String place) {
		this.place = place;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTimeZone() {
		return timezone;
	}

	public void setTimeZone(String timezone) {
		this.timezone = timezone;
	}
}
