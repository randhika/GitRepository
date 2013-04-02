package com.android.guia102android.activity;

import android.app.Activity;
import android.os.AsyncTask;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTask;
import br.livroandroid.utils.AndroidUtils;

public class Guia102Activity extends Activity{
    private TransacaoTask transacao;
    
    public void alert(String mensagem){
	AndroidUtils.alertDialog(this, mensagem);
    }
    
    public void startTransacao(Transacao transacao){
	    this.transacao = new TransacaoTask(this, transacao, R.string.aguarde);
	    this.transacao.execute();
    }
    
    public void startTransacao(Transacao transacao, int mensagem){
	    this.transacao = new TransacaoTask(this, transacao, mensagem);
	    this.transacao.execute();
    }
  
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(transacao != null){
            boolean running = transacao.getStatus() == AsyncTask.Status.RUNNING;
            if( running ){
        	transacao.cancel(true);
        	transacao.fecharProgress();
            }
        }
    }
    
}
