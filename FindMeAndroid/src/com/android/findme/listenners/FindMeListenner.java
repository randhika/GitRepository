package com.android.findme.listenners;

import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.findme.ChattingRoomActivity;
import com.android.findme.FindMeAppActivity;
import com.android.findme.R;
import com.findme.model.Usuario;

public class FindMeListenner {
	
	private Usuario app_user;
	private Activity context;
	
	public FindMeListenner(){}
	
	public FindMeListenner(Usuario app_user,Activity context) {
		super();
		this.app_user = app_user;
		this.context = context;
	}

	public void createIncommingDataNotification(Message message){
		try{
			Intent intent = new Intent(context, ChattingRoomActivity.class);
			Usuario recipient_user = new Usuario();
			recipient_user.setUser_name(message.getFrom());
			recipient_user.setXmpp_name();
			intent.putExtra("app_user", app_user);
			intent.putExtra("recipient_user", recipient_user);
			intent.putExtra("message", message.getBody());
			PendingIntent pending = PendingIntent.getActivity(context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
			NotificationManager notification_manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new NotificationCompat.Builder(context).build();
			notification.icon = R.drawable.alert_48;
			//numero a que mostra a quantidade de notificacoes acumuladas
//		notification.number
			notification.defaults = Notification.DEFAULT_VIBRATE;
			notification.tickerText = "Mensagem de " + message.getFrom();
			notification.contentIntent = pending;
			RemoteViews rv =  new RemoteViews(context.getPackageName(), R.layout.notification_view);
			rv.setTextViewText(R.id.rv_notification, message.getFrom() + " disse : " + message.getBody());
			notification.contentView = rv;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			notification_manager.notify(FindMeAppActivity.NOTIFICATION_ID, notification);
		}catch(Exception e){
			Log.e(FindMeAppActivity.LOG_TAG, "erro ao processar mensagem do chat criado!!", e);
		}
	}
	
}