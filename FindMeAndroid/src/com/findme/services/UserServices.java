package com.findme.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.livroandroid.utils.HttpHelper;

import com.android.findme.util.UsuarioConverter;
import com.findme.model.Usuario;

public class UserServices {
	private static String URL_REST_PREFIX = "http://192.168.1.2:62418/findmeServices/findme/{query}";
	
	public static List<Usuario> findUsers(String gender_users, Usuario user) throws JSONException{
//		String url = URL_REST_PREFIX.replace("{query}", user.getGender() + "/" + user.getLocation().getLatitude() + "/" 
//					+ user.getLocation().getLongitude() + "/" + user.getLocation().getEndereco());
//		System.out.println(url);
//		InputStream response = HttpHelper.doPost(url, "UTF-8");
		List<Usuario> users = null;
		String resposta = "[{\"user_id\":1,\"facebookId\":\"100005428717630\",\"gender\":\"MALE\",\"xmpp_name\":\"dani@192.168.1.2/findme\",\"picturePath\":\"\",\"user_name\":\"dani\"},{\"user_id\":2,\"facebookId\":\"100005428717630\",\"gender\":\"MALE\",\"xmpp_name\":\"danilo@192.168.1.2/findme\",\"picturePath\":\"http://caminho_da_foto.jpg\",\"user_name\":\"danilo\"}]";
//		if(response != null){
			try {
//				JSONArray json_users_list = new JSONArray(HttpHelper.streamToString(response, "UTF-8"));
				JSONArray json_users_list = new JSONArray(resposta);
				users = new ArrayList<Usuario>();
				for(int i =0 ; i < json_users_list.length() ; i++){
					UsuarioConverter converter = new UsuarioConverter();
					if(json_users_list.length() > 0){
						JSONObject json = (JSONObject)json_users_list.get(i); 
						Usuario usuario = (Usuario) converter.jsonToObject(json);
						users.add(usuario);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
//		}
		return users;
	}
}
