package com.android.findme.util;

import org.json.JSONObject;

import com.android.findme.interfaces.IJsonConverter;
import com.findme.model.Usuario;

public class UsuarioConverter implements IJsonConverter {

	@Override
	public Object jsonToObject(JSONObject jsonObject) {
		// Long user_id, String user_name, String facebookId,String gender,
		// String xmpp_name, String picturePath
		Usuario retorno = new Usuario(null, jsonObject.optString("user_name"),
				jsonObject.optString("facebookId"),
				jsonObject.optString("gender"),
				jsonObject.optString("xmpp_name"),
				jsonObject.optString("picturePath"));
		return retorno;
	}

}
