package com.findme.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import br.livroandroid.utils.ModelConverter;

public class Location extends ModelConverter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -15350906756190405L;
	private Long location_id;
	private Double latitude;
	private Double longitude;
	private String endereco;
	private List<Usuario> usuarios;
	
	
	public Location(Long location_id, Double latitude, Double longitude,
			String endereco) {
		super();
		this.location_id = location_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.endereco = endereco;
	}


	public Location(){}

	public Long getLocation_id() {
		return location_id;
	}


	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}

	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public String getEndereco() {
		return endereco;
	}



	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}



	public List<Usuario> getUsuarios() {
		return usuarios;
	}



	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}



	@Override
	public void invoqueSetters(Object valorJson, Method setters, Object model)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		setters.invoke(model, valorJson);
	}

	
}
