package br.livroandroid.orientacao;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

public class OrientacaoUtils {
//    verifica qual a orientacao da tela
    public int getOrientacao(Context context){
	int orientacao = context.getResources().getConfiguration().orientation;
	return orientacao;
    }
    
    public boolean isVertical(Context context){
	int orientacao = context.getResources().getConfiguration().orientation;
	boolean vertical = orientacao == Configuration.ORIENTATION_PORTRAIT;
	return vertical;
    }
   
    public boolean isHorizontal(Context context){
	int orientacao = context.getResources().getConfiguration().orientation;
	boolean vertical = orientacao == Configuration.ORIENTATION_LANDSCAPE;
	return vertical;
    }
    
    public void setOrientacaoVertical(Activity context){
	context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    public void setOrientacaoHorizontal(Activity context){
	context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

}
