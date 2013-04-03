package br.com.suaempresa.modelos;

import java.io.Serializable;

public class Cidade implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String KEY= "cidadeId";
	private Integer id;
	private Integer id_estado;
	private String nome;
	
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
	public Integer getId_estado() {
		return id_estado;
	}
	public void setId_estado(Integer idEstado) {
		id_estado = idEstado;
	}

}
