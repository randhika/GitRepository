package com.android.guia102android.activity;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.utils.AndroidUtils;

public class Dashboard extends Guia102Activity implements Transacao{
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        startTransacao(this, R.string.atualizando);
    }

    public void buscaPorAtividade(View v){
	startActivity(new Intent(this, TelaListaAtividades.class));
    }
    public void buscaPorBairro(View v){
	startActivity(new Intent(this, TelaListaBairros.class));
	
    }

    @Override
    public void atualizaView() {
	Toast.makeText(this, "Arquivos Atualizados!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void executar() throws Exception {
	 if(!AndroidUtils.isNetworkAvailable(this) && (new File(Environment.getExternalStorageDirectory() + "/atividades.xml").exists())){
	      Toast.makeText(this, "Voc� precisa conectar a uma rede ao menos uma vez para baixar os dados!", Toast.LENGTH_SHORT).show();
	      return;
	 }
	Guia102Service.baixaArquivosXml(this);
    }
}
