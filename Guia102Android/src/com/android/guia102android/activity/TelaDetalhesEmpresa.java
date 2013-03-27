package com.android.guia102android.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import br.com.guia102.overlays.EmpresaOverlay;
import br.com.suaempresa.modelos.Empresa;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class TelaDetalhesEmpresa extends MapActivity{

    private Empresa empresa;
    private List<Overlay> overlays;
    private MapView mapView;
    private MapController controller;
    private LocationListener listenner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.empresa_detalhes);
	empresa = (Empresa) getIntent().getSerializableExtra(Empresa.KEY);
	TextView titulo = (TextView) findViewById(R.id.titulo);
	TextView descricao = (TextView) findViewById(R.id.tObservacoes);
	TextView enderecoTxView = (TextView) findViewById(R.id.tEndereco);
	enderecoTxView.setText(empresa.getRua() + "," + empresa.getNumero());
	titulo.setText(empresa.getNome_fantasia());
	descricao.setText(empresa.getObservacoes());
	
	 
        LocationManager locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setCostAllowed(true);
        String provider = locMan.getBestProvider(criteria, true);
        boolean providerActive = locMan.isProviderEnabled(provider);
        mapView = (MapView) findViewById(R.id.mapView);
        controller  = mapView.getController();
        overlays = mapView.getOverlays();
        mapView.displayZoomControls(true);
        controller.setZoom(16);
        mapView.setSatellite(false);
        if(!providerActive){
            Toast.makeText(this, provider + " inativo!" , Toast.LENGTH_SHORT).show();
        }
        Geocoder geo = new Geocoder(this);
        EmpresaOverlay ovEmp = null;
        Bitmap bitmap = null;
        final Location myLocation = new Location(provider);
        mapView.displayZoomControls(true);
        try {
            Address endereco = geo.getFromLocationName(empresa.getRua() + " " + empresa.getNumero() +" Curitiba",1).get(0);
            System.out.println("BAIRRO " + endereco.getAddressLine(1));
            myLocation.setLatitude(endereco.getLatitude());
            myLocation.setLongitude(endereco.getLongitude());
            Resources res = getResources();
            bitmap = BitmapFactory.decodeResource(res,R.drawable.ic_guia102);
            Bitmap novoBitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
            ovEmp = new EmpresaOverlay(endereco, novoBitmap);
            overlays.add(ovEmp);
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
	
        
        listenner = new LocationListener() {
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
		atualizaMinhaPosicao(myLocation);
	    }
	};
	atualizaMinhaPosicao(myLocation);
	//TESTAR E SE PRECISO ATIVAR GPS
	locMan.requestLocationUpdates(provider, 1, 20, listenner);
    }
	

    public void onClick(View v) {
	String url = empresa.getSite();
	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    
    public void atualizaMinhaPosicao(Location location){
	if(location != null){
	    GeoPoint p = new GeoPoint(Double.valueOf(location.getLatitude()* 1E6).intValue(),Double.valueOf(location.getLongitude()* 1E6).intValue());
	    mapView.displayZoomControls(true);
	    controller.animateTo(p);
	}
    }
    
    @Override
    protected boolean isRouteDisplayed() {
	// TODO Auto-generated method stub
	return false;
    }
}
