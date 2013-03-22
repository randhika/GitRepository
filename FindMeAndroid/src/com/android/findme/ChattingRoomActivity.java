package com.android.findme;

import java.util.ArrayList;
import java.util.Iterator;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
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

public class ChattingRoomActivity extends FindMeAppActivity{

	private Chat chat;
	private ArrayList<Message> mensagens;
	private Handler handler = new Handler();
	private ListView lv_msgs;
	private EditText et_msg;
	private Usuario recipient_user;
	private Usuario app_user;
	
	public void setConnection(){
		if(connection == null){
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					connectXMPP();
				};
			});
			t.start();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chatting_room);
		recipient_user = (Usuario) getIntent().getSerializableExtra("user_selected");
		app_user = (Usuario) getIntent().getSerializableExtra("app_user");
		if(savedInstanceState != null){
			mensagens = (ArrayList<Message>) savedInstanceState.getSerializable("mensagens");
		}else{
			mensagens = new ArrayList<Message>();
		}
//		setConnection();
		chat = iniciateChat();
		et_msg = (EditText) findViewById(R.id.et_msg);
		lv_msgs = (ListView) findViewById(R.id.lv_msgs);
		setAdapter();
		Log.i(LOG_TAG, "Chatting with " + chat.getParticipant());
		setTitle(recipient_user.getUser_name());
		showRosters();
	}
	
	public Chat iniciateChat(){
		ChatManager chatmannager = connection.getChatManager();
		Chat chat = chatmannager.createChat(recipient_user.getUser_name()+"@"+connection.getHost(), new MessageListener() {
			@Override
			public void processMessage(Chat arg0, Message arg1) {
				Log.i(LOG_TAG, "Mensagem recebida de " + arg1.getFrom() + " " + arg1.getBody());
				if(arg1.getBody() != null){
					addMessage(arg1);
					handler.post(new Runnable() {
						@Override
						public void run() {
							setAdapter();
						}
					});
				}
				
			}
		});
		return chat;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chatting_room, menu);
		return true;
	}

	public void enviaMsg(View v){
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
			Log.e(LOG_TAG,"Mensagem não enviada");
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("mensagens", mensagens);
		super.onSaveInstanceState(outState);
	}
	
	public void setAdapter(){
		lv_msgs.setAdapter(new ChatAdapter(ChattingRoomActivity.this, mensagens, app_user, recipient_user));
	}
	
	public synchronized void addMessage(Message msg){
		mensagens.add(msg);
	}
	
	public void showRosters(){
		if(connection != null){
			Roster rosters = connection.getRoster();
			Iterator<RosterEntry> it = rosters.getEntries().iterator();
			while(it.hasNext()){
				RosterEntry entry = it.next();
				System.out.println(entry.getType());
			}
			
		}
		
	}
}
