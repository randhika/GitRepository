package com.android.findme;


import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import br.livroandroid.utils.MediaFileUtils;

import com.facebook.widget.ProfilePictureView;

public class Dashboard extends FindMeAppActivity{
	private SharedPreferences myPrefs;
	private ProfilePictureView profileView;
	private ImageView fotoRodape;
	private TextView tv_endereco;
	private String id;
	private String username;
	private String arquivo_foto;
	private Location mylocation;
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_dashboard);
		myPrefs = getSharedPreferences("user", MODE_PRIVATE);
		username = myPrefs.getString("username", null);
		id= myPrefs.getString("id", null);
		fotoRodape =(ImageView) findViewById(R.id.iv_find_foto_footer);
		profileView = (ProfilePictureView) findViewById(R.id.foto_footer);
		tv_endereco = (TextView) findViewById(R.id.tv_endereco);
		arquivo_foto = myPrefs.getString("foto", null);
		setTitle(username);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Log.i(LOG_TAG, "foto do prefs " + arquivo_foto);
		if(arquivo_foto == null){
			profileView.setCropped(true);
			profileView.setProfileId(id);
		}else{
			trocaImagens(profileView, fotoRodape, arquivo_foto);
		}
		getLocation();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dashboard_menu, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		arquivo_foto = myPrefs.getString("foto", null);
		if(requestCode == MediaFileUtils.TAKE_PICTURE && resultCode == RESULT_OK){
			trocaImagens(profileView, fotoRodape, arquivo_foto);
		}else if(requestCode == MediaFileUtils.TAKE_GALLERY && resultCode == RESULT_OK){
			trocaImagens(profileView, fotoRodape, getPathFileSelected(data));
		}
	}
	
	
	public void getLocation(){
		Log.i(LOG_TAG, "GetLocation");
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, false);
		mylocation = locationManager.getLastKnownLocation(provider);
		Log.i(LOG_TAG, "MyLocation " + mylocation);
		if(mylocation != null){
			Geocoder geocoder = new Geocoder(this);
			try {
				Address endereco = geocoder.getFromLocation(mylocation.getLatitude(), mylocation.getLongitude(), 1).get(0);
				Log.i(LOG_TAG, "ENDERECO " + endereco.getAddressLine(0));
				tv_endereco.setText(endereco.getAddressLine(0));
				tv_endereco.invalidate();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateLocation(){
		Log.i(LOG_TAG, "Atualizando Localizacao");
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 100, new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
			
			@Override
			public void onProviderEnabled(String provider) {
			}
			
			@Override
			public void onProviderDisabled(String provider) {
			}
			
			@Override
			public void onLocationChanged(Location location) {
				getLocation();
			}
		});
	}
}
