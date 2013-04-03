package br.com.guia102.modelos;

import java.io.Serializable;


public class Empresa implements Serializable
{
	
    /**
     * 
     */
    private static final long serialVersionUID = 6007567421753571546L;
    public static final String KEY= "empresa";
    private Integer id;
	private Integer id_bairro;
	private Integer id_atividade;
	private Integer id_vendedor;
	private String nome_fantasia;
	private String razao_social;
	private String telefone1;
	private String telefone2;
	private String celular;
	private String fax;
	private String data_cadastro;
	private String dia_pagamento;
	private String rua;
	private String email;
	private String site;
	private String cpf;
	private String rg;
	private String cnpj;
	private String cep;
	private String observacoes;
	private String logomarca;
	private String login;
	private String senha;
	private String cidade;
	private String regIP;
	private Integer ativo;
	private Integer numero;
	private Integer em_dia;
	private String atividade;
	private String bairro;
	private String promocao;

	public String getPromocao() {
		return promocao;
	}

	public void setPromocao(String promocao) {
		this.promocao = promocao;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getId_bairro() {
		return id_bairro;
	}

	public void setId_bairro(Integer idBairro) {
		id_bairro = idBairro;
	}

	public Integer getId_atividade() {
		return id_atividade;
	}

	public void setId_atividade(Integer idAtividade) {
		id_atividade = idAtividade;
	}

	public Integer getId_vendedor() {
		return id_vendedor;
	}

	public void setId_vendedor(Integer idVendedor) {
		id_vendedor = idVendedor;
	}

	public String getNome_fantasia() {
		return nome_fantasia;
	}

	public void setNome_fantasia(String nomeFantasia) {
		nome_fantasia = nomeFantasia;
	}

	public String getRazao_social() {
		return razao_social;
	}

	public void setRazao_social(String razaoSocial) {
		razao_social = razaoSocial;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(String dataCadastro) {
		data_cadastro = dataCadastro;
	}

	public String getDia_pagamento() {
		return dia_pagamento;
	}

	public void setDia_pagamento(String diaPagamento) {
		dia_pagamento = diaPagamento;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getLogomarca() {
		return logomarca;
	}

	public void setLogomarca(String logomarca) {
		this.logomarca = logomarca;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getRegIP() {
		return regIP;
	}

	public void setRegIP(String regIP) {
		this.regIP = regIP;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getEm_dia() {
		return em_dia;
	}

	public void setEm_dia(Integer emDia) {
		em_dia = emDia;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}