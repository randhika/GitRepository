package com.findme.adapters;

import java.util.List;

import android.app.Activity;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import br.livroandroid.utils.DownloadImagemUtil;

import com.facebook.widget.ProfilePictureView;
import com.findme.model.Usuario;

public abstract class FindMeBaseAdapter extends BaseAdapter {
	
	protected List<?> lista;
	protected Activity context;
	
	public FindMeBaseAdapter(Activity context,List<?> lista){
		this.lista = lista;
		this.context = context;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	public void gerenciaFotoPerfil(ImageView iv_profile, ProfilePictureView pv_fb_profile,ProgressBar loader, Usuario user){
		
		if(user.getPicturePath() == null || user.getPicturePath().equals("")){
			pv_fb_profile.setProfileId(user.getFacebookId());
			loader.setVisibility(ProgressBar.INVISIBLE);
		}else{
			pv_fb_profile.setVisibility(ProfilePictureView.INVISIBLE);
			DownloadImagemUtil downloader = new DownloadImagemUtil(context);
			downloader.download(context, user.getPicturePath(), iv_profile, loader);
		}
		pv_fb_profile.invalidate();
		loader.invalidate();
		iv_profile.invalidate();
	}
	
}
