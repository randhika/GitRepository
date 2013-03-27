package com.android.guia102android.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import br.com.suaempresa.modelos.Empresa;
import br.livroandroid.transacao.Transacao;

import com.android.guia102.adapters.EmpresaAdapter;

public class TelaListaEmpresas extends Guia102Activity implements
	OnItemClickListener, Transacao {
    private ListView listView;
    private List<Empresa> empresas;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.listas);
	empresas = (List<Empresa>) getLastNonConfigurationInstance();
	listView = (ListView) findViewById(R.id.lista_atividades);
	listView.setOnItemClickListener(this);
	TextView titulo = (TextView) findViewById(R.id.titulo);
	String tituloString = getIntent().getStringExtra("categoria");
	tituloString = tituloString.toUpperCase();
	titulo.setText(tituloString);
	if (empresas == null && savedInstanceState != null) {
	    ListaEmpresas lista = (ListaEmpresas) savedInstanceState
		    .getSerializable(ListaEmpresas.KEY);
	    empresas = lista.empresas;
	}
	if (empresas != null) {
	    atualizaView();
	} else {
	    startTransacao(this);
	}
    }

    public void onItemClick(AdapterView<?> parent, View v, int posicao, long id) {
	Empresa e = (Empresa) parent.getAdapter().getItem(posicao);
	Intent i = new Intent(this, TelaDetalhesEmpresa.class);
	i.putExtra(Empresa.KEY, e);
	Log.i(Guia102Aplicacao.TAG, "EMPRESA " + e.getNome_fantasia());
	startActivity(i);
    }

    
    @Override
    public void atualizaView() {
//	atualiza os carros na thread principal
	if(empresas != null && !empresas.isEmpty()){
	    listView.setAdapter(new EmpresaAdapter(this, empresas));
	    Empresa e = empresas.get(0);
//	    atualizarDetalhes(c);
	}
	
    }

    @Override
    public void executar() throws Exception {
	Bundle bundle = getIntent().getExtras();
	String atividadeId = String.valueOf(bundle.getInt("atividadeId"));
	String cidadeId = String.valueOf(bundle.getInt("cidadeId"));
	System.out.println("ATIV " + atividadeId);
	System.out.println("CIDADE " + cidadeId);
	this.empresas = Guia102Service.getEmpresas(TelaListaEmpresas.this,"XmlEmpresas",atividadeId ,cidadeId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	outState.putSerializable(ListaAtividades.KEY, new ListaEmpresas(empresas));
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
	return empresas;
    }
}
