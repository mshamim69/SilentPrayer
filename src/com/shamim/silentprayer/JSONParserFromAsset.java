package com.shamim.silentprayer;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;

public class JSONParserFromAsset {
	private Context mainPageContext;
	
	public JSONParserFromAsset(Context ctx) {
		this.mainPageContext = ctx;
	}

	public String loadJSONFromAsset(String file) {
		Log.d(MainActivity.DBGTAG, "Loading JSON file to parse: " + file);
		
        String json = null;
        try {
            InputStream is =  mainPageContext.getAssets().open(file);
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        
        return json;
    }
}
