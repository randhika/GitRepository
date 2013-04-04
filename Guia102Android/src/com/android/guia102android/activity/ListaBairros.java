package com.android.guia102android.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.guia102.modelos.Bairro;

public class ListaBairros implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8160622704795053134L;
    /**
     * 
     */
    public static final String KEY = "bairros";
    protected List<Bairro> bairros= new ArrayList<Bairro>();
    
    public ListaBairros(List<Bairro> bairros){
	this.bairros= bairros;
    }
    
}
