package com.findme.adapters;

import java.util.List;
import java.util.Locale;

import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.findme.FindMeAppActivity;
import com.android.findme.R;
import com.android.findme.util.ValidateUtils;
import com.facebook.widget.ProfilePictureView;
import com.findme.model.Usuario;

public class ChatAdapter extends FindMeBaseAdapter {
	
	private Usuario app_user, user_recipient;
	
	public ChatAdapter(Activity context, List<Message> mensagens, Usuario app_user, Usuario user_recipient) {
		super(context, mensagens);
		this.app_user = app_user;
		this.user_recipient = user_recipient;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View linha = convertView;
		Message mensagem = (Message) lista.get(position);
		LayoutInflater inflater = context.getLayoutInflater();
		ViewHolder holder;
		if(linha == null){
			holder = new ViewHolder();
			Log.i(this.getClass().getSimpleName().toUpperCase(new Locale("pt-BR")), "MESSAGE FROM " + mensagem.getFrom());
			if(mensagem.getFrom().equalsIgnoreCase(app_user.getUser_name())){
				linha = inflater.inflate(R.layout.sender_msg_row, null);
				holder.mensagem = (TextView) linha.findViewById(R.id.tv_user_chat_msg);
			}else if(mensagem.getFrom().equalsIgnoreCase(user_recipient.getUser_name()+"@192.168.1.2/findme")){
				linha = inflater.inflate(R.layout.receiver_msg_row, null);
//				holder.foto = (ImageView) linha.findViewById(R.id.iv_recipient_photo);
//				holder.profile_thumb = (ProfilePictureView) linha.findViewById(R.id.recipient_row_profile_picture);
				holder.mensagem = (TextView) linha.findViewById(R.id.tv_recipient_msg);
//				holder.progress = (ProgressBar) linha.findViewById(R.id.recipient_progress);
			}
			linha.setTag(holder);
		}else{
			holder = (ViewHolder) linha.getTag();
		}
		if(!ValidateUtils.validateIsNull(holder.foto,holder.profile_thumb,holder.progress, user_recipient)){
			gerenciaFotoPerfil(holder.foto, holder.profile_thumb,holder.progress, user_recipient);
		}
		holder.mensagem.setText(mensagem.getBody());
		return linha;
	}

	static class ViewHolder {
		ImageView foto;
		ProfilePictureView profile_thumb;
		TextView mensagem;
		ProgressBar progress;
	}
	
}
