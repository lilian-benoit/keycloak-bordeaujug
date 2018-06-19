package fr.lbenoit.presentation.keycloak.rest.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/speakers")
public class SpeakersEndPoint {

	ObjectMapper objectMapper = new ObjectMapper();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public List<Map<String, Object>> getAllSpeakers() throws IOException {
		InputStream is = getClass().getResourceAsStream("/speakers.json");
		
		List<Map<String, Object>> speakers = objectMapper.readValue(is,
                        new TypeReference<ArrayList<HashMap<String, Object>>>() {});

        return speakers;
	}

}
