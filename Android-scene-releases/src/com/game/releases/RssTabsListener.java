package com.game.releases;

import android.annotation.TargetApi;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.v4.app.Fragment;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RssTabsListener implements TabListener {

	private Fragment frag;
	
	public RssTabsListener(Fragment frag) {
		super();
		this.frag = frag;
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

}
