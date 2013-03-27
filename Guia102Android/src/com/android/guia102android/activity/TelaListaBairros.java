package com.android.guia102android.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.suaempresa.modelos.Bairro;
import br.livroandroid.transacao.Transacao;

import com.android.guia102.adapters.BairroAdapter;

public class TelaListaBairros extends Guia102Activity implements
	OnItemClickListener, Transacao {
    private ListView listView;
    private List<Bairro> bairros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.listas);
	listView = (ListView) findViewById(R.id.lista_atividades);
	listView.setOnItemClickListener(this);
	TextView titulo = (TextView) findViewById(R.id.titulo);
	titulo.setText("Bairros");
	if (savedInstanceState != null) {
	    ListaBairros lista = (ListaBairros) savedInstanceState.getSerializable(ListaBairros.KEY);
	    bairros = lista.bairros;
	}
	if (bairros != null) {
	    atualizaView();
	} else {
	    startTransacao(this);
	}
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int posicao, long id) {
	Toast.makeText(this, "Vai buscar a empresa pelo Bairro", Toast.LENGTH_SHORT).show();
//	Atividade atividade =(Atividade) parent.getAdapter().getItem(posicao);
//	if(atividade != null){
//	    Intent i = new Intent(this, TelaListaEmpresas.class);
//	    i.putExtra(Atividade.KEY,atividade.getId());
//	    i.putExtra("categoria",atividade.getNome());
//	    i.putExtra(Cidade.KEY,1);
//	    Log.i(Guia102Aplicacao.TAG, "ATIVIDADE" + atividade.getNome());
//	    startActivity(i);
//	}
    }

    
    @Override
    public void atualizaView() {
	if(bairros != null && !bairros.isEmpty()){
	    listView.setAdapter(new BairroAdapter(this, bairros));
	}
	
    }

    @Override
    public void executar() throws Exception {
	this.bairros = Guia102Service.getBairros(this,"XmlBairros");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	outState.putSerializable(ListaAtividades.KEY, new ListaBairros(bairros));
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
	return bairros;
    }
    
}
