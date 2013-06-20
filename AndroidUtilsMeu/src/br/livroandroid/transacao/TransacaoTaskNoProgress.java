package br.livroandroid.transacao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import br.livroandroid.utils.AndroidUtils;
import br.livroandroid.utils.Contants;

public class TransacaoTaskNoProgress extends AsyncTask<Void, Void, Boolean> {

	private final Context context;
	private final Transacao transacao;
	private Throwable exception;
	private ProgressBar progresso;

	public TransacaoTaskNoProgress(Context context, Transacao transacao, ProgressBar progresso) {
		this.context = context;
		this.transacao = transacao;
		this.progresso =progresso;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progresso.setVisibility(View.VISIBLE);
		progresso.invalidate();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			Log.d(Contants.LOG_TAG, "doInBackground");
			transacao.executar();
			return true;
		} catch (Throwable e) {
			Log.e(Contants.LOG_TAG, e.getMessage(), e);
			this.exception = e;
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {
			progresso.setVisibility(View.INVISIBLE);
			progresso.invalidate();
			transacao.atualizaView();
			Log.d(Contants.LOG_TAG, "Atualizou a view e chamou onPostExecute");
		} else {
			AndroidUtils
					.alertDialog(context, "Erro :" + exception.getMessage());
		}
	}

}
