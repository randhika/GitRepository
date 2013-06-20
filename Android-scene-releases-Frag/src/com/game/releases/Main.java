package com.game.releases;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.game.releases.fragments.ReleasesFragment;
import com.game.releases.models.Platforms;

public class Main extends SherlockFragmentActivity implements OnClickListener{

	private NotificationManager notificationManager;
	private Button bt_x360;
	private Button bt_ps3;
	private Button bt_wii;
	private Button bt_PC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		setContentView(R.layout.layout_main);
		notificationManager.cancel(RssService.NOTIFICATION_ID);
		setTitle(R.string.app_name);
		setTitleColor(getResources().getColor(R.color.DarkOliveGreen));
//		LastReleasesFragment rel_frag = new LastReleasesFragment();
//		FragmentTransaction transaction = getSupportFragmentManager()
//				.beginTransaction();
//		transaction.add(R.id.telaLista, rel_frag);
//		transaction.commit();
		bt_x360 = (Button) findViewById(R.id.btx360);
		bt_ps3 = (Button) findViewById(R.id.btPs3);
		bt_wii = (Button) findViewById(R.id.btWii);
		bt_PC = (Button) findViewById(R.id.btPc);
		bt_x360.setOnClickListener(this);
		bt_ps3.setOnClickListener(this);
		bt_PC.setOnClickListener(this);
		bt_wii.setOnClickListener(this);
		
	}

	public void buildIntent(int category, String title, int icon) {
		setTitle(R.string.app_name);
		Bundle bundle = new Bundle();
		bundle.putInt("category", category);
		bundle.putString("title", title);
		bundle.putInt("icon", icon);
		ReleasesFragment rel_frag = new ReleasesFragment();
		rel_frag.setArguments(bundle);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.telaLista, rel_frag);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.settings:
			intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btx360:
			buildIntent(0, "Xbox360", R.drawable.x360_icon_mini);
//			if (v.isPressed()) {
//				((Button) v).setCompoundDrawables(null, getResources().getDrawable(R.drawable.x360_icon_pressed), null, null);
//			}
			setBgIfJeellyBeans(v);
			setViewBgInactive(bt_PC);
			setViewBgInactive(bt_ps3);
			setViewBgInactive(bt_wii);
			break;
		case R.id.btPs3:
			buildIntent(1, "Playstation 3", R.drawable.ps3_icon_48);
			setBgIfJeellyBeans(v);
			setViewBgInactive(bt_PC);
			setViewBgInactive(bt_x360);
			setViewBgInactive(bt_wii);
			break;
		case R.id.btWii:
			buildIntent(2, "Wii", R.drawable.wii_icon_48);
			setBgIfJeellyBeans(v);
			setViewBgInactive(bt_PC);
			setViewBgInactive(bt_x360);
			setViewBgInactive(bt_ps3);
			break;
		case R.id.btPc:
			buildIntent(3, "PC", R.drawable.pc_icon_48);
			setBgIfJeellyBeans(v);
			setViewBgInactive(bt_ps3);
			setViewBgInactive(bt_x360);
			setViewBgInactive(bt_wii);
		}
	}
	
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void setBgIfJeellyBeans(View v){
		if(Build.VERSION.SDK_INT ==  Build.VERSION_CODES.JELLY_BEAN){
			v.setBackground(getResources().getDrawable(R.drawable.bt_selected_bg_left));
		}else{
			v.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_selected_bg_left));
		}
	}
	
	private void setViewBgInactive(View v){
		v.setBackgroundColor(0);
	}

	@Override
	protected void onDestroy() {
		if (!ServiceUtils.isServiceStarted(this, RssService.class.getName())) {
			Intent intentService = new Intent(this, RssService.class);
			startService(intentService);
		}
		super.onDestroy();
	}

}
