package com.shamim.silentprayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private View settingsActivityView;
	private Context settingsPageContext;
	private SettingsPageManager settingsManager;
	private String[] settingsData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(MainActivity.DBGTAG, "SettingsActivity: onCreate() is called.");
		
		// Disable the detection of any violations
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		this.settingsPageContext = this;
		setContentView(R.layout.settings_activity);
		this.settingsActivityView = findViewById(R.id.settingsActivityId);
		
		this.settingsData = new String[6];
		this.settingsManager = new SettingsPageManager();
		settingsManager.setSettingsPageItems(settingsActivityView);
		
		((Spinner) findViewById(R.id.countryId)).setAdapter(getAdapter(R.array.countries));
		((Spinner) findViewById(R.id.cityId)).setAdapter(getAdapter(R.array.cities));
		((Spinner) findViewById(R.id.calculationId)).setAdapter(getAdapter(R.array.calculation_methods));
		((Spinner) findViewById(R.id.juristicId)).setAdapter(getAdapter(R.array.juristic_methods));
		((Spinner) findViewById(R.id.silentPeriodId)).setAdapter(getAdapter(R.array.silent_periods));

		addButtonListener();

		addSpinnerItemSelectedListener();
	}

	private void addSpinnerItemSelectedListener() {
		Spinner spinner = (Spinner) findViewById(R.id.silentPeriodId);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView tv = (TextView) findViewById(R.id.customTitleId);
				EditText etv = (EditText) findViewById(R.id.customTimeId);
				
				if (parent.getItemAtPosition(position).toString().equals("Custom")) {
					tv.setVisibility(View.VISIBLE);
					etv.setVisibility(View.VISIBLE);
				}
				else{
					tv.setVisibility(View.INVISIBLE);
					etv.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	private ArrayAdapter<CharSequence> getAdapter(int arrayId) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(settingsPageContext, arrayId,
				R.layout.custom_spinner);
		return adapter;
	}

	private void addButtonListener() {
		Button saveBtn = (Button) findViewById(R.id.saveSettingsId);
		Button cancelBtn = (Button) findViewById(R.id.cancelSettingsId);

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(settingsPageContext, "Settings is being cancelled...", Toast.LENGTH_LONG).show();
				
				setResult(RESULT_OK);
				finish();
			}
		});

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(settingsPageContext, "Settings is being saved...", Toast.LENGTH_LONG).show();
				settingsManager.saveSettingsValues(settingsData);
				
				Intent intent = new Intent();
				intent.putExtra("SETTINGS_DATA", settingsData);
				
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

}