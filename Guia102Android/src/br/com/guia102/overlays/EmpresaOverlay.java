package br.com.guia102.overlays;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class EmpresaOverlay extends Overlay {
    private Address address;
    private Bitmap bitmap;
    
    public EmpresaOverlay(Address endereco, Bitmap bitmap) {
	this.address = endereco;
	this.bitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas, MapView mapview, boolean shadow) {
	Projection projection = mapview.getProjection();
	if(shadow == false){
	    Double lat = address.getLatitude()*1E6;
	    Double longitude = address.getLongitude()*1E6;
	    
	    GeoPoint geo = new GeoPoint(lat.intValue(), longitude.intValue());
	    Point mypoint = new Point();
	    projection.toPixels(geo, mypoint);
	    
	    Paint paint = new Paint();
	    paint.setARGB(250, 255, 255, 255);
	    paint.setAntiAlias(true);
	    paint.setFakeBoldText(true);
	    canvas.drawBitmap(bitmap,mypoint.x,mypoint.y, paint);
	}
	
        super.draw(canvas, mapview, shadow);
    }
    
    
    @Override
    public boolean onTap(GeoPoint arg0, MapView arg1) {
        // TODO Auto-generated method stub
        return false;
    }
}
