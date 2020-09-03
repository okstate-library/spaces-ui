package com.okstatelibrary.spacesui.services;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okstatelibrary.spacesui.models.AccessToken;

@Service
public class AccessTokenService {

	private RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();

	public AccessToken getAccessToken(String url, String clientID, String clientSecret)
			throws RestClientException, JSONException, JsonParseException, JsonMappingException, IOException {

		MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
		parametersMap.set("client_id", clientID);
		parametersMap.set("client_secret", clientSecret);
		parametersMap.set("grant_type", "client_credentials");
		AccessToken accesstoken = mapper.readValue(restTemplate.postForObject(url, parametersMap, String.class),
				new TypeReference<AccessToken>() {
				});
		return accesstoken;
	}

}
