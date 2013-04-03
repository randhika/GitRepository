package br.com.suaempresa.modelos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class Atividade implements Serializable 
{
    /**
     * 
     */
    private static final long serialVersionUID = 4482263862699359852L;
    public static final String KEY= "atividadeId";
	private Integer id;
	private String nome;
	private List<Empresa> empresas;

	public List<Empresa> getEmpresas() throws SQLException {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}