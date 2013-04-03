package br.com.suaempresa.modelos;

import java.util.List;

public class Estado {
	private Integer id;
	private String nome;
	private List<Cidade> cidades;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Cidade> getCidades() {
	    return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
	    this.cidades = cidades;
	}

}
