package com.findme.model;

import java.io.Serializable;
import java.util.List;

public class ListaUsuarios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2718167143076264785L;
	
	private List<Usuario> users;
	
	public ListaUsuarios(List<Usuario> users){
		this.users = users;
	}

	public List<Usuario> getUsers() {
		return users;
	}

	public void setUsers(List<Usuario> users) {
		this.users = users;
	}

	
}
