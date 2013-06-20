package com.findme.services;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.findme.model.Usuario;

@WebService
@Path("/findme")
public class FindMeServices{
	
	@GET
	@Path("/boys/{latitude}/{longitude}/{endereco}")
	@Produces("application/json")
	public List<Usuario> getBoys(@PathParam("longitude") String latitude,@PathParam("longitude") String longitude,@PathParam("endereco") String endereco){
		List<Usuario> usuarios = new ArrayList<>();
		return usuarios;
	}
	
	@GET
	@Path("/test")
	public Response test(){
		System.out.println("service chamado");
		return Response.status(Status.OK).entity("Resposta do WS").build();
	}
}
