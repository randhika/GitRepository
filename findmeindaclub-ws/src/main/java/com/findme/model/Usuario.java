package com.findme.model;

import java.io.Serializable;


public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1578865085361783861L;
	private Long user_id;
	private String user_name;
	private String facebookId;
	private String latitude;
	private String longitude;
	private String address;
	private Enum<SEXO> gender;
	private String picturePath;
	public enum SEXO {MALE,FEMALE}
	
	public Usuario(String facebookId,String user_name, String latitude, String longitude,
			String address, Enum<SEXO> gender, String picturePath) {
		super();
		this.facebookId = facebookId;
		this.user_name = user_name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.gender = gender;
		this.picturePath = picturePath;
	}

	
	public Long getUser_id() {
		return user_id;
	}


	public String getUserName() {
		return user_name;
	}


	public void setUserName(String userName) {
		this.user_name = userName;
	}


	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}



	public Enum<SEXO> getGender() {
		return gender;
	}

	public void setGender(Enum<SEXO> gender) {
		this.gender = gender;
	}

	public Usuario(){
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
}