package br.livroandroid.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class MediaFileUtils {
	
	public static final int IMAGE_TYPE = 1; 
	public static final int VIDEO_TYPE = 2; 
	public static final Integer TAKE_PICTURE = 1001;
	public static final Integer TAKE_MOVIE = 1002;
	public static final Integer TAKE_GALLERY = 1003;
	
	public static File getFile(Integer type, String fbId){
		String timestamp = new SimpleDateFormat("ddMMyyyy_ssmm", Locale.getDefault()).format(Calendar.getInstance().getTime());
		File diretorio = null;
		File arquivo = null;
		if(type == IMAGE_TYPE){
			diretorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"FindMe");
			if(!diretorio.exists()){
				Log.i("UTILS", "Deveria criar diretorio" + diretorio.getPath());
				if(diretorio.mkdirs()){
					Log.i("UTILS", "Criou diretorio" + diretorio.getPath());
				}
			}
			arquivo = new File(diretorio, "IMG_"+fbId + "_" + timestamp + ".jpg");
		}else if(type ==VIDEO_TYPE){
			diretorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),"FindMe");
			if(!diretorio.exists()){
			}
			arquivo = new File(diretorio,"VID_" + fbId + "_" + timestamp + ".mp4");
		}
		return arquivo;
	}
	
	public static Uri getUrifromFile(Integer type, String fbId){
		Uri uri = Uri.fromFile(getFile(type, fbId));
		return uri;
	}
	
	public static String takePicture(Activity activity, String id){
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File caminho = getFile(MediaFileUtils.IMAGE_TYPE,id);
		Uri uri = Uri.fromFile(caminho);
		camera.putExtra(MediaStore.EXTRA_OUTPUT,uri);
		activity.startActivityForResult(camera, TAKE_PICTURE);
		return caminho.getPath();
	}
	public static void captureVideo(Activity activity, String id){
		Intent camera = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		camera.putExtra(MediaStore.EXTRA_OUTPUT,MediaFileUtils.getUrifromFile(MediaFileUtils.IMAGE_TYPE,id));
		camera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
		activity.startActivityForResult(camera, TAKE_MOVIE);
	}
	

	public static void gallerySearch(Activity activity){
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(intent,TAKE_GALLERY);
	}
	public static void gallerySearch(Activity activity, String type){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(type);
		activity.startActivityForResult(intent,TAKE_GALLERY);
	}
}
