package com.game.releases;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.util.Log;

public class ServiceUtils{
	public static final String LOG_TAG = "SERVICE";
	
	public static boolean isServiceStarted(Context ctx, String serviceName){
		ActivityManager manager =  (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
		
		for(RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
			if(service.service.getClassName().equals(serviceName)){
				Log.i(LOG_TAG, serviceName + " IS RUNNING");
				return true;
			}
		}
		return false;
	}

}
