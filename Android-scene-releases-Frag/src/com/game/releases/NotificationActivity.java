package com.game.releases;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.game.releases.adapters.LastReleaseAdapter;
import com.game.releases.models.ReleaseParcel;

public class NotificationActivity extends SherlockActivity {
	
	private ListView last_releases;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Links Downloads http://www.bj2.me/download.php?id=472497
//		Links Downloads http://xspeeds.eu/download.php?id=309321
		setContentView(R.layout.last_releases);
		NotificationManager mannager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mannager.cancel(RssService.NOTIFICATION_ID);
		last_releases = (ListView) findViewById(R.id.lv_lastReleases);
		List<ReleaseParcel> releases_names =  (ArrayList<ReleaseParcel>) getIntent().getSerializableExtra("relParcels");
		last_releases.setAdapter(new LastReleaseAdapter(releases_names, this));
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(NotificationActivity.this, Main.class));
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
