package com.android.guia102android.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import br.com.suaempresa.modelos.Empresa;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TelaDetalhesEmpresa extends FragmentActivity implements Transacao {

	private Empresa empresa;
	private GoogleMap map;
	private Location enterprise_location;
	private TransacaoTask task;
	private GroundOverlay overlay;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empresa_detalhes);
		empresa = (Empresa) getIntent().getSerializableExtra(Empresa.KEY);
		TextView titulo = (TextView) findViewById(R.id.titulo);
		TextView descricao = (TextView) findViewById(R.id.tObservacoes);
		TextView site = (TextView) findViewById(R.id.tsite);
		site.setText(empresa.getSite());
		titulo.setText(empresa.getNome_fantasia());
		descricao.setText(empresa.getObservacoes());
		task = new TransacaoTask(this, this, R.string.aguarde);
		task.execute();
	}

	public void onClick(View v) {
		String url = empresa.getSite();
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}

	public void locatePositionInMap() {
		try {
			Geocoder geo = new Geocoder(this);
			enterprise_location = new Location(LocationManager.GPS_PROVIDER);
			Address endereco = geo.getFromLocationName(
					empresa.getRua() + " " + empresa.getNumero() + ","
							+ empresa.getCidade(), 1).get(0);
			Log.i(Guia102Aplicacao.TAG,
					"Endereço " + endereco.getAddressLine(0));
			enterprise_location.setLatitude(endereco.getLatitude());
			enterprise_location.setLongitude(endereco.getLongitude());
		} catch (Exception e) {
			Log.e(Guia102Aplicacao.TAG, "LOCATION ERROR", e);
			e.printStackTrace();
		}
	}

	public void setUpMapIfNeeded() {
		if (map == null) {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.frag_map)).getMap();
			if (map != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		if (enterprise_location != null) {
			LatLng position = new LatLng(enterprise_location.getLatitude(),
					enterprise_location.getLongitude());
			MarkerOptions marker = new MarkerOptions();
			marker = marker
					.position(position)
					.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.ic_guia102_circle48px)))
					.snippet(empresa.getRua()+", " + empresa.getNumero())
					.title(empresa.getNome_fantasia());
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
			map.addMarker(marker);
		}
	}
	
	private void setUpMapOverlay() {
		if (enterprise_location != null) {
			LatLng position = new LatLng(enterprise_location.getLatitude(),
					enterprise_location.getLongitude());
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 11));
			  overlay = map.addGroundOverlay(new GroundOverlayOptions()
              .image(BitmapDescriptorFactory.fromResource(R.drawable.ic_guia102)).anchor(0, 1)
              .position(position, 8600f, 6500f));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Log.i(Guia102Aplicacao.TAG, "onResume()");
	}

	@Override
	public void atualizaView() {
		// Log.i(Guia102Aplicacao.TAG, "atualizandoView()");
		setUpMapIfNeeded();
	}

	@Override
	public void executar() throws Exception {
		// Log.i(Guia102Aplicacao.TAG, "executar()");
		locatePositionInMap();
	}
}
