package com.android.findme;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import br.livroandroid.transacao.Transacao;

import com.findme.adapters.UserAdapter;
import com.findme.model.ListaUsuarios;
import com.findme.model.Usuario;
import com.findme.services.UserServices;

public class ListUsersActivity extends FindMeAppActivity implements Transacao{

	private ListaUsuarios users;
	private ListView lv_users;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_layout);
		
		
		List<Usuario> list = new ArrayList<Usuario>();
		Usuario u = new Usuario("100005428717630", "danilo","", "", "", Usuario.SEXO.MALE, "");
		list.add(u);
		u = new Usuario("100005428717630", "Daniele", "","", "", Usuario.SEXO.FEMALE, "");
		list.add(u);
		u = new Usuario("100005428717630", "Ezias", "","", "", Usuario.SEXO.MALE, "");
		list.add(u);
		users = new ListaUsuarios(list);
		
		lv_users = (ListView) findViewById(R.id.lv_users);
//		lv_users.setAdapter(new UserAdapter(this, list));
		
		startTransacao(this, this);
		
//		TransacaoTask task = new TransacaoTask(this, this, R.string.sair);
//		task.execute();
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
		UserServices.findBoys();
	}
}
