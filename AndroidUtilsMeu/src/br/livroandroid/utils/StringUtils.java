package br.livroandroid.utils;

import java.util.Locale;

public class StringUtils {
	
	public static String primeiraMaiusc(String s){
		String aux = s.replaceFirst("[a-z]", s.substring(0,1).toUpperCase(new Locale("pt-BR")));
		return aux;
	}
}
