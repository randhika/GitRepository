package br.com.suaempresa.modelos;

import java.io.Serializable;


public class Bairro implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1232859921391145753L;
	private Integer id;
	private Integer id_cidade;
	private String nome;
	private Cidade cidade;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
