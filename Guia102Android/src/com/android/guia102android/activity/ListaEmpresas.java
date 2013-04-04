package com.android.guia102android.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.suaempresa.modelos.Empresa;

public class ListaEmpresas implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8160622704795053134L;
    /**
     * 
     */
    public static final String KEY = "empresas";
    protected List<Empresa> empresas = new ArrayList<Empresa>();
    
    public ListaEmpresas(List<Empresa> empresas){
	this.empresas= empresas;
    }
    
}
