package com.android.findme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTask;

import com.findme.adapters.UserAdapter;
import com.findme.model.ListaUsuarios;
import com.findme.model.Usuario;
import com.findme.services.UserServices;

public class ListUsersActivity extends FindMeAppActivity implements Transacao{

	private ListaUsuarios users;
	private ListView lv_users;
	private String users_gender;
	private Usuario app_user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_layout);
		lv_users = (ListView) findViewById(R.id.lv_users);
		Bundle bundle = getIntent().getExtras();
		app_user = (Usuario) bundle.getSerializable("app_user");
		users_gender = bundle.getString("users_gender");
		
		lv_users.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Usuario user = (Usuario) adapter.getItemAtPosition(position);
				Intent intent = new Intent(getApplicationContext(), ChattingRoomActivity.class);
				intent.putExtra("recipient_user", user);
				intent.putExtra("app_user", app_user);
				intent.putExtra("chat_create", true);
				startActivity(intent);
			}
		});
		
		startTransacao(new TransacaoTask(this, this, R.string.logando));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("users",users);
	}

	@Override
	public void atualizaView() {
		lv_users.setAdapter(new UserAdapter(this, users.getUsers()));
	}


	@Override
	public void executar() throws Exception {
		users = new ListaUsuarios(UserServices.findUsers(users_gender, app_user));
		if(connection == null){
			connectXMPP();
		}
	}
	
}
