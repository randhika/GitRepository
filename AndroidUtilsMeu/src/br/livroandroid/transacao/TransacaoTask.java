package br.livroandroid.transacao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import br.livroandroid.utils.AndroidUtils;
import br.livroandroid.utils.Contants;

public class TransacaoTask extends AsyncTask<Void, Void, Boolean> {

	private final Context context;
	private final Transacao transacao;
	private ProgressDialog progresso;
	private Throwable exception;
	private int aguardeMsg;

	public TransacaoTask(Context context, Transacao transacao, int msg) {
		this.context = context;
		this.transacao = transacao;
		this.aguardeMsg = msg;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		abrirProgress();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			Log.d(Contants.LOG_TAG, "doInBackground");
			transacao.executar();
		} catch (Throwable e) {
			Log.e(Contants.LOG_TAG, e.getMessage(), e);
			this.exception = e;
			return false;
		} finally {
			try {
				fecharProgress();
			} catch (Exception e) {
				this.exception = e;
				Log.e(Contants.LOG_TAG, e.getMessage(), e);
			}
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {
			transacao.atualizaView();
			Log.d(Contants.LOG_TAG, "Atualizou a view e chamou onPostExecute");
		} else {
			AndroidUtils
					.alertDialog(context, "Erro :" + exception.getMessage());
		}
	}

	public void abrirProgress() {
		try {
			Log.d(Contants.LOG_TAG, "onPreExecute");
			progresso = ProgressDialog.show(context, "",
					context.getString(aguardeMsg));
		} catch (Exception e) {
			this.exception = e;
			Log.e(Contants.LOG_TAG, e.getMessage(), e);
		}
	}

	public void fecharProgress() {
		try {
			if (progresso != null)
				progresso.dismiss();
//			Log.d(Contants.LOG_TAG, "FECHANDO PROGRESS");
		} catch (Exception e) {
			this.exception = e;
			Log.e(Contants.LOG_TAG, e.getMessage(), e);
		}
	}

}
