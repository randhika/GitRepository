package com.findme.model;

import java.io.Serializable;

import com.android.findme.FindMeAppActivity;


public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1578865085361783861L;
	private Long user_id;
	private String user_name;
	private String xmpp_name;
	private String facebookId;
	private Enum<SEXO> gender;
	private String picturePath;
	private Location location;
	
	public enum SEXO {MALE,FEMALE}
	
	public Usuario(Long user_id, String user_name, String facebookId,
			String gender, String xmpp_name, String picturePath) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.facebookId = facebookId;
		if(gender.equalsIgnoreCase("male")){
			this.gender = SEXO.MALE;
		}else if(gender.equalsIgnoreCase("female")){
			this.gender = SEXO.FEMALE;
			
		}
		this.xmpp_name = xmpp_name;
		this.picturePath = picturePath;
	}

	
	public Usuario(){}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getXmpp_name() {
		return xmpp_name;
	}

	public void setXmpp_name() {
		this.xmpp_name = user_name+FindMeAppActivity.SUFIX_SMACK;
	}


	public Enum<SEXO> getGender() {
		return gender;
	}

	public void setGender(Enum<SEXO> gender) {
		this.gender = gender;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		if(user_name.trim().endsWith(FindMeAppActivity.SUFIX_SMACK)){
			user_name = user_name.split(FindMeAppActivity.SUFIX_SMACK)[0];
		}
		this.user_name = user_name;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	@Override
	public String toString() {
		return "Usuario [user_id=" + user_id + ", user_name=" + user_name
				+ ", facebookId=" + "facebookId" 
				+ ", gender=" + gender + ", picturePath=" + picturePath + "]";
	}
	
	
	
}
