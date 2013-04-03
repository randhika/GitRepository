package com.android.guia102android.activity;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.utils.AndroidUtils;

public class Dashboard extends Guia102Activity implements Transacao {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		if (new File(APP_FILE_DOWN_DIR).list().length == 0) {
			if (!AndroidUtils.isNetworkAvailable(this)) {
				AndroidUtils.alertDialog(this, R.string.conexao_indisponivel);
				finish();
			} else {
				startTransacao(this, R.string.atualizando);
			}
		}

	}

	public void buscaPorAtividade(View v) {
		startActivity(new Intent(this, TelaListaAtividades.class));
	}

	public void buscaPorBairro(View v) {
		startActivity(new Intent(this, TelaListaBairros.class));

	}

	@Override
	public void atualizaView() {
		Toast.makeText(this, "Arquivos Atualizados!", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void executar() throws Exception {
		Guia102Service.baixaArquivosXml(this);
	}
}
