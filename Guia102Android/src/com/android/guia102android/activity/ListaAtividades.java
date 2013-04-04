package com.android.guia102android.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.suaempresa.modelos.Atividade;

public class ListaAtividades implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = -8160622704795053134L;
	/**
     * 
     */
	public static final String KEY = "atividades";
	protected List<Atividade> atividades = new ArrayList<Atividade>();

	public ListaAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

}
