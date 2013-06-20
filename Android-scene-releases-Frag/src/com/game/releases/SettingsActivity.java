package com.game.releases;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.game.releases.models.Rss;
import com.game.releases.models.RssAbgx;
import com.game.releases.models.RssBj;
import com.game.releases.models.RssXbSky;
import com.game.releases.models.RssXspeeds;

public class SettingsActivity extends Activity{

	public static final String PREFERENCES = "XceneRelPref";
	public static final String NOTIFY_PREF = "notify";
	public static final String PERIOD_PREF = "period";
	public static final String CONSOLE_PREF = "console";
	private CheckBox notify;
	private CheckBox LED;
	private RadioGroup console; 
	private Spinner period;
	private String[] periods = {"Selecione o período","5 minutes ","10 minutes", "30 minutes", "1 hour", "4 hours"};
	private SharedPreferences myprefs;
	private SpinnerAdapter spinnerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		myprefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
		period = (Spinner)  findViewById(R.id.period);
		spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, periods);
		period.setAdapter(spinnerAdapter);
		notify = (CheckBox) findViewById(R.id.notify);
		getPreferences();
	}
	
	@Override
	public void onBackPressed() {
		Log.i("SETTINGS", ""+notify.isChecked());
		setPreferences();
		Intent intentService = new Intent(this, RssService.class);
		startService(intentService);
		super.onBackPressed();
	}
	
	private void setPreferences(){
		Editor edit = myprefs.edit();
		edit.putBoolean(NOTIFY_PREF, notify.isChecked());
		int period = getSpinnerItemValue();
		edit.putInt(PERIOD_PREF, period);
//		setar console
		edit.commit();
	}
	private void getPreferences(){
		notify.setChecked(myprefs.getBoolean(NOTIFY_PREF, false));
		Log.i("SETTINGS", "" + getSpinnerItemPos(myprefs.getInt(PERIOD_PREF, 0)));
		period.setSelection(getSpinnerItemPos(myprefs.getInt(PERIOD_PREF, 0)));
//		setar console
	}
	
	private int getSpinnerItemPos(int period){
		switch (period) {
		case 5:
 			return 1;
		case 10:
			return 2;
		case 30:
			return 3;
		case 60:
			return 4;
		case 240:
			return 5;
		default:
			return 0;
		}
	}
	private int getSpinnerItemValue(){
		switch (period.getSelectedItemPosition()) {
		case 1:
			return 5;
		case 2:
			return 10;
		case 3:
			return 30;
		case 4:
			return 60;
		case 5:
			return 240;
		default:
			return 0;
		}
	}
}
