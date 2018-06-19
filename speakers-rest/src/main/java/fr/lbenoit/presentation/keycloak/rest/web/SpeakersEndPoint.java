package fr.lbenoit.presentation.keycloak.rest.web;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/speakers")
public class SpeakersEndPoint {

	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public String getAllSpeakers() throws IOException {
		return "TODO";
	}

}
