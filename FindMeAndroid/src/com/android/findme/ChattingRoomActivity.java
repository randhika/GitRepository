package com.android.findme;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.findme.adapters.ChatAdapter;
import com.findme.model.Usuario;

public class ChattingRoomActivity extends FindMeAppActivity {

	private Chat chat;
	private ArrayList<Message> mensagens;
	private Handler handler = new Handler();
	private ListView lv_msgs;
	private EditText et_msg;
	private Usuario recipient_user;
	private Usuario app_user;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chatting_room);
		if (savedInstanceState != null) {
			mensagens = (ArrayList<Message>) savedInstanceState
					.getSerializable("mensagens");
		} else {
			mensagens = new ArrayList<Message>();
		}
		recipient_user = (Usuario) getIntent().getSerializableExtra(
				"recipient_user");
		app_user = (Usuario) getIntent().getSerializableExtra("app_user");
		String chatId = getIntent().getStringExtra("chat_id");
		chat = iniciateChat(chatId);
		Log.i(LOG_TAG, "Chatting with " + chat.getParticipant());
		et_msg = (EditText) findViewById(R.id.et_msg);
		lv_msgs = (ListView) findViewById(R.id.lv_msgs);
		setTitle(recipient_user.getUser_name());
		setAdapter();
	}

	public Chat iniciateChat(String chatId) {
		MessageListener msgListenner = new MessageListener() {
			@Override
			public void processMessage(Chat chat, Message msg) {
				Log.i(LOG_TAG, "Mensagem recebida de " + msg.getFrom()
						+ " " + msg.getBody());
				if (msg.getBody() != null) {
					addMessage(msg);
					handler.post(new Runnable() {
						@Override
						public void run() {
							setAdapter();
						}
					});
				}
			}
		};
		
		ChatManager chatmannager = connection.getChatManager();
		Chat chat;
		Usuario recipient_user = (Usuario) getIntent().getSerializableExtra(
				"recipient_user");
		if(chatId != null){
			Log.i(LOG_TAG, "Chatting LISTENNER " + chatId);
			chat = chatmannager.getThreadChat(chatId);
			chat.addMessageListener(msgListenner);
			Message mensagem = new Message(app_user.getXmpp_name());
			mensagem.setFrom(recipient_user.getXmpp_name());
			mensagem.setBody(getIntent().getStringExtra("message"));
			addMessage(mensagem);
		}else{
			chat = chatmannager.createChat(recipient_user.getXmpp_name(),msgListenner);
		}
		// if(chat != null){
		// try {
		// connection.getRoster().createEntry(destinatario,
		// recipient_user.getUser_name(), null);
		// } catch (XMPPException e) {
		// e.printStackTrace();
		// Log.e(LOG_TAG,"Entry not Created");
		// }
		// }
		return chat;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chatting_room, menu);
		return true;
	}

	public void enviaMsg(View v) {
		try {
			Message mensagem = new Message();
			mensagem.setBody(et_msg.getText().toString());
			mensagem.setFrom(app_user.getUser_name());
			chat.sendMessage(mensagem);
			addMessage(mensagem);
			handler.post(new Runnable() {
				@Override
				public void run() {
					setAdapter();
					et_msg.setText("");
				}
			});
		} catch (XMPPException e) {
			Log.e(LOG_TAG, "Mensagem não enviada");
			e.printStackTrace();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("mensagens", mensagens);
		super.onSaveInstanceState(outState);
	}

	public void setAdapter() {
		lv_msgs.setAdapter(new ChatAdapter(ChattingRoomActivity.this,
				mensagens, app_user, recipient_user));
	}

	public synchronized void addMessage(Message msg) {
		mensagens.add(msg);
	}

	@Override
	protected void onDestroy() {
		if (connection != null) {
			Log.i(LOG_TAG, "ondestroy CHatting Room");
			connection = null;
		}
		super.onDestroy();
	}

}
