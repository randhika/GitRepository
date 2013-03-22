package com.android.findme;


import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.livroandroid.utils.MediaFileUtils;

import com.facebook.widget.ProfilePictureView;
import com.findme.model.Usuario;

public class Dashboard extends FindMeAppActivity{
	private SharedPreferences myPrefs;
	private ProfilePictureView profileView;
	private ImageView fotoRodape;
	private TextView tv_endereco;
	private Location mylocation;
	private LocationManager locationManager;
	private Usuario app_user;
	
	private LocationListener location_listener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.i(LOG_TAG, "STATUS CHANGED - " + provider + " - status =" + status);
			//o Status ok deve == 2
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			Log.i(LOG_TAG, provider + " ENABLED");
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			Log.w(LOG_TAG, provider + " DISABLED");
		}
		
		@Override
		public void onLocationChanged(Location location) {
			updateLocation();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Listener gps_listener = new GpsStatus.Listener() {
			
			@Override
			public void onGpsStatusChanged(int event) {
				if(event == GpsStatus.GPS_EVENT_STARTED){
					System.out.println("GPS_EVENT_STARTED");
				}
				if(event == GpsStatus.GPS_EVENT_STOPPED){
					System.out.println("GPS_EVENT_STOPPED");
				}
				if(event == GpsStatus.GPS_EVENT_FIRST_FIX){
					System.out.println("GPS_EVENT_FIRST_FIX");
				}
				if(event == GpsStatus.GPS_EVENT_SATELLITE_STATUS){
					System.out.println("GPS_EVENT_SATELLITE_STATUS");
				}
				
			}
		};
		
		setChatListenner();
		setContentView(R.layout.layout_dashboard);
		myPrefs = getSharedPreferences("user", MODE_PRIVATE);
		
		app_user = new Usuario(null, myPrefs.getString("username", null), myPrefs.getString("id", null),
				myPrefs.getString("sexo", null), myPrefs.getString("foto", null));
		
		fotoRodape =(ImageView) findViewById(R.id.iv_find_foto_footer);
		profileView = (ProfilePictureView) findViewById(R.id.foto_footer);
		tv_endereco = (TextView) findViewById(R.id.tv_endereco);
		setTitle(app_user.getUser_name());
//		System.out.println(id);
		
		setTitleColor(Color.WHITE);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		locationManager.addGpsStatusListener(gps_listener);
//		if(app_user.getPicturePath() == null){
//			profileView.setCropped(true);
//			profileView.setProfileId(app_user.getFacebookId());
//		}else{
//			trocaImagens(profileView, fotoRodape, app_user.getPicturePath());
//		}
		
		setUserProfilePicture(profileView, fotoRodape, app_user);
		
		getMyLocation();
		updateLocation();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 500, location_listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dashboard_menu, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		app_user.setPicturePath(myPrefs.getString("foto", null));
		if(requestCode == MediaFileUtils.TAKE_PICTURE && resultCode == RESULT_OK){
			trocaImagens(profileView, fotoRodape, app_user.getPicturePath());
		}else if(requestCode == MediaFileUtils.TAKE_GALLERY && resultCode == RESULT_OK){
			trocaImagens(profileView, fotoRodape, getPathFileSelected(data));
		}
	}
		
	public  void checkGPSStatus(LocationManager locationManager){
		LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
		if(!locationManager.isProviderEnabled(provider.getName())){
			final AlertDialog alert = new AlertDialog.Builder(this).setTitle(provider.getName()+ " Desabilitado")
					.setMessage("Cara, habilite seu " + provider.getName() + " senão não funciona!").create();
			alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					alert.dismiss();
				}
			});
			alert.show();
		}
	}
	
	public void getMyLocation(){
		checkGPSStatus(locationManager);
		Log.i(LOG_TAG, "Getting Location");
		mylocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Log.i(LOG_TAG, "MyLocation " + mylocation);
	}
	
	public void updateLocation(){
		Log.i(LOG_TAG, "Atualizando Localizacao");
		if(mylocation != null){
			Geocoder geocoder = new Geocoder(this);
			try {
				Address endereco = geocoder.getFromLocation(mylocation.getLatitude(), mylocation.getLongitude(), 1).get(0);
				Log.i(LOG_TAG, "ENDERECO " + endereco.getAddressLine(0));
				tv_endereco.setText(endereco.getAddressLine(0));
				tv_endereco.invalidate();
				com.findme.model.Location location = new com.findme.model.Location(null,mylocation.getLatitude(), mylocation.getLongitude(),endereco.getAddressLine(0)); 
				app_user.setLocation(location);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			Toast.makeText(getApplicationContext(), "Não consegui pegar sua localização!",Toast.LENGTH_LONG).show();
		}
	}
	
	public void findBoys(View v){
		Intent intent = new Intent(this, ListUsersActivity.class);
		intent.putExtra("app_user", app_user);
		intent.putExtra("users_gender", "male");
		startActivity(intent);
	}
	public void findGirls(View v){
		Intent intent = new Intent(this, ListUsersActivity.class);
		intent.putExtra("app_user", app_user);
		intent.putExtra("users_gender", "female");
		startActivity(intent);
	}
	public void findAll(View v){
		Intent intent = new Intent(this, ListUsersActivity.class);
		intent.putExtra("app_user", app_user);
		intent.putExtra("users_gender", "both");
		startActivity(intent);
	}
	
	public void sincronizar(MenuItem menu){
		Toast.makeText(getApplicationContext(), "Sincronizando Localização...",Toast.LENGTH_SHORT).show();
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				profileView.setProfileId(app_user.getFacebookId());
				getMyLocation();
				updateLocation();
			}
		});
	}
}
