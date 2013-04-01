package com.android.findme.listenners;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.app.Activity;

import com.findme.model.Usuario;

public class ChatNoLocalMessageListenner extends FindMeListenner implements
		MessageListener {

	
	public ChatNoLocalMessageListenner(Usuario app_user, Activity context) {
		super(app_user, context);
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		createIncommingDataNotification(message);
	}

}
