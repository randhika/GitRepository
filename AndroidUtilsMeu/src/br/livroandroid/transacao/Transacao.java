package br.livroandroid.transacao;

public interface Transacao {

    public void atualizaView() ;
    public void executar()throws Exception;
}
