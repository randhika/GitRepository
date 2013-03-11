package br.livroandroid.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class AndroidUtils {
    
    public static boolean isNetworkAvailable(Context context){
	ConnectivityManager conex = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	if(conex == null){
	    return false;
	}else {
	    NetworkInfo[] info = conex.getAllNetworkInfo();
	    if(info != null){
		for(int i =0 ; i < info.length ; i++){
		    if(info[i].getState().equals(NetworkInfo.State.CONNECTED) ){
			return true;
		    }
		}
	    }
	}
	return false;
    }

    public static void alertDialog(final Context context, final int mensagem){
	try{
	    AlertDialog alert = new AlertDialog.Builder(context).setTitle(context.getString(R.string.app_name)).setMessage(mensagem).create();
        	alert.setButton("OK", new DialogInterface.OnClickListener() {
        	    
        	    @Override
        	    public void onClick(DialogInterface dialog, int which) {
        		return;	
        	    }
        	});
        	alert.show();
	}catch (Exception e) {
	    Log.e(Contants.LOG_TAG, e.getMessage(), e);
	}
    }

    public static void alertDialog(Context context, String mensagem) {
	try{
	    AlertDialog alert = new AlertDialog.Builder(context).setTitle(context.getString(R.string.app_name)).setMessage(mensagem).create();
	    alert.setButton("OK", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
		    return;	
		}
	    });
	    alert.show();
	}catch (Exception e) {
	    Log.e(Contants.LOG_TAG, e.getMessage(), e);
	}
	
    }
    
    public static boolean isSDK_3(){
	int apiLevel = Build.VERSION.SDK_INT;
	if(apiLevel >= 11){
	    return true;
	}
	return false;
    }
    
//    retorna se a tela eh large ou xlarge
    public static boolean isTablet(Context context ){
	return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    
//    retorna se eh um table com sdk 3.x
    public static boolean isTablet3x(Context context){
	return isSDK_3() && isTablet(context);
    }
}