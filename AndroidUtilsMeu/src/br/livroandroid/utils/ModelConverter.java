package br.livroandroid.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.json.JSONObject;

public abstract class ModelConverter {
	
	public Object jsonToModel(JSONObject jsonObject, Object javaObject){
		Iterator<String> it = jsonObject.keys();
		while(it.hasNext()){
			try {
				String chave = it.next().toString();
				String nome_metodo_set = "set" + StringUtils.primeiraMaiusc(chave);
				String nome_metodo_get = "get" + StringUtils.primeiraMaiusc(chave);
				Class<?> tipoRetorno = javaObject.getClass().getMethod(nome_metodo_get,new Class[0]).getReturnType();
//				System.out.println(nome_metodo_get + " return " + tipoRetorno.getName());
				Method setters = javaObject.getClass().getMethod(nome_metodo_set,tipoRetorno);
				Object valorJson = jsonObject.opt(chave);
//				System.out.println("Valor JSON " + valorJson.toString());
				invoqueSetters(valorJson, setters, javaObject);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		return javaObject;
	}
	
	public abstract void invoqueSetters(Object valorJson, Method setters, Object model) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;

}
