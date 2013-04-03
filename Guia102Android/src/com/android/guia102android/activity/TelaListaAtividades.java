package com.android.guia102android.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.guia102.modelos.Atividade;
import br.com.guia102.modelos.Cidade;
import br.livroandroid.transacao.Transacao;

import com.android.guia102.adapters.AtividadeAdapter;

public class TelaListaAtividades extends Guia102Activity implements
		OnItemClickListener, Transacao {
	private ListView listView;
	private List<Atividade> atividades;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listas);
		listView = (ListView) findViewById(R.id.lista_atividades);
		listView.setOnItemClickListener(this);
		TextView titulo = (TextView) findViewById(R.id.titulo);
		titulo.setText("Categorias");
		if (savedInstanceState != null) {
			ListaAtividades lista = (ListaAtividades) savedInstanceState
					.getSerializable(ListaAtividades.KEY);
			atividades = lista.atividades;
		}
		if (atividades != null) {
			atualizaView();
		} else {
			startTransacao(this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int posicao, long id) {
		Atividade atividade = (Atividade) parent.getAdapter().getItem(posicao);
		if (atividade != null) {
			Intent i = new Intent(this, TelaListaEmpresas.class);
			i.putExtra(Atividade.KEY, atividade.getId());
			i.putExtra("categoria", atividade.getNome());
			i.putExtra(Cidade.KEY, 1);
			Log.i(Guia102Aplicacao.TAG, "ATIVIDADE" + atividade.getNome());
			startActivity(i);
		}
	}

	@Override
	public void atualizaView() {
		if (atividades != null && !atividades.isEmpty()) {
			listView.setAdapter(new AtividadeAdapter(this, atividades));
		}

	}

	@Override
	public void executar() throws Exception {
		this.atividades = Guia102Service.getAtividades(this, "XmlAtividades");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(ListaAtividades.KEY, new ListaAtividades(
				atividades));
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return atividades;
	}

}
