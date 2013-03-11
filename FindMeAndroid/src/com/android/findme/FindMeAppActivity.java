package com.android.findme;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import br.livroandroid.utils.MediaFileUtils;

import com.facebook.Session;

public class FindMeAppActivity extends Activity {
	public static String LOG_TAG = "FINDME";
	private SharedPreferences myprefs;
	public static final int TIRA_FOTO = 1002;
	public static final int FOTO_GALERIA = 1003;
	public static final String APP_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)  + "/FindMe";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myprefs = getSharedPreferences("user", MODE_PRIVATE);
	}
	
	public void trocaImagens(View invisibleView, ImageView visibleView, String filePath){
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		visibleView.setImageBitmap(bitmap);
		if(bitmap != null){
			Log.i(LOG_TAG, "Image Found!");
			invisibleView.setVisibility(View.INVISIBLE);
			invisibleView.invalidate();
			visibleView.setVisibility(ImageView.VISIBLE);
			visibleView.invalidate();
		}
	}
	
/**
 * 	MENU ONCLICK METHODS
 */
	
	public void newPhoto(MenuItem menu){
		Editor editor = myprefs.edit();
		editor.putString("foto",MediaFileUtils.takePicture(this, myprefs.getString("id",null)));
		editor.commit();
	}

	public void logOut(MenuItem menu){
		Log.i(LOG_TAG, "Logging Out!");
		try{
			Session.getActiveSession().closeAndClearTokenInformation();
			if(Session.getActiveSession() == null || Session.getActiveSession().isClosed()){
				Log.i(LOG_TAG, "Session Closed!");
				myprefs.edit().clear().commit();
				if(myprefs.getBoolean("confirmado", false) == false){
					startActivity(new Intent(getApplicationContext(),LoginActivity.class));
					finish();
				}
			}
		}catch(Exception e){
			Log.e(LOG_TAG,"Erro Logout", e);
		}
	}
	
	public void selecionaGaleria(MenuItem item){
		Toast.makeText(getApplicationContext(), "abre a galeria", Toast.LENGTH_SHORT).show();
//		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//		startActivityForResult(intent,FOTO_GALERIA);
		MediaFileUtils.gallerySearch(this, "image/*");
	}
	
	public String getPathFileSelected(Intent data){
		Uri imagemSelecionada = data.getData();
		Log.i(LOG_TAG, "URI selecionado >>>" + imagemSelecionada);
		String[] colunaFilePath = {MediaStore.Images.Media.DATA};
		Cursor cursor = getContentResolver().query(imagemSelecionada, colunaFilePath, null, null, null);
		cursor.moveToFirst();
		int indexColuna = cursor.getColumnIndex(colunaFilePath[0]);
		String arquivo = cursor.getString(indexColuna);
		Log.i(LOG_TAG, "Arquivo selecionado >>>" + arquivo);
		myprefs.edit().putString("foto", arquivo);
		return arquivo;
	}
	
	public static String takePicture(Activity activity, String id){
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File caminho = MediaFileUtils.getFile(MediaFileUtils.IMAGE_TYPE,id);
		Uri uri = Uri.fromFile(caminho);
		camera.putExtra(MediaStore.EXTRA_OUTPUT,uri);
		activity.startActivityForResult(camera, TIRA_FOTO);
		return caminho.getPath();
	}
	
	public void printHash(){
		// Add code to print out the key hash
	    try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.danilo.findme", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
	}
}
