package com.findme.services;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.livroandroid.utils.HttpHelper;
import br.livroandroid.utils.StringUtils;

import com.findme.model.Usuario;

public class UserServices {
	private static String URL_REST_PREFIX = "http://192.168.1.2:62418/findmeServices/findme/{query}";
	
	public static List<Usuario> findBoys(){
		String url = URL_REST_PREFIX.replace("{query}", "boys/123/321/endtest");
		System.out.println(url);
		InputStream response = HttpHelper.doPost(url, "UTF-8");
		if(response != null){
			try {
				JSONArray json_users_list = new JSONArray(HttpHelper.streamToString(response, "UTF-8"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void testJson(){
		
	}
	
	public static Usuario jsonToUsuario(JSONObject o){
		Usuario retorno = null;
//		Method[] metodos = retorno.getClass().getMethods();
		Iterator<String> it = o.keys();
		while(it.hasNext()){
			String chave = it.next().toString();
			String metodo = "set" + StringUtils.primeiraMaiusc(chave);
			System.out.println(metodo);
			System.out.println("chave " + o.opt(chave));
		}
		return null;
	}
}
