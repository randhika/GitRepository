package com.game.releases;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.game.releases.models.ReleaseParcel;

public class NotificationActivity extends Activity {
	
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
		ArrayList<ReleaseParcel> releases_names =  (ArrayList<ReleaseParcel>) getIntent().getSerializableExtra("relParcels");
		System.out.println(releases_names == null);
		List<String> list = new ArrayList<String>();
//		list.addAll(releases_names);
		last_releases.setAdapter(new ArrayAdapter<ReleaseParcel>(this, android.R.layout.simple_list_item_1,releases_names));
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
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
