package com.game.releases;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.game.releases.models.Rss;
import com.game.releases.models.RssAbgx;
import com.game.releases.models.RssBj;
import com.game.releases.models.RssXbSky;
import com.game.releases.models.RssXspeeds;

public class Main extends ApplicationActivity{

	private NotificationManager notificationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		setContentView(R.layout.layout_main);
		notificationManager.cancel(RssService.NOTIFICATION_ID);
	}
	
	public void getFeedsAbgx(View v){
		buildIntent(new RssAbgx(), "ABGX");
	}
	public void getFeedsXspeeds(View v){
		buildIntent(new RssXspeeds(), "Xspeeds");
	}
	public void getFeedsBj(View v){
		buildIntent(new RssBj() , "Bj-Share");
	}
	public void getFeedsSky(View v){
		buildIntent(new RssXbSky() , "XBOX-SKY");
	}
	
	public void buildIntent(Rss rss, String title){
		Intent i = new Intent(this, ReleasesActivity.class);
		i.putExtra("rssurl", rss);
		i.putExtra("title", title);
		startActivity(i);
	}
	
	@Override
	protected void onDestroy() {
		if(!ServiceUtils.isServiceStarted(this, RssService.class.getName())){
			Intent intentService = new Intent(this, RssService.class);
			startService(intentService);
		}
		super.onDestroy();
	}
}
