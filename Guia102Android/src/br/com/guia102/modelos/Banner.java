package br.com.suaempresa.modelos;

import java.io.Serializable;

public class Banner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3886507181729643849L;
	private Integer id_cidade;
	private String nome_arquivo;
	private Cidade cidade;
	
	public String getNome_arquivo() {
		return nome_arquivo;
	}

	public void setNome_arquivo(String nomeArquivo) {
		nome_arquivo = nomeArquivo;
	}

	public Integer getId_cidade() {
		return id_cidade;
	}
	public void setId_cidade(Integer idCidade) {
		id_cidade = idCidade;
	}

	public Cidade getCidade() {
	    return cidade;
	}

	public void setCidade(Cidade cidade) {
	    this.cidade = cidade;
	}

}
