package com.findme.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.livroandroid.utils.DownloadImagemUtil;

import com.android.findme.R;
import com.facebook.widget.ProfilePictureView;
import com.findme.model.Usuario;

public class UserAdapter extends BaseAdapter {
	
	private List<Usuario> users;
	private Activity context;
	
	public UserAdapter(Activity context,List<Usuario> users){
		this.users = users;
		this.context = context;
	}

	@Override
	public int getCount() {
		return users.size();
	}

	@Override
	public Object getItem(int position) {
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
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
		Usuario user = users.get(position);
		DownloadImagemUtil downloader = new DownloadImagemUtil(context);
//		downloader.download(context, user.getPicturePath(), holder.foto, progress)
		
		holder.profile_thumb.setProfileId(user.getFacebookId());
		holder.nome.setText(user.getUserName());
		holder.foto.setContentDescription(user.getUserName());
		holder.sexo.setContentDescription(user.getGender().name());
		return viewLinha;
	}

	static class ViewHolder {
		ImageView foto;
		ProfilePictureView profile_thumb;
		TextView nome;
		ImageView sexo;
//		TextView local;
	}
}
