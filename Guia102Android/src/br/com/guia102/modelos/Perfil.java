package br.com.suaempresa.modelos;

public class Perfil
{
	private Integer id;
	private String descricao;
    
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}