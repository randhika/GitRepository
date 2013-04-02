package com.android.findme;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTask;
import br.livroandroid.utils.AndroidUtils;
import br.livroandroid.utils.MediaFileUtils;

import com.facebook.widget.ProfilePictureView;
import com.findme.model.Usuario;

public class Dashboard extends FindMeAppActivity implements Transacao {
	private SharedPreferences myPrefs;
	private ProfilePictureView profileView;
	private ImageView fotoRodape;
	private TextView tv_endereco;
	private Location mylocation;
	private LocationManager locationManager;
	private Usuario app_user;
	private Address endereco;

	private LocationListener location_listener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			if (status != 2) {
				Log.i(LOG_TAG, "STATUS NOK, CHANGED - " + provider
						+ " - status =" + status);
			}
			// o Status ok deve == 2
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

		// Listener gps_listener = new GpsStatus.Listener() {
		//
		// @Override
		// public void onGpsStatusChanged(int event) {
		// if(event == GpsStatus.GPS_EVENT_STARTED){
		// System.out.println("GPS_EVENT_STARTED");
		// }
		// if(event == GpsStatus.GPS_EVENT_STOPPED){
		// System.out.println("GPS_EVENT_STOPPED");
		// }
		// if(event == GpsStatus.GPS_EVENT_FIRST_FIX){
		// System.out.println("GPS_EVENT_FIRST_FIX");
		// }
		// if(event == GpsStatus.GPS_EVENT_SATELLITE_STATUS){
		// System.out.println("GPS_EVENT_SATELLITE_STATUS");
		// }
		//
		// }
		// };

		setContentView(R.layout.layout_dashboard);
		myPrefs = getSharedPreferences("user", MODE_PRIVATE);

		app_user = new Usuario(null, myPrefs.getString("username", null),
				myPrefs.getString("id", null), myPrefs.getString("sexo", null),
				"", myPrefs.getString("foto", null));

		fotoRodape = (ImageView) findViewById(R.id.iv_find_foto_footer);
		profileView = (ProfilePictureView) findViewById(R.id.foto_footer);
		tv_endereco = (TextView) findViewById(R.id.tv_endereco);
		setTitle(app_user.getUser_name());
		// System.out.println(id);

		setTitleColor(Color.WHITE);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// locationManager.addGpsStatusListener(gps_listener);
		// if(app_user.getPicturePath() == null){
		// profileView.setCropped(true);
		// profileView.setProfileId(app_user.getFacebookId());
		// }else{
		// trocaImagens(profileView, fotoRodape, app_user.getPicturePath());
		// }

		setUserProfilePicture(profileView, fotoRodape, app_user);
		getMyLocation();
		startTransacao(new TransacaoTask(Dashboard.this, Dashboard.this,
				R.string.logando));
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				30, 500, location_listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dashboard_menu, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		startTransacao(new TransacaoTask(Dashboard.this, Dashboard.this,
				R.string.logando));
		Log.i(LOG_TAG, "DASHBOARD onResume");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		app_user.setPicturePath(myPrefs.getString("foto", null));
		if (requestCode == MediaFileUtils.TAKE_PICTURE
				&& resultCode == RESULT_OK) {
			trocaImagens(profileView, fotoRodape, app_user.getPicturePath());
		} else if (requestCode == MediaFileUtils.TAKE_GALLERY
				&& resultCode == RESULT_OK) {
			trocaImagens(profileView, fotoRodape, getPathFileSelected(data));
		}
	}

	public void getMyLocation() {
		AndroidUtils.checkGpsStatus(this,locationManager, R.string.alert_unavailable_gps_title, R.string.alert_unavailable_gps_message);
		Log.i(LOG_TAG, "Getting Location");
		mylocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Log.i(LOG_TAG, "MyLocation " + mylocation);
	}

	public Address inverseGeocoding() {
		Log.i(LOG_TAG, "Atualizando Localizacao");
		Address endereco = null;
		if (mylocation != null) {
			Geocoder geocoder = new Geocoder(this);
			try {
				endereco = geocoder.getFromLocation(mylocation.getLatitude(),
						mylocation.getLongitude(), 1).get(0);
				Log.i(LOG_TAG, "ENDERECO " + endereco.getAddressLine(0));
			} catch (IOException e) {
				Log.e(LOG_TAG, "Geocoding Error", e);
			}
		}
		return endereco;
	}

	public void updateLocation() {
		if (mylocation != null) {
			if (endereco != null){
				
				tv_endereco.setText(endereco.getAddressLine(0));
			tv_endereco.invalidate();
			com.findme.model.Location location = new com.findme.model.Location(
					null, mylocation.getLatitude(), mylocation.getLongitude(),
					endereco.getAddressLine(0));
			app_user.setLocation(location);
			}else {
			Toast.makeText(getApplicationContext(),
					"Não consegui pegar sua localização!", Toast.LENGTH_LONG)
					.show();
			}
		}
	}

	/**
	 * ONCLICK METHODS
	 */

	public void findBoys(View v) {
		Intent intent = new Intent(this, ListUsersActivity.class);
		intent.putExtra("app_user", app_user);
		intent.putExtra("users_gender", "male");
		startActivity(intent);
	}

	public void findGirls(View v) {
		Intent intent = new Intent(this, ListUsersActivity.class);
		intent.putExtra("app_user", app_user);
		intent.putExtra("users_gender", "female");
		startActivity(intent);
	}

	public void findAll(View v) {
		Intent intent = new Intent(this, ListUsersActivity.class);
		intent.putExtra("app_user", app_user);
		intent.putExtra("users_gender", "both");
		startActivity(intent);
	}

	/**
	 * async methods
	 */

	public void sincronizar(MenuItem menu) {
		Toast.makeText(getApplicationContext(), "Sincronizando Localização...",
				Toast.LENGTH_SHORT).show();
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				profileView.setProfileId(app_user.getFacebookId());
				getMyLocation();
				updateLocation();
			}
		});
	}

	public void setXMPPConnection() {
		if (connection == null) {
			connectXMPP();
		}
	}

	@Override
	public void atualizaView() {
		setChatListenner(app_user);
		updateLocation();
	}

	@Override
	public void executar() throws Exception {
		setXMPPConnection();
		endereco = inverseGeocoding();
	}
}
