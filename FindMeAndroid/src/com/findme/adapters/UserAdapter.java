package com.findme.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.findme.R;
import com.android.findme.util.ValidateUtils;
import com.facebook.widget.ProfilePictureView;
import com.findme.model.Usuario;

public class UserAdapter extends FindMeBaseAdapter {
	
	public UserAdapter(Activity context,List<Usuario> users){
		super(context, users);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View viewLinha = convertView;
		if(viewLinha == null){
			LayoutInflater infrater = context.getLayoutInflater();
			viewLinha = infrater.inflate(R.layout.user_row, null);
			ViewHolder holder = new ViewHolder();
			holder.foto = (ImageView) viewLinha.findViewById(R.id.iv_user_photo);
			holder.nome = (TextView) viewLinha.findViewById(R.id.tv_user_name);
			holder.sexo = (ImageView) viewLinha.findViewById(R.id.iv_user_gender);
			holder.profile_thumb = (ProfilePictureView) viewLinha.findViewById(R.id.user_row_profile_picture);
			viewLinha.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) viewLinha.getTag();
		Usuario user = (Usuario) lista.get(position);
		holder.loader = (ProgressBar) viewLinha.findViewById(R.id.progress_user_foto);
		if(!ValidateUtils.validateIsNull(holder.foto,holder.profile_thumb,holder.loader, user)){
			gerenciaFotoPerfil(holder.foto, holder.profile_thumb,holder.loader, user);
		}
		holder.nome.setText(user.getXmpp_name());
		holder.foto.setContentDescription(user.getUser_name());
		holder.sexo.setContentDescription(user.getGender().name());
		return viewLinha;
	}

	static class ViewHolder {
		ImageView foto;
		ProfilePictureView profile_thumb;
		TextView nome;
		ImageView sexo;
		ProgressBar loader;
//		TextView local;
	}
}
