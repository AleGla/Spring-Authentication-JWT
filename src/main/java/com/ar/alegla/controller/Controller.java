package com.ar.alegla.controller;

import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ar.alegla.model.Pokemon;
import com.ar.alegla.model.RequestPokemon;


@RestController
public class Controller {

	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping(value = "/pokemon/search",
				produces = "application/json",
				consumes = "application/json")
	public String getGreeting(@RequestBody RequestPokemon name, @RequestHeader String authentication ) {	
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authentication", authentication);
		headers.add("user-agent", "Application");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		final String URL = "https://pokeapi.co/api/v2/pokemon/" + name.getName();
		ResponseEntity<Pokemon> pokemon = restTemplate.exchange(URL, HttpMethod.GET, entity, Pokemon.class);
		HashMap<String, String> response = new HashMap();
		response.put("name", pokemon.getBody().getName());
		response.put("height", pokemon.getBody().getHeight().toString());
		response.put("weight", pokemon.getBody().getWeight().toString());
		response.put("type", pokemon.getBody().getTypes().get(0).getType().getName());
		log.info("Response --> " + response.toString());
		return new JSONObject(response).toString();
		
	}
	
	 	
	
}
